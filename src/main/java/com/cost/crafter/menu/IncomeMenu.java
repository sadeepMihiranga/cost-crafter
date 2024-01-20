package com.cost.crafter.menu;

import com.cost.crafter.dto.Transaction;
import com.cost.crafter.service.TransactionService;
import de.vandermeer.asciitable.AsciiTable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class IncomeMenu extends BaseMenuHandler{

    private TransactionService transactionService = null;

    public void showIncomeTransactionsMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("\nIncome Transactions Menu\n");
            int selectedOption = 0;
            do {
                System.out.println("-------------------------------------\n");
                System.out.println("1 - Create new income");
                System.out.println("2 - View/Update income transactions");
                System.out.println("4 - Main menu");
                System.out.println("5 - Exit");
                System.out.println("\n-------------------------------------\n");
                System.out.print("Select an option : ");

                switch (intSelectedOption(br, 5)) {
                    case 1 -> createIncomeTransaction();
                    case 2 -> viewAllIncomeTransactions();
                    case 3 -> goToMainMenu();
                    case 4 -> exit();
                    default -> showErrorMessage("Invalid option ! Please try again.");
                }
            } while (selectedOption != 5);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void createIncomeTransaction() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("\nEnter details for the new income transaction:");

            System.out.print("Enter income amount: ");
            String incomeAmount = br.readLine();

            System.out.print("Enter income description: ");
            String description = br.readLine();


            // Call the service method to insert the new category
            TransactionService transactionService = new TransactionService();
            transactionService.addIncomeTransaction(loggedUser().getUserId(), Double.parseDouble(incomeAmount),description);

            System.out.println("Income transaction added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMessage("Error creating income transaction");
        }
    }

    private void viewAllIncomeTransactions(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        AsciiTable asciiTable = null;
        List<Integer> incomeTransList = null;
        try {
            showMenuHeader("\nIncome Transactions\n");
            int selectedOption = 0;
            int maxIncomeTransId = 0;
            int exitOptionId = 0;
            incomeTransList = new ArrayList<>();

            transactionService = new TransactionService();
            List<Transaction> userIncomeTransactions = transactionService.fetchIncomeTransactions(loggedUser().getUserId());

            asciiTable = initTable("Income Transaction Id", "Amount", "Description", "Created Date", "Updated Date");
            for (Transaction incomeTransaction : userIncomeTransactions) {
                addTableRow(asciiTable, incomeTransaction.getTransactionId(), incomeTransaction.getTransactionAmount(), incomeTransaction.getDescription(), incomeTransaction.getCreatedDate()
                , incomeTransaction.getUpdatedDate());

                if (maxIncomeTransId < incomeTransaction.getTransactionId()) {
                    maxIncomeTransId = incomeTransaction.getTransactionId();
                }
                incomeTransList.add(incomeTransaction.getTransactionId());
            }
            final String renderedTable = asciiTable.render();

            do {
                System.out.println("To update choose a income transaction by id,\n");
                System.out.println(renderedTable);
                System.out.println("\nOr choose a menu option");

                final int mainMenuOptionId = maxIncomeTransId + 1;
                exitOptionId = maxIncomeTransId + 2;

                System.out.println("-------------------------------------\n");
                System.out.println(String.format("%s - Main menu", mainMenuOptionId));
                System.out.println(String.format("%s - Exit", exitOptionId));
                System.out.println("\n-------------------------------------\n");
                System.out.print("Select an option : ");
                selectedOption = intSelectedOption(br, exitOptionId+1);

                if (incomeTransList.contains(selectedOption)) {
                    updateIncomeTransaction(selectedOption);
                } else if (selectedOption == mainMenuOptionId) {
                    goToMainMenu();
                } else if (selectedOption == exitOptionId) {
                    exit();
                } else {
                    showErrorMessage("Invalid option ! Please try again.");
                }
            } while (selectedOption != exitOptionId);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            asciiTable = null;
            incomeTransList = null;
            transactionService = null;
        }
    }

    private void updateIncomeTransaction(Integer transactionId){}
}
