
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
            final String readQuery = "SELECT expenses_categories.name, user_budget.budget_amount, SUM(transaction.transaction_amount) AS actual_expense " +
                    "FROM expenses_categories " +
                    "JOIN user_budget ON expenses_categories.category_id = user_budget.expenses_category_id " +
                    "LEFT JOIN transaction ON user_budget.user_id = transaction.user_id AND user_budget.expenses_category_id = transaction.expenses_category_id " +
                    "WHERE user_budget.month = ? " +
                    "GROUP BY expenses_categories.category_id";
            List<GenerateReport> monthlyReportDataList = read(readQuery, new GenerateReportMapper(), userInputMonth);
            return monthlyReportDataList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error while executing SQL");
        }
    }
}
