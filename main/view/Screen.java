package main.view;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import main.model.User;
import main.view.panle.*;

/**
 * The overall screen that will be shown to the user. 
 * 
 * <p>This is the interface for controlling the Panles that will be shown to the user.</p> 
 * @author Sam Whitlock
 */
public class Screen {

    /**
     * Simple tester for the visuals
     */
    public static void main(String[] args) {
        System.out.println("Running screen testing function.");
        Screen screen = new Screen();
    }
    
    /**
     * Simple contructor that initializes all of the panle and opens the login panle
     */
    public Screen() {
        
        // Creating the frame that will actually be displayed
        frame = new JFrame("Amazing Board Game App - Now with API (soon we promise) -");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Making sure we actually have a place to put our panles
        panles = new ArrayList<>();

        // Creating all of the Panles that we can show
        this.panles.add(new LoginPanle());
        this.panles.add(new StickySubPanle());
        this.panles.add(new DashboardPanle());
        this.panles.add(new SettingsPanle());

        // Adding them to the frame so that we can show them
        for (Panle p : panles) {
            frame.add(p);
        }


        // Some eye candy 
        frame.getContentPane().setBackground(SCREEN_BACKGROUND_COLOR);

        // Final steps to make sure that the frame shows up
        frame.setSize(500, 400);
        frame.setVisible(true);

    }

    /**
     * Shows the Panle who's name matches the inputted String
     * @param String name The name of the Panle to show
     */
    public void showPanle(String name) {
        System.out.println(String.format("About to try to show panel %s", name));
        // Iterating through and only showing the ones that are either directly specified in the input or the sticky subpanle
        for (Panle p : panles) {
            if (p.equals(name) || p.equals("sticky")) {
                p.showPanle();
            } else {
                p.hidePanle();
            }
        }
        frame.revalidate();
        frame.repaint();

    }

    /**
     * Hides the Panle who's name matches the inputted String
     * @param String name The name of the Panle to hide
     */
    public void hidePanle(String name) {
        getPanle(name).hidePanle();
    }

    /**
     * A login function. Presents the user with the login panle and returns the User that gets logged in.
     * Takes the user to the dashboard after they have signed in.
     * @param ArrayList<User> users The possible user that they may sign into
     * @return user The User that logged in
     */
    public User login(ArrayList<User> users) {

        // Showing the login screen
        showPanle("login");

        // Getting the login 


        // Showing the dashboard
        showPanle("dashboard");

        return null; // Unfinished

    }
    
    
    // Getters and setters 

    /**
     * Gets the panel that matches the inputted name
     * @param String name The name of the panel to get; This must match one of the Panles, otherwise returns null
     */
    public Panle getPanle(String name) {

        // Iterating over the panles to get the one they want
        for (Panle p : panles) {
            if (name.equals(p.getName())) {
                return p;
            }
        }
        
        // If we get here, then we didn't have the panle, so we panic
        assert name.equals("alwaysError") : String.format("ERROR: Bad input; getPanle(%s)", name);
        return null;
    }

    // Constants (generally for consistent visuals)
    public static final Color SCREEN_BACKGROUND_COLOR = new Color(6, 28, 43);

    
    private JFrame frame;
    private ArrayList<Panle> panles;
    
}