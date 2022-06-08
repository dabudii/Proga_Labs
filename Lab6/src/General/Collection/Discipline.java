package General.Collection;

import java.io.Serializable;

/**
 * Discipline - class that includes name of discipline and lecture hours.
 */
public class Discipline implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Integer lectureHours; //Поле может быть null

    /**
     * Constructor of the class.
     * @param name Name of the discipline.
     * @param lectureHours Lecture hours of the discipline.
     */
    public Discipline(String name, Integer lectureHours){
        this.name = name;
        this.lectureHours = lectureHours;
    }

    /**
     * @return Name of the discipline.
     */
    public String getName(){
        return name;
    }

    /**
     * @return Lecture hours of the discipline.
     */
    public Integer getLectureHours(){
        return lectureHours;
    }

    /**
     * @param name Name of the discipline.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * @param lectureHours Lecture hours of the discipline.
     */
    public void setLectureHours(Integer lectureHours){
        this.lectureHours = lectureHours;
    }

    /**
     * @return Format output info of the coordinates.
     */
    public String toString() {
        String info = "";
        info += "\n Название дисциплины: " + name;
        info += "\n Количество лекционных часов: " + lectureHours;
        return info;
    }

    /**
     * @param obj The object to equal to.
     * @return The equals return true if objects are equal to each other.
     */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Discipline) {
            Discipline disciplineObj = (Discipline) obj;
            return name.equals(disciplineObj.getName()) && (int)lectureHours==disciplineObj.getLectureHours();
        }
        return false;
    }
}
