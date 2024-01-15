package com.cost.crafter.menu;

import com.cost.crafter.dto.User;
import com.cost.crafter.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartMenu {

    public void showStartMenu() {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("\nCost Crafter\n");
            int selectedOption;
            do {
                System.out.println("-------------------------------------\n");
                System.out.println("1 - Register");
                System.out.println("2 - Login");
                System.out.println("3 - Exit");
                System.out.println("\n-------------------------------------\n");
                System.out.print("Select an option : ");

                try {
                    selectedOption = Integer.parseInt(br.readLine());
                } catch (NumberFormatException ex) {
                    selectedOption = 4;
                }

                switch (selectedOption) {
                    case 1 -> showRegisterUserMenu();
                    case 2 -> showLogin();
                    case 3 -> exit();
                    default -> System.out.println("Invalid option ! Please try again.");
                }
            } while (selectedOption != 3);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void showRegisterUserMenu() {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            User user = new User();
            System.out.println("\nUser Registration\n");
            System.out.println("-------------------------------------\n");
            System.out.print("Enter username = ");
            user.setUsername(br.readLine());
            System.out.print("Enter password = ");
            user.setPassword(br.readLine());

            UserService userService = new UserService();
            userService.registerUser(user);

            System.out.println("\nUser registered successfully !\n");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void showLogin() {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("\nLogin Page\n");
            System.out.println("-------------------------------------\n");
            System.out.print("Enter username = ");
            String username = br.readLine();
            System.out.print("Enter password = ");
            String password = br.readLine();

            UserService userService = new UserService();
            User user = userService.login(username, password);

            if (user == null) {
                handleInvalidLogin();
            } else {
                MainMenu mainMenu = new MainMenu();
                mainMenu.showMainMenu();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void handleInvalidLogin() {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int selectedOption;
            do {
                System.out.println("\nInvalid Login Credentials\n");
                System.out.println("-------------------------------------\n");

                System.out.println("-------------------------------------\n");
                System.out.println("1 - Try Login Again");
                System.out.println("2 - Main Menu");
                System.out.println("3 - Exit");
                System.out.println("\n-------------------------------------\n");
                System.out.print("Select an option : ");

                try {
                    selectedOption = Integer.parseInt(br.readLine());
                } catch (NumberFormatException ex) {
                    selectedOption = 4;
                }

                switch (selectedOption) {
                    case 1 -> showLogin();
                    case 2 -> showStartMenu();
                    case 3 -> exit();
                    default -> System.out.println("Invalid option ! Please try again.");
                }
            } while (selectedOption != 3);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void showMainMenu() {

    }

    private void exit() {
        System.out.println("Thank you !");
        System.exit(0);
    }
}
