package Server.Utility;

import General.Collection.Coordinates;
import General.Collection.Difficulty;
import General.Collection.Discipline;
import General.Collection.LabWork;
import General.Utility.Printer;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TreeSet;

/**
 * Class, that parses data to the collection or gets the data and puts into the collection from the xml file.
 */
public class XMLParser {

    /**
     * @param collection
     * @return String, that keeps data into xml form.
     */
    public String parseToXml(TreeSet<LabWork> collection) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version = \"1.0\"?>\n");
        sb.append("<treeset>\n");
        for (LabWork lab : collection) {
            sb.append("\t<lab>\n");
            sb.append("\t\t<name>").append(lab.getName()).append("</name>");
            sb.append("\n\t\t<coordinate_x>").append(lab.getCoordinates().getX()).append("</coordinate_x>");
            sb.append("\n\t\t<coordinate_y>").append(lab.getCoordinates().getY()).append("</coordinate_y>");
            sb.append("\n\t\t<creation_date>").append(lab.getCreationDate()).append("</creation_date>");
            sb.append("\n\t\t<minimal_point>").append(lab.getMinimalPoint()).append("</minimal_point>");
            sb.append("\n\t\t<difficulty>").append(lab.getDifficulty()).append("</difficulty>");
            try{
                sb.append("\n\t\t<discipline_name>").append(lab.getDiscipline().getName()).append("</discipline_name>");
                sb.append("\n\t\t<lecture_hours>").append(lab.getDiscipline().getLectureHours()).append("</lecture_hours>");
            } catch (NullPointerException ignore){

            }

            sb.append("\n\t</lab>\n");
        }
        sb.append("</treeset>\n");
        return sb.toString();
    }

    /**
     *
     * @param text Data to parse.
     * @return Tree set of labworks.
     * @throws ParserConfigurationException If parsing is running and something is wrong.
     * @throws SAXException Encapsulate a general SAX error or warning.
     * @throws IOException  Signals that an I/O exception of some sort has occurred.
     */
    public TreeSet<LabWork> parseToCollection(InputSource text) throws ParserConfigurationException,SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        XmlHandler handler = new XmlHandler();
        try {
            parser.parse(text, handler);
        } catch (SAXException ignored) {
        }
        return handler.labs;
    }

    private static class XmlHandler extends DefaultHandler {

        private TreeSet<LabWork> labs = new TreeSet<>();

        private String name;

        private Integer x = null;

        private Float y = null;

        private LocalDateTime creationDate = null;

        private Float minimalPoint = null;

        private Difficulty difficulty = null;

        private String  nameDiscipline = null;

        private Integer lectureHours = null;

        private String lastElementName;

        /**
         * @param uri The Namespace URI, or the empty string if the element has no Namespace URI or if Namespace processing is not being performed.
         * @param localName  The local name (without prefix), or the empty string if Namespace processing is not being performed.
         * @param qName      The qualified name (with prefix), or the empty string if qualified names are not available.
         * @param attributes The attributes attached to the element. If there are no attributes, it shall be an empty Attributes object.
         */
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            lastElementName = qName;
        }

        /**
         * @param ch     The characters.
         * @param start  The start position in the character array.
         * @param length The number of characters to use from the character array.
         * @throws ClassCastException бросается, когда мы пытаемся привести значение между тегами к недопустимому типу в поле, имя которого совпадает в названием тега
         */
        public void characters(char[] ch, int start, int length) throws ClassCastException {
            String information = new String(ch, start, length);

            information = information.replace("\n", "").trim();
            try {
                if (!information.isEmpty()) {
                    if (lastElementName.equals("name"))
                        name = information;
                    else if (lastElementName.equals("coordinate_x"))
                        x = Integer.parseInt(information);
                    else if (lastElementName.equals("coordinate_y"))
                        y = Float.parseFloat(information);
                    else if (lastElementName.equals("creation_date"))
                        creationDate = LocalDateTime.parse(information);
                    else if(lastElementName.equals("minimal_point"))
                        minimalPoint = Float.parseFloat(information);
                    else if(lastElementName.equals("difficulty"))
                        difficulty = Difficulty.valueOf(information);
                    else if(lastElementName.equals("discipline_name"))
                        nameDiscipline = information;
                    else if(lastElementName.equals("lecture_hours"))
                        lectureHours = Integer.parseInt(information);
                }
            } catch (IllegalArgumentException ex) {
                System.err.println("Указанной константы перечисляемого типа не существует, либо невозможно преобразование типов");
            }
        }

        /**
         * @param uri       The Namespace URI, or the empty string if the element has no Namespace URI or if Namespace processing is not being performed.
         * @param localName The local name (without prefix), or the empty string if Namespace processing is not being performed.
         * @param qName     The qualified name (with prefix), or the empty string if qualified names are not available.
         */
        public void endElement(String uri, String localName, String qName) {
            if (qName.equals("lab")) {
                if ((name != null && !name.isEmpty()) && (x <= 802) && (minimalPoint != null && minimalPoint > 0) && (nameDiscipline != null && !name.isEmpty())) {

                    Coordinates coordinates = new Coordinates(x);
                    float floatY;
                    if (y != null) {
                        floatY = y;
                        coordinates.setY(floatY);
                    }

                    if (creationDate == null) {
                        String i = Instant.now().toString();
                        creationDate = LocalDateTime.parse(i);
                    }

                    Discipline discipline = new Discipline(nameDiscipline, lectureHours);
                    LabWork lab = new LabWork(name,coordinates,creationDate,minimalPoint,difficulty,discipline);

                    if (minimalPoint != null) {
                        lab.setMinimalPoint(minimalPoint);
                    }

                    if(difficulty != null) {
                        lab.setDifficulty(difficulty);
                    }

                    labs.add(lab);


                } else Printer.printerror("Указаны не все параметры, либо параметры не принадлежат допустимой ОДЗ");

                name = null;
                x = null;
                y = null;
                creationDate = null;
                minimalPoint = null;
                difficulty = null;
                nameDiscipline = null;
                lectureHours = null;
            }
        }
    }
}
