package com.cost.crafter.dal.mapper;

import com.cost.crafter.dal.BaseDAL;
import com.cost.crafter.dto.ExpensesCategory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpensesCategoryMapper implements BaseDAL.RowMapper<ExpensesCategory> {

    @Override
    public ExpensesCategory mapRow(ResultSet resultSet) throws SQLException {

        ExpensesCategory expensesCategory = new ExpensesCategory();
        expensesCategory.setId(resultSet.getLong("category_id"));
        expensesCategory.setName(resultSet.getString("name"));
        expensesCategory.setDescription(resultSet.getString("description"));

        return expensesCategory;
    }
}
