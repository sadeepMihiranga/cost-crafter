package com.cost.crafter;

import com.cost.crafter.config.DbConnection;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Cost Crafter");

        try {
            DbConnection instance = DbConnection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}