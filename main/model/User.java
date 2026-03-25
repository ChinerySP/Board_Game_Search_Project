package main.model;

public class User {

    /**
     * A collection of GameLists specific to the user
     */
    public GameLists() {

    }

    /**
     * Used to reset the current password for the user
     * @param newPassword The new password chosen by the user
     */
    public void resetPassword(String newPassword) {
        password = newPassword;
    }

    /**
     * The user will input a password in order to log in
     * to their account
     * @param guess The password input by the user
     * @return True if the password is correct, False if incorrect
     */
    public bool login(String guess) {
        if (guess == password) {
            return True;
        }
        else return False;
    }

    /**
     * Uses View to prompt user if they would like to save data
     * upon logout
     * @return User either chooses to logout with our without saving data
     * True for save, False for not saved
     */
    public bool logout() {

    }

    /**
     * User data is loaded
     * @return
     */
    public bool loadUserData() {

    }

    /**
     * Used by logout() to save user data upon logout based
     *  on user choice
     * @return True for save data and False for not saved
     */
    public bool saveUserData() {

    }

    private String password;
    private Config config;
}