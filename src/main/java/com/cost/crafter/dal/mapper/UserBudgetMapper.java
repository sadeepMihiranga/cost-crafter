package com.cost.crafter.dal.mapper;

import com.cost.crafter.dal.BaseRepository;
import com.cost.crafter.dto.UserBudget;
import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBudgetMapper implements BaseRepository.RowMapper<UserBudget> {

    @Override
    public UserBudget mapRow(ResultSet resultSet) throws SQLException {
        UserBudget userBudget = new UserBudget();
        userBudget.setUserBudgetId(resultSet.getInt("user_budget_id"));
        userBudget.setUserId(resultSet.getInt("user_id"));
        userBudget.setExpenseCategoryId(resultSet.getInt("expenses_category_id"));
        userBudget.setMonth(resultSet.getString("month"));
        userBudget.setBudgetAmount(resultSet.getDouble("budget_amount"));

        if (BaseRepository.RowMapper.columnExists(resultSet, "name")) {
            userBudget.setExpenseCategoryName(resultSet.getString("name"));
        }
        return userBudget;
    }


}
