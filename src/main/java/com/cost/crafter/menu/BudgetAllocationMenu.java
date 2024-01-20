package com.cost.crafter.menu;

import com.cost.crafter.dto.UserBudget;
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
            showMenuHeader("\nBudget Allocation Menu\n");
            int selectedOption = 0;
            do {
                System.out.println("-------------------------------------\n");
                System.out.println("1 - View/Update budget allocations");
                System.out.println("2 - Allocate budget");
                System.out.println("3 - Main menu");
                System.out.println("4 - Exit");
                System.out.println("\n-------------------------------------\n");
                System.out.print("Select an option : ");

                switch (intSelectedOption(br, 5)) {
                    case 1 -> showBudgetAllocations();
                    case 2 -> showAllocateBudgetMenu();
                    case 3 -> goToMainMenu();
                    case 4 -> exit();
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

            asciiTable = initTable("Expense Category Id", "Name", "Description");
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

            userBudgetService = new UserBudgetService();
            UserBudget userBudget = userBudgetService.checkForDuplicateBudgetEntry(loggedUser().getUserId(), month,
                    expensesCategoryId);
            if (userBudget != null) {
                showErrorMessage(String.format("\nSelected expense category and month %s has already allocated budget", month));
                allocateBudget(expensesCategoryId);
            }

            System.out.println("\nEnter budget amount : ");
            final double amount = doubleSelectedOption(br, -1);
            if (amount < 0) {
                showErrorMessage("Invalid amount ! Please try again.");
                allocateBudget(expensesCategoryId);
            }

            final boolean isAllocated = userBudgetService.allocateBudget(loggedUser().getUserId(), expensesCategoryId,
                    month, amount);
            if (!isAllocated) {
                allocateBudget(expensesCategoryId);
            }

            showSuccessMessage("\nBudget allocated successfully");
            showManageBudgetsMenu();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("\nError while allocating budget");
        } finally {
            userBudgetService = null;
        }
    }

    private void showBudgetAllocations() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        AsciiTable asciiTable = null;
        List<Integer> budgetIdList = null;
        try {
            showMenuHeader("\nBudget Allocations\n");
            int selectedOption = 0;
            int maxCategoryId = 0;
            int exitOptionId = 0;
            budgetIdList = new ArrayList<>();

            userBudgetService = new UserBudgetService();
            List<UserBudget> userBudgets = userBudgetService.fetchUserBudgetEntries(loggedUser().getUserId());

            asciiTable = initTable("Budget Id", "Expense Category Id", "Month", "Amount");
            for (UserBudget userBudget : userBudgets) {
                addTableRow(asciiTable, userBudget.getUserBudgetId(), userBudget.getExpenseCategoryId(),
                        userBudget.getMonth(), userBudget.getBudgetAmount());

                if (maxCategoryId < userBudget.getExpenseCategoryId()) {
                    maxCategoryId = userBudget.getExpenseCategoryId();
                }
                budgetIdList.add(userBudget.getUserBudgetId());
            }
            final String renderedTable = asciiTable.render();

            do {
                System.out.println("To update choose a budget by id,\n");
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

                if (budgetIdList.contains(selectedOption)) {
                    /*final int finalSelectedOption = selectedOption;
                    UserBudget selectedUserBudget = userBudgets.stream().filter(userBudget -> userBudget
                            .getUserBudgetId().equals(finalSelectedOption)).findAny().orElse(null);*/
                    updateBudgetAllocation(selectedOption);
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
        } finally {
            asciiTable = null;
            budgetIdList = null;
            userBudgetService = null;
        }
    }

    private void updateBudgetAllocation(Integer userBudgetId) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            showMenuHeader("\nUpdate Budget Allocation\n");
            int selectedOption = 0;

            userBudgetService = new UserBudgetService();
            UserBudget budgetDetails = userBudgetService.getBudgetDetailsById(userBudgetId);

            do {
                System.out.println("Expense category id : " + budgetDetails.getExpenseCategoryId());
                System.out.println("Expense category name : " + budgetDetails.getExpenseCategoryName());
                System.out.println("Month : " + budgetDetails.getMonth());
                System.out.println("Budget amount : " + budgetDetails.getBudgetAmount());

                System.out.println("\nSelect an option :");

                System.out.println("-------------------------------------\n");
                System.out.println(String.format("%s - Update budget amount", 1));
                System.out.println(String.format("%s - Main menu", 2));
                System.out.println(String.format("%s - Exit", 3));
                System.out.println("\n-------------------------------------\n");
                System.out.print("Select an option : ");
                selectedOption = intSelectedOption(br, 4);

                if (selectedOption == 1) {
                    System.out.print("\nEnter new budget amount to update : ");
                    final double newAmount = doubleSelectedOption(br, -1);
                    if (newAmount < 0) {
                        showErrorMessage("Invalid amount ! Please try again.");
                        updateBudgetAllocation(userBudgetId);
                    }

                    final boolean isUpdated = userBudgetService.updateBudgetAllocation(budgetDetails, newAmount);
                    if (!isUpdated) {
                        updateBudgetAllocation(userBudgetId);
                    }
                    showSuccessMessage("\nBudget allocation updated successfully\n");
                } else if (selectedOption == 2) {
                    goToMainMenu();
                } else if (selectedOption == 3) {
                    exit();
                } else {
                    showErrorMessage("Invalid option ! Please try again.");
                }
            } while (selectedOption != 3);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            userBudgetService = null;
        }
    }
}
