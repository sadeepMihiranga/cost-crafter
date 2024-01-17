package com.cost.crafter.service;

import com.cost.crafter.config.DbConnectionManager;
import com.cost.crafter.dal.BaseDAL;
import com.cost.crafter.dal.mapper.ExpensesCategoryMapper;
import com.cost.crafter.dto.ExpensesCategory;
import com.cost.crafter.dto.UserExpensesCategory;

import java.sql.SQLException;
import java.util.List;

public class ExpensesCategoryService {

    public void transferCategories(Integer userId) throws Exception {
        List<ExpensesCategory> expensesCategories = fetchDefaultExpensesCategories();
        for (ExpensesCategory category : expensesCategories) {
            insertUserExpensesCategory(new UserExpensesCategory(userId, category.getName(), category.getDescription()));
        }
    }

    public void insertUserExpensesCategory(UserExpensesCategory userExpensesCategory) throws Exception {
        DbConnectionManager connection = null;
        BaseDAL baseDAL = null;
        try {
            connection = DbConnectionManager.getInstance();
            baseDAL = new BaseDAL(connection);

            final String insertQuery = "INSERT INTO user_expenses_categories (user_id, name, description) " +
                    "VALUES (?, ?, ?)";
            Object[] values = {userExpensesCategory.getUserId(), userExpensesCategory.getName(),
                    userExpensesCategory.getDescription()};
            baseDAL.create(insertQuery, values);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public List<ExpensesCategory> fetchDefaultExpensesCategories() throws Exception {
        DbConnectionManager connection = null;
        BaseDAL baseDAL = null;
        try {
            connection = DbConnectionManager.getInstance();
            baseDAL = new BaseDAL(connection);

            final String readQuery = "SELECT * FROM expenses_categories";

            List<ExpensesCategory> expensesCategoryList = baseDAL.read(readQuery, new ExpensesCategoryMapper(), null);
            return expensesCategoryList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error while executing SQL");
        }
    }
}
