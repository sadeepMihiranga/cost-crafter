package com.cost.crafter.dal;

import com.cost.crafter.config.DbConnectionManager;
import com.cost.crafter.dal.mapper.TransactionMapper;
import com.cost.crafter.dto.Transaction;

import java.sql.SQLException;
import java.util.List;

public class TransactionRepository extends BaseRepository{

    public TransactionRepository() throws SQLException{
        super(DbConnectionManager.getInstance());
    }

    public int insertTransaction(Transaction transaction) throws Exception{
        try{
            final String insertIncomeQuery = "INSERT INTO transaction (user_id, expenses_category_id, " +
                    "transaction_type, transaction_amount, description, is_recurring, recurrence_type, " +
                    "recurrence_upto, transaction_date, created_date, updated_date, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Object[] values = {transaction.getUserId(), transaction.getExpensesCategoryId() == 0 ? null
                    : transaction.getExpensesCategoryId(), transaction.getTransactionType(),
                    transaction.getTransactionAmount(), transaction.getDescription(),
                    false, null, null, "", transaction.getCreatedDate(), transaction.getUpdatedDate(),
                    transaction.getStatus()};
            return create(insertIncomeQuery, values);
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("Error while executing income transaction insert SQL");
        }
    }

    public List<Transaction> fetchTransactions(Integer userId, String transactionType) throws Exception {
        try {
            final String selectQuery =
                    "SELECT tr.*, ec.name as expenses_category " +
                    "FROM transaction tr " +
                    "LEFT JOIN user_expenses_categories ec ON tr.expenses_category_id = ec.expenses_category_id " +
                    "WHERE tr.user_id = ? AND tr.transaction_type = ?";
            Object[] values = {userId, transactionType};
            return read(selectQuery, new TransactionMapper(), values);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while executing SQL");
        }
    }

}
