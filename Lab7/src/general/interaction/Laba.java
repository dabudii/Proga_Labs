package general.interaction;

import general.collection.*;

import java.io.Serializable;

public class Laba implements Serializable{
        private String name; //Поле не может быть null, Строка не может быть пустой
        private Coordinates coordinates; //Поле не может быть null
        private Float minimalPoint; //Поле не может быть null, Значение поля должно быть больше 0
        private Difficulty difficulty; //Поле может быть null
        private Discipline discipline; //Поле не может быть null

        /**
         * Constructor of the class.
         * @param name is the name of the lab.
         * @param coordinates are the coordinates of the lab.
         * @param minimalPoint is the minimal point of the lab.
         * @param difficulty is the difficulty of the lab.
         * @param discipline is the discipline of the lab.
         */
        public Laba(String name, Coordinates coordinates, Float minimalPoint, Difficulty difficulty, Discipline discipline){
            this.name = name;
            this.coordinates = coordinates;
            this.minimalPoint = minimalPoint;
            this.difficulty = difficulty;
            this.discipline = discipline;
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
         * @return Format output info of the lab.
         */
        public String toString() {
            String info = "";
            info += "Заготовка:Лабораторная работа";
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

}
