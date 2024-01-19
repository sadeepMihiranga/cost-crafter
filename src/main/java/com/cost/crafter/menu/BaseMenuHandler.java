package com.cost.crafter.menu;

import com.cost.crafter.dto.User;

import java.io.BufferedReader;
import java.io.IOException;

public class BaseMenuHandler {

    private static User loggedUser;

    protected void exit() {
        System.out.println("Thank you !");
        System.exit(0);
    }

    protected int checkSelectedOption(BufferedReader br, int defaultInvalidOption) throws IOException {
        try {
            return Integer.parseInt(br.readLine());
        } catch (NumberFormatException ex) {
            return defaultInvalidOption;
        }
    }

    protected void goToMainMenu() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.showMainMenu();
    }

    protected void logout() {
        setLoggedUser(null);
        StartMenu startMenu = new StartMenu();
        startMenu.showStartMenu();
    }

    protected User loggedUser() {
        return this.loggedUser;
    }

    protected void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}
