package main.view.panle.customComponents;

// TODO prune
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * A simple wrapper for the JTextField that allows for better customization
 * @author Sam Whitlock
 */
public class PrettyTextInput extends JTextField {

    /**
     * Simple default constructor for the pretty text input
     */
    public PrettyTextInput() {
        super();

        // Setting to invisible so that we can actually see the background we draw
        this.setOpaque(false);
    }
    
    /**
     * Updated paintComponent method to allow for updated visuals
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Drawing the backgrounnd to be nice and rounded
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 7, 7));
        g2.dispose();
        super.paintComponent(g);
    }

}
