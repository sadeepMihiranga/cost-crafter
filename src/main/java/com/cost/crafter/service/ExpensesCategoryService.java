package com.cost.crafter.service;

import com.cost.crafter.dal.ExpensesCategoryRepository;
import com.cost.crafter.dto.ExpensesCategory;

import java.util.List;

public class ExpensesCategoryService {

    private  ExpensesCategoryRepository expensesCategoryRepository = null;

    public List<ExpensesCategory> fetchDefaultExpensesCategories() throws Exception {
        try {
            expensesCategoryRepository = new ExpensesCategoryRepository();
            return expensesCategoryRepository.fetchDefaultExpensesCategories();
        } catch (Exception e) {
//            e.printStackTrace();
            throw new Exception("Error while fetching default expenses categories");
        } finally {
            expensesCategoryRepository = null;
        }
    }
}
