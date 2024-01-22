package com.cost.crafter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Transaction {

    private Integer transactionId;
    private Integer userId;
    private Integer expensesCategoryId;
    private String expensesCategory;
    private String transactionType;
    private Double transactionAmount;
    private String description;
    private Boolean isRecurring;
    private String recurrenceType;
    private Integer recurrenceUpto;
    private Date transactionDate;
    private Date createdDate;
    private Date updatedDate;
    private Boolean status;

    public Transaction(Integer userId, Integer expensesCategoryId, String transactionType, Double transactionAmount,
                       String description, Date transactionDate, Date createdDate, Date updatedDate, Boolean status) {
        this.userId = userId;
        this.expensesCategoryId = expensesCategoryId;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.status = status;
    }
}
