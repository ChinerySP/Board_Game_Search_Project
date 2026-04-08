package main.view.panle;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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

        // This Panle will use a gridbag because it is my ✨favorite✨ (also so that the sizing works out but whatever)
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints(); 
        
        // Regions for the left and the right of the screen
        // These by default hold the user's favorite games and a collection of their games 
        results = new RoundedPanle(CORNER_ROUNDING_RADIUS);
        right = new RoundedPanle(CORNER_ROUNDING_RADIUS);

        // Actually showing them on the screen
        // this.add(left);
        this.add( Box.createHorizontalStrut(DISTANCE_BETWEEN_COMPONENTS) );
        this.add(right);


        // Just a bit of eye candy
        this.setBackground(new Color(0, 0, 0, 0));

    }


    // Owns its own instance of the games details subpanle so that it can show it whenever it wants
    // private GameDetailsSubPanle;

    private JPanel results;
    private JPanel right;


}
