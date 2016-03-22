
package banking;
public class BankClerk {

    private String userName;
    private int userID;
    private int userPIN;

    public BankClerk() {
    }

    public BankClerk(String name, int userID, int pin) {
        this.userName = name;
        this.userID = userID;
        this.userPIN = pin;
    }

    /**
     * * @return the userID
     */
    public int getUserID() {
        return userID;
    }
    /**
     * * @param userID the userID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * * @return the userPIN
     */
    public int getUserPIN() {
        return userPIN;
    }

    /**
     * * @param userPIN the userPIN to set
     */
    public void setUserPIN(int pin) {
        this.userPIN = pin;
    }

    /**
     * * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
