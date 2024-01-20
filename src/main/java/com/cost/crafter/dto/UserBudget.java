package com.cost.crafter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserBudget {

    private Integer userBudgetId;
    private Integer userId;
    private Integer expenseCategoryId;
    private String month;
    private Double budgetAmount;

    // not a data entity
    private String expenseCategoryName;

    public UserBudget(Integer userId, Integer expenseCategoryId, String month, Double budgetAmount) {
        this.userId = userId;
        this.expenseCategoryId = expenseCategoryId;
        this.month = month;
        this.budgetAmount = budgetAmount;
    }
}
