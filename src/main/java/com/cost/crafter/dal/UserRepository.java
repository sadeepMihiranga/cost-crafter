package com.cost.crafter.dal;

import com.cost.crafter.config.DbConnectionManager;
import com.cost.crafter.dal.mapper.UserMapper;
import com.cost.crafter.dto.User;

import java.sql.SQLException;

public class UserRepository extends BaseRepository {

    public UserRepository() throws SQLException {
        super(DbConnectionManager.getInstance());
    }

    public int insertUser(User user) throws Exception {
        try {
            final String insertQuery = "INSERT INTO user (user_name, password, first_name, last_name, email," +
                    " date_of_birth, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
            Object[] values = {user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(),
                    user.getEmail(), user.getDateOfBirth(), null};
            return create(insertQuery, values);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error while executing user insert SQL");
        }
    }

    public User login(String username) throws Exception {
        try {
            final String readQuery = "SELECT * FROM user WHERE user_name = ?";

            Object[] values = {username};
            return readOne(readQuery, new UserMapper(), values);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error while executing user login SQL");
        }
    }
}
