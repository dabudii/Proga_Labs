package client.utility;

import general.exceptions.MustBeNotEmptyException;
import general.exceptions.NotInLimitsException;
import general.utility.Printer;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class SecurityAsker {
    private Scanner userScanner;

    public SecurityAsker(Scanner userScanner){
        this.userScanner = userScanner;
    }

    public String askLogin(){
        String login;
        while(true){
            try{
                Printer.println("Введите логин:");
                login = userScanner.nextLine();
                if(login.equals("")){
                    throw new MustBeNotEmptyException();
                }
                break;
            } catch (NoSuchElementException exception){
                Printer.printerror("Обрнаружен ввод CTRL+D! Срочное завершение программы...");
                Printer.println("Выводов до отключения: 3");
                Printer.println("Выводов до отключения: 2");
                Printer.println("Выводов до отключения: 1");
                System.exit(1);
            } catch (MustBeNotEmptyException exception){
                Printer.printerror("Имя не может быть пустым!");
            } catch (IllegalStateException exception){
                Printer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return login;
    }

    public String askPassword(){
        String password;
        while (true){
            try{
                Printer.println("Введите пароль:");
                password = userScanner.nextLine();
                break;
            } catch (NoSuchElementException exception){
                Printer.printerror("Обрнаружен ввод CTRL+D! Срочное завершение программы...");
                Printer.println("Выводов до отключения: 3");
                Printer.println("Выводов до отключения: 2");
                Printer.println("Выводов до отключения: 1");
                System.exit(1);
            } catch (IllegalStateException exception){
                Printer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return password;
    }

    public boolean ask(String question){
        String answer = "";
        String finalQuestion = question + " (+/-):";
        while(true){
            try{
                Printer.println(finalQuestion);
                answer = userScanner.nextLine().trim();
                if(!answer.equals("+")&&!answer.equals("-")){
                    throw new NotInLimitsException();
                }
                break;
            } catch (NoSuchElementException exception){
                Printer.printerror("Обрнаружен ввод CTRL+D! Срочное завершение программы...");
                Printer.println("Выводов до отключения: 3");
                Printer.println("Выводов до отключения: 2");
                Printer.println("Выводов до отключения: 1");
                System.exit(1);
            } catch (NotInLimitsException exception){
                Printer.printerror("Ответ должен быть представлен в виде '+' или '-'!");
            } catch (IllegalStateException exception){
                Printer.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return answer.equals("+");
    }
}
