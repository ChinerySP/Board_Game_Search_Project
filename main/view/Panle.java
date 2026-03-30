package main.view;

import javax.swing.*;

import main.view.panle.RoundedPanle;

/**
 * A superclass that provides basic visibility control for other Panles. 
 * <p>Note that this class is not meant to be be used raw in the final version, just </p>
 * @author Sam Whitlock
 */
public class Panle extends RoundedPanle { // Yes, I am sticking to the bit of panel vs panle

    /**
     * Super constructor to create a Panle with a specific name
     * This is intended for internal use only, only to be called by subclasses
     * @param String name The name of the panle
     */
    public Panle(String name) {
        // All of our panles will be defaulted to just a box layout (they are all rather simple in their layout)
        super(CORNER_ROUNDING_RADIUS);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Storing the name
        this.name = name;

        // Adding invisible padding so that there is some breathing room between all of the panles
        this.setBorder(BorderFactory.createEmptyBorder(DISTANCE_BETWEEN_COMPONENTS, DISTANCE_BETWEEN_COMPONENTS,
                DISTANCE_BETWEEN_COMPONENTS, DISTANCE_BETWEEN_COMPONENTS));

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
        if (this == other) return true;
        if (other == null) return false;

        // If it is a String, we compare it to the name
        if (other instanceof String) {
            return name.equals(other);
        }
        
        // If it is a Panle, we want to compare names
        if (other instanceof Panle) {
            return name.equals(((Panle)other).getName());
        }

        // Anything else will be false
        return false;
    
    }

    /**
     * Gets the name of the class as a String
     */
    public String getName() {
        return name;
    }

    final private String name;

    // The distance between every component displayed, simply to make it look nice
    public static final int DISTANCE_BETWEEN_COMPONENTS = 10;
    public static final int CORNER_ROUNDING_RADIUS = 20;

}
