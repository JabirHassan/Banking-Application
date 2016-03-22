
package banking;

public class Customer {

    private String customerName;
    private int customerID;

    public Customer() {
    }

    public Customer(String name, int id) {
        this.customerName = name;
        this.customerID = id;
    
}
    /**
     * * @return the custName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * * @param custName the custName to set
     */
    public void setCustomerName(String custName) {
        this.customerName = custName;
    }

    /**
     * * @return the CustomerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * * @param CustomerID the CustomerID to set
     */
    public void setCustomerID(int CustomerID) {
        this.customerID = CustomerID;
    }
}
