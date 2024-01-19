package com.cost.crafter.menu;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BudgetAllocationMenu extends BaseMenuHandler {

    public void showManageBudgetsMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("\nBudget Allocation Menu\n");
            int selectedOption = 0;
            do {
                System.out.println("-------------------------------------\n");
                System.out.println("1 - View budget allocations");
                System.out.println("2 - Allocate budget");
                System.out.println("3 - Update budget allocations");
                System.out.println("4 - Main menu");
                System.out.println("5 - Exit");
                System.out.println("\n-------------------------------------\n");
                System.out.print("Select an option : ");

                switch (checkSelectedOption(br, 6)) {
                    case 1 -> showBudgetAllocation();
                    case 2 -> showAllocateBudgetMenu();
                    case 3 -> updateBudgetAllocation();
                    case 4 -> goToMainMenu();
                    case 5 -> exit();
                    default -> System.out.println("Invalid option ! Please try again.");
                }
            } while (selectedOption != 5);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void showAllocateBudgetMenu() {
        System.out.println(loggedUser().getUsername());
    }

    private void showBudgetAllocation() {
    }

    private void updateBudgetAllocation() {
    }
}
