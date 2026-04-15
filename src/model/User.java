package model;
import java.util.ArrayList;
import model.*;

public class User {

    public User() {
        gameLists = new ArrayList<GameList>();
        gameLists.add(new GameList("Favorites"));
        this.password = "password";
        this.userName = "TJS";
    }

    // Getters and setters for username and password

    public String getUserName() {
        return userName;
    }

    public void setUserName(String newName) {
        userName = newName;
    }

    public String getPassword() {
        return password;
    }

    public void resetPassword(String newPassword) {
        password = newPassword;
    }

    public void addGameList(String listName) {
        gameLists.add(new GameList(listName));
    }

    public void setAPI(Boolean state) {
        config.setAPIActive(state);
    }

    public ArrayList<GameList> getGameLists() {
        return gameLists;
    }

    /**
     * The user will input a password in order to log in
     * to their account
     * @param guess The password input by the user
     * @return True if the password is correct, False if incorrect
     */
    public boolean login(String guess) {
        return guess.equals(password);
    }

    public boolean logout() {

        return true;
    }

    public boolean loadUserData() {
        return true;
    }

    public boolean saveUserData() {
        return true;
    }

    private ArrayList<GameList> gameLists;
    private String password;
    private Config config;
    private String userName;
}