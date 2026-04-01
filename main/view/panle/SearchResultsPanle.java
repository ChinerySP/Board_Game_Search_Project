package main.view.panle;

import java.awt.Color;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import main.view.*;
import main.view.panle.customComponents.RoundedPanle;


/**
 * The Panle that holds the search results after a search
 * @author Sam Whitlock
 */
public class SearchResultsPanle extends Panle {

    /**
     * Testing runner function
     */
    public static void main(String[] args) {
        Screen screen = new Screen();
        screen.showPanle("sticky");
        screen.showPanle("searchresults");
    }

    /**
     * Creates a new instance of the StickySubPanle
     */
    public SearchResultsPanle() {
        super("searchresults");
        
        // This panle also use a Box layout so that we can use each side to house either the GameDetails panle or collections
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        // Regions for the left and the right of the screen
        // These by default hold the user's favorite games and a collection of their games 
        left = new RoundedPanle(CORNER_ROUNDING_RADIUS);
        right = new RoundedPanle(CORNER_ROUNDING_RADIUS);

        // Actually showing them on the screen
        this.add(left);
        this.add( Box.createHorizontalStrut(DISTANCE_BETWEEN_COMPONENTS) );
        this.add(right);

        // TODO remove because these are for testing 
        left.setBackground(new Color(255, 0, 0));
        right.setBackground(new Color(0, 255, 0));

        // Just a bit of eye candy
        this.setBackground(new Color(0, 0, 0, 0));

    }


    // Owns its own instance of the games details subpanle so that it can show it whenever it wants
    // private GameDetailsSubPanle;

    private JPanel left;
    private JPanel right;


}
