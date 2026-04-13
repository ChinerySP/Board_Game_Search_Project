package model;
import java.util.ArrayList;
import model.*;


public class User {

    public User() {
        gameLists = new ArrayList<GameList>();
        gameLists.add(new GameList("Favorites"));
        password = "password";
    }

//    public

    /**
     * A collection of GameLists specific to the user
     */
    public ArrayList<GameList> getGameLists() {
        return gameLists;
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
    public boolean login(String guess) {
        if (guess.equals(password)) {
            return true;
        }
        else return false;
    }

    /**
     * Uses View to prompt user if they would like to save data
     * upon logout
     * @return User either chooses to logout with our without saving data
     * True for save, False for not saved
     */
    public boolean logout() {
        return true;
    }

    /**
     * User data is loaded
     * @return
     */
    public boolean loadUserData() {
        return true;
    }

    /**
     * Used by logout() to save user data upon logout based
     *  on user choice
     * @return True for save data and False for not saved
     */
    public boolean saveUserData() {
        return true;
    }

    private ArrayList<GameList> gameLists;
    private String password;
    private Config config;
}