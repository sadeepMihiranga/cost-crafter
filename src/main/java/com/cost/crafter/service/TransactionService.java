package com.cost.crafter.service;

import com.cost.crafter.dal.TransactionRepository;
import com.cost.crafter.dto.Transaction;
import com.cost.crafter.enums.TransactionType;
import com.mysql.cj.util.StringUtils;

import java.util.Date;
import java.util.List;

import static com.cost.crafter.util.FontColors.ANSI_RED;
import static com.cost.crafter.util.FontColors.ANSI_RESET;

public class TransactionService {

    private TransactionRepository  transactionRepository = null;

    public boolean addIncomeTransaction (Integer userId, Double transactionAmount, String description) throws Exception{
        System.out.println("\n Adding Income...");

        //input validations for income entry
        if (userId == null) {
            System.out.println(ANSI_RED + "\nUser id is required" + ANSI_RESET);
            return false;
        }
        if (StringUtils.isNullOrEmpty(description)) {
            System.out.println(ANSI_RED + "\nDescription is required" + ANSI_RESET);
            return false;
        }
        if (transactionAmount == null) {
            System.out.println(ANSI_RED + "\nIncome Amount is required" + ANSI_RESET);
            return false;
        }

        Date createdDate = new Date();
        transactionRepository = new TransactionRepository();
        transactionRepository.insertTransaction(new Transaction(userId, 0, TransactionType.CREDIT.toString(), transactionAmount, description, createdDate , createdDate, true));
        return true;
    }

    public boolean addExpenseTransaction (Integer userId, Integer expensesCategoryId, Double transactionAmount, String description) throws Exception {
        System.out.println("\n Adding Expense...");

        if (userId == null) {
            System.out.println(ANSI_RED + "\nUser ID is required" + ANSI_RESET);
            return false;
        }
        if (expensesCategoryId == null) {
            System.out.println(ANSI_RED + "\nExpense Category ID is required" + ANSI_RESET);
            return false;
        }
        if (StringUtils.isNullOrEmpty(description)) {
            System.out.println(ANSI_RED + "\nDescription is required" + ANSI_RESET);
            return false;
        }
        if (transactionAmount == null) {
            System.out.println(ANSI_RED + "\nExpense Amount is required" + ANSI_RESET);
            return false;
        }

        Date createdDate = new Date();
        transactionRepository = new TransactionRepository();
        transactionRepository.insertTransaction(new Transaction(userId, expensesCategoryId, TransactionType.DEBIT.toString(), transactionAmount, description, createdDate , createdDate, true));
        return true;
    }

    public List<Transaction> fetchTransactions(Integer userId, String transactionType) throws Exception{
         transactionRepository = new TransactionRepository();
         return transactionRepository.fetchTransactions(userId, transactionType);
    }
}
