package com.cost.crafter.menu;

import com.cost.crafter.dto.User;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.io.BufferedReader;
import java.io.IOException;

import static com.cost.crafter.util.FontColors.*;

public class BaseMenuHandler {

    private static User loggedUser;

    protected void exit() {
        System.out.println("Thank you !");
        System.exit(0);
    }

    protected int intSelectedOption(BufferedReader br, int defaultInvalidOption) throws IOException {
        try {
            return Integer.parseInt(br.readLine());
        } catch (NumberFormatException ex) {
            return defaultInvalidOption;
        }
    }

    protected double doubleSelectedOption(BufferedReader br, double defaultInvalidOption) throws IOException {
        try {
            return Double.parseDouble(br.readLine());
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

    protected AsciiTable initTable(Object... columns) {
        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow(columns);
        asciiTable.addRule();

        asciiTable.setTextAlignment(TextAlignment.CENTER);

        return asciiTable;
    }

    protected void addTableRow(AsciiTable asciiTable, Object... dataRow) {
        asciiTable.addRow(dataRow);
        asciiTable.addRule();
    }

    protected void showErrorMessage(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    protected void showMenuHeader(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    protected void showSuccessMessage(String message) {
        System.out.println(ANSI_GREEN + message + ANSI_RESET);
    }
}
