package com.cost.crafter.dto;

import lombok.Data;

@Data
public class GenerateReport {

    private String categoryName;
    private Double budgetAmount;
    private Double actualExpense;

}
