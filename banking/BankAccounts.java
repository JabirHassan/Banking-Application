package banking;

import java.util.ArrayList;

public class BankAccounts implements Account {

    private static BankAccounts accounts_instance;
    private BankMainAccount mainAccount = BankMainAccount.getInstance();
    private int accNum = 100;

    private BankAccounts() {
    }
//Return a singleton Accounts class 

    public static BankAccounts getInstance() {
        if (accounts_instance == null) {
            accounts_instance = new BankAccounts();
        }
        return accounts_instance;
    }

    //Add accounts to list 
    public void addAccount(int customerID, String customerName, String accountType) {
        switch (accountType) {
            case "Current Account": {
                accNum++;
                ArrayList<BaseAccount> accounts = new ArrayList<>();
                BaseAccount newAccount = new CurrentAccount(customerID, customerName, accNum, "Current Account");
                mainAccount.registerAccount(newAccount);
                accounts.add(newAccount);
                accounts.get(0).setDailyAllowance(300);
                accounts.get(0).setOverdraft(true);
                accounts.get(0).setAddInterest(true);
                accounts.get(0).setInterestRate(0.015);
                break;
            }
            case "Savings Account": {
                accNum++;
                ArrayList<BaseAccount> accounts = new ArrayList<BaseAccount>();
                BaseAccount newAccount = new CurrentAccount(customerID, customerName, accNum, "Savings Account");
                mainAccount.registerAccount(newAccount);
                accounts.add(newAccount);
                accounts.get(0).setDailyAllowance(300);
                accounts.get(0).setOverdraft(false);
                accounts.get(0).setAddInterest(true);
                accounts.get(0).setInterestRate(0.03);
                break;
            }
            case "Business Account": {
                accNum++;
                ArrayList<BaseAccount> accounts = new ArrayList<BaseAccount>();
                BaseAccount newAccount = new BusinessAccount(customerID, customerName, accNum, "Business Account");
                mainAccount.registerAccount(newAccount);
                accounts.add(newAccount);
                accounts.get(0).setDailyAllowance(500);
                accounts.get(0).setOverdraft(true);
                accounts.get(0).setAddInterest(true);
                accounts.get(0).setInterestRate(0.015);
                //getBank().add(accounts);
                break;
            }
            case "SMB Account": {
                accNum++;
                ArrayList<BaseAccount> accounts = new ArrayList<BaseAccount>();
                BaseAccount newAccount = new SMBAccount(customerID, customerName, accNum, "Small To Medium Business Account");
                mainAccount.registerAccount(newAccount);
                accounts.add(newAccount);
                accounts.get(0).setDailyAllowance(500);
                accounts.get(0).setOverdraft(true);
                accounts.get(0).setAddInterest(true);
                accounts.get(0).setInterestRate(0.015);
                break;
            }
            case "Student Account": {
                accNum++;
                ArrayList<BaseAccount> accounts = new ArrayList<BaseAccount>();
                BaseAccount newAccount = new StudentAccount(customerID, customerName, accNum, "Student Account");
                mainAccount.registerAccount(newAccount);
                accounts.add(newAccount);
                accounts.get(0).setDailyAllowance(300);
                accounts.get(0).setOverdraft(true);
                accounts.get(0).setAddInterest(true);
                accounts.get(0).setInterestRate(0.015);
                break;
            }
            case "IR Account": {
                accNum++;
                ArrayList<BaseAccount> accounts = new ArrayList<BaseAccount>();
                BaseAccount newAccount = new IRAccount(customerID, customerName, accNum, "IR Account");
                mainAccount.registerAccount(newAccount);
                accounts.add(newAccount);
                accounts.get(0).setAddInterest(true);
                accounts.get(0).setOverdraft(true);
                accounts.get(0).setInterestRate(0.015);
                break;
            }
            case "High Interest Account": {
                accNum++;
                ArrayList<BaseAccount> accounts = new ArrayList<BaseAccount>();
                BaseAccount newAccount = new HIAccount(customerID, customerName, accNum, "High Interest Account");
                mainAccount.registerAccount(newAccount);
                accounts.add(newAccount);
                accounts.get(0).setDailyAllowance(300);
                accounts.get(0).setOverdraft(true);
                accounts.get(0).setAddInterest(true);
                accounts.get(0).setInterestRate(0.05);
                break;
            }
            case "Islamic Account": {
                accNum++;
                ArrayList<BaseAccount> accounts = new ArrayList<BaseAccount>();
                BaseAccount newAccount = new IslamicAccount(customerID, customerName, accNum, "Islamic Account");
                mainAccount.registerAccount(newAccount);
                accounts.add(newAccount);
                accounts.get(0).setDailyAllowance(300);
                accounts.get(0).setOverdraft(true);
                accounts.get(0).setAddInterest(false);
                break;
            }
            case "Private Account": {
                accNum++;
                ArrayList<BaseAccount> accounts = new ArrayList<BaseAccount>();
                BaseAccount newAccount = new PrivateAccount(customerID, customerName, accNum, "Private Account");
                mainAccount.registerAccount(newAccount);
                accounts.add(newAccount);
                accounts.get(0).setDailyAllowance(300);
                accounts.get(0).setOverdraft(true);
                accounts.get(0).setAddInterest(true);
                accounts.get(0).setInterestRate(0.015);
                break;
            }
            case "LCR Account": {
                accNum++;
                ArrayList<BaseAccount> accounts = new ArrayList<BaseAccount>();
                BaseAccount newAccount = new LCRAccount(customerID, customerName, accNum, "Low Credit Rating Account");
                mainAccount.registerAccount(newAccount);
                accounts.add(newAccount);
                accounts.get(0).setDailyAllowance(300);
                accounts.get(0).setOverdraft(false);
                accounts.get(0).setAddInterest(true);
                accounts.get(0).setInterestRate(0.015);
                break;
            }
        }
    }

    //Return accounts number 
    public int getAccNumber(int customerNo) {
        int accNumber = 0;
        for (int i = 0; i < mainAccount.getObservers().size(); i++) {
            if (mainAccount.getObservers().get(i).getCustomerNumber() == customerNo) {
                accNumber = mainAccount.getObservers().get(i).getAccountNumber();
            }
        }
        return accNumber;
    }

    //Check account number 
    public BaseAccount checkAccNo(int accNo) {
        BaseAccount found = null;
        for (BaseAccount b : mainAccount.getObservers()) {
            if (b.getAccountNumber() == accNo) {
                found = b;
            }
        }
        return found;
    }

    @Override
    public void updateAccount() {
    }
}
