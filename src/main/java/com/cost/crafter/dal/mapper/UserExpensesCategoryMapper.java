package com.cost.crafter.dal.mapper;

import com.cost.crafter.dal.BaseRepository;
import com.cost.crafter.dto.UserExpensesCategory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserExpensesCategoryMapper implements BaseRepository.RowMapper<UserExpensesCategory> {

    @Override
    public UserExpensesCategory mapRow(ResultSet resultSet) throws SQLException {

        UserExpensesCategory userExpensesCategory = new UserExpensesCategory();
        userExpensesCategory.setUserId(resultSet.getInt("user_id"));
        userExpensesCategory.setExpensesCategoryId(resultSet.getInt("expenses_category_id"));
        userExpensesCategory.setName(resultSet.getString("name"));
        userExpensesCategory.setDescription(resultSet.getString("description"));
        return userExpensesCategory;
    }
}
