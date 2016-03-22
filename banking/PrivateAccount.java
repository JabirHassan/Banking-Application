
package banking;

class PrivateAccount extends BaseAccount {

    public PrivateAccount(int custNumber, String custName, int accNumber, String accType) {
        super(accNumber, custName, custNumber, accType);
    }
}
