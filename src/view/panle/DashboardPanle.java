package src.view.panle;

import java.awt.Color;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import src.view.*;
import src.view.panle.customComponents.RoundedPanle;


/**
 * The dashboard that acts as the home page for each user. Displays the user's favorites list and all of their lists so that they can access them
 * @author Sam Whitlock
 */
public class DashboardPanle extends Panle {

    /**
     * Testing main method 
     */
    public static void main(String[] args) {
        Screen screen = new Screen();
        screen.showPanle("sticky");
        screen.showPanle("dashboard");
    }

    /**
     * Creates a new instance of the StickySubPanle
     */
    public DashboardPanle() {
        super("dashboard");
        
        // This panle also use a Box layout so that we can use each side to house either the GameDetails panle or collections
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        // Regions for the left and the right of the screen
        // These by default hold the user's favorite games and a collection of their games 
        left = new GameListSubPanle();
        right = new GameListSubPanle();

        // Actually showing them on the screen
        this.add(left);
        this.add( Box.createHorizontalStrut(DISTANCE_BETWEEN_COMPONENTS) );
        this.add(right);


        // Just a bit of eye candy



    }



    // TODO change to private
    public GameListSubPanle left;
    public GameListSubPanle right;


}
