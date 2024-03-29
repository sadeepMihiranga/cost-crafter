package com.cost.crafter.service;

import com.cost.crafter.dal.UserExpensesCategoryRepository;
import com.cost.crafter.dto.ExpensesCategory;
import com.cost.crafter.dto.UserExpensesCategory;

import java.util.List;

public class UserExpensesCategoryService {

    private ExpensesCategoryService expensesCategoryService = null;

    private UserExpensesCategoryRepository userExpensesCategoryRepository = null;

    public void syncDefaultCategories(Integer userId) throws Exception {
        if (userId == null) {
            throw new Exception("User id is required");
        }
        try {
            // fetch default expenses categories
            expensesCategoryService = new ExpensesCategoryService();
            List<ExpensesCategory> expensesCategories = expensesCategoryService.fetchDefaultExpensesCategories();
            // insert into user's expenses categories
            for (ExpensesCategory category : expensesCategories) {
                insertUserExpensesCategory(new UserExpensesCategory(userId, category.getName(), category.getDescription()));
            }
        } catch (Exception e) {
            throw new Exception("Error while syncing default expenses categories");
        } finally {
            expensesCategoryService = null;
        }
    }

    public void insertUserExpensesCategory(UserExpensesCategory userExpensesCategory) throws Exception {
        try {
            userExpensesCategoryRepository = new UserExpensesCategoryRepository();
            userExpensesCategoryRepository.insertUserExpensesCategory(userExpensesCategory);
        } catch (Exception e) {
            throw new Exception("Error while inserting user expenses categories");
        } finally {
            userExpensesCategoryRepository = null;
        }
    }

    public List<UserExpensesCategory> fetchAllCategories(Integer userId) throws Exception {
        if (userId == null) {
            throw new Exception("User id is required");
        }
        try {
            userExpensesCategoryRepository = new UserExpensesCategoryRepository();
            return userExpensesCategoryRepository.fetchAllCategories(userId);
        } catch (Exception e) {
            throw new Exception("Error while fetching user expenses categories");
        } finally {
            userExpensesCategoryRepository = null;
        }

    }

    public UserExpensesCategory fetchUserExpensesCategory(UserExpensesCategory existingCategory) {
        try {
            userExpensesCategoryRepository = new UserExpensesCategoryRepository();
            return userExpensesCategoryRepository.fetchUserExpensesCategory(existingCategory);
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        } finally {
            userExpensesCategoryRepository = null;
        }
    }

    public void updateUserExpensesCategory(UserExpensesCategory existingCategory) throws Exception {
        try {
            userExpensesCategoryRepository = new UserExpensesCategoryRepository();
            userExpensesCategoryRepository.updateUserExpensesCategory(existingCategory);
        } catch (Exception e) {
            throw new Exception("Error while updating user expenses category");
        } finally {
            userExpensesCategoryRepository = null;
        }
    }

    public void deleteUserExpensesCategory(UserExpensesCategory categoryToDelete) throws Exception {
        try {
            // Check if the category exists before attempting to delete
            UserExpensesCategory existingCategory = fetchUserExpensesCategory(categoryToDelete);

            if (existingCategory != null) {
                userExpensesCategoryRepository = new UserExpensesCategoryRepository();
                userExpensesCategoryRepository.deleteUserExpensesCategory(categoryToDelete);
            } else {
                System.out.println("Category with ID " + categoryToDelete.getExpensesCategoryId() + " does not exist.");
            }
        } catch (Exception e) {
            throw new Exception("Error while deleting user expenses category");
        } finally {
            userExpensesCategoryRepository = null;
        }
    }

}
