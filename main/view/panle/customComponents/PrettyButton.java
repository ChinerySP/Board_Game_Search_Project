package main.view.panle.customComponents;

// TODO prune
import javax.swing.*;
import java.awt.*;


/**
 * A simple wrapper for the JButton class that makes it match our theme
 * @author Sam Whitlock
 */
public class PrettyButton extends JButton {

    /**
     * A simple constructor creates a new PrettyButton with the inputted text on it
     * @param String text The text to display on the 
     */
    public PrettyButton(String text) {
        super(text);

        // Making sure that we do not have a border
        this.setBorder(null);

    }

    /**
     * An overridden paint component method so that it looks right
     * @param Graphics g The inputted graphics object to paint with 
     */
    protected void paintComponent(Graphics g) {

        // Casting to draw in 2D and whatnot normal things
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Changing the color if it has been pressed
        if (getModel().isPressed()) {
            g2.setColor(PrettyButton.BUTTON_PUSHED_COLOR);
        } else if (getModel().isRollover()) {
            g2.setColor(PrettyButton.BUTTON_MOUSE_OVER_COLOR);
        } else {
            g2.setColor(PrettyButton.BUTTON_UNPUSHED_COLOR);
        }

        // Drawing the actual button itself
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), PrettyButton.BORDER_RADIUS, PrettyButton.BORDER_RADIUS);

        g2.setColor(PrettyButton.BUTTON_TEXT_COLOR);
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(getText(), x, y);
        g2.dispose();

    }


    // Aestetic constants
    public static final int BORDER_RADIUS = 15;
    public static final Color BUTTON_UNPUSHED_COLOR = new Color(128, 135, 162);
    public static final Color BUTTON_PUSHED_COLOR = new Color(73, 77, 100);
    public static final Color BUTTON_MOUSE_OVER_COLOR = new Color(165, 173, 203);
    public static final Color BUTTON_TEXT_COLOR = new Color(202, 211, 245);


}
