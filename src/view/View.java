package view;

import javax.swing.JOptionPane;

import control.*;
import model.*;
import view.panle.DashboardPanle;
import view.panle.SearchResultsPanle;

/**
 * The main class that interfaces with the rest of the view
 * @author Sam Whitlock
 */
public class View {

    /**
     * The main constructor that starts up the view
     */
    public View(Control controller) {

        // Saving the controller
        this.controller = controller;

        // Instaniating the screen
        screen = new Screen(this);

        // Defaulting to no User logged in
        activeUser = null;

    }
    
    /**
     * Prompts the user for a login
     */
    public void promptLogin() {
        screen.hidePanle("sticky");
        screen.showPanle("login");
    }
    
    /**
     * Tells the controller that login information has been submitted
     * To be called when the login button is clicked
     * @param String username The username being signed into
     * @param String password The password to authenticate the username
     */
    public void submitLogin(String username, String password) {

        // Trying to log in
        User loggingInUser = controller.verifyLogin(username, password);

        if (loggingInUser == null) {
            // The login failed

            // Telling the user that the login failed
            JOptionPane.showMessageDialog(screen.getFrame(),
                    "The username and password did not match, please try again.", "Login Failed",
                    JOptionPane.WARNING_MESSAGE);
            
        } else {
            // Login successful

            // Saving the user for later use without querying the database
            activeUser = loggingInUser;

            // Telling the dashboard which user's info to display
            ((DashboardPanle) screen.getPanle("dashboard")).setUser(loggingInUser);

            // Updating everything to the new Panle
            screen.showPanle("dashboard");
            screen.showPanle("sticky");

            // Add this line to refresh components once they are on the screen
            screen.refreshPanles();

        }

    }

    /**
     * Tells the controller to write the data to the file
     */
    public void saveData() {
        controller.saveData();
    }

    
    /**
     * A wrapper function that tells the Screen this View is holding to show a panle
     * @param String The name of the panle to show
     */
    public void showPanle(String name) {
        screen.showPanle(name);
    }

    /**
     * Tells the controller that the user has requested to change the API state
     * @param boolean The state to set the API to 
     */
    public void setAPI(boolean state) {
        controller.setAPI(state);
    }

    /**
     * Tells the controller that the User wants to search for specific keywords
     * Then shows those search results to the user in the search results panle
     * @param String[] The keywords to search for
     */
    public void search(String[] keywords) {

        ( (SearchResultsPanle) screen.getPanle("searchresults") ).setGameList(controller.search(keywords));
        showPanle("searchresults");            

    }

    /**
     * Tells the controller that that User needs to be logged in
     * @param String The username of the user to be logged in
     */
    public User forgotPassword(String username) {

        // Requesting the user
        User toLogin = controller.forgotPassword(username);

        if (toLogin == null) {

            // Tell them that they couldn't find the user with that username
            JOptionPane.showMessageDialog(
                    screen.getFrame(),
                    "A user with that username could not be found",
                    "User Not Found",
                    JOptionPane.ERROR_MESSAGE);

        } else {

            // Updating the stored information
            activeUser = toLogin;

            // Showing the dashboard
            showPanle("dashboard");
            showPanle("sticky");

            return toLogin;
        }
        return null;
    }

    /**
     * Tells the controller to delete the specified user
     * @param User The user to be deleted
     */
    public void deleteAccount(User toDelete) {
        controller.deleteAccount(toDelete);
    }
    
    /**
     * Redraws and revalidates all of the panles to fix any bugs or update everything
     */
    public void updateTheme() {
        screen.updateTheme();
    }

    /**
     * Logs the user out of the program
     */
    public void logOut() {
        promptLogin();
    }

    /**
     * A simple way to ensure that everything being shwon to the user is updated
     * Keep in mind that this is not the most efficient, but it will work for this use case
     */
    public void refreshPanles() {
        screen.refreshPanles();
    }

    /**
     * Tells the controller to make a new User in the database
     */
    public void newUser(User newUser) {
        controller.newUser(newUser);
    }

    /**
     * Returns the frame that is being shown
     * @return Screen The screen that is being shown
     */
    public Screen getScreen() {
        return screen;
    }
   
    /** 
     * Returns the user that is currently logged in
     * @return User
     */
    public User getUser() {
        return activeUser;
    }

    /** 
     * Changes the user that is currently logged in
     * @param newUser The new user to show info for
     */
    public void setUser(User newUser) {
        activeUser = newUser;
    }

    // The user that is actively logged in 
    private User activeUser;
    

    // The screen that actually displays stuff
    private Screen screen;

    // The controller, so that we can call functions to tell it when things happen
    private Control controller;

}
