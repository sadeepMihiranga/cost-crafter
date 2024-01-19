package com.cost.crafter.dal;

import com.cost.crafter.config.DbConnectionManager;
import com.cost.crafter.dal.mapper.UserExpensesCategoryMapper;
import com.cost.crafter.dto.UserExpensesCategory;

import java.sql.SQLException;
import java.util.List;

public class UserExpensesCategoryRepository extends BaseRepository {

    public UserExpensesCategoryRepository() throws SQLException {
        super(DbConnectionManager.getInstance());
    }

    public void insertUserExpensesCategory(UserExpensesCategory userExpensesCategory) throws Exception {
        try {
            final String insertQuery = "INSERT INTO user_expenses_categories (user_id, name, description) " +
                    "VALUES (?, ?, ?)";
            Object[] values = {userExpensesCategory.getUserId(), userExpensesCategory.getName(),
                    userExpensesCategory.getDescription()};
            create(insertQuery, values);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while executing SQL");
        }
    }
    public List<UserExpensesCategory> fetchAllCategories(Integer userId) throws Exception {
        try {
            final String insertQuery = "SELECT * FROM user_expenses_categories WHERE user_id = ?";
            Object[] values = {userId};
            return read(insertQuery, new UserExpensesCategoryMapper(), values);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while executing SQL");
        }
    }
}
