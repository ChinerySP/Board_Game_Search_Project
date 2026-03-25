package main.view;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
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

        // Adding them to the screen for testing
        // Box box = Box.createVerticalBox();
        // box.add(panles.get(0));
        // box.add(panles.get(1));
        // frame.add(box);
        frame.add(this.panles.get(0));

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

        // Iterating until we find the panle that they want
        for (Panle p : panles) {
            if (name.equals(p.getName())) {
                // TODO Stuff to show panele (hide other panle, then show the panle they want)

                return;
            }
        }

    }

    // Commented until User is implemented (or at least created) so that it doesn't cause errors on compilation
    // /**
    //  * A login function. Presents the user with the login panle and returns the User that gets logged in
    //  * @param ArrayList<User> users The possible user that they may sign into
    //  * @return user The User that logged in
    //  */
    // public User login(ArrayList<User> users) {

    //     // Showing the login screen
    //     showPanle("login");

    //     // Getting the login 

    //     // while ()

    //     return null; // Unfinished

    // }


    public static final Color SCREEN_BACKGROUND_COLOR = new Color(6, 28, 43);

    private JFrame frame;
    private ArrayList<Panle> panles;
    
}