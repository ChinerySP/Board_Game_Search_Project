package src.view.panle.customComponents;

// TODO prune this to only import things that we need
import java.awt.*;
import javax.swing.*;

import src.view.Panle;

// Yes I know this is very extra, but I have an idea of what I want in my mind and I think this could make it work nicely
/**
 * An extension of JPanel that allows for rounded corners. There are no other differences, I just like how it looks more
 * @author Sam Whitlock
 */
public class RoundedPanle extends JPanel {

    /**
     * Creates a rounded panle with specified radius
     * @param int radius The radius of the corner of the panle
     */
    public RoundedPanle(int radius) {
        super();

        this.radius = radius;

        // Making sure that the regular background doesn't show up
        setOpaque(false);
    }
    
    /**
     * Creates a rounded panle with specified radius and layout manager
     * @param int radius The radius of the corner of the panle
     * @param LayoutManager layout The layout of the contents of the panle
     */
    public RoundedPanle(int radius, LayoutManager layout) {
        super(layout);
        this.radius = radius;
        setOpaque(false);
    }

    /**
     * Paints the panle with its rounded corners
     * @param Graphics g The graphic objects that we are drawing on
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // We want to play with a 2D graphics object instead of a regular Graphics object, so we cast here
        Graphics2D g2 = (Graphics2D) g;

        // Calculating how far offset we need to be so that we don't draw our background over the spacing
        Insets insets = getInsets();
        int x = insets.left;
        int y = insets.top;

        // Making it looks smooth
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Painting the border color
        g2.setColor(Panle.BORDER_COLOR);
        g2.fillRoundRect(x - Panle.BORDER_WIDTH, y - Panle.BORDER_WIDTH,
                getWidth() - insets.left - insets.right + 2 * Panle.BORDER_WIDTH,
                getHeight() - insets.bottom - insets.top + 2 * Panle.BORDER_WIDTH,
                radius, radius);

        // Painting the background color
        g2.setColor(getBackground());
        g2.fillRoundRect(x, y, getWidth() - insets.left - insets.right, getHeight() - insets.bottom - insets.top,
                radius, radius);

    }

    private int radius;
    
    /**
     * Returns the radius of this RoundedPanle
     */
    public int getRadius() { return this.radius; }

    /**
     * Updates the radius of the RoundedPanle 
     * @param int radius The new radius of the panle
     */
    public void setRadius(int radius) { this.radius = radius; }


}