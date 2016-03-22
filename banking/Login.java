
package banking;
import java.util.ArrayList;

public class Login {

    public ArrayList<BankClerk> bankclerks = new ArrayList<>();

    public Login() {
        bankclerks.add(new BankClerk("Adam Stevenson", 1, 123));
    }

    public boolean checkUserLogin(int id, int pin) {
        if (checkID(id) && checkPIN(pin)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkID(int userID) {
        boolean id = true;
        for (BankClerk b : bankclerks) {
            if (b.getUserID() == userID) {
                id = true;
            } else {
                id = false;
            }
        }
        return id;
    }

    public boolean checkPIN(int pin) {
        boolean pass = true;
        for (BankClerk b : bankclerks) {
            if (b.getUserPIN() == pin) {
                pass = true;
            } else {
                pass = false;
            }
        }
        return pass;
    }

    public String getUserName(int userID) {
        String name = "";
        for (BankClerk b : bankclerks) {
            if (b.getUserID() == userID) {
                name = b.getUserName();
            }
        }
        return name;
    }
}
