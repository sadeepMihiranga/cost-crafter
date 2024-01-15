package com.cost.crafter;

import com.cost.crafter.config.DbConnectionManager;
import com.cost.crafter.dal.BaseDAL;
import com.cost.crafter.dal.dao.ExpensesCategoryDao;
import com.cost.crafter.dal.mapper.ExpensesCategoryMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        System.out.println("Cost Crafter");

        try {
            DbConnectionManager connection = DbConnectionManager.getInstance();
            BaseDAL baseDAL = new BaseDAL(connection);

            String insertQuery = "INSERT INTO transaction_categories (name, description) VALUES (?, ?)";
            Object[] values = {"Fuel_"+ UUID.randomUUID(), "Description"};
            baseDAL.create(insertQuery, values);

            String readQuery = "SELECT category_id, name, description FROM transaction_categories";
            List<ExpensesCategoryDao> expensesCategoryDaoList = baseDAL.read(readQuery, new ExpensesCategoryMapper(), null);

            System.out.println(expensesCategoryDaoList.size());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}