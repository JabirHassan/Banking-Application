
package banking;

import java.util.Date;

public class Transaction {

    private String date;
    private int AccountNo = 0;
    private String action = "";
    private double amount = 0;
    private double balance = 0;

    public Transaction(String date, int accNumber, String action, double amount, double balance) {
        this.date = date;
        this.AccountNo = accNumber;
        this.action = action;
        this.amount = amount;
        this.balance = balance;
    }

    /**
     * * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * * @return the AccountNo
     */
    public int getAccountNo() {
        return AccountNo;
    }

    /**
     * * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * * @return the balance
     */
    public double getBalance() {
        return balance;
    }
}
