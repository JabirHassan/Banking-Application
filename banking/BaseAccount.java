
package banking;

import java.util.ArrayList;

public class BaseAccount implements Account {

    private int accountNumber = 0;
    private int customerNumber = 0;
    private ArrayList<String> customerName;
    private String accType;
    private double balance = 0.00;
    private double dailyAllowance = 0;
    private double dailyWithdrawal = 0;
    private double overdraftLimit = 0;
    private boolean overdraft;
    private boolean addInterest;
    private double interestRate = 0;

    public BaseAccount(int accNumber, String custName, int custNumber, String accType) {
        customerName = new ArrayList<>();
        this.accountNumber = accNumber;
        this.customerNumber = custNumber;
        this.customerName.add(custName);
        this.accType = accType;
    }

    public double get_balance() {
        return balance;
     }

    /**
     * * @return the accountNumber
     */
    public int getAccountNumber() {
        return this.accountNumber;
    }

    /**
     * * @param accountNumber the accountNumber to set
     */
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * * @return the customerNumber
     */
    public int getCustomerNumber() {
        return customerNumber;
    }

    /**
     * * @param customerNumber the customerNumber to set
     */
    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    /**
     * * @return the CustomerName
     */
    public ArrayList<String> getCustomerName() {
        return customerName;
    }

    /**
     * * @param CustomerName the CustomerName to set
     */
    public void setCustomerName(String CustomerName) {
        this.customerName.add(CustomerName);
    }

    /**
     * * @return the accType
     */
    public String getAccType() {
        return accType;
    }
    /**
     * * @param accType the accType to set
     */
    public void setAccType(String accType) {
        this.accType = accType;
    }

    /**
     * * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.balance += balance;
    }

    /**
     * * @return the dailyAllowance
     */
    public double getDailyAllowance() {
        return dailyAllowance;
    }

    /**
     * * @param dailyAllowance the dailyAllowance to set
     */
    public void setDailyAllowance(double dailyAllowance) {
        this.dailyAllowance = dailyAllowance;
    }

    /**
     * * @return the overdraftLimit
     */
    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    /**
     * * @param overdraftLimit the overdraftLimit to set
     */
    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    /**
     * * @return the overdraft
     */
    public boolean isOverdraft() {
        return overdraft;
    }
    /**
     * * @param overdraft the overdraft to set
     */
    public void setOverdraft(boolean overdraft) {
        this.overdraft = overdraft;
    }

    /**
     * * @return the addInterest
     */
    public boolean isAddInterest() {
        return addInterest;
    }

    /**
     * * @param addInterest the addInterest to set
     */
    public void setAddInterest(boolean addInterest) {
        this.addInterest = addInterest;
    }

    /**
     * * @return the dailyWithdrawal
     */
    public double getDailyWithdrawal() {
        return dailyWithdrawal;
    }

    /**
     * * @param dailyWithdrawal the dailyWithdrawal to set
     */
    public void setDailyWithdrawal(double dailyWithdrawal) {
        this.dailyWithdrawal = dailyWithdrawal;
    }

    /**
     * * @return the interestRate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * * @param interestRate the interestRate to set
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public void updateAccount() {
        
    }
    }
