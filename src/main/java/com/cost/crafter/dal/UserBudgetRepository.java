package com.cost.crafter.dal;

import com.cost.crafter.config.DbConnectionManager;
import com.cost.crafter.dto.UserBudget;

import java.sql.SQLException;

public class UserBudgetRepository extends BaseRepository {

    public UserBudgetRepository() throws SQLException {
        super(DbConnectionManager.getInstance());
    }

    public void insertUserBudget(UserBudget userBudget) throws Exception {
        try {
            final String insertQuery = "INSERT INTO user_budget (user_id, expenses_category_id, month, budget_amount) " +
                    "VALUES (?, ?, ?, ?)";
            Object[] values = {userBudget.getUserId(), userBudget.getExpenseCategoryId(), userBudget.getMonth(),
                    userBudget.getBudgetAmount()};
            create(insertQuery, values);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while executing SQL");
        }
    }
}
