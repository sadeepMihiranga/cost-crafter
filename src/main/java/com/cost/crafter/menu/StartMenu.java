package com.cost.crafter.menu;

import com.cost.crafter.dto.User;
import com.cost.crafter.service.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.cost.crafter.util.FontColors.*;

public class StartMenu extends BaseMenuHandler {

    public void showStartMenu() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            showMenuHeader("\nCost Crafter\n");
            int selectedOption = 0;
            do {
                System.out.println("-------------------------------------\n");
                System.out.println("1 - Register");
                System.out.println("2 - Login");
                System.out.println("3 - Exit");
                System.out.println("\n-------------------------------------\n");
                System.out.print("Select an option : ");

                switch (intSelectedOption(br, 4)) {
                    case 1 -> showRegisterUserMenu();
                    case 2 -> showLogin();
                    case 3 -> exit();
                    default -> showErrorMessage("Invalid option ! Please try again.");
                }
            } while (selectedOption != 3);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void showRegisterUserMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        UserService userService = null;
        try {
            User user = new User();
            System.out.println("\nUser Registration\n");
            System.out.println("-------------------------------------\n");

            System.out.print("Enter username : ");
            user.setUsername(br.readLine());

            System.out.print("Enter password : ");
            user.setPassword(br.readLine());

            System.out.print("First name : ");
            user.setFirstName(br.readLine());

            System.out.print("Last name : ");
            user.setLastName(br.readLine());

            System.out.print("Email : ");
            user.setEmail(br.readLine());

            System.out.print("Date of birth (yyyy-MM-dd) : ");
            user.setDateOfBirth(br.readLine());

            userService = new UserService();
            if (userService.registerUser(user)) {
                showSuccessMessage("\nUser registered successfully !\n");
            }
        } catch (Exception exception) {
            showErrorMessage("\nError while registering user");
            showRegisterUserMenu();
        } finally {
            userService = null;
        }
    }

    private void showLogin() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        MainMenu mainMenu = null;
        try {
            showMenuHeader("\nLogin Page\n");
            System.out.println("-------------------------------------\n");
            System.out.print("Enter username : ");
            String username = br.readLine();
            System.out.print("Enter password : ");
            String password = br.readLine();

            UserService userService = new UserService();
            User user = userService.login(username, password);

            if (user == null) {
                handleInvalidLogin();
            } else {
                setLoggedUser(user);
                mainMenu = new MainMenu();
                mainMenu.showMainMenu();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            showErrorMessage("\nError while login user");
        } finally {
            mainMenu = null;
        }
    }

    private void handleInvalidLogin() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            int selectedOption = 0;
            do {
                System.out.println(ANSI_RED + "\nInvalid Login Credentials\n" + ANSI_RESET);
                System.out.println("-------------------------------------\n");

                System.out.println("-------------------------------------\n");
                System.out.println("1 - Try Login Again");
                System.out.println("2 - Main Menu");
                System.out.println("3 - Exit");
                System.out.println("\n-------------------------------------\n");
                System.out.print("Select an option : ");

                switch (intSelectedOption(br, 4)) {
                    case 1 -> showLogin();
                    case 2 -> showStartMenu();
                    case 3 -> exit();
                    default -> showErrorMessage("Invalid option ! Please try again.");
                }
            } while (selectedOption != 3);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
