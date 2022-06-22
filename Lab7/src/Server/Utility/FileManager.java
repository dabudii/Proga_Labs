package Server.Utility;

import General.Collection.LabWork;
import General.Utility.Printer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.TreeSet;

/**
 * Class that operates with the file for saving/loading collection.
 */
public class FileManager {
    private String envVariable;

    /**
     * Constructor of the class.
     */
    public FileManager(String envVariable)
    {
        this.envVariable = envVariable.trim();
    }

    /**
     * @param str Collection to write.
     */
    public void writeCollection(String str){
        if(!envVariable.isEmpty()){
            try (FileWriter collectionFileWriter = new FileWriter(envVariable)){
                collectionFileWriter.write(str);
                Printer.println("Успешное сохранение коллекции в файл!");
            } catch (IOException exception){
                Printer.printerror("Загрузочный файл не может быть открыт!");
            }
        } else Printer.printerror("Переменная с загрузочным файлом не найдена!");
    }

    /**
     * @return Readed colletion.
     */
    public TreeSet<LabWork> readCollection(){
        if(!envVariable.isEmpty()) {
            try (FileReader collectionFileReader = new FileReader(envVariable)){
                XMLParser xmlParser = new XMLParser();
                String line = "";
                while(collectionFileReader.ready()){
                    line = line + (char)collectionFileReader.read();
                }
                TreeSet<LabWork> labWorks = xmlParser.parseToCollection(new InputSource(new StringReader(line)));
                Printer.println("Коллекция успнешно загружена!");
                return labWorks;
            } catch (IOException exception){
                Printer.printerror("Файла с таким именем не найдено!");
            } catch (ParserConfigurationException | SAXException exception) {
                Printer.printerror("Непредвиденная ошибка!");
            }
        } else {
            Printer.printerror("Обнаружена пустая строка!");
        }
        return new TreeSet<>();
    }

    public boolean readCollectionProv(){
        if(!envVariable.isEmpty()) {
            try (FileReader collectionFileReader = new FileReader(envVariable)){
                return true;
            } catch (IOException exception){
                Printer.printerror("Файла с таким именем не найдено!");
                return false;
            }
        } else {
            Printer.printerror("Обнаружена пустая строка!");
            return false;
        }
    }
}

