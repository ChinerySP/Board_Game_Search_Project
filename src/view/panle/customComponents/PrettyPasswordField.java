package view.panle.customComponents;

import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import view.panle.*;
import view.panle.colors.*;

/**
 * A simple wrapper for the JPasswordField that matches the themeing (I am way too extra, I apologize)
 * @author Sam Whitlock
 */
public class PrettyPasswordField extends JPasswordField implements Themeable {

    /**
     * Simple default constructor
     */
    public PrettyPasswordField() {
        super();

        // ✨Style✨
        this.setBorder(new EmptyBorder(0, 5, 0, 5));
        this.setBackground(Panle.colors.getSurface0());
        this.setForeground(Panle.colors.getText());
        this.setCaretColor(Panle.colors.getText());
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));

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

        // Drawing the background to be nice and rounded
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 7, 7));
        g2.dispose();
        
        super.paintComponent(g);
    }

    /**
     * Implementation of update theme for pretty password input
     */
    @Override
    public void updateTheme() {
        // Fetch the newest colors from the static variable
        this.setBackground(Panle.colors.getSurface0());
        this.setForeground(Panle.colors.getText());
        this.setCaretColor(Panle.colors.getText());
        
        // Tell Swing this component needs to be redrawn
        this.repaint();
    }

}