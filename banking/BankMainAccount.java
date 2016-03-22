
package banking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BankMainAccount implements Bank {

    private static BankMainAccount mainAccount_instance;
    private double bankBalance = 10000;
    private ArrayList<ArrayList<Transaction>> main_transactions = new ArrayList<>();
    private ArrayList<BaseAccount> observers;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy"); 
    private Date date = new Date();
    private double charge = 30;

    private BankMainAccount() {
        observers = new ArrayList();
    } 
    //Return a singleton BankMainAccount class 
    public static BankMainAccount getInstance() {
        if (mainAccount_instance == null) {
            mainAccount_instance = new BankMainAccount();
        }
        return mainAccount_instance;
    } 
    //Add accounts to observers list 
    @Override
    public void registerAccount(BaseAccount newAccount) {
        getObservers().add(newAccount);
    } 
    //remove accounts from observers list 
    @Override
    public void removerAccount(BaseAccount oldAccount) {
        int index = getObservers().indexOf(oldAccount);
        getObservers().remove(index);
    } 
    //update all accoounts in observers list 
    @Override
    public void updateAccount() {
        for (BaseAccount o : observers) {
            if (o.get_balance() > 0 && o.isAddInterest()) {
                o.setBalance((o.get_balance() * o.getInterestRate()));
                bankBalance -= (o.get_balance() * o.getInterestRate());
                ArrayList<Transaction> t = new ArrayList<>();
                t.add(new Transaction(dateFormat.format(date), o.getAccountNumber(), "Interest", (o.get_balance() * o.getInterestRate()), o.get_balance()));
                getMain_transactions().add(t);
            } else if (o.get_balance() < 0) {
                o.setBalance(-charge);
                bankBalance += charge;
                ArrayList<Transaction> t = new ArrayList<>();
                t.add(new Transaction(dateFormat.format(date), o.getAccountNumber(), "O/D charge", -charge, o.get_balance()));
                getMain_transactions().add(t);
            }
        }
    }
    /**
     * * @return the observers
     */
    public ArrayList<BaseAccount> getObservers() {
        return observers;
    }

    /**
     * * @param observers the observers to set
     */
    public void setObservers(ArrayList<BaseAccount> observers) {
        this.observers = observers;
    }

    /**
     * * @return the main_transactions
     */
    public ArrayList<ArrayList<Transaction>> getMain_transactions() {
        return main_transactions;
    }
}
