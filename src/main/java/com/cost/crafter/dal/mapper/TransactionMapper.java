package com.cost.crafter.dal.mapper;

import com.cost.crafter.dal.BaseRepository;
import com.cost.crafter.dto.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionMapper implements BaseRepository.RowMapper<Transaction> {

    @Override
    public Transaction mapRow(ResultSet resultSet) throws SQLException{
        Transaction transaction = new Transaction();
        transaction.setTransactionId(resultSet.getInt("transaction_id"));
        transaction.setUserId(resultSet.getInt("user_id"));
        transaction.setExpensesCategoryId(resultSet.getInt("expenses_category_id"));
        transaction.setExpensesCategory(resultSet.getString("expenses_category"));
        transaction.setTransactionType(resultSet.getString("transaction_type"));
        transaction.setTransactionAmount(resultSet.getDouble("transaction_amount"));
        transaction.setDescription(resultSet.getString("description"));
        transaction.setIsRecurring(resultSet.getBoolean("is_recurring"));
        transaction.setRecurrenceType(resultSet.getString("recurrence_type"));
        transaction.setRecurrenceUpto(resultSet.getInt("recurrence_upto"));
        transaction.setCreatedDate(resultSet.getDate("created_date"));
        transaction.setUpdatedDate(resultSet.getDate("updated_date"));
        transaction.setStatus(resultSet.getBoolean("status"));

        return transaction;
    }
}
