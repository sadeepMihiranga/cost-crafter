package com.cost.crafter.service;

import com.cost.crafter.dal.TransactionRepository;
import com.cost.crafter.dal.UserExpensesCategoryRepository;
import com.cost.crafter.dto.Transaction;
import com.cost.crafter.dto.UserExpensesCategory;
import com.cost.crafter.enums.TransactionType;
import com.mysql.cj.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.cost.crafter.util.FontColors.ANSI_RED;
import static com.cost.crafter.util.FontColors.ANSI_RESET;

public class TransactionService {

    private TransactionRepository  transactionRepository = null;

    public boolean addIncomeTransaction (Integer userId, Double transactionAmount, String description) throws Exception {
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

        // TODO : need to ask for the transaction date if we manage backdated incomes

        Date createdDate = new Date();
        transactionRepository = new TransactionRepository();
        transactionRepository.insertTransaction(new Transaction(userId, 0,
                TransactionType.CREDIT.toString(), transactionAmount, description, createdDate, createdDate ,
                createdDate, true));
        return true;
    }

    public boolean addExpenseTransaction (Integer userId, String transactionDate, Integer expensesCategoryId, Double transactionAmount,
                                          String description) throws Exception {
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

        // TODO : need to ask for the transaction date if manage backdated expenses
        // TODO : if not insert transaction date, then we cannot map budget with expenses. coz there are no attribute
        //        to map month in budget entity with transaction entity

        Date createdDate = new Date();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        transactionRepository = new TransactionRepository();
        transactionRepository.insertTransaction(new Transaction(userId, expensesCategoryId,
                TransactionType.DEBIT.toString(), transactionAmount, description, dateFormatter.parse(transactionDate), createdDate,
                createdDate, true));
        return true;
    }

    public List<Transaction> fetchTransactions(Integer userId, String transactionType) throws Exception{
         transactionRepository = new TransactionRepository();
         return transactionRepository.fetchTransactions(userId, transactionType);
    }

    public Transaction getTransactionById(Integer transactionId) throws Exception {
        transactionRepository = new TransactionRepository();
        return transactionRepository.transactionDetailsById(transactionId);
    }

    public boolean updateTransaction(Transaction transaction) throws Exception {
        Date updatedDate = new Date();
        transaction.setUpdatedDate(updatedDate);
        transactionRepository = new TransactionRepository();
        transactionRepository.updateTransaction(transaction);
        return true;
    }

    public void deleteTransaction(int transactionId) throws Exception {
        try {
            transactionRepository.deleteTransaction((transactionId));
        } catch (Exception e) {
            throw new Exception("Error while deleting user expenses category");
        }
    }
}
