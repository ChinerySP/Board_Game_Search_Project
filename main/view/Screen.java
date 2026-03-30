package main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.border.Border;

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
        screen.showPanle("sticky");
        screen.showPanle("dashboard");
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
        this.panles.add(new SearchResultsPanle());


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

        // Grabbing the actual place where the content is stored in the frame
        Container contentPane = frame.getContentPane(); 

        // The sticky subpanle is different than the others because of its location, so we check it first
        if (name.equals("sticky")) {
            // Checking to see if it is already there
            BorderLayout layout = (BorderLayout) contentPane.getLayout();
            Component current = layout.getLayoutComponent(BorderLayout.NORTH);

            // Skipping it if already is there
            if (current != null) return;
           
            // Adding it in because it isn't there
            contentPane.add(getPanle("sticky"), BorderLayout.NORTH);
            return;

        }

        // Saving the layout that we are using so that we can query it
        BorderLayout layout = (BorderLayout) contentPane.getLayout();
        Component current = layout.getLayoutComponent(BorderLayout.CENTER);
        
        // Removing whatever is in the center if there is something there, otherwise exiting
        if (current != null) {
            contentPane.remove(current);
        }

        // If the right one is in the center, we don't have to do anything (makes sure there isn't a flash of nothing)
        if (current != null && current.equals(name)) return; 
        
        // Grabbing the target
        Panle target = getPanle(name);
        if (target == null) return;
        
        // Making sure that the settings panle is only shown when there is a User defined
        if (target instanceof SettingsPanle && !((SettingsPanle) target).hasUser()) {
            return;
        }
        
        // Showing the panle
        contentPane.add(target, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();

    }


    /**
     * Hides the Panle who's name matches the inputted String
     * @param String name The name of the Panle to hide
     */
    public void hidePanle(String name) {
        frame.remove(this.getPanle(name));
        // getPanle(name).hidePanle();
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

        //! This will likely be moved to the View object?
        // Getting the login 


        // Showing the dashboard
        frame.add(this.getPanle("sticky"), BorderLayout.NORTH);
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