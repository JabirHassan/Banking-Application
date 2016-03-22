
package banking;

public class CurrentAccount extends BaseAccount {

    public CurrentAccount(int custNumber, String custName, int accNumber, String accType) {
        super(accNumber, custName, custNumber, accType);
    }
}
