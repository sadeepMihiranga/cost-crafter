package com.cost.crafter.menu;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainMenu extends BaseMenuHandler {

    public void showMainMenu() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("\nMain Menu\n");
            int selectedOption = 0;
            do {
                System.out.println("-------------------------------------\n");
                System.out.println("1 - Manage expenses categories");
                System.out.println("2 - Manage budgets");
                System.out.println("3 - Manage incomes");
                System.out.println("4 - Manage expenses");
                System.out.println("\n-------------------------------------\n");
                System.out.print("Select an option : ");

                switch (checkSelectedOption(br, 6)) {
                    case 1 -> showManageExpensesCategoriesMenu();
                    case 2 -> showManageBudgetsMenu();
                    case 3 -> showManageExpensesCategoriesMenu();
                    case 4 -> showManageExpensesCategoriesMenu();
                    case 5 -> exit();
                    default -> System.out.println("Invalid option ! Please try again.");
                }
            } while (selectedOption != 5);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    private void showManageExpensesCategoriesMenu() {
        
    }

    private void showManageBudgetsMenu() {

    }
}
