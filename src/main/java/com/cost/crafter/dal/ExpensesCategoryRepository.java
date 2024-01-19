package com.cost.crafter.dal;

import com.cost.crafter.config.DbConnectionManager;
import com.cost.crafter.dal.mapper.ExpensesCategoryMapper;
import com.cost.crafter.dto.ExpensesCategory;

import java.sql.SQLException;
import java.util.List;

public class ExpensesCategoryRepository {

    public List<ExpensesCategory> fetchDefaultExpensesCategories() throws Exception {
        DbConnectionManager connection = null;
        BaseRepository baseRepository = null;
        try {
            connection = DbConnectionManager.getInstance();
            baseRepository = new BaseRepository(connection);

            final String readQuery = "SELECT * FROM expenses_categories";

            List<ExpensesCategory> expensesCategoryList = baseRepository.read(readQuery, new ExpensesCategoryMapper(),
                    null);
            return expensesCategoryList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error while executing SQL");
        }
    }
}
