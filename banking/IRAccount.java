
package banking;

class IRAccount extends BaseAccount {

    public IRAccount(int custNumber, String custName, int accNumber, String accType) {
        super(accNumber, custName, custNumber, accType);
    }
}
