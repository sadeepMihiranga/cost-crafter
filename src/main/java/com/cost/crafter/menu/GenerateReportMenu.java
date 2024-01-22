package com.cost.crafter.menu;

import com.cost.crafter.dal.GenerateReportRepository;
import com.cost.crafter.dto.GenerateReport;
import com.cost.crafter.service.GenerateReportService;

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
            exception.printStackTrace();
        }
    }

    private void generateMonthlyTransactionSummary() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Enter month (YYYY-MM): ");
            String inputMonth = br.readLine();

            String[] parts = inputMonth.split("-");
            if (parts.length != 2 || inputMonth.length() != 7) {
                System.out.print("Invalid Date format");
            }

            int selectedYear = Integer.parseInt(parts[0]);
            int selectedMonth = Integer.parseInt(parts[1]);

            generateReportService = new GenerateReportService(new GenerateReportRepository());
            List<GenerateReport> reportData = generateReportService.fetchMonthlyReportData(loggedUser().getUserId(), inputMonth);

            if (reportData.isEmpty()) {
                System.out.println("No data found for the selected month.");
                return;
            }

            int selectedMonthNum = Integer.parseInt(parts[1], 10);
            String monthInText = MonthConverter(selectedMonthNum);
            System.out.println("Report Data for " + monthInText);
            for (GenerateReport reportDetails : reportData) {
                System.out.println("---------------------------");
                System.out.println("Category Name: " + reportDetails.getCategoryName());
                System.out.println("BudgetAmount: " + reportDetails.getBudgetAmount());
                System.out.println("ActualExpense: " + reportDetails.getActualExpense());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Error reading input");
        }
    }

    private void generateSixMonthlyTransactionSummary() {
        try {
            generateReportService = new GenerateReportService(new GenerateReportRepository());
            List<GenerateReport> sixMonthReportData = generateReportService.fetchSixMonthReportData(loggedUser().getUserId());

            if (sixMonthReportData.isEmpty()) {
                System.out.println("No data found for the past 6 months.");
                return;
            }

            System.out.println("Report Data for past 6 months");
            for (GenerateReport reportDetails : sixMonthReportData) {
                System.out.println("---------------------------");
                System.out.println("Category Name: " + reportDetails.getCategoryName());
                System.out.println("BudgetAmount: " + reportDetails.getBudgetAmount());
                System.out.println("ActualExpense: " + reportDetails.getActualExpense());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Error reading input");
        }
    }

    public static String MonthConverter(int selectedMonthNumber) {
        String[] monthNames = {
                "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December"
        };

        // Check if the dateNumber is within the valid range
        if (selectedMonthNumber >= 1 && selectedMonthNumber <= 12) {
            // Convert the numeric month to its text representation
            return monthNames[selectedMonthNumber - 1];
        } else {
            // Return an error message for invalid month value
            return "Invalid month value. Please provide a value between 1 and 12.";
        }
    }
}
