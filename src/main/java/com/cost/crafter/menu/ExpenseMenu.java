package com.cost.crafter.menu;

import com.cost.crafter.dto.Transaction;
import com.cost.crafter.dto.UserExpensesCategory;
import com.cost.crafter.enums.TransactionType;
import com.cost.crafter.service.TransactionService;
import com.cost.crafter.service.UserExpensesCategoryService;
import de.vandermeer.asciitable.AsciiTable;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
                System.out.println("3 - Delete Expense transactions");
                System.out.println("4 - Main menu");
                System.out.println("5 - Exit");
                System.out.println("\n-------------------------------------\n");
                System.out.print("Select an option : ");

                switch (intSelectedOption(br, 5)) {
                    case 1 -> createExpenseTransaction();
                    case 2 -> viewAllExpenseTransactions();
                    case 3 -> deleteExpenseTransactions();
                    case 4 -> goToMainMenu();
                    case 5 -> exit();
                    default -> showErrorMessage("Invalid option ! Please try again.");
                }
            } while (selectedOption != 5);
        } catch (Exception exception) {
            showErrorMessage("Error occurred while displaying Expense Transactions Menu!");
        }
    }

    private void viewAllExpenseTransactions() {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        List<Integer> expenseTransLis = null;
        try {
            showMenuHeader("\nExpense Transactions\n");

            int selectedOption = 0;
            int maxIncomeTransId = 0;
            int exitOptionId = 0;
            expenseTransLis = new ArrayList<>();

            do {
                System.out.println("To update choose a Expense transaction by id,\n");

                AsciiTable asciiTable = null;
                TransactionService transactionService = new TransactionService();

                try{
                    expenseTransLis = new ArrayList<>();

                    List<Transaction> userIncomeTransactions = transactionService.fetchTransactions(loggedUser().getUserId(), TransactionType.DEBIT.toString());

                    asciiTable = initTable("Transaction Id", "Transaction Date", "Expense Category", "Description", "Amount", "Created Date", "Updated Date");
                    for (Transaction transaction : userIncomeTransactions) {
                        addTableRow(asciiTable, transaction.getTransactionId(), transaction.getTransactionDate(), transaction.getExpensesCategory(), transaction.getDescription(), transaction.getTransactionAmount(),  transaction.getCreatedDate()
                                , transaction.getUpdatedDate());

                        if (maxIncomeTransId < transaction.getTransactionId()) {
                            maxIncomeTransId = transaction.getTransactionId();
                        }
                        expenseTransLis.add(transaction.getTransactionId());
                    }
                } catch (Exception e){
                    showErrorMessage("Error occurred while displaying Data table.");
                }

                System.out.println(asciiTable.render());
                System.out.println("\nOr choose a menu option");

                final int mainMenuOptionId = maxIncomeTransId + 1;
                exitOptionId = maxIncomeTransId + 2;

                System.out.println("-------------------------------------\n");
                System.out.println(String.format("%s - Main menu", mainMenuOptionId));
                System.out.println(String.format("%s - Exit", exitOptionId));
                System.out.println("\n-------------------------------------\n");
                System.out.print("Select an option : ");
                selectedOption = intSelectedOption(br, exitOptionId+1);

                if (expenseTransLis.contains(selectedOption)) {
                    updateExpenseTransaction(selectedOption);
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
            expenseTransLis = null;
        }
    }

    private String renderedDataTable() throws Exception {
        AsciiTable asciiTable = null;
        List<Integer> expenseTransLis = null;
        TransactionService transactionService = new TransactionService();

        try{
            int maxIncomeTransId = 0;
            expenseTransLis = new ArrayList<>();

            List<Transaction> userIncomeTransactions = transactionService.fetchTransactions(loggedUser().getUserId(), TransactionType.DEBIT.toString());

            asciiTable = initTable("Transaction Id", "Transaction Date", "Expense Category", "Description", "Amount", "Created Date", "Updated Date");
            for (Transaction transaction : userIncomeTransactions) {
                addTableRow(asciiTable, transaction.getTransactionId(), transaction.getTransactionDate(), transaction.getExpensesCategory(), transaction.getDescription(), transaction.getTransactionAmount(),  transaction.getCreatedDate()
                        , transaction.getUpdatedDate());

                if (maxIncomeTransId < transaction.getTransactionId()) {
                    maxIncomeTransId = transaction.getTransactionId();
                }
                expenseTransLis.add(transaction.getTransactionId());
            }
        } catch (Exception e){
            showErrorMessage("Error occurred while displaying Data table.");
        }
        return asciiTable.render();
    }

    private void updateExpenseTransaction(Integer transactionId){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        TransactionService transactionService = new TransactionService();

        try {
            showMenuHeader("\nUpdate Expense \n");
            int selectedOption = 0;

            Transaction transactionDetails = transactionService.getTransactionById(transactionId);

            do {
                System.out.println("Expense transaction ID : " + transactionDetails.getTransactionId());
                System.out.println("Expense transaction Date : " + transactionDetails.getTransactionDate());
                System.out.println("Expense Category : " + transactionDetails.getExpensesCategory());
                System.out.println("Expense transaction Amount : " + transactionDetails.getTransactionAmount());
                System.out.println("Expense transaction Description : " + transactionDetails.getDescription());
                System.out.println("Transaction Created Date : " + transactionDetails.getCreatedDate());

                System.out.println("\nSelect an option :");

                System.out.println("-------------------------------------\n");
                System.out.println(String.format("%s - Update Expense Date", 1));
                System.out.println(String.format("%s - Update Expense Category", 2));
                System.out.println(String.format("%s - Update Expense Amount", 3));
                System.out.println(String.format("%s - Update Expense Description", 4));
                System.out.println(String.format("%s - Main menu", 5));
                System.out.println(String.format("%s - Exit", 6));
                System.out.println("\n-------------------------------------\n");
                System.out.print("Select an option : ");
                selectedOption = intSelectedOption(br, 5);

                if (selectedOption == 1) {
                    try {
                        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                        System.out.print("\nEnter new Expense Date to update (yyyy-MM-dd) : ");
                        String transactionDate = br.readLine();
                        transactionDetails.setTransactionDate(dateFormatter.parse(transactionDate));
                    } catch (ParseException e){
                        showErrorMessage("Invalid Date! Please enter Date in given format.");
                        updateExpenseTransaction(transactionId);
                    }
                    final boolean isUpdated = transactionService.updateTransaction(transactionDetails);
                    if (!isUpdated) {
                        updateExpenseTransaction(transactionId);
                    }
                    showSuccessMessage("\nTransaction Date updated successfully\n");
                    viewAllExpenseTransactions();
                } else if (selectedOption == 2) {
                    ExpensesCategoriesMenu expensesCategoriesMenu = new ExpensesCategoriesMenu();
                    expensesCategoriesMenu.viewExpenseCategories();

                    System.out.print("\nEnter new Expense Category ID to update : ");
                    int categoryId;
                    try {
                        categoryId = Integer.parseInt(br.readLine());
                    } catch (NumberFormatException e) {
                        showErrorMessage("Invalid category id. Please enter a valid number!");
                        return;
                    }

                    UserExpensesCategory existingCategory = new UserExpensesCategory(loggedUser().getUserId(), "", "");
                    existingCategory.setExpensesCategoryId(categoryId);
                    UserExpensesCategoryService userExpensesCategoryService = new UserExpensesCategoryService();
                    UserExpensesCategory category = userExpensesCategoryService.fetchUserExpensesCategory(existingCategory);

                    if(category == null){
                        showErrorMessage("Invalid Expense Category ID! Please try again.");
                        updateExpenseTransaction(transactionId);
                    }
                    transactionDetails.setExpensesCategoryId(categoryId);
                    final boolean isUpdated = transactionService.updateTransaction(transactionDetails);
                    if (!isUpdated) {
                        updateExpenseTransaction(transactionId);
                    }
                    showSuccessMessage("\nExpense Category is updated successfully\n");
                    viewAllExpenseTransactions();
                } else if (selectedOption == 3) {
                    System.out.print("\nEnter new Expense amount to update : ");
                    final double newAmount = doubleSelectedOption(br, -1);

                    if (newAmount < 0 ) {
                        showErrorMessage("Invalid amount ! Please try again.");
                        updateExpenseTransaction(transactionId);
                    }
                    transactionDetails.setTransactionAmount(newAmount);
                    final boolean isUpdated = transactionService.updateTransaction(transactionDetails);
                    if (!isUpdated) {
                        updateExpenseTransaction(transactionId);
                    }
                    showSuccessMessage("\nExpense amount updated successfully\n");
                    viewAllExpenseTransactions();
                } else if(selectedOption == 4){
                    System.out.print("\nEnter new Expense description to update : ");
                    final String newDescription = br.readLine();

                    if (newDescription.isEmpty() ) {
                        showErrorMessage("Invalid description ! Please try again.");
                        updateExpenseTransaction(transactionId);
                    }
                    transactionDetails.setDescription(newDescription);
                    final boolean isUpdated = transactionService.updateTransaction(transactionDetails);
                    if (!isUpdated) {
                        updateExpenseTransaction(transactionId);
                    }
                    showSuccessMessage("\nExpense amount updated successfully\n");
                    viewAllExpenseTransactions();

                } else if (selectedOption == 5) {
                    goToMainMenu();
                } else if (selectedOption == 6) {
                    exit();
                } else {
                    showErrorMessage("Invalid option! Please try again.");
                }
            } while (selectedOption != 4);
        } catch (Exception exception) {
            showErrorMessage("Error occurred While updating.");
        } finally {
            transactionService = null;
        }
    }

    private void createExpenseTransaction() {

        ExpensesCategoriesMenu expensesCategoriesMenu = new ExpensesCategoriesMenu();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        TransactionService transactionService = new TransactionService();

        try {
            expensesCategoriesMenu.viewExpenseCategories();

            System.out.println("\nEnter details for the new Expense transaction:\n");

            System.out.print("Enter Expense Category ID: ");
            String expenseCategoryId = br.readLine();

            System.out.print("Enter Expense Date (yyyy-MM-dd): ");
            String expenseDate = br.readLine();

            System.out.print("Enter Expense amount: ");
            String expenseAmount = br.readLine();

            System.out.print("Enter Expense description: ");
            String description = br.readLine();

            System.out.print("Is this a recurring expense? (Y/N): ");
            String isRecurring = br.readLine().toUpperCase();

            if (isRecurring.equals("Y")) {
                System.out.print("Enter Recurrence Type (D-DAILY, W-WEEKLY, M-MONTHLY, Y-YEARLY): ");
                String recurrenceType = br.readLine().toUpperCase();
                String[] recurringTypes =  {"D", "W", "M", "Y"};
                boolean isValidRecurringType = Arrays.asList(recurringTypes).contains(recurrenceType);

                if (isValidRecurringType){
                    System.out.print("Enter Recurrence End Date (yyyy-MM-dd): ");
                    String recurrenceEndDate = br.readLine();

                    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

                    try{
                        transactionService.addExpenseRecurringTransaction(loggedUser().getUserId(), dateFormatter.parse(expenseDate), Integer.parseInt(expenseCategoryId),
                                Double.parseDouble(expenseAmount), description, true, recurrenceType, dateFormatter.parse(recurrenceEndDate));
                        showSuccessMessage("Recurring Expense transaction added successfully!");

                    } catch (ParseException e) {
                        showErrorMessage("Invalid Date input. Please try again.");
                        createExpenseTransaction();
                    }

                } else {
                    showErrorMessage("Invalid Recurring type. Please try again.");
                    createExpenseTransaction();
                }

            } else if (isRecurring.equals("N")) {
                transactionService.addExpenseTransaction(loggedUser().getUserId(), expenseDate, Integer.parseInt(expenseCategoryId), Double.parseDouble(expenseAmount),description);
                showSuccessMessage("Expense transaction added successfully!");
            } else {
                showErrorMessage("Invalid Input. Please try again with Y or N.");
                createExpenseTransaction();
            }

        } catch (Exception e) {
            showErrorMessage("Error occurred while creating Expense transaction");
        }
    }

    private void deleteExpenseTransactions() throws Exception {

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            TransactionService transactionService = new TransactionService();

            showMenuHeader("\nDelete Expense Transaction\n");

            System.out.println(renderedDataTable());

            System.out.println("0 - Main Menu,\n");

            System.out.println("To Delete choose a Expense transaction ID,\n");

            int transactionId = 0;
            try {
                transactionId = Integer.parseInt(br.readLine());
            } catch (NumberFormatException e) {
                showErrorMessage("Invalid Transaction ID. Please enter a valid Input.");
                deleteExpenseTransactions();
            }

            if (transactionId == 0){
                goToMainMenu();
                return;
            }

            Transaction transactionDetails = transactionService.getTransactionById(transactionId);
            if (transactionDetails == null) {
                showErrorMessage("Transaction ID not found. Please try again");
                deleteExpenseTransactions();
            }

            transactionService.deleteTransaction(transactionId);

            showSuccessMessage("Expense Transaction deleted successfully");
        } catch (Exception e){
            showErrorMessage("Error occurred whileE deleting Expense Transaction");
        }
    }
}
