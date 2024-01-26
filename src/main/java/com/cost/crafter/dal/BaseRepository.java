package com.cost.crafter.dal;

import com.cost.crafter.config.DbConnectionManager;
import com.cost.crafter.dto.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseRepository {

    // Connection provider class for managing database connections
    private DbConnectionManager connectionManager;

    public BaseRepository(DbConnectionManager connectionProvider) {
        this.connectionManager = connectionProvider;
    }

    // Create operation
    protected int create(String insertQuery, Object... parameters) throws Exception {
        int createdId = 0;
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery,
                    Statement.RETURN_GENERATED_KEYS);

            // Set parameters
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }

            // Execute the query
            preparedStatement.executeUpdate();

            // Return created primary id
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next())
            {
                createdId = rs.getInt(1);
            }
            return createdId;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    protected int batchCreate(String insertQuery, List<Transaction> transactions) throws Exception {

        int[] insertCount;
        Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

        try {
            connection.setAutoCommit(false); // Start transaction

            for (Transaction transaction : transactions) {
                preparedStatement.setInt(1, transaction.getUserId());
                preparedStatement.setInt(2, transaction.getExpensesCategoryId());
                preparedStatement.setString(3, transaction.getTransactionType());
                preparedStatement.setDouble(4, transaction.getTransactionAmount());
                preparedStatement.setString(5, transaction.getDescription());
                preparedStatement.setBoolean(6, true);
                preparedStatement.setString(7, transaction.getRecurrenceType());
                preparedStatement.setInt(8, 0);
                preparedStatement.setDate(9, new Date(transaction.getTransactionDate().getTime()));
                preparedStatement.setTimestamp(10, new Timestamp(transaction.getCreatedDate().getTime()));
                preparedStatement.setTimestamp(11, new Timestamp(transaction.getUpdatedDate().getTime()));
                preparedStatement.setBoolean(12, transaction.getStatus());
                preparedStatement.addBatch();
            }

            insertCount = preparedStatement.executeBatch();
            connection.commit();

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback in case of an error
                } catch (SQLException ex) {
                    throw new Exception("Error executing batch insert for transactions", e);
                }
            }
            throw new Exception("Error executing batch insert for transactions", e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return insertCount.length;
    }

    // Read operation
    protected <T> List<T> read(String selectQuery, RowMapper<T> rowMapper, Object... parameters) throws Exception {
        List<T> result = new ArrayList<>();

        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

            // Set parameters
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    preparedStatement.setObject(i + 1, parameters[i]);
                }
            }

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Map the result set to objects using the provided RowMapper
                while (resultSet.next()) {
                    result.add(rowMapper.mapRow(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        return result;
    }

    /**
     * @return Data entity if matching record found, if not return null
     * */
    protected <T> T readOne(String selectQuery, RowMapper<T> rowMapper, Object... parameters) throws Exception {

        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

            // Set parameters
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    preparedStatement.setObject(i + 1, parameters[i]);
                }
            }

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Map the result set to objects using the provided RowMapper
                while (resultSet.next()) {
                    return (rowMapper.mapRow(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        return null;
    }

    // Update operation
    protected int update(String updateQuery, Object... parameters) throws Exception {
        return create(updateQuery, parameters);
    }

    // Delete operation
    protected int delete(String deleteQuery, Object... parameters) throws Exception {
        return create(deleteQuery, parameters);
    }

    // Functional interface for mapping rows from ResultSet to objects
    public interface RowMapper<T> {
        T mapRow(ResultSet resultSet) throws SQLException;

        static boolean columnExists(ResultSet resultSet, String columnName) throws SQLException {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                if (metaData.getColumnName(i).equalsIgnoreCase(columnName)) {
                    return true;
                }
            }

            return false;
        }
    }
}
