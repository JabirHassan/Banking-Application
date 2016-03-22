
package banking;

import java.util.ArrayList;
import java.util.Date;

public class Loan {

    private double interest = 0.15;
    private int customerNo = 0;
    private String customerName = "";
    private double amountBorrowed = 0;
    private double finalAmount = 0;
    private double monthlyPayment = 0;
    public ArrayList<Payments> payments = new ArrayList<>();

    public Loan(int custNo, String custName, double amountBorrowed, double finalAmount, double monthlyPayment) {
        this.customerNo = custNo;
        this.customerName = custName;
        this.amountBorrowed = amountBorrowed;
        this.monthlyPayment = monthlyPayment;
        this.finalAmount = finalAmount;
    }

    /**
     * * @return the interest
     */
    public double getInterest() {
        return interest;
    }

    /**
     * * @param interest the interest to set
     */
    public void setInterest(double interest) {
        this.interest = interest;
    }

    /**
     * * @return the monthlyPayment
     */
    public double getMonthlyPayment() {
        return monthlyPayment;
    }
    /**
     * * @param monthlyPayment the monthlyPayment to s
     et
*/
    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    /**
     * * @return the payments
     */
    public ArrayList<Payments> getPayments() {
        return payments;
    }

    /**
     * * @param payments the payments to set
     */
    public void setPayments(ArrayList<Payments> payments) {
        this.payments = payments;
    }

    public void addPaymentHistory(double amount, String date) {
        payments.add(new Payments(amount, date));
    } 
    //Inner class for holding payments details 
    public class Payments {

        private double paymentAmount = 0;
        private String paymentDate;
        private double Balance = finalAmount - paymentAmount;

        public Payments(double paymentAmount, String date) {
            this.paymentAmount = paymentAmount;
            this.paymentDate = date;
        }

        /**
         * * @return the paymentAmount
         */
        public double getPaymentAmount() {
            return paymentAmount;
        }

        /**
         * * @param paymentAmount the paymentAmount to set
         */
        public void setPaymentAmount(double paymentAmount) {
            this.paymentAmount = paymentAmount;
        }
        /**
         * * @return the paymentDate*/
        public String getPaymentDate() {
            return paymentDate;
        }

        /**
         * * @param paymentDate the paymentDate to set
         */
        public void setPaymentDate(String paymentDate) {
            this.paymentDate = paymentDate;
        }

        /**
         * * @return the Balance
         */
        public double getBalance() {
            return Balance;
        }
    }

    /**
     * * @return the customerNo
     */
    public int getCustomerNo() {
        return customerNo;
    }

    /**
     * * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * * @return the amountBorrowed
     */
    public double getAmountBorrowed() {
        return amountBorrowed;
    }

    /**
     * * @return the finalAmount
     */
    public double getFinalAmount() {
        return finalAmount;
    }
}
