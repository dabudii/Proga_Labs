package client.utility;

import general.interaction.Profile;
import general.interaction.Request;

import java.util.Scanner;

public class SecurityHandler {
    private final String login = "login";
    private final String register = "register";

    private Scanner userScanner;

    public SecurityHandler(Scanner userScanner){
        this.userScanner = userScanner;
    }

    public Request handle(){
        SecurityAsker securityAsker = new SecurityAsker(userScanner);
        String ask = securityAsker.ask("У вас уже есть учетная запись?") ? login : register;
        Profile profile = new Profile(securityAsker.askLogin(),securityAsker.askPassword());
        return new Request(ask, "", profile);
    }
}
