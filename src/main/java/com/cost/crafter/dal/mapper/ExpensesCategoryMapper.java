package com.cost.crafter.dal.mapper;

import com.cost.crafter.dal.BaseRepository;
import com.cost.crafter.dto.ExpensesCategory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpensesCategoryMapper implements BaseRepository.RowMapper<ExpensesCategory> {

    @Override
    public ExpensesCategory mapRow(ResultSet resultSet) throws SQLException {

        ExpensesCategory expensesCategory = new ExpensesCategory();
        expensesCategory.setId(resultSet.getLong("category_id"));
        expensesCategory.setName(resultSet.getString("name"));
        expensesCategory.setDescription(resultSet.getString("description"));

        return expensesCategory;
    }
}
