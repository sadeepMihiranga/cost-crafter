package com.cost.crafter.dto;

public class GenerateReport {
    private String categoryName;
    private double budgetAmount;
    private double actualExpense;

    public GenerateReport() {
        this.categoryName = categoryName;
        this.budgetAmount = budgetAmount;
        this.actualExpense = actualExpense;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public double getActualExpense() {
        return actualExpense;
    }

    public void setCategoryName(String name) {
    }

    public void setBudgetAmount(double budgetAmount) {
    }

    public void setActualExpense(double actualExpense) {
    }
}
