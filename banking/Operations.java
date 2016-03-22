package banking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Operations extends TimerTask {

    private static Operations operations_instance;
    private String[] overdraftOptions = {"Enable Overdraft", "Revoke Overdraft", "Change Overdraft Limit", "Return to Main Menu"};
    private ArrayList<Customer> customers;
    private ArrayList<ArrayList<Transaction>> transactions = new ArrayList<>();
    private ArrayList<ArrayList<Loan>> loans = new ArrayList<>();
    private boolean transferAction = false;
    private boolean stateOfTransfer = false;
    private boolean cancelTransfer = false;
    private int accountTransferredFrom = 0;
    private double amountWithdrawn = 0;
    private BankMainAccount main;
    private BankAccounts accountsList = BankAccounts.getInstance();
    private Date date = new Date();
    private int today = new Date().getDate();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
    public Login login = new Login();
    private boolean updateAccount = true;
    private Timer timer;

    private Operations() {
        System.out.println("Date is " + dateFormat.format(date));
        customers = new ArrayList<>();
        main = BankMainAccount.getInstance();
        createTempAccounts();
        timer = new Timer();
        timer.schedule(this, new Date(), 1000);
    }

    //Adds interest or applies charge to accounts 
    public void run() {
        if (date.getDate() == 01 && updateAccount) {
            System.out.println("Balance Checked...");
            main.updateAccount();
            for (ArrayList<Transaction> array : main.getMain_transactions()) {
                getTransactions().add(array);
            }
            updateAccount = false;
        } else if (date.getDate() != 30) {
            updateAccount = true;
        }
    }

    //returns a singleton instance of Operations 
    public static Operations get_instance() {
        if (operations_instance == null) {
            operations_instance = new Operations();
        }
        return operations_instance;
    }

    //Invokes the different methods according to choice passed from Menu 
    public void doOperation(int customerNo, int choice, String accountChoice) {
        System.out.println("choice :" + choice);
        int operation = choice;
        switch (operation) {
            case 1: {
                System.out.println("Choice 1: Check balance");
                check_balance(customerNo);
                break;
            }
            case 2: {
                System.out.println("Choice 2: Withdraw");
                amountWithdrawn = withdraw(customerNo);
                break;
            }
            case 3: {
                System.out.println("Choice 3: Deposit");
                deposit(customerNo);
                break;
            }
            case 4: {
                System.out.println("Choice 4: transfer");
                transferAction = true;
                transfer_money(customerNo);
                break;
            }
            case 5: {
                System.out.println("Choice 5: View Accounts");
                view_accounts(customerNo);
                break;
            }
            case 6: {
                System.out.println("Choice 6: view transactions");
                view_transactions(customerNo);
                break;
            }
            case 7: {
                System.out.println("Choice 7: Add account holder");
                add_account_holder(customerNo);
                break;
            }
            case 8: {
                System.out.println("Choice 8: new account");
                create_new_account(accountChoice);
                break;
            }
            case 9: {
                System.out.println("Choice 9: manage overdraft");
                manage_overdraft(customerNo);
                break;
            }
            case 10: {
                System.out.println("Choice 10: manage loan");
                manage_loan(customerNo);
                break;
            }
        }
    }

    public void check_balance(int customerNo) {
        for (int i = 0; i < main.getObservers().size(); i++) {
            BaseAccount account = main.getObservers().get(i);
            if (account.getCustomerNumber() == customerNo) {
                System.out.println("----------------------------------------------------------------------------------------------------------------------------");
                System.out.print("Account Number: " + account.getAccountNumber());
                System.out.print("\tAccount Type: " + account.getAccType());
                System.out.print("\tAccount Balance: £" + account.get_balance());
                System.out.println("\tAvailable to spend: " + (account.get_balance() + account.getOverdraftLimit()));
                System.out.println("----------------------------------------------------------------------------------------------------------------------------");
            }
        }
    }

    public double withdraw(int customerNo) {
        double amountWithdrawn = 0;
        check_balance(customerNo);
        Scanner input = new Scanner(System.in);
        int accNo = 0;
        System.out.println();
        System.out.println("Please enter account number you wish to withdraw from:");
        System.out.println();
        try {
            accNo = Integer.parseInt(input.nextLine());
        } catch (Exception e) {
        }
        boolean found = false;
        for (int i = 0; i < main.getObservers().size(); i++) {
            if (main.getObservers().get(i).getAccountNumber() == accNo && main.getObservers().get(i).getCustomerNumber() == customerNo) {
                found = true; //Used to track if the withdrawal is to be transferred to another account 
                accountTransferredFrom = main.getObservers().get(i).getAccountNumber(); //If no transfer is required 
                if (transferAction == false) {
                    double amount = 0;
                    boolean checkAmount = false;
                    while (checkAmount == false) {
                        System.out.println("Please enter amount required:");
                        try {
                            amount = Integer.parseInt(input.nextLine());
                        } catch (Exception e) {
                        }
                        if (amount == 0) {
                            System.out.println("Error! Please Try Again");
                        }
                    }
                    if (main.getObservers().get(i).getDailyAllowance() - main.getObservers().get(i).getDailyWithdrawal() >= amount && main.getObservers().get(i).get_balance() + main.getObservers().get(i).getOverdraftLimit() > amount) {
                        checkAmount = true;
                        break;
                    } else if (main.getObservers().get(i).get_balance() < amount) {
                        System.out.println("__________________________________________________________________");
                        System.out.println();
                        System.out.println("Sorry there are insufficient funds in this account");
                        System.out.println("__________________________________________________________________");
                        System.out.println();
                        System.out.println("Please press enter to continue.");
                        try {
                            Scanner keyboard = new Scanner(System.in);
                            keyboard.nextLine();
                        } catch (Exception e) {
                        }
                        break;
                    } else if (main.getObservers().get(i).getDailyAllowance() == main.getObservers().get(i).getDailyWithdrawal()) {
                        amount = 0;
                        System.out.println("__________________________________________________________________");
                        System.out.println();
                        System.out.println("Sorry You have reached your daily limit for this account.");
                        System.out.println();
                        System.out.println("__________________________________________________________________");
                        System.out.println();
                        System.out.println("Please press enter to continue.");
                        try {
                            Scanner keyboard = new Scanner(System.in);
                            keyboard.nextLine();
                        } catch (Exception e) {
                        }
                        withdraw(customerNo);
                    } else if (amount > main.getObservers().get(i).getDailyAllowance() - main.getObservers().get(i).getDailyWithdrawal()) {
                        amount = 0;
                        System.out.println("__________________________________________________________________");
                        System.out.println();
                        System.out.println("Sorry the amount exeeds your daily allowance");
                        System.out.println("The maximum amount you can withdraw today is: " + (main.getObservers().get(i).getDailyAllowance() - main.getObservers().get(i).getDailyWithdrawal()));
                        System.out.println("__________________________________________________________________");
                        System.out.println();
                        System.out.println("Please press enter to continue.");
                        try {
                            Scanner keyboard = new Scanner(System.in);
                            keyboard.nextLine();
                        } catch (Exception e) {
                        }
                    }

                    if (checkAmount) {
                        if (amount == 0) {
                            System.out.println("Error! Please try again");
                            break;
                        } else {
                            main.getObservers().get(i).setBalance(-amount);
                            main.getObservers().get(i).setDailyWithdrawal(amount);
                            amountWithdrawn = amount;
                            ArrayList<Transaction> t = new ArrayList<>();
                            t.add(new Transaction(dateFormat.format(date), main.getObservers().get(i).getAccountNumber(), "Withdraw", (amount * -1), main.getObservers().get(i).get_balance()));
                            getTransactions().add(t);
                            System.out.println();
                            System.out.println("__________________________________________________________________");
                            System.out.println();
                            System.out.println("£" + amount + " have successfully been withdrawn from A/C No." + main.getObservers().get(i).getAccountNumber());
                            System.out.println("__________________________________________________________________");
                        }
                    }
                } else if (transferAction == true) {
                    double amount = 0;
                    boolean checkAmount = true;
                    while (checkAmount) {
                        System.out.println("Please enter amount required:");
                        try {
                            amount = Integer.parseInt(input.nextLine());
                        } catch (Exception e) {
                        }
                        if (amount == 0) {
                            System.out.println("Error! Please Try Again");
                            break;
                        } else if (main.getObservers().get(i).get_balance() > amount) {
                            checkAmount = false;
                            main.getObservers().get(i).setBalance(-amount);
                            amountWithdrawn = amount;
                            ArrayList<Transaction> t = new ArrayList<Transaction>();
                            t.add(new Transaction(dateFormat.format(date), main.getObservers().get(i).getAccountNumber(), "Transfer", (amount * -1), main.getObservers().get(i).get_balance()));
                            getTransactions().add(t);
                            System.out.println("__________________________________________________________________");
                            System.out.println();
                            System.out.println("£" + amount + " have successfully been withdrawn from A/C No." + main.getObservers().get(i).getAccountNumber());
                            System.out.println("__________________________________________________________________");
                            stateOfTransfer = true;
                        } else {
                            System.out.println("Sorry there are insufficient funds.");
                            break;
                        }
                    }
                } else {
                    System.out.println("__________________________________________________________________");
                    System.out.println();
                    System.out.println("This account does not exist! Press enter to try again.");
                    System.out.println("__________________________________________________________________");
                    withdraw(customerNo);
                }
            }

            if (found == false) {
                System.out.println("Wrong account number or account does not exist");
                stateOfTransfer = false;
            }
        }
        return amountWithdrawn;
    }

    public void transfer_money(int custNumber) {
        amountWithdrawn = withdraw(custNumber);
        if (stateOfTransfer == true) {
            deposit(custNumber);
            transferAction = false;
        }
        if (stateOfTransfer == false) {
            System.out.println("An error has occured. This Transaction has been cancelled.");
            for (int i = 0; i < main.getObservers().size(); i++) {
                if (main.getObservers().get(i).getAccountNumber() == accountTransferredFrom) {
                    main.getObservers().get(i).setBalance(amountWithdrawn);
                    ArrayList<Transaction> t = new ArrayList<>();
                    t.add(new Transaction(dateFormat.format(date), main.getObservers().get(i).getAccountNumber(), "Refund ", amountWithdrawn, main.getObservers().get(i).get_balance()));
                    getTransactions().add(t);
                }
            }
        }
    }

    public void deposit(int customerNo) {
        Scanner input = new Scanner(System.in);
        boolean found = false;
        if (transferAction == false) {
            check_balance(customerNo);
            int accNo = 0;
            System.out.println("Please enter Account Number:");
            try {
                accNo = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
            }
            for (int i = 0; i < main.getObservers().size(); i++) {
                double amount = 0;
                if (main.getObservers().get(i).getAccountNumber() == accNo && main.getObservers().get(i).getCustomerNumber() == customerNo) {
                    found = true;
                    System.out.println();
                    System.out.println("Please enter Amount:");
                    try {
                        amount = Double.parseDouble(input.nextLine());

                    } catch (Exception e) {
                    }
                    if (amount == 0) {
                        System.out.println("Error. Please Try Again");
                    } else {
                        main.getObservers().get(i).setBalance(amount);
                        ArrayList<Transaction> t = new ArrayList<>();
                        t.add(new Transaction(dateFormat.format(date), main.getObservers().get(i).getAccountNumber(), "Cash Deposit", amount, main.getObservers().get(i).get_balance()));
                        getTransactions().add(t);
                        System.out.println();
                        System.out.println("__________________________________________________________________");
                        System.out.println();
                        System.out.println("£" + amount + " is successfully deposited to Account Number: " + main.getObservers().get(i).getAccountNumber());
                        System.out.println();
                        System.out.println("__________________________________________________________________");
                        System.out.println("Press Enter to continue.");
                    }
                }
            }
            if (found == false) {
                System.out.println("Wrong account number or account does not exist.");
                return;
            }
        } else if (transferAction == true) {
            int accNo = 0;
            System.out.println("Please enter Account Number to transfer to:");
            try {
                accNo = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
            }
            boolean found2 = false;
            for (int i = 0; i < main.getObservers().size(); i++) {
                if (main.getObservers().get(i).getAccountNumber() == accNo) {
                    found = true;
                    main.getObservers().get(i).setBalance(amountWithdrawn);
                    System.out.println("New balance is " + main.getObservers().get(i).get_balance());
                    ArrayList<Transaction> t = new ArrayList<>();
                    t.add(new Transaction(dateFormat.format(date), main.getObservers().get(i).getAccountNumber(), "Transferred", amountWithdrawn, main.getObservers().get(i).get_balance()));
                    getTransactions().add(t);

                    System.out.println();
                    System.out.println("__________________________________________________________________");
                    System.out.println();
                    System.out.println("£" + amountWithdrawn + " is successfully transferred to Account Number: " + main.getObservers().get(i).getAccountNumber());
                    System.out.println();
                    System.out.println("__________________________________________________________________");
                    System.out.println("Press Enter to continue.");
                }
                if (found == true) {
                    return;
                }
            }
            if (found2 == false) {
                transferAction = false;
                stateOfTransfer = false;
            }
        }
    }

    public void view_accounts(int customerNo) {
        for (int i = 0; i < main.getObservers().size(); i++) {
            if (main.getObservers().get(i).getCustomerNumber() == customerNo) {
                System.out.println();
                System.out.println("---------------------------------------------------" + "-----------------------------------------------------------------------------------------");
                System.out.print("Customer No:" + main.getObservers().get(i).getCustomerNumber());
                System.out.print("\tAccount Holder:" + main.getObservers().get(i).getCustomerName());
                System.out.print("\tAccount No:" + main.getObservers().get(i).getAccountNumber());
                System.out.print("\tAccount Type:" + main.getObservers().get(i).getAccType());
                System.out.print("\tBalance: £" + main.getObservers().get(i).get_balance());
                if (main.getObservers().get(i).isOverdraft() == true) {
                    System.out.print("\tOverdraft : £" + main.getObservers().get(i).getOverdraftLimit());
                }
                System.out.println();
                System.out.println("---------------------------------------------------" + "-----------------------------------------------------------------------------------------" + "");
                System.out.println();
            }
        }
    }

    public void view_transactions(int customerNo) {
        view_accounts(customerNo);
        Scanner input = new Scanner(System.in);
        int accNo = 0;
        System.out.println("Please enter Account Number:");
        try {
            accNo = Integer.parseInt(input.nextLine());
        } catch (Exception e) {
        }
        boolean foundAccount = false;
        for (int i = 0; i < main.getObservers().size(); i++) {
            if (main.getObservers().get(i).getAccountNumber() == accNo) {
                foundAccount = true;
            }
        }
        System.out.println("FoundAccount " + foundAccount);
        if (foundAccount) {
            System.out.println();
            System.out.println();
            System.out.println("Account Number: " + accNo);
            System.out.println();
            System.out.println("Date" + "\t\t\tAction" + "\t\t\tAmount" + "\t\t\tBalance");
            System.out.println("---------------------------------------------------------------------------------------");
            for (int i = 0; i < getTransactions().size(); i++) {
                if (getTransactions().get(i).get(0).getAccountNo() == accNo) {
                    System.out.println(getTransactions().get(i).get(0).getDate() + "\t\t" + getTransactions().get(i).get(0).getAction() + "\t\t£" + getTransactions().get(i).get(0).getAmount() + "\t\t\t£" + getTransactions().get(i).get(0).getBalance());
                }
            }
        } else if (foundAccount == false) {
            System.out.println("Account Does not Exist");
        }
    }

    public void add_account_holder(int customerNo) {
        view_accounts(customerNo);
        Scanner input = new Scanner(System.in);
        int accNo = 0;
        System.out.println("Please enter Account Number:");
        try {
            accNo = Integer.parseInt(input.nextLine());
        } catch (Exception e) {

        }
        boolean found = false;
        for (int s = 0; s < main.getObservers().size(); s++) {
            String newHolder = "";
            BaseAccount account = main.getObservers().get(s);
            if (account.getAccountNumber() == accNo) {
                found = true;
                System.out.println("Please enter new Account Holder's full name:");
                try {
                    newHolder = input.nextLine();
                } catch (Exception e) {
                }
                if (newHolder.isEmpty()) {
                    System.out.println("Error! No name has been entered.");
                    break;
                }
                account.setCustomerName(newHolder);
                System.out.println();
                System.out.println("__________________________________________________________________");
                System.out.println("New Account Holder: " + newHolder + " has been Successfully added: " + account.getAccountNumber());
                System.out.println("__________________________________________________________________");
                System.out.println();
                System.out.println("Press Enter to continue.");
                break;
            }
        }
        if (found == false) {
            System.out.println("Account Number Does Not exist!");
        }
    }

    public void create_new_account(String accountChoice) {
        Scanner input = new Scanner(System.in);
        int customerType = getCustomerType();
        int customerID = 0;
        if (customerType == 1) {
            customerID = createCustomerID();
            String customerName = "";
            CHECK:
            while (customerName.length() == 0) {
                System.out.println("Enter New Customer full name:");
                customerName = input.nextLine();

                if ("".equals(customerName) && customerName == null) {
                    continue CHECK;
                }
            }
            getCustomers().add(new Customer(customerName, customerID));
            getAccountsList().addAccount(customerID, customerName, accountChoice);
            int accountNo = getAccountsList().getAccNumber(customerID);
            ArrayList<Transaction> newTrans = new ArrayList<>();
            newTrans.add(new Transaction(dateFormat.format(date), accountNo, "A/C Created", 0, 0));
            getTransactions().add(newTrans);
            System.out.println();
            System.out.println("__________________________________________________________________");
            System.out.println();
            System.out.println("New Account successfully created.");
            System.out.println("The new customer Id is: " + customerID);
            System.out.println("Customer Name: " + customerName);
            System.out.println("Account Number: " + getAccountsList().getAccNumber(customerID));
            System.out.println("Account Type: " + accountChoice);
            System.out.println();
            System.out.println("__________________________________________________________________");
        } else if (customerType == 2) {
            boolean checkID = true;
            CHECK_ID:
            while (checkID) {
                int existingCus = 0;
                System.out.println();
                System.out.println("Enter existing customer ID:");
                try {
                    existingCus = Integer.parseInt(input.nextLine());
                } catch (Exception e) {
                }
                if (checkCustomerID(existingCus)) {
                    String customerName = "";
                    for (BaseAccount b : main.getObservers()) {
                        if (b.getCustomerNumber() == existingCus) {
                            customerName = b.getCustomerName().toString();
                            System.out.println("Customer Name: " + customerName);
                        }
                    }
                    getAccountsList().addAccount(existingCus, customerName, accountChoice);
                    System.out.println();
                    System.out.println("__________________________________________________________________");
                    System.out.println();
                    System.out.println("New Account successfully created.");
                    System.out.println("Account Type: " + accountChoice);
                    for (BaseAccount a : main.getObservers()) {
                        if (a.getCustomerNumber() == existingCus && a.getAccType() == accountChoice) {
                            System.out.println("Account Number: " + a.getAccountNumber());
                        }
                    }
                    System.out.println();
                    System.out.println("__________________________________________________________________");
                    checkID = false;
                } else {
                    System.out.println("__________________________________________________________________");
                    System.out.println();
                    System.out.println("Customer Number does not exist.");
                    System.out.println();
                    System.out.println("__________________________________________________________________");
                    continue CHECK_ID;
                }
            }
        }

    }

    public void manage_overdraft(int customerNo) {
        view_accounts(customerNo);
        Scanner input = new Scanner(System.in);
        int accNo = 0;
        System.out.println("Please enter Account Number:");
        System.out.println();
        try {
            accNo = Integer.parseInt(input.nextLine());
        } catch (Exception e) {
        }
        boolean accountFound = false;
        for (BaseAccount account : main.getObservers()) {
            if (accNo == account.getAccountNumber()) {
                accountFound = true;
                if (account.isOverdraft() == false) {
                    System.out.println("Overdraft feature cannot be enabled for this account type.");
                    break;
                } else if (account.isOverdraft()) {
                    int choice = 0;
                    for (int s = 0; s < overdraftOptions.length; s++) {
                        System.out.println((s + 1) + ".\t" + overdraftOptions[s]);
                    }
                    System.out.println();
                    System.out.println("Please choose an option");
                    System.out.println();
                    try {
                        choice = Integer.parseInt(input.nextLine());
                    } catch (Exception e) {
                    }
                    switch (choice) {
                        case 1: {
                            account.setOverdraft(true);
                            System.out.println("Overdraft has been enabled.");
                            break;
                        }
                        case 2: {
                            account.setOverdraft(false);
                            ArrayList<Transaction> t = new ArrayList<>();
                            t.add(new Transaction(dateFormat.format(date), account.getAccountNumber(), "Overdraft Removed", (account.getOverdraftLimit() * -1), account.get_balance()));
                            getTransactions().add(t);
                            System.out.println("Overdraft has been disabled.");
                            break;
                        }
                        case 3: {
                            double limit = 0;
                            System.out.println("Please enter new Overdraft Limit:");
                            try {
                                limit = Double.parseDouble(input.nextLine());
                            } catch (Exception e) {
                            }
                            account.setOverdraftLimit(limit);
                            ArrayList<Transaction> t = new ArrayList<>();
                            t.add(new Transaction(dateFormat.format(date), account.getAccountNumber(), "OverDraft Added", limit, account.get_balance()));
                            getTransactions().add(t);
                            System.out.println("New Overdraft limit has been set.");
                            break;
                        }
                        case 4: {
                            break;
                        }
                        default: {
                            System.out.println("Unrecognised choice!");
                            manage_overdraft(customerNo);
                        }
                    }
                }
            }
        }
        if (accountFound == false) {
            System.out.println("Account Number does not exist");
        }
    }

    public void manage_loan(int customerNo) {
        Scanner input = new Scanner(System.in);
        String customerName = "";
        int choice = 0;
        System.out.println();
        System.out.println("Please choose an option:");
        System.out.println();
        System.out.println("1.\tAdd New Loan" + "\n2.\tView Loan Details" + "\n3.\tView Payments history" + "\n4.\tAdd New Payment Details" + "\n5.\tExit");
        try {
            choice = Integer.parseInt(input.nextLine());
        } catch (Exception e) {
        }
        if (choice == 1) {
            double amount = 0;
            double finalAmount = 0;
            double period = 0;
            double interest = 0;
            System.out.println("Please enter amount borrowed:");
            try {
                amount = Double.parseDouble(input.nextLine());
            } catch (Exception e) {
            }
            System.out.println("Please enter period of loan(in years):");
            try {
                period = Double.parseDouble(input.nextLine());
            } catch (Exception e) {
            }
            System.out.println("Please enter rate of interest:");
            try {
                interest = Double.parseDouble(input.nextLine());
            } catch (Exception e) {
            }
            finalAmount = Math.round(amount * Math.pow((1 + interest / 100), period));
            double monthlyPayment = Math.round(finalAmount / (period * 12));
            System.out.println();
            System.out.println();
            ArrayList<Loan> newLoan = new ArrayList<>();
            newLoan.add(new Loan(customerNo, customerName, amount, finalAmount, monthlyPayment));
            getLoans().add(newLoan);
            System.out.println("___________________________________________________________________________");
            System.out.println();
            System.out.println("New Loan Account is successfully set up for Customer Number: " + customerNo);
            System.out.println("Loan Amount: £" + amount);
            System.out.println("Final Amount to paid back: £" + finalAmount);
            System.out.println("Monthly Payment : £" + monthlyPayment);
            System.out.println();
            System.out.println("___________________________________________________________________________");
            manage_loan(customerNo);
        }
        if (choice == 2) {
            if (getLoans().isEmpty()) {
                System.out.println("No Loans Exist for ths customer.");
            } else {
                for (int i = 0; i < getLoans().size(); i++) {
                    ArrayList<Loan> viewLoan = getLoans().get(i);
                    if (viewLoan.get(0).getCustomerNo() == customerNo) {
                        System.out.println();
                        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
                        System.out.println();
                        System.out.print("Customer No.:" + customerNo + "\tCustomer Name: " + viewLoan.get(0).getCustomerName() + "\tAmount Borowed: £" + viewLoan.get(0).getAmountBorrowed() + "\tAmount to pay back: £" + viewLoan.get(0).getFinalAmount() + "\tMonthly Payment: £" + viewLoan.get(0).getMonthlyPayment());
                        System.out.println();
                        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
                        System.out.println();
                    } else {
                        System.out.println("No Loan exists for this customer.");
                        manage_loan(customerNo);
                    }
                }
            }
        }
        if (choice == 3) {
            if (!getLoans().isEmpty()) {
                for (int s = 0; s < getLoans().size(); s++) {
                    boolean foundHistory = false;
                    ArrayList<Loan> viewPayments = getLoans().get(s);
                    if (viewPayments.get(s).getCustomerNo() == customerNo) {
                        ArrayList<Loan.Payments> payments = viewPayments.get(s).getPayments();
                        for (int r = 0; r < payments.size(); r++) {
                            System.out.println();
                            System.out.println("--------------------------------------------------------------------");
                            System.out.println();
                            System.out.print("\tDate : " + payments.get(r).getPaymentDate());
                            System.out.print("Amount : " + payments.get(r).getPaymentAmount());
                            System.out.println();
                            System.out.println("--------------------------------------------------------------------");
                            System.out.println();
                        }
                    }
                    if (foundHistory == false) {
                        System.out.println("There is no payment history yet");
                    }
                }
            } else {
                System.out.println("There is no payment history yet");
            }
            manage_loan(customerNo);
        }
        if (choice == 4) {
            view_accounts(customerNo);
            System.out.println("Please enter Account Number: ");
            int accNo = 0;
            try {
                accNo = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
            }
            boolean foundAccoount = false;
            while (foundAccoount == false) {
                for (int s = 0; s < main.getObservers().size(); s++) {
                    BaseAccount newPayment = main.getObservers().get(s);
                    if (getLoans().size() == 0) {
                        System.out.println("There are no loans set up yet");
                        break;
                    }
                    if (newPayment.getAccountNumber() == accNo) {
                        foundAccoount = true;
                        for (int a = 0; a < getLoans().size(); a++) {
                            ArrayList<Loan> loan = getLoans().get(a);
                            if (loan.get(a).getCustomerNo() == customerNo) {
                                double payment = loan.get(a).getMonthlyPayment();
                                loan.get(0).addPaymentHistory(payment, dateFormat.format(date));
                                main.getObservers().get(s).setBalance(-payment);
                                ArrayList<Transaction> newTransaction = new ArrayList<>();
                                newTransaction.add(new Transaction(dateFormat.format(date), main.getObservers().get(s).getAccountNumber(), "Loan Payment", (payment * -1), main.getObservers().get(s).get_balance()));
                                getTransactions().add(newTransaction);
                                System.out.println("________________________________________________________________________");
                                System.out.println();
                                System.out.println("Payment of £" + payment + " from A/C Number: " + accNo + " is complete");
                                System.out.println();
                                System.out.println("________________________________________________________________________");
                            }
                            break;
                        }
                    }
                }
            }
            if (foundAccoount == false) {
                System.out.println("____________________________________");
                System.out.println();
                System.out.println("Unrecognised Account Number.");
                System.out.println();
                System.out.println("____________________________________");
                return;
            }
        }
        if (choice == 5) {
            return;
        } else {
            manage_loan(customerNo);
        }
    }

//Verifies the type of customer 
    public int getCustomerType() {
        Scanner input = new Scanner(System.in);
        int customerType = 0;
        System.out.println();
        System.out.println("1.\tNew Customer\t2.\tCurrent Customer");
        try {
            customerType = Integer.parseInt(input.nextLine());
        } catch (Exception e) {
        }
        if (customerType < 1 || customerType > 2) {
            System.out.println("Unrecognised choice!");
            getCustomerType();
        }
        return customerType;
    }
//Create a new customer number 

    public int createCustomerID() {
        int id = 0;
        if (getCustomers().size() > 0) {
            id = getCustomers().get(getCustomers().size() - 1).getCustomerID();
            id++;
        } else {
            id = 1;
        }
        return id;
    }
//Check if customer number exists 

    public boolean checkCustomerID(int id) {
        boolean exists = false;
        for (Customer c : getCustomers()) {
            if (c.getCustomerID() == id) {
                exists = true;
            }
        }
        return exists;
    }

    /**
     * * @return the transactions
     */
    public ArrayList<ArrayList<Transaction>> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<ArrayList<Transaction>> transactions) {
        this.transactions = transactions;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public ArrayList<ArrayList<Loan>> getLoans() {
        return loans;
    }

    public void setLoans(ArrayList<ArrayList<Loan>> loans) {
        this.loans = loans;
    }

    public BankAccounts getAccountsList() {
        return accountsList;
    }

    public void setAccountsList(BankAccounts accountsList) {
        this.accountsList = accountsList;
    }

    //Create temporary customers and accounts for testing purposes 
    public void createTempAccounts() {
        int customerID_1 = 1;

        String customer_1 = "Adam Johnson";
        int customerID_2 = 2;
        String customer_2 = "Mark Thompson";
        int customerID_3 = 3;
        String customer_3 = "Ali Malik";

        getCustomers().add(new Customer(customer_1, customerID_1));
        getAccountsList().addAccount(customerID_1, customer_1, "Current Account");
        getCustomers().add(new Customer(customer_2, customerID_2));
        getAccountsList().addAccount(customerID_2, customer_2, "Student Account");
        getCustomers().add(new Customer(customer_3, customerID_3));
        getAccountsList().addAccount(customerID_3, customer_3, "Savings Account");
        main.getObservers().get(0).setBalance(-100);
        main.getObservers().get(1).setBalance(1000);
        main.getObservers().get(2).setBalance(1000);
    }
}
