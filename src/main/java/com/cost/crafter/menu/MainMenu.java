package com.cost.crafter.menu;

import com.cost.crafter.dto.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainMenu extends BaseMenuHandler {

    public void showMainMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            showMenuHeader("\nMain Menu\n");
            int selectedOption = 0;
            do {
                System.out.println("-------------------------------------\n");
                System.out.println("1 - Manage expenses categories");
                System.out.println("2 - Manage budgets");
                System.out.println("3 - Manage incomes");
                System.out.println("4 - Manage expenses");
                System.out.println("5 - Logout");
                System.out.println("6 - Exit");
                System.out.println("\n-------------------------------------\n");
                System.out.print("Select an option : ");

                switch (intSelectedOption(br, 6)) {
                    case 1 -> showManageExpensesCategoriesMenu();
                    case 2 -> showManageBudgetsMenu();
                    case 3 -> showManageIncomesMenu();
                    case 4 -> showManageExpensesMenu();
                    case 5 -> logout();
                    case 6 -> exit();
                    default -> showErrorMessage("Invalid option ! Please try again.");
                }
            } while (selectedOption != 5);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    private void showManageExpensesCategoriesMenu() {
        ExpensesCategoriesMenu expensesCategoriesMenu = new ExpensesCategoriesMenu();
        expensesCategoriesMenu.showManageExpensesCategoriesMenu();
    }

    private void showManageBudgetsMenu() {
        BudgetAllocationMenu budgetAllocationMenu = new BudgetAllocationMenu();
        budgetAllocationMenu.showManageBudgetsMenu();
    }

    private void showManageIncomesMenu() {
        IncomeMenu incomeMenu = new IncomeMenu();
        incomeMenu.showIncomeTransactionsMenu();

    }

    private void showManageExpensesMenu() {

    }
}
