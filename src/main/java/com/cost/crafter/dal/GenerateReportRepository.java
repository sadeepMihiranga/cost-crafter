
package com.cost.crafter.dal;

import com.cost.crafter.config.DbConnectionManager;
import com.cost.crafter.dal.mapper.GenerateReportMapper;
import com.cost.crafter.dto.GenerateReport;

import java.sql.SQLException;
import java.util.List;

public class GenerateReportRepository extends BaseRepository {

    public GenerateReportRepository() throws SQLException {
        super(DbConnectionManager.getInstance());
    }

    public List<GenerateReport> fetchMonthlyReportData(Integer userId, String userInputMonth) throws Exception {
        try {
            final String readQuery = "SELECT user_expenses_categories.name, user_budget.budget_amount, SUM(transaction.transaction_amount) AS actual_expense " +
                    "FROM user_expenses_categories " +
                    "JOIN user_budget ON user_expenses_categories.expenses_category_id = user_budget.expenses_category_id " +
                    "LEFT JOIN transaction ON user_budget.user_id = transaction.user_id AND user_budget.expenses_category_id = transaction.expenses_category_id " +
                    "WHERE user_budget.month LIKE ? AND transaction.user_id = ? " +
                    "GROUP BY user_expenses_categories.expenses_category_id";

            List<GenerateReport> monthlyReportDataList = read(readQuery, new GenerateReportMapper(), "%" + userInputMonth + "%", userId);
            return monthlyReportDataList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error while executing SQL");
        }
    }

    public List<GenerateReport> fetchSixMonthReportData(Integer userId) throws Exception {
        try {
            final String readQuery = "SELECT DATE_FORMAT(transaction.transaction_date, '%Y-%m') AS name, " +
                    "COALESCE(SUM(user_budget.budget_amount), 0) AS budget_amount, " +
                    "COALESCE(SUM(transaction.transaction_amount), 0) AS actual_expense " +
                    "FROM user_budget " +
                    "JOIN user_expenses_categories ON user_budget.expenses_category_id = user_expenses_categories.expenses_category_id " +
                    "LEFT JOIN transaction ON user_budget.user_id = transaction.user_id " +
                    "AND user_budget.expenses_category_id = transaction.expenses_category_id " +
                    "WHERE user_budget.user_id = ? " +
                    "AND DATE_FORMAT(transaction.transaction_date, '%Y-%m') >= DATE_FORMAT(CURDATE() - INTERVAL 6 MONTH, '%Y-%m') " +
                    "GROUP BY DATE_FORMAT(transaction.transaction_date, '%Y-%m') " +
                    "ORDER BY DATE_FORMAT(transaction.transaction_date, '%Y-%m') DESC";

            List<GenerateReport> sixMonthlyReportDataList = read(readQuery, new GenerateReportMapper(), userId);

            return sixMonthlyReportDataList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error while executing SQL");
        }
    }
}
