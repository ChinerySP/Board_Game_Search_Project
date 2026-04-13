package view;

import javax.swing.JOptionPane;

import control.*;
import model.*;
import view.panle.DashboardPanle;

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
            // JOptionPane.showMessageDialog(screen.getFrame(), "The username and password did not match, please try again.", "Login Failed", JOptionPane.WARNING_MESSAGE);

            // TODO revert this to actually check, right now it just tries skips to the dashboard for testing
            
            ((DashboardPanle)screen.getPanle("dashboard")).setUser(new User());
            
            // Updating everything to the new Panle
            screen.hidePanle("login");
            screen.showPanle("dashboard"); 
            screen.showPanle("sticky");
        } else {
            // Login successful

            // Saving the user for later use without querying the database
            activeUser = loggingInUser;

            // Telling the dashboard which user's info to display
            ((DashboardPanle) screen.getPanle("dashboard")).setUser(loggingInUser);

            // Updating everything to the new Panle
            screen.showPanle("dashboard");
            screen.showPanle("sticky");
        }
        
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
     * @param String[] The keywords to search for
     * @return GameList The list of Games that the User requested
     */
    public GameList search(String[] keywords) {
        return controller.search(keywords);
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
                JOptionPane.ERROR_MESSAGE
            );

        } else {

            // Showing the dashboard
            showPanle("dashboard");
            showPanle("sticky");

            // Updating the stored information
            activeUser = toLogin;
            return toLogin;
        }
        return null;
    }

    
   
    // The user that is actively logged in 
    private User activeUser;
    public User getUser() {
        return activeUser;
    }
    public void setUser(User newUser) {
        activeUser = newUser;
    }

    // The screen that actually displays stuff
    private Screen screen;

    // The controller, so that we can call functions to tell it when things happen
    private Control controller;

}
