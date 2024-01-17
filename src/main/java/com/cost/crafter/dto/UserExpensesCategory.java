package com.cost.crafter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserExpensesCategory {

    private Integer expensesCategoryId;
    private Integer userId;
    private String name;
    private String description;

    public UserExpensesCategory(Integer userId, String name, String description) {
        this.userId = userId;
        this.name = name;
        this.description = description;
    }
}
