package view.panle.customComponents;

import javax.swing.*;
import java.awt.*;

/**
 * A simple wrapper for the JToggleButton class that makes it match our theme
 * @author Sam Whitlock
 */
public class PrettyToggleButton extends JToggleButton {

    /**
     * A simple constructor creates a new PrettyToggleButton with the inputted text on it
     * @param text The text to display on the button
     */
    public PrettyToggleButton(String text) {
        super(text);

        // Making sure that we do not have a border
        this.setBorder(null);
        
        // Stopping Swing from drawing the default background and focus rings
        this.setContentAreaFilled(false); 
        this.setFocusPainted(false);
    }

    /**
     * An overridden paint component method so that it looks right
     * @param g The inputted graphics object to paint with 
     */
    @Override
    protected void paintComponent(Graphics g) {

        // Casting to draw in 2D and whatnot normal things
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Changing the color based on the current state of the toggle button
        if (getModel().isPressed()) {
            // Actively being clicked down
            g2.setColor(PrettyToggleButton.BUTTON_PUSHED_COLOR);
        } else if (getModel().isSelected()) {
            // Toggled ON state
            g2.setColor(PrettyToggleButton.BUTTON_SELECTED_COLOR);
        } else if (getModel().isRollover()) {
            // Hovering over it while it is OFF
            g2.setColor(PrettyToggleButton.BUTTON_MOUSE_OVER_COLOR);
        } else {
            // Default OFF state
            g2.setColor(PrettyToggleButton.BUTTON_UNPUSHED_COLOR);
        }

        // Drawing the actual button itself
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), PrettyToggleButton.BORDER_RADIUS, PrettyToggleButton.BORDER_RADIUS);

        // Drawing the text centered
        g2.setColor(PrettyToggleButton.BUTTON_TEXT_COLOR);
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(getText(), x, y);
        g2.dispose();

    }

    // Aesthetic constants
    public static final int BORDER_RADIUS = 15;
    public static final Color BUTTON_UNPUSHED_COLOR = new Color(128, 135, 162);
    public static final Color BUTTON_PUSHED_COLOR = new Color(73, 77, 100);
    // Added a color specifically for the "ON" state 
    public static final Color BUTTON_SELECTED_COLOR = new Color(95, 100, 125); 
    public static final Color BUTTON_MOUSE_OVER_COLOR = new Color(165, 173, 203);
    public static final Color BUTTON_TEXT_COLOR = new Color(202, 211, 245);

}