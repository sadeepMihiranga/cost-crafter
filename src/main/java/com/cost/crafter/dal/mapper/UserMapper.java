package com.cost.crafter.dal.mapper;

import com.cost.crafter.dal.BaseDAL;
import com.cost.crafter.dto.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements BaseDAL.RowMapper<User>{

    @Override
    public User mapRow(ResultSet resultSet) throws SQLException {

        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setUsername(resultSet.getString("user_name"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setDateOfBirth(resultSet.getString("date_of_birth"));

        return user;
    }
}