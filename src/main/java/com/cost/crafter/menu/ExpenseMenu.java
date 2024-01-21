package com.cost.crafter.menu;

import com.cost.crafter.dal.TransactionRepository;
import com.cost.crafter.dto.Transaction;
import com.cost.crafter.enums.TransactionType;
import com.cost.crafter.service.TransactionService;
import de.vandermeer.asciitable.AsciiTable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ExpenseMenu extends BaseMenuHandler {

    public void showExpenseTransactionsMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {

            showMenuHeader("Expense Transactions Menu");

            int selectedOption = 0;
            do {
                System.out.println("-------------------------------------\n");
                System.out.println("1 - Create new Expense");
                System.out.println("2 - View/Update Expense transactions");
                System.out.println("3 - Main menu");
                System.out.println("4 - Exit");
                System.out.println("\n-------------------------------------\n");
                System.out.print("Select an option : ");

                switch (intSelectedOption(br, 5)) {
                    case 1 -> createExpenseTransaction();
                    case 2 -> viewAllExpenseTransactions();
                    case 3 -> goToMainMenu();
                    case 4 -> exit();
                    default -> showErrorMessage("Invalid option ! Please try again.");
                }
            } while (selectedOption != 5);
        } catch (Exception exception) {
            showErrorMessage("Error occurred while displaying Expense Transactions Menu!");
        }
    }

    private void viewAllExpenseTransactions() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        AsciiTable asciiTable = null;
        List<Integer> incomeTransList = null;
        try {
            showMenuHeader("\nExpense Transactions\n");
            int selectedOption = 0;
            int maxIncomeTransId = 0;
            int exitOptionId = 0;
            incomeTransList = new ArrayList<>();

            TransactionService transactionService = new TransactionService();
            List<Transaction> userIncomeTransactions = transactionService.fetchTransactions(loggedUser().getUserId(), TransactionType.DEBIT.toString());

            asciiTable = initTable("Income Transaction Id", "Expense Category", "Description", "Amount", "Created Date", "Updated Date");
            for (Transaction transaction : userIncomeTransactions) {
                addTableRow(asciiTable, transaction.getTransactionId(), transaction.getExpensesCategory(), transaction.getDescription(), transaction.getTransactionAmount(),  transaction.getCreatedDate()
                        , transaction.getUpdatedDate());

                if (maxIncomeTransId < transaction.getTransactionId()) {
                    maxIncomeTransId = transaction.getTransactionId();
                }
                incomeTransList.add(transaction.getTransactionId());
            }
            final String renderedTable = asciiTable.render();

            do {
                System.out.println("To update choose a Expense transaction by id,\n");
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
                    showErrorMessage("Invalid option! Please try again.");
                }
            } while (selectedOption != exitOptionId);
        } catch (Exception exception) {
            showErrorMessage("Error occurred! Please try again.");
        } finally {
            asciiTable = null;
            incomeTransList = null;
        }
    }

    private void updateIncomeTransaction(int selectedOption) {
        // Todo
    }

    private void createExpenseTransaction() {

        ExpensesCategoriesMenu expensesCategoriesMenu = new ExpensesCategoriesMenu();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            expensesCategoriesMenu.viewExpenseCategories();

            System.out.println("\nEnter details for the new Expense transaction:\n");

            System.out.print("Enter Expense Category ID: ");
            String expenseCategoryId = br.readLine();

            System.out.print("Enter Expense amount: ");
            String expenseAmount = br.readLine();

            System.out.print("Enter income description: ");
            String description = br.readLine();

            TransactionService transactionService = new TransactionService();
            transactionService.addExpenseTransaction(loggedUser().getUserId(), Integer.parseInt(expenseCategoryId), Double.parseDouble(expenseAmount),description);

            showSuccessMessage("Expense transaction added successfully!");
        } catch (Exception e) {
            showErrorMessage("Error occurred while creating Expense transaction");
        }
    }

    public List<Transaction> fetchExpenseTransactions(Integer userId) throws Exception{
        TransactionRepository transactionRepository = new TransactionRepository();
        return transactionRepository.fetchTransactions(userId, TransactionType.DEBIT.toString());
    }
}
