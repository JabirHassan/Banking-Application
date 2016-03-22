package banking;

import java.util.Arrays;
import java.util.Scanner;

public class Menu {

    private static Scanner input = new Scanner(System.in);
    private Operations operations;
    private String[] options = {"Check Balance", "Withdraw", "Deposit", "Transfer Money", "View Accounts", "View Transactions", "Add Account Holder", "Create New Account", "Manage Overdraft", "Manage Loan", "Log Out"};
    private String[] accountType = {"Current Account", "Savings Account", "Business Account", "SMB Account", "Student Account", "IR Account", "High Interest Account", "Islamic Account", "Private Account", "LCR Account"};

    public Menu() {
        operations = Operations.get_instance();
        userLogin();
    }

    public void userLogin() {
        System.out.println("Press Enter to Log in");
        try {
            Scanner keyboard = new Scanner(System.in);
            keyboard.nextLine();
        } catch (Exception e) {
        }
        int userID = 0;
        System.out.println("Please enter your user ID:");
        try {
            userID = Integer.parseInt(input.nextLine());
        } catch (Exception e) {
        }
        int userPIN = 0;
        System.out.println("Please enter your PIN");
        try {
            userPIN = Integer.parseInt(input.nextLine());
        } catch (Exception e) {
        }
        boolean checkLogin = operations.login.checkUserLogin(userID, userPIN);
        if (checkLogin == false) {
            System.out.println("You have entered invalid Login details. Please try again ");
            userLogin();
        } else if (checkLogin == true) {
            String name = operations.login.getUserName(userID);
            System.out.println();
            System.out.println("Welsome " + name);
            System.out.println();
            mainMenu(userID);
        }
    }

    //Display main menu 
    public void mainMenu(int userID) {
        int id = userID;
        int customerNo = 0;
        int option = 0;
        String accountChoice = "";
        for (int i = 1; i < options.length + 1; i++) {
            System.out.println((i) + ".\t" + options[i - 1]);
        }
        System.out.println("Please make a choice");
        try {
            option = Integer.parseInt(input.nextLine());
        } catch (Exception e) {
        }
        if (option < 1 || option > options.length) {
            System.out.println("-------------------------------------");
            System.out.println();
            System.out.println("Error! Unrecognised Choice");
            System.out.println();
            System.out.println("-------------------------------------");
            mainMenu(id);
        }
        if (option == 11) {
            System.exit(0);
        }

        if (option != 8) {
            customerNo = getCustomerNo();
            if (customerNo > 0) {
                operations.doOperation(customerNo, option, accountChoice);
            }
        } else if (option == 8) {
            boolean foundType = false;
            while (foundType == false) {
                accountChoice = accountTypes();
                if (Arrays.asList(accountType).contains(accountChoice)) {
                    operations.doOperation(1, option, accountChoice);
                    foundType = true;
                } else {
                    System.out.println("Unrecognised Choice! Please try again");
                }
            }
        }

        System.out.println();

        System.out.println("Press Enter to return to Main Menu..");
        try {
            Scanner keyboard = new Scanner(System.in);
            keyboard.nextLine();
        } catch (Exception e) {
        }
        mainMenu(userID);
    }
//Display account types 

    public String accountTypes() {
        Scanner input = new Scanner(System.in);
        boolean found = true;
        int accountChoice = 0;
        String type = "";
        System.out.println("Please choose an account type:");
        System.out.println("--------------------------------");
        for (int i = 0; i < accountType.length; i++) {
            System.out.println((i + 1) + ".\t " + accountType[i]);
        }

            try {
                accountChoice = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
            }
            switch (accountChoice) {
                case (1): {
                    type = accountType[0];
                    break;
                }
                case (2): {
                    type = accountType[1];
                    break;
                }
                case (3): {
                    type = accountType[2];
                    break;
                }
                case (4): {
                    type = accountType[3];
                    break;
                }
                case (5): {
                    type = accountType[4];
                    break;
                }
                case (6): {
                    type = accountType[5];
                    break;
                }
                case (7): {
                    type = accountType[6];
                    break;
                }
                case (8): {
                    type = accountType[7];
                    break;
                }
                case (9): {
                    type = accountType[8];
                    break;
                }
                case (10): {
                    type = accountType[9];
                    break;
                }
            }
        
        return type;
    }

    //check and return customer number 
    public int getCustomerNo() {
        int custNo = 0;
        boolean check = true;
        while (check) {
            System.out.println("Please enter Customer Number..");
            int customerNo = 0;
            try {
                customerNo = Integer.parseInt(input.nextLine());
            } catch (Exception e) {

            }
            if (operations.checkCustomerID(customerNo) == true) {
                custNo = customerNo;
                check = false;
            } else {
                System.out.println("This customer number is invalid");
                System.out.println();
                System.out.println("1. Try again\t\t2. Exit");
                int option = 0;
                try {
                    option = Integer.parseInt(input.nextLine());
                } catch (Exception e) {
                }
                if (option != 1) {
                    break;
                }
            }
        }
        return custNo;
    }
}
