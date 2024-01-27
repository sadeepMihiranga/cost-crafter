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
            throw new Exception("Error while inserting expense categories");
        }
    }
    public List<UserExpensesCategory> fetchAllCategories(Integer userId) throws Exception {
        try {
            final String insertQuery = "SELECT * FROM user_expenses_categories WHERE user_id = ?";
            Object[] values = {userId};
            return read(insertQuery, new UserExpensesCategoryMapper(), values);
        } catch (Exception e) {
            throw new Exception("Error while fetching all expense categories");
        }
    }

    public void deleteUserExpensesCategory(UserExpensesCategory categoryToDelete) throws Exception {
        try {
            final String deleteQuery = "DELETE FROM user_expenses_categories WHERE user_id = ? AND expenses_category_id = ?";
            Object[] values = {categoryToDelete.getUserId(), categoryToDelete.getExpensesCategoryId()};
            delete(deleteQuery, values);
        } catch (Exception e) {
            throw new Exception("Error while deleting user expenses category");
        }
    }

    public void updateUserExpensesCategory(UserExpensesCategory existingCategory) throws Exception {
        try {
            final String updateQuery = "UPDATE user_expenses_categories SET name = ?, description = ? " +
                    "WHERE user_id = ? AND expenses_category_id = ?";
            Object[] values = {existingCategory.getName(), existingCategory.getDescription(),
                    existingCategory.getUserId(), existingCategory.getExpensesCategoryId()};
            update(updateQuery, values);
        } catch (Exception e) {
            throw new Exception("Error while updating user expenses category");
        }
    }

    public UserExpensesCategory fetchUserExpensesCategory(UserExpensesCategory existingCategory) throws Exception {
        try {
            final String selectQuery = "SELECT * FROM user_expenses_categories WHERE user_id = ? AND expenses_category_id = ?";
            Object[] values = { existingCategory.getUserId(), existingCategory.getExpensesCategoryId() };

            return readOne(selectQuery, new UserExpensesCategoryMapper(), values);
        } catch (Exception e) {
            throw new Exception("Error while fetching user expenses category");
        }
    }

}
