package view.panle;

import java.awt.Color;
import java.util.concurrent.ThreadFactory;

import javax.swing.*;

import view.*;
import view.panle.colors.*;
import view.panle.customComponents.RoundedPanle;

/**
 * A superclass that provides basic visibility control for other Panles. 
 * <p>Note that this class is not meant to be be used raw in the final version, just </p>
 * @author Sam Whitlock
 */
public class Panle extends RoundedPanle { // Yes, I am sticking to the bit of panel vs panle

    /**
     * Super constructor to create a Panle with a specific name
     * This is intended for internal use only, only to be called by subclasses
     * @param String The name of the panle
     * @param View The view that owns the panle
     */
    public Panle(String name, View view) {
        // All of our panles will be defaulted to just a box layout (they are all rather simple in their layout)
        super(CORNER_ROUNDING_RADIUS);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Storing instance variables
        this.name = name;
        this.view = view;

        // Adding invisible padding so that there is some breathing room between all of the panles
        this.setBorder(BorderFactory.createEmptyBorder(DISTANCE_BETWEEN_COMPONENTS, DISTANCE_BETWEEN_COMPONENTS,
                DISTANCE_BETWEEN_COMPONENTS, DISTANCE_BETWEEN_COMPONENTS));


        // Setting up the colors
        this.setBackground(Panle.colors.getBase());




        // Showing the Panle (to hide the panle, it will be removed from the frame)
        this.setVisible(true);
        
    }

    /**
     * A simple comparison for both names and other Panles
     * @param Object other The other object to compare against
     */
    @Override
    public boolean equals(Object other) {

        // Default simple cases
        if (this == other)
            return true;
        if (other == null)
            return false;

        // If it is a String, we compare it to the name
        if (other instanceof String) {
            return name.equals(other);
        }

        // If it is a Panle, we want to compare names
        if (other instanceof Panle) {
            return name.equals(((Panle) other).getName());
        }

        // Anything else will be false
        return false;

    }
    

    /**
     * Updates this panle's theme to match the global state
     */
    public void updateTheme() {
        this.setBackground(Panle.colors.getBase());
    }

    /**
     * Gets the name of the class as a String
     */
    public String getName() {
        return name;
    }

    final private String name;

    // The view that owns this Panle
    protected View view;

    // Some dimensional constants
    public static final int DISTANCE_BETWEEN_COMPONENTS = 10;
    public static final int CORNER_ROUNDING_RADIUS = 20;
    public static final int BORDER_WIDTH = 1;


    // The Panles will all reference this static color pallatte, so that we can change it easily
    public static Colors colors = new DarkMode();

}
