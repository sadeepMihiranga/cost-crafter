package com.cost.crafter.menu;

import com.cost.crafter.dto.User;
import com.cost.crafter.dto.UserExpensesCategory;
import com.cost.crafter.service.UserExpensesCategoryService;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.List;

public class ExpensesCategoriesMenu  extends BaseMenuHandler{

    private UserExpensesCategoryService userExpensesCategoryService = null;

    public void showManageExpensesCategoriesMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("\nExpense Category Menu\n");
            int selectedOption = 0;
            do {
                System.out.println("-------------------------------------\n");
                System.out.println("1 - Create new expense category");
                System.out.println("2 - View all expense categories");
                System.out.println("3 - Update a expense category");
                System.out.println("4 - Delete a expense category");
                System.out.println("5 - Main menu");
                System.out.println("6 - Exit");
                System.out.println("\n-------------------------------------\n");
                System.out.print("Select an option : ");

                switch (intSelectedOption(br, 5)) {
                    case 1 -> createExpenseCategory();
                    case 2 -> viewExpenseCategories();
                    case 3 -> updateExpenseCategory();
                    case 4 -> deleteExpenseCategory();
                    case 5 -> goToMainMenu();
                    case 6 -> exit();
                    default -> showErrorMessage("Invalid option ! Please try again.");
                }
            } while (selectedOption != 5);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void createExpenseCategory() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("\nEnter details for the new expense category:");
            System.out.println("---------------------------------------------");

            System.out.print("Enter category name: ");
            String name = br.readLine();
            if (name.isEmpty()) {
                showErrorMessage("Category name cannot be empty.");
                return;
            }

            System.out.print("Enter category description (Optional): ");
            String description = br.readLine();

            UserExpensesCategory newCategory = new UserExpensesCategory(loggedUser().getUserId(), name, description);

            // Call the service method to insert the new category
            UserExpensesCategoryService userExpensesCategoryService = new UserExpensesCategoryService();
            userExpensesCategoryService.insertUserExpensesCategory(newCategory);

            System.out.println("Expense category created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Error creating expense category");
        }
    }

    public void viewExpenseCategories() {
        try {
            userExpensesCategoryService = new UserExpensesCategoryService();
            List<UserExpensesCategory> categories = userExpensesCategoryService.fetchAllCategories(loggedUser().getUserId());

            if (categories.isEmpty()) {
                System.out.println("No expense categories found.");
                return;
            }

            System.out.println("\nExpense Categories:\n");
            System.out.println("---------------------------");
            for (UserExpensesCategory category : categories) {
                System.out.println("Category ID: " + category.getExpensesCategoryId());
                System.out.println("Name: " + category.getName());
                System.out.println("Description: " + category.getDescription());
                System.out.println("---------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Error fetching expense categories");
        }
    }

    private void updateExpenseCategory() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Enter the id of the category to update: ");
            int categoryId;
            try {
                categoryId = Integer.parseInt(br.readLine());
            } catch (NumberFormatException e) {
                showErrorMessage("Invalid category id. Please enter a valid number!");
                return;
            }

            UserExpensesCategory existingCategory = new UserExpensesCategory(loggedUser().getUserId(), "", "");
            existingCategory.setExpensesCategoryId(categoryId);

            // Fetch the existing category details
            UserExpensesCategoryService userExpensesCategoryService = new UserExpensesCategoryService();
            existingCategory = userExpensesCategoryService.fetchUserExpensesCategory(existingCategory);

            if (existingCategory != null) {
                System.out.println("\nEnter new details for the expense category (press Enter to keep existing values):");

                System.out.print("Enter new category name: ");
                String newName = br.readLine();
                existingCategory.setName(newName.isEmpty() ? existingCategory.getName() : newName);

                System.out.print("Enter new category description: ");
                String newDescription = br.readLine();
                existingCategory.setDescription(newDescription.isEmpty() ? existingCategory.getDescription() : newDescription);

                // Update the category
                userExpensesCategoryService.updateUserExpensesCategory(existingCategory);

                System.out.println("Expense category updated successfully!");
            } else {
                showErrorMessage("Category not found with the given ID");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Error updating expense category");
        }
    }

    private void deleteExpenseCategory() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Enter the ID of the category to delete: ");
            int categoryId;
            try {
                categoryId = Integer.parseInt(br.readLine());
            } catch (NumberFormatException e) {
                showErrorMessage("Invalid category ID. Please enter a valid number.");
                return;
            }

            UserExpensesCategory categoryToDelete = new UserExpensesCategory(loggedUser().getUserId(), "", "");
            categoryToDelete.setExpensesCategoryId(categoryId);

            // Delete the category
            UserExpensesCategoryService userExpensesCategoryService = new UserExpensesCategoryService();
            userExpensesCategoryService.deleteUserExpensesCategory(categoryToDelete);

            System.out.println("Expense category deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Error deleting expense category");
        }
    }
}
