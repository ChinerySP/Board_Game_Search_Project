package control;

import view.*;
import model.*;

public class Control {
    
    /**
     * Constructor, begins the app itself
     */
    public Control() {

        // Initializing the other two branches
        view = new View(this);
        model = new Model(this);

        // Beginning the view by asking for the login from the view
        view.promptLogin();

    }

    /**
     * Verifies the login information, and if it matches, tells the view to 
     * open the dashboard
     * @param String username The username of the user logging in 
     * @param String password The password to check against
     * @return User The user that logged in; null if there was no match
     */
    public User verifyLogin(String username, String password) {
        return model.verifyLogin(username, password);
    }

    /**
     * A forgot password method, skips verifying the login with a password
     * @param 
     */
    public User forgotPassword(String username) {
        return model.getUser(username);
    }

    /**
     * Tells the database to delete the inputted user
     * @param User The user to delete
     */
    public void deleteAccount(User toDelete) {
        model.deleteAccount(toDelete);
    }

    /**
     * Gives a list of searching keywords to the database as a search querie.
     * @param String[] keywords The keywords that we are searching for
     * @return GameList A ordered GameList holding all of the results of the search
     */
    public GameList search(String[] keywords) {
        return model.search(keywords);
    }

    /**
     * Tells the database to add a new user
     * @param User The user to add 
     */
    public void newUser(User newUser) {
        model.newUser(newUser);
    }

    /**
     * Tells the database to save the data that has been changed since it was last saved
     */
    public void saveData() {
        // TODO Make this actually do something in the model
        System.out.println("Saving is not implemented yet, but the Controller did try to save it.");
    }


    /**
     * Tells the model to turn the API to a specified state
     * @param boolean The state to set the API to
     */
    public void setAPI(boolean state) {
        model.setAPI(state);
    }

    // The view and the model, so that we can interface with them
    private View view;
    private Model model;

}
