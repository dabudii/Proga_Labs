package Collection;

/**
 * Class of collection's objects.
 */
public class LabWork implements Comparable<LabWork>{
    private static long idAuto = 1;
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float minimalPoint; //Поле не может быть null, Значение поля должно быть больше 0
    private Difficulty difficulty; //Поле может быть null
    private Discipline discipline; //Поле не может быть null

    /**
     * Constructor of the class.
     * @param id is the ID of the lab.
     * @param name is the name of the lab.
     * @param coordinates are the coordinates of the lab.
     * @param creationDate is the creation date of the lab.
     * @param minimalPoint is the minimal point of the lab.
     * @param difficulty is the difficulty of the lab.
     * @param discipline is the discipline of the lab.
     */
    public LabWork(long id, String name, Coordinates coordinates, java.time.LocalDateTime creationDate, Float minimalPoint, Difficulty difficulty, Discipline discipline){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.minimalPoint = minimalPoint;
        this.difficulty = difficulty;
        this.discipline = discipline;
    }

    /**
     * Constructor of the class.
     * @param name is the name of the lab.
     * @param coordinates are the coordinates of the lab.
     * @param creationDate is the creation date of the lab.
     * @param minimalPoint is the minimal point of the lab.
     * @param difficulty is the difficulty of the lab.
     * @param discipline is the discipline of the lab.
     */
    public LabWork(String name, Coordinates coordinates, java.time.LocalDateTime creationDate, Float minimalPoint, Difficulty difficulty, Discipline discipline){
        this.id = idAuto++;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.minimalPoint = minimalPoint;
        this.difficulty = difficulty;
        this.discipline = discipline;
    }

    /**
     * @return ID of the lab.
     */
    public long getId() {
        return id;
    }

    /**
     * @return Name of the lab.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return Coordinates of the lab.
     */
    public Coordinates getCoordinates()
    {
        return coordinates;
    }

    /**
     * @return Creadtion date of the lab.
     */
    public java.time.LocalDateTime getCreationDate()
    {
        return creationDate;
    }

    /**
     * @return Minimal point of the lab.
     */
    public Float getMinimalPoint()
    {
        return minimalPoint;
    }

    /**
     **@return Difficulty of the lab.
     */
    public Difficulty getDifficulty()
    {
        return difficulty;
    }

    /**
     * @return Discipline of the lab.
     */
    public Discipline getDiscipline()
    {
        return discipline;
    }

    /**
     * @param name of the lab.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * @param x coordinate of the lab.
     */
    public void setCoordinateX(int x){
        this.getCoordinates().setX(x);
    }

    /**
     * @param y coordinate of the lab.
     */
    public void setCoordinateY(float y){
        this.getCoordinates().setY(y);
    }

    /**
     * @param minimalPoint of the lab.
     */
    public void setMinimalPoint(Float minimalPoint){
        this.minimalPoint = minimalPoint;
    }

    /**
     * @param difficulty of the lab.
     */
    public void setDifficulty(Difficulty difficulty){
        this.difficulty = difficulty;
    }

    /**
     * @param discipline of the lab.
     */
    public void setDiscipline(Discipline discipline){
        this.discipline = discipline;
    }

    /**
     * @return Format output info of the lab.
     */
    public String toString() {
        String info = "";
        info += "Лабораторная работа №" + id;
        info += " (добавлен " + creationDate.toLocalDate() + " " + creationDate.toLocalTime() + ")";
        info += "\n Имя: " + name;
        info += "\n Местоположение: " + coordinates;
        info += "\n Минимальное количество баллов: " + minimalPoint;
        info += "\n Сложность: " + difficulty;
        info += discipline;
        return info;
    }

    /**
     * @param obj The object to equal to.
     * @return The equals return true if objects are equal to each other.
     */
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        else if(obj instanceof LabWork){
            LabWork labObj = (LabWork) obj;
            return name.equals(labObj.getName())&&
                    coordinates.equals(labObj.getCoordinates())&&
                    difficulty.equals(labObj.getDifficulty())&&
                    discipline.equals(labObj.getDiscipline())&&
                    (float)minimalPoint==labObj.getMinimalPoint();
        }
        return false;
    }

    /**
     * @param labObj The object to compare to.
     * @return
     */
    public int compareTo(LabWork labObj){
        return (int)getId() - (int)labObj.getId();
    }

}
