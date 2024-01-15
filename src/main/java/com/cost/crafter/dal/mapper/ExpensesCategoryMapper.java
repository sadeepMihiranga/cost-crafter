package com.cost.crafter.dal.mapper;

import com.cost.crafter.dal.BaseDAL;
import com.cost.crafter.dal.dao.ExpensesCategoryDao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpensesCategoryMapper implements BaseDAL.RowMapper<ExpensesCategoryDao> {

    @Override
    public ExpensesCategoryDao mapRow(ResultSet resultSet) throws SQLException {

        ExpensesCategoryDao expensesCategoryDao = new ExpensesCategoryDao();
        expensesCategoryDao.setId(resultSet.getLong("category_id"));
        expensesCategoryDao.setName(resultSet.getString("name"));
        expensesCategoryDao.setDescription(resultSet.getString("description"));

        return expensesCategoryDao;
    }
}
