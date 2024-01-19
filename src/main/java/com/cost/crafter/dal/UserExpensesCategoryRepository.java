package com.cost.crafter.dal;

import com.cost.crafter.config.DbConnectionManager;
import com.cost.crafter.dto.UserExpensesCategory;

public class UserExpensesCategoryRepository {

    public void insertUserExpensesCategory(UserExpensesCategory userExpensesCategory) throws Exception {
        DbConnectionManager connection = null;
        BaseRepository baseRepository = null;
        try {
            connection = DbConnectionManager.getInstance();
            baseRepository = new BaseRepository(connection);

            final String insertQuery = "INSERT INTO user_expenses_categories (user_id, name, description) " +
                    "VALUES (?, ?, ?)";

            Object[] values = {userExpensesCategory.getUserId(), userExpensesCategory.getName(),
                    userExpensesCategory.getDescription()};
            baseRepository.create(insertQuery, values);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while executing SQL");
        }
    }
}
