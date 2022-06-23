package client.utility;

import general.collection.*;
import general.exceptions.*;
import general.utility.Printer;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class that asks about lab's values.
 */
public class McPolite {
    private final int MAX_X = 802;
    private final int MIN_MINIMAL_POINT = 0;

    private Scanner userScanner;
    private boolean fileMode;

    /**
     * Constructor of the class.
     */
    public McPolite(Scanner userScanner){
        this.userScanner = userScanner;
        fileMode = false;
    }

    /**
     * Sets Mc Polite to file mode.
     */
    public void setFileMode(){
        fileMode = true;
    }

    /**
     * Sets Mc Polite to user mode.
     */
    public void setUserMode(){
        fileMode = false;
    }

    /**
     * @return Lab's name.
     * @throws WrongInputInScriptException If script is running and something goes wrong.
     */
    public String askName() throws WrongInputInScriptException{
        String name = "";
        while(true){
            try{
                Printer.println("Введите имя:");
                name = userScanner.nextLine().trim();
                if(fileMode){
                    Printer.println(name);
                }
                if(name.equals("")){
                    throw new MustBeNotEmptyException();
                }
                break;
            } catch (NoSuchElementException exception){
                Printer.printerror("Обрнаружен ввод CTRL+D! Срочное завершение программы...");
                Printer.println("Выводов до отключения: 3");
                Printer.println("Выводов до отключения: 2");
                Printer.println("Выводов до отключения: 1");
                if(fileMode)
                {
                    throw new WrongInputInScriptException();
                }
                System.exit(1);
            } catch (MustBeNotEmptyException exception){
                Printer.printerror("Имя не может быть пустым!");
                if(fileMode){
                    throw new WrongInputInScriptException();
                }
            } catch (IllegalStateException exception){
                Printer.printerror("Непредвиденная ошибка!");
            System.exit(0);
            }
        }
        return name;
    }

    /**
     * @return Lab's x coordinate.
     * @throws WrongInputInScriptException If script is running and something goes wrong.
     */
    public double askX() throws WrongInputInScriptException{
        String strX = "";
        Integer x;
        while(true){
            try{
                Printer.println("Введите координату Х:");
                strX = userScanner.nextLine().trim();
                if(fileMode){
                    Printer.println(strX);
                }
                x = Integer.parseInt(strX);
                if(x>MAX_X){
                    throw new NotInLimitsException();
                }
                break;
            } catch (NoSuchElementException exception){
                Printer.printerror("Обрнаружен ввод CTRL+D! Срочное завершение программы...");
                Printer.println("Выводов до отключения: 3");
                Printer.println("Выводов до отключения: 2");
                Printer.println("Выводов до отключения: 1");
                if(fileMode)
                {
                    throw new WrongInputInScriptException();
                }
                System.exit(1);
            } catch (NumberFormatException exception){
                Printer.printerror("Координата Х должна быть прдеставлена числом!");
                if(fileMode){
                    throw new WrongInputInScriptException();
                }
            } catch (IllegalStateException exception){
                Printer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInLimitsException exception){
                Printer.printerror("Координата X не может превышать "+MAX_X+"!");
                if(fileMode){
                    throw new WrongInputInScriptException();
                }
            }
        }
        return x;
    }

    /**
     * @return Lab's y coordinate.
     * @throws WrongInputInScriptException If script is running and something goes wrong.
     */
    public double askY() throws WrongInputInScriptException{
        String strY = "";
        float y;
        while(true){
            try{
                Printer.println("Введите координату Y:");
                strY = userScanner.nextLine().trim();
                if(fileMode){
                    Printer.println(strY);
                }
                y = Float.parseFloat(strY);
                break;
            } catch (NoSuchElementException exception){
                Printer.printerror("Обрнаружен ввод CTRL+D! Срочное завершение программы...");
                Printer.println("Выводов до отключения: 3");
                Printer.println("Выводов до отключения: 2");
                Printer.println("Выводов до отключения: 1");
                if(fileMode)
                {
                    throw new WrongInputInScriptException();
                }
                System.exit(1);
            } catch (NumberFormatException exception){
                Printer.printerror("Координата Y должна быть прдеставлена числом!");
                if(fileMode){
                    throw new WrongInputInScriptException();
                }
            } catch (IllegalStateException exception){
                Printer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }

    /**
     * @return Lab's minimal point.
     * @throws WrongInputInScriptException If script is running and something goes wrong.
     */
    public Float askMinimalPoint() throws WrongInputInScriptException{
        String strMinimalPoint = "";
        Float minimalPoint;
        while(true){
            try{
                Printer.println("Введите минимальное количество баллов за лабораторную:");
                strMinimalPoint = userScanner.nextLine().trim();
                if(fileMode){
                    Printer.println(strMinimalPoint);
                }
                minimalPoint = Float.parseFloat(strMinimalPoint);
                if(minimalPoint<MIN_MINIMAL_POINT){
                    throw new NotInLimitsException();
                }
                break;
            } catch (NoSuchElementException exception){
                Printer.printerror("Обрнаружен ввод CTRL+D! Срочное завершение программы...");
                Printer.println("Выводов до отключения: 3");
                Printer.println("Выводов до отключения: 2");
                Printer.println("Выводов до отключения: 1");
                if(fileMode)
                {
                    throw new WrongInputInScriptException();
                }
                System.exit(1);
            } catch (NumberFormatException exception){
                Printer.printerror("Количество минимальных баллов должно быть представлено числом!");
                if(fileMode){
                    throw new WrongInputInScriptException();
                }
            } catch (IllegalStateException exception){
                Printer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInLimitsException exception){
                Printer.printerror("Минимальное количество баллов должно быть больше "+MIN_MINIMAL_POINT+"!");
                if(fileMode){
                    throw new WrongInputInScriptException();
                }
            }
        }
        return minimalPoint;
    }

    /**
     * @return Lab's coordinates.
     * @throws WrongInputInScriptException If script is running and something goes wrong.
     */
    public Coordinates askCoordinates() throws WrongInputInScriptException{
        int x;
        float y;
        x = (int) askX();
        y = (float) askY();
        return new Coordinates(x,y);
    }

    /**
     * @return Lab's difficulty.
     * @throws WrongInputInScriptException If script is running and something goes wrong.
     */
    public Difficulty askDifficulty() throws WrongInputInScriptException {
        String strDifficulty = "";
        Difficulty difficulty;
        while(true){
            try{
                Printer.println("Сложности : ");
                Printer.println(Difficulty.nameList());
                Printer.println("Введите сложность:");
                strDifficulty = userScanner.nextLine().trim();
                if(fileMode){
                    Printer.println(strDifficulty);
                }
                difficulty = Difficulty.values()[Integer.parseInt(strDifficulty)-1];
                break;
            } catch (NoSuchElementException exception){
                Printer.printerror("Обрнаружен ввод CTRL+D! Срочное завершение программы...");
                Printer.println("Выводов до отключения: 3");
                Printer.println("Выводов до отключения: 2");
                Printer.println("Выводов до отключения: 1");
                if(fileMode)
                {
                    throw new WrongInputInScriptException();
                }
                System.exit(1);
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException exception){
                Printer.printerror("Данной сложности нет в списке!");
                if(fileMode){
                    throw new WrongInputInScriptException();
                }
            } catch (IllegalStateException exception){
                Printer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return difficulty;
    }

    /**
     * @return Lab's discipline's name.
     * @throws WrongInputInScriptException If script is running and something goes wrong.
     */
    public String askDisciplineName() throws WrongInputInScriptException{
        String disciplineName = "";
        while(true){
            try{
                Printer.println("Введите название дисциплины:");
                disciplineName = userScanner.nextLine().trim();
                if(fileMode){
                    Printer.println(disciplineName);
                }
                if(disciplineName.equals("")){
                    throw new MustBeNotEmptyException();
                }
                break;
            } catch (NoSuchElementException exception){
                Printer.printerror("Обрнаружен ввод CTRL+D! Срочное завершение программы...");
                Printer.println("Выводов до отключения: 3");
                Printer.println("Выводов до отключения: 2");
                Printer.println("Выводов до отключения: 1");
                if(fileMode)
                {
                    throw new WrongInputInScriptException();
                }
                System.exit(1);
            } catch (MustBeNotEmptyException exception){
                Printer.printerror("Имя дисциплины не должно быть пустым!");
                if(fileMode){
                    throw new WrongInputInScriptException();
                }
            } catch (IllegalStateException exception){
                Printer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return disciplineName;
    }

    /**
     * @return Lab's discipline's lecture hours.
     * @throws WrongInputInScriptException If script is running and something goes wrong.
     */
    public Integer askDisciplineLectureHours() throws WrongInputInScriptException{
        String disciplineLectureHours = "";
        Integer lectureHours;
        while(true){
            try{
                Printer.println("Введите количество лекционных часов:");
                disciplineLectureHours = userScanner.nextLine().trim();
                if(fileMode){
                    Printer.println(disciplineLectureHours);
                }
                lectureHours = Integer.parseInt(disciplineLectureHours);
                break;
            } catch (NoSuchElementException exception){
                Printer.printerror("Обрнаружен ввод CTRL+D! Срочное завершение программы...");
                Printer.println("Выводов до отключения: 3");
                Printer.println("Выводов до отключения: 2");
                Printer.println("Выводов до отключения: 1");
                if(fileMode)
                {
                    throw new WrongInputInScriptException();
                }
                System.exit(1);
            } catch (NumberFormatException exception){
                Printer.printerror("Количество лекционных часов должно быть представлено числом!");
                if(fileMode){
                    throw new WrongInputInScriptException();
                }
            } catch (IllegalStateException exception){
                Printer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return lectureHours;
    }

    /**
     * @return Lab's discipline.
     * @throws WrongInputInScriptException If script is running and something goes wrong.
     */
    public Discipline askDiscipline() throws WrongInputInScriptException{
        String name;
        Integer lectureHours;
        name = askDisciplineName();
        lectureHours = askDisciplineLectureHours();
        return new Discipline(name, lectureHours);
    }

    /**
     * @param question A question.
     * @return Answer (t/f).
     * @throws WrongInputInScriptException If script is running and something is wrong.
     */
    public boolean ask(String question) throws WrongInputInScriptException{
        String answer = "";
        String finalQuestion = question + " (+/-):";
        while(true){
            try{
                Printer.println(finalQuestion);
                answer = userScanner.nextLine().trim();
                if(fileMode){
                    Printer.println(answer);
                }
                if(!answer.equals("+")&&!answer.equals("-")){
                    throw new NotInLimitsException();
                }
                break;
            } catch (NoSuchElementException exception){
                Printer.printerror("Обрнаружен ввод CTRL+D! Срочное завершение программы...");
                Printer.println("Выводов до отключения: 3");
                Printer.println("Выводов до отключения: 2");
                Printer.println("Выводов до отключения: 1");
                if(fileMode)
                {
                    throw new WrongInputInScriptException();
                }
                System.exit(1);
            } catch (NotInLimitsException exception){
                Printer.printerror("Ответ должен быть представлен в виде '+' или '-'!");
                if(fileMode){
                    throw new WrongInputInScriptException();
                }
            } catch (IllegalStateException exception){
                Printer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return answer.equals("+");
    }

}
