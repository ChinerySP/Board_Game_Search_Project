package Main.View;

// TODO prune this to only import things that we need
import Main.View.Panles.RoundedPanle;
import javax.swing.*;

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
        // All of our panels will be defaulted to just a box layout (they are all rather simple in their layout)
        super(CORNER_ROUNDING_RADIUS);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Storing the name
        this.name = name;

        // Adding invisible padding so that there is some breathing room between all of the panles
        this.setBorder(BorderFactory.createEmptyBorder(DISTANCE_BETWEEN_COMPONENTS, DISTANCE_BETWEEN_COMPONENTS,
                DISTANCE_BETWEEN_COMPONENTS, DISTANCE_BETWEEN_COMPONENTS));
    }

    /**
     * A simple comparason for the name of the panle
     * @param String other The name to compare to
     */
    public boolean equals(String other) {
        return name.equals(other);
    }

    final private String name;

    // The distance between every component displayed, simply to make it look nice
    public static final int DISTANCE_BETWEEN_COMPONENTS = 10;
    public static final int CORNER_ROUNDING_RADIUS = 20;

}
