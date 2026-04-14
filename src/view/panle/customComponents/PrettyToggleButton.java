package view.panle.customComponents;

import javax.swing.*;

import view.panle.Panle;

import java.awt.*;
import view.panle.colors.*;

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
            g2.setColor(Panle.colors.getSurface2());
        } else if (getModel().isSelected()) {
            // Toggled ON state
            g2.setColor(Panle.colors.getSurface2());
        } else if (getModel().isRollover()) {
            // Hovering over it while it is OFF
            g2.setColor(Panle.colors.getSurface1());
        } else {
            // Default OFF state
            g2.setColor(Panle.colors.getSurface0());
        }

        // Drawing the actual button itself
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), PrettyToggleButton.BORDER_RADIUS,
                PrettyToggleButton.BORDER_RADIUS);

        // Drawing the text centered
        g2.setColor(Panle.colors.getText());
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(getText(), x, y);
        g2.dispose();

    }

    // Any specific constants
    public static final int BORDER_RADIUS = 15;

}