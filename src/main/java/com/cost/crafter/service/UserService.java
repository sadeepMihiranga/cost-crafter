package com.cost.crafter.service;

import com.cost.crafter.config.DbConnectionManager;
import com.cost.crafter.dal.BaseDAL;
import com.cost.crafter.dal.mapper.UserMapper;
import com.cost.crafter.dto.User;
import com.cost.crafter.enums.MethodResponseState;
import com.cost.crafter.security.BCrypt;
import com.cost.crafter.util.CommonValidatorUtil;
import com.cost.crafter.util.DateTimeUtil;
import com.mysql.cj.util.StringUtils;

import java.sql.SQLException;
import java.util.regex.Pattern;

import static com.cost.crafter.util.FontColors.ANSI_RED;
import static com.cost.crafter.util.FontColors.ANSI_RESET;

public class UserService {

    public boolean registerUser (User user) throws Exception {

        System.out.println("\nRegistering...");

        // input validation
        if (!DateTimeUtil.isValid(user.getDateOfBirth(), "yyyy-MM-dd")) {
            System.out.println(ANSI_RED + "\nDate of birth should be in yyyy-MM-dd format" + ANSI_RESET);
            return false;
        }

        if (!CommonValidatorUtil.isEmailValid(user.getEmail())) {
            System.out.println(ANSI_RED + "\nEmail is invalid" + ANSI_RESET);
            return false;
        }

        if (!CommonValidatorUtil.isAlpha(user.getFirstName())) {
            System.out.println(ANSI_RED + "\nFirst name can only contains letters" + ANSI_RESET);
            return false;
        }

        if (!StringUtils.isNullOrEmpty(user.getLastName()) && !CommonValidatorUtil.isAlpha(user.getLastName())) {
            System.out.println(ANSI_RED + "\nLast name can only contains letters" + ANSI_RESET);
            return false;
        }

        final String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hashedPassword);

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
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public User login(String username, String password) throws Exception {

        DbConnectionManager connection = null;
        BaseDAL baseDAL = null;
        try {
            connection = DbConnectionManager.getInstance();
            baseDAL = new BaseDAL(connection);

            String readQuery = "SELECT * FROM user WHERE user_name = ?";

            Object[] values = {username};
            User user = baseDAL.readOne(readQuery, new UserMapper(), values);

            if (user == null) {
                return null;
            }
            if (!BCrypt.checkPassword(password, user.getPassword())) {
                return null;
            }
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error while executing SQL");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while processing login");
        }
    }
}
