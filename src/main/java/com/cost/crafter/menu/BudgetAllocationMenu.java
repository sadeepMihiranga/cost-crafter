package com.cost.crafter.menu;

import com.cost.crafter.dto.UserExpensesCategory;
import com.cost.crafter.service.UserBudgetService;
import com.cost.crafter.service.UserExpensesCategoryService;
import com.cost.crafter.util.CommonValidatorUtil;
import de.vandermeer.asciitable.AsciiTable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BudgetAllocationMenu extends BaseMenuHandler {

    private UserExpensesCategoryService userExpensesCategoryService = null;

    private UserBudgetService userBudgetService = null;

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

                switch (intSelectedOption(br, 6)) {
                    case 1 -> showBudgetAllocation();
                    case 2 -> showAllocateBudgetMenu();
                    case 3 -> updateBudgetAllocation();
                    case 4 -> goToMainMenu();
                    case 5 -> exit();
                    default -> showErrorMessage("Invalid option ! Please try again.");
                }
            } while (selectedOption != 5);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void showAllocateBudgetMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Integer> categoryIdList = null;
        AsciiTable asciiTable = null;
        try {
            userExpensesCategoryService = new UserExpensesCategoryService();
            List<UserExpensesCategory> userExpensesCategories = userExpensesCategoryService
                    .fetchAllCategories(loggedUser().getUserId());

            int maxCategoryId = 0;
            categoryIdList = new ArrayList<>();
            int selectedOption = 0;
            int exitOptionId = 0;

            asciiTable = initTable("Category Id", "Name", "Description");
            for (UserExpensesCategory category : userExpensesCategories) {
                addTableRow(asciiTable, category.getExpensesCategoryId(), category.getName(), category.getDescription());

                if (maxCategoryId < category.getExpensesCategoryId()) {
                    maxCategoryId = category.getExpensesCategoryId();
                }
                categoryIdList.add(category.getExpensesCategoryId());
            }
            final String renderedTable = asciiTable.render();

            do {
                System.out.println("\nChoose a expenses category,\n");
                System.out.println(renderedTable);
                System.out.println("\nOr choose a menu option");

                final int mainMenuOptionId = maxCategoryId + 1;
                exitOptionId = maxCategoryId + 2;

                System.out.println("-------------------------------------\n");
                System.out.println(String.format("%s - Main menu", mainMenuOptionId));
                System.out.println(String.format("%s - Exit", exitOptionId));
                System.out.println("\n-------------------------------------\n");
                System.out.print("Select an option : ");
                selectedOption = intSelectedOption(br, exitOptionId+1);

                if (categoryIdList.contains(selectedOption)) {
                    allocateBudget(selectedOption);
                } else if (selectedOption == mainMenuOptionId) {
                    goToMainMenu();
                } else if (selectedOption == exitOptionId) {
                    exit();
                } else {
                    showErrorMessage("Invalid option ! Please try again.");
                }
            } while (selectedOption != exitOptionId);

        } catch (Exception exception) {
            exception.printStackTrace();
            showErrorMessage("\nError while allocating budget");
        } finally {
            userExpensesCategoryService = null;
            categoryIdList = null;
            asciiTable = null;
        }
    }

    private void allocateBudget(Integer expensesCategoryId) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("\nEnter a month to allocate the budget. Ex: 2024-02");
            System.out.print(": ");
            final String month = br.readLine();

            if (!CommonValidatorUtil.isBudgetMonthValid(month)) {
                showErrorMessage("Invalid month ! Please try again.");
                allocateBudget(expensesCategoryId);
            }

            // TODO : only future month validation

            // TODO : only one budget for one month validation

            System.out.println("\nEnter budget amount : ");
            final double amount = doubleSelectedOption(br, -1);

            if (amount < 0) {
                showErrorMessage("Invalid amount ! Please try again.");
                allocateBudget(expensesCategoryId);
            }

            userBudgetService = new UserBudgetService();
            boolean isAllocated = userBudgetService.allocateBudget(loggedUser().getUserId(), expensesCategoryId, month, amount);

            if (!isAllocated) {
                allocateBudget(expensesCategoryId);
            }
            System.out.println("Budget allocated successfully");
            showManageBudgetsMenu();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("\nError while allocating budget");
        } finally {
            userBudgetService = null;
        }
    }

    private void showBudgetAllocation() {
    }

    private void updateBudgetAllocation() {
    }
}
