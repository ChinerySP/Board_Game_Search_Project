package view.panle.customComponents;

import javax.swing.JButton;
import view.panle.Panle;
import view.panle.colors.*;
import java.awt.*;


/**
 * A simple wrapper for the JButton class that makes it match our theme
 * @author Sam Whitlock
 */
public class PrettyButton extends JButton implements Themeable {

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
            g2.setColor(Panle.colors.getSurface2());
        } else if (getModel().isRollover()) {
            g2.setColor(Panle.colors.getSurface1());
        } else {
            g2.setColor(Panle.colors.getSurface0());
        }

        // Drawing the actual button itself
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), PrettyButton.BORDER_RADIUS, PrettyButton.BORDER_RADIUS);

        g2.setColor(Panle.colors.getText());
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(getText(), x, y);
        g2.dispose();

    }

    /**
     * Implementation of updateTheme for pretty button
     */
    @Override
    public void updateTheme() {
        // Fetch the newest colors from the static variable
        this.setBackground(Panle.colors.getSurface0());
        this.setForeground(Panle.colors.getText());
        
        // Tell Swing this component needs to be redrawn
        this.repaint();
    }


    // Aestetic constants
    public static final int BORDER_RADIUS = 15;



}
