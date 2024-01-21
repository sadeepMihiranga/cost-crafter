package com.cost.crafter.dal.mapper;

import com.cost.crafter.dal.BaseRepository;
import com.cost.crafter.dto.ExpensesCategory;
import com.cost.crafter.dto.GenerateReport;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenerateReportMapper implements BaseRepository.RowMapper<GenerateReport> {

    @Override
    public GenerateReport mapRow(ResultSet resultSet) throws SQLException {

        GenerateReport generateReport = new GenerateReport();
        generateReport.setCategoryName(resultSet.getString("name"));
        generateReport.setBudgetAmount(resultSet.getDouble("budget_amount"));
        generateReport.setActualExpense(resultSet.getDouble("actual_expense"));
        return generateReport;
    }
}
