package view.panle;

import java.awt.Color;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import view.*;
import model.*;
import view.panle.customComponents.RoundedPanle;


/**
 * The dashboard that acts as the home page for each user. Displays the user's favorites list and all of their lists so that they can access them
 * @author Sam Whitlock
 */
public class DashboardPanle extends Panle {

    /**
     * Creates a new instance of the StickySubPanle
     * @param View The view that owns this Panle
     */
    public DashboardPanle(View view) {
        super("dashboard", view);

        // This panle also use a Box layout so that we can use each side to house either the GameDetails panle or collections
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Regions for the left and the right of the screen
        // These by default hold the user's favorite games and a collection of their games 
        left = new GameListSubPanle(view);
        right = new GameListSubPanle(view);

        // Actually showing them on the screen
        this.add(left);
        this.add(Box.createHorizontalStrut(DISTANCE_BETWEEN_COMPONENTS));
        this.add(right);

        // Just a bit of eye candy

    }


    // Getters and setters
    /**
     * Returns the user that is being displayed
     * @return User
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Sets the user that is being displayed
     * @param User newUser The new user to display
     */
    public void setUser(User newUser) {
        user = newUser;
    }
    


    // The user that is currently being displayed
    private User user;

    // TODO change to private
    public GameListSubPanle left;
    public GameListSubPanle right;


}
