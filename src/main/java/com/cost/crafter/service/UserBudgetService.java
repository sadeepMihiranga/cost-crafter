package com.cost.crafter.service;

import com.cost.crafter.dal.UserBudgetRepository;
import com.cost.crafter.dto.UserBudget;
import com.mysql.cj.util.StringUtils;

import static com.cost.crafter.util.FontColors.ANSI_RED;
import static com.cost.crafter.util.FontColors.ANSI_RESET;

public class UserBudgetService {

    private UserBudgetRepository userBudgetRepository = null;

    public boolean allocateBudget(Integer userId, Integer expensesCategoryId, String month,
                                  Double amount) throws Exception {
        if (expensesCategoryId == null) {
            System.out.println(ANSI_RED + "\nExpense category id is required" + ANSI_RESET);
            return false;
        }
        if (StringUtils.isNullOrEmpty(month)) {
            System.out.println(ANSI_RED + "\nMonth is required" + ANSI_RESET);
            return false;
        }
        if (amount == null) {
            System.out.println(ANSI_RED + "\nAmount is required" + ANSI_RESET);
            return false;
        }

        userBudgetRepository = new UserBudgetRepository();
        userBudgetRepository.insertUserBudget(new UserBudget(userId, expensesCategoryId, month, amount));
        return true;
    }
}
