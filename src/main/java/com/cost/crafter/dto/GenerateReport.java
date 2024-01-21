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

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public void setActualExpense(double actualExpense) {
        this.actualExpense = actualExpense;
    }

}
