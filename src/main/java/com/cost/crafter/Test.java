package com.cost.crafter;

import com.cost.crafter.config.DbConnectionManager;
import com.cost.crafter.dal.BaseDAL;
import com.cost.crafter.dto.ExpensesCategory;
import com.cost.crafter.dal.mapper.ExpensesCategoryMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class Test {

    public static void main(String[] args) {
        try {
            DbConnectionManager connection = DbConnectionManager.getInstance();
            BaseDAL baseDAL = new BaseDAL(connection);

            String insertQuery = "INSERT INTO transaction_categories (name, description) VALUES (?, ?)";
            Object[] values = {"Fuel_"+ UUID.randomUUID(), "Description"};
            baseDAL.create(insertQuery, values);

            String readQuery = "SELECT category_id, name, description FROM transaction_categories";
            List<ExpensesCategory> expensesCategoryList = baseDAL.read(readQuery, new ExpensesCategoryMapper(), (Object) null);

            System.out.println(expensesCategoryList.size());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
