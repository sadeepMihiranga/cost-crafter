package com.cost.crafter.menu;

import com.cost.crafter.dal.GenerateReportRepository;
import com.cost.crafter.dto.GenerateReport;
import com.cost.crafter.service.GenerateReportService;
import de.vandermeer.asciitable.AsciiTable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class GenerateReportMenu extends BaseMenuHandler {

    private GenerateReportService generateReportService = null;

    void showGenerateReportMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            showMenuHeader("\nGenerate Report Menu\n");
            System.out.println("1 - Generate Monthly Transaction Summary");
            System.out.println("2 - Generate 6-Monthly Transaction Summary");
            System.out.println("3 - Back to Main Menu");
            System.out.print("\nSelect an option : ");

            switch (intSelectedOption(br, 3)) {
                case 1 -> generateMonthlyTransactionSummary();
                case 2 -> generateSixMonthlyTransactionSummary();
                case 3 -> goToMainMenu();
                default -> showErrorMessage("Invalid option! Please try again.");
            }
        } catch (Exception exception) {
            showErrorMessage("Error while processing.");
        }
    }

    private void generateMonthlyTransactionSummary() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Enter month (YYYY-MM): ");
            String inputMonth = br.readLine();

            String[] parts = inputMonth.split("-");
            if (parts.length != 2 || inputMonth.length() != 7) {
                showErrorMessage("Invalid Date format");
            }

            generateReportService = new GenerateReportService(new GenerateReportRepository());
            List<GenerateReport> reportData = generateReportService.fetchMonthlyReportData(loggedUser().getUserId(), inputMonth);

            if (reportData.isEmpty()) {
                showErrorMessage("No data found for the selected month.");
                return;
            }

            int selectedMonthNum = Integer.parseInt(parts[1], 10);
            final String monthInText = monthConverter(selectedMonthNum);

            if (monthInText == null) {
                generateMonthlyTransactionSummary();
            }

            AsciiTable asciiTable = initTable("Category Name", "Budget Amount", "Actual Expense");

            System.out.println("Report Data for " + monthInText);
            for (GenerateReport reportDetails : reportData) {
                addTableRow(asciiTable, reportDetails.getCategoryName(), reportDetails.getBudgetAmount(), reportDetails.getActualExpense());
//                System.out.println("Category Name: " + reportDetails.getCategoryName());
//                System.out.println("BudgetAmount: " + reportDetails.getBudgetAmount());
//                System.out.println("ActualExpense: " + reportDetails.getActualExpense());
            }
            System.out.println(asciiTable.render());
        } catch (Exception e) {
            showErrorMessage("Error while generating the report");
        }
    }

    private void generateSixMonthlyTransactionSummary() {
        try {
            generateReportService = new GenerateReportService(new GenerateReportRepository());
            List<GenerateReport> sixMonthReportData = generateReportService.fetchSixMonthReportData(loggedUser().getUserId());

            if (sixMonthReportData.isEmpty()) {
                showErrorMessage("No data found for the past 6 months.");
                return;
            }

            AsciiTable asciiTable = initTable("Category Name", "Budget Amount", "Actual Expense");

            System.out.println("Report Data for past 6 months\n");
            for (GenerateReport reportDetails : sixMonthReportData) {
                addTableRow(asciiTable, reportDetails.getCategoryName(), reportDetails.getBudgetAmount(), reportDetails.getActualExpense());
//                System.out.println("Category Name: " + reportDetails.getCategoryName());
//                System.out.println("BudgetAmount: " + reportDetails.getBudgetAmount());
//                System.out.println("ActualExpense: " + reportDetails.getActualExpense());
            }

            System.out.println(asciiTable.render());
        } catch (Exception e) {
            showErrorMessage("Error reading input");
        }
    }

    public String monthConverter(int selectedMonthNumber) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December"};

        // check if the dateNumber is within the valid range
        if (selectedMonthNumber >= 1 && selectedMonthNumber <= 12) {
            // convert the numeric month to its text representation
            return monthNames[selectedMonthNumber - 1];
        } else {
            showErrorMessage("Invalid month value. Please provide a value between 1 and 12.");
            return null;
        }
    }
}
