
package banking;

class StudentAccount extends BaseAccount {

    public StudentAccount(int custNumber, String custName, int accNumber, String accType) {
        super(accNumber, custName, custNumber, accType);
    }
}
