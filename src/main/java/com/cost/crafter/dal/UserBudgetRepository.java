package com.cost.crafter.dal;

import com.cost.crafter.config.DbConnectionManager;
import com.cost.crafter.dal.mapper.UserBudgetMapper;
import com.cost.crafter.dto.UserBudget;

import java.sql.SQLException;
import java.util.List;

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

    public UserBudget fetchDuplicateBudgetEntry(Integer userId, String month, Integer expensesCategoryId)
            throws Exception {
        try {
            final String insertQuery = "SELECT * FROM user_budget WHERE user_id = ? AND month = ? AND expenses_category_id = ?";
            Object[] values = {userId, month, expensesCategoryId};
            return readOne(insertQuery, new UserBudgetMapper(), values);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while executing SQL");
        }
    }

    public List<UserBudget> fetchUserBudgetEntries(Integer userId) throws Exception {
        try {
            final String insertQuery = "SELECT * FROM user_budget WHERE user_id = ?";
            Object[] values = {userId};
            return read(insertQuery, new UserBudgetMapper(), values);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while executing SQL");
        }
    }
}
