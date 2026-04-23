package view.panle.customComponents;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import view.panle.Panle;
import view.panle.colors.Themeable;

/**
 * An extension of JPopupMenu that allows for rounded corners, matching the other panles
 * @author Sam Whitlock
 */
public class RoundedPopupMenu extends JPopupMenu implements Themeable {

    private int radius;

    /**
     * Creates a rounded popup menu with the specified radius.
     * @param radius The radius of the corners of the popup
     */
    public RoundedPopupMenu(int radius) {
        super();
        this.radius = radius;
        
        // Hiding the background
        setOpaque(false);
        
        // Padding ofc
        setBorder(new EmptyBorder(
            Panle.BORDER_WIDTH + 4, 
            Panle.BORDER_WIDTH + 4, 
            Panle.BORDER_WIDTH + 4, 
            Panle.BORDER_WIDTH + 4
        ));
    }

    @Override
    protected void paintComponent(Graphics g) {
        // We don't call super.paintComponent(g) here because we are handling all of the background drawing
        
        Graphics2D g2 = (Graphics2D) g;

        // Making it look smooth
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Painting the border color
        g2.setColor(Panle.colors.getBlue());
        g2.fillRoundRect(0, 0, width, height, radius, radius);

        // Painting the background color inside the border
        g2.setColor(getBackground());
        g2.fillRoundRect(
            Panle.BORDER_WIDTH, 
            Panle.BORDER_WIDTH, 
            width - (2 * Panle.BORDER_WIDTH), 
            height - (2 * Panle.BORDER_WIDTH), 
            radius, radius
        );
    }

    @Override
    public void updateTheme() {
        revalidate();
        repaint();
    }

    public int getRadius() { return this.radius; }
    public void setRadius(int radius) { this.radius = radius; }
}