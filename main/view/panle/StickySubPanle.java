package main.view.panle;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JTextField;

import main.view.Panle;


/**
 * The panel that always "sticks" to the top of the screen. It holds things that can be useful at all points in the program, like the search bar and a settings button.
 * @author Sam Whitlock
 */
public class StickySubPanle extends Panle {

    /**
     * Creates a new instance of the StickySubPanle
     */
    public StickySubPanle() {
        super("sticky");
      
        // The sticky subpanle will use the box layout to hold all components horizontally
        componentBox = Box.createHorizontalBox();

        // Instantiating all of the things that need to be in the sticky subpanle
        componentBox.add(new JButton("Home"));
        componentBox.add( Box.createHorizontalStrut(10) );
        componentBox.add( new JTextField("Search for whatever your heart desires...") );
        componentBox.add( Box.createHorizontalStrut(10) );
        componentBox.add(new JButton("Settings"));

        // Adding in the actual box
        this.add(componentBox);
         
        // Some simple changes to ensure that the panle looks nice
        this.setMaximumSize(new Dimension(this.getMaximumSize().width, 30));

    
        


    }

    /**
     * 
     */


    private Box componentBox;

}
