package com.cost.crafter.service;

import com.cost.crafter.config.DbConnectionManager;
import com.cost.crafter.dal.BaseDAL;
import com.cost.crafter.dal.mapper.ExpensesCategoryMapper;
import com.cost.crafter.dal.mapper.UserMapper;
import com.cost.crafter.dto.ExpensesCategory;
import com.cost.crafter.dto.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    public void registerUser (User user) {

        System.out.println("\nRegistering...");

        // input validation

        // save user info
        DbConnectionManager connection = null;
        BaseDAL baseDAL = null;
        try {
            connection = DbConnectionManager.getInstance();
            baseDAL = new BaseDAL(connection);

            final String insertQuery = "INSERT INTO user (user_name, password, first_name, last_name, email, date_of_birth, gender) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            Object[] values = {user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(),
                    user.getEmail(), user.getDateOfBirth(), null};
            baseDAL.create(insertQuery, values);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User login(String username, String password) {

        DbConnectionManager connection = null;
        BaseDAL baseDAL = null;
        try {
            connection = DbConnectionManager.getInstance();
            baseDAL = new BaseDAL(connection);

            String readQuery = "SELECT * FROM user WHERE user_name = ? AND password = ? ";

            Object[] values = {username, password};
            return baseDAL.readOne(readQuery, new UserMapper(), values);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
