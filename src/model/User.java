package model;
import java.util.HashMap;
import java.util.ArrayList;
import model.*;

public class User {

    /**
     * Default constructor for the User class
     * userName is set to TJS and password is set to "password"
     */
    public User() {
        userLists = new ArrayList<GameList>();
        userRatings = new HashMap<Integer, Rating>();
        this.config = new Config();
        this.password = "password";
        this.userName = "TJS";
    }

    /**
     * Creates a new User with information provided to function
     * @param user Contains the username that the new user will have
     * @param password Contains the password that will be associated with the user
     */
    public User(String user, String password) {
        userLists = new ArrayList<GameList>();
        userRatings = new HashMap<Integer, Rating>();
        this.config = new Config();
        this.password = password;
        this.userName = user;
    }

    /**
     * Getter that returns the current username
     * @return returns userName variabe
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Changes the current Username variable
     * @param newName The new username that will be assigned to the variable userName
     */
    public void setUserName(String newName) {
        userName = newName;
    }

    /**
     * Gives the current password associated with the user
     * @return Returns the password variable
     */
    public String getPassword() {
        return password;
    }

    /**
     * Changes the current password variable
     * @param newPassword the new password that will be assigned the variable "password"
     */
    public void resetPassword(String newPassword) {
        password = newPassword;
    }

    /**
     * Adds a new list of games associated with the current user
     * @param listName The name of the game list that is being created
     */
    public void addGameList(String listName) {
        userLists.add(new GameList(listName));
    }

    /**
     * Adds a currently created game list to the array "userLists"
     * @param toAdd The GameList that will be added to the array
     */
    public void addGameList(GameList toAdd) {
        userLists.add(toAdd);
    }

    /**
     * Changes the current state of the API activation
     * @param state The state that will be assigned to "apiActive"
     *              True for active, false for inactive
     */
    public void setAPI(Boolean state) {
        config.setAPIActive(state);
    }

    /**
     * Gives the current list of games associated with the user
     * @return Returns the array of game lists labeled "userLists"
     */
    public ArrayList<GameList> getGameLists() {
        return userLists;
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

    /**
     * Changes the current state of "isDark" in the Config class
     * @param state The state that will be assigned to variable "isDark"
     */
    public void setDarkMode(boolean state) {
        config.setDarkMode(state);
    }

    /**
     * Gives the current state of the "isDark" variable
     * @return Returns "isDark" from the config class
     */
    public boolean getDarkMode() {
        return config.isDarkMode();
    }

    /**
     * Gives the current status of the API
     * @return Returns the state of
     */
    public boolean getAPI() {
        return config.isApiActive();
    }

    /**
     * Gives the rating for a game specified by input "game"
     * @param game Game that is used to retrieve rating information from
     * @return Returns the rating related to "game"
     */
    public Rating getRating(Game game) {
        return userRatings.get(game.getId());
    }

    /**
     * Sets a rating for a game specified by "game"
     * @param game The game that a rating will be assigned to
     * @param newRating The new rating that will be assigned to "game"
     */
    public void setRating(Game game, Rating newRating) {
        userRatings.put(game.getId(), newRating);
    }

    @Deprecated
    public boolean logout() {
        return true;
    }

    // The following commands are way more databasey
    @Deprecated
    public boolean loadUserData() {
        return true;
    }

    @Deprecated
    public boolean saveUserData() {
        return true;
    }

    private HashMap<Integer, Rating> userRatings;
    private ArrayList<GameList> userLists;
    private String password;
    private Config config;
    private String userName;
}