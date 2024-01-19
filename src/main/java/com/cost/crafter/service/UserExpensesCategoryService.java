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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            throw new Exception("Error while fetching user expenses categories");
        } finally {
            userExpensesCategoryRepository = null;
        }

    }
}
