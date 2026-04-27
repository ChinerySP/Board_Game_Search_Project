package view.panle.colors;
import java.awt.Color;

/**
 * A superclass to allow for the switching between collor palletes (largely dark and light mode)
 * @author Sam Whitlock
 */
public abstract class Colors {

    // Backgrounds
    protected Color base;
    protected Color mantle;
    protected Color crust;
    
    // Text
    protected Color text;
    protected Color subtext0;
    protected Color subtext1;
    
    // Surfaces / Overlays
    protected Color surface0;
    protected Color surface1;
    protected Color surface2;

    // Accents
    protected Color blue;
    protected Color green;
    protected Color red;
    protected Color yellow;
    protected Color peach;
    protected Color mauve;

    /**
     * Gets the base color for this theme 
     */
    public Color getBase() { return base; }
    /**
     * Gets the mantle color for this theme 
     */
    public Color getMantle() { return mantle; }
    /**
     * Gets the crust color for this theme 
     */
    public Color getCrust() { return crust; }
    
    /**
     * Gets the text color for this theme 
     */
    public Color getText() { return text; }
    /**
     * Gets the subtext0 color for this theme 
     */
    public Color getSubtext0() { return subtext0; }
    /**
     * Gets the subtext1 color for this theme 
     */
    public Color getSubtext1() { return subtext1; }
    
    /**
     * Gets the surface0 color for this theme 
     */
    public Color getSurface0() { return surface0; }
    /**
     * Gets the surface1 color for this theme 
     */
    public Color getSurface1() { return surface1; }
    /**
     * Gets the surface2 color for this theme 
     */
    public Color getSurface2() { return surface2; }
    
    /**
     * Gets the blue color for this theme 
     */
    public Color getBlue() { return blue; }
    /**
     * Gets the green color for this theme 
     */
    public Color getGreen() { return green; }
    /**
     * Gets the red color for this theme 
     */
    public Color getRed() { return red; }
    /**
     * Gets the yellow color for this theme 
     */
    public Color getYellow() { return yellow; }
    /**
     * Gets the peach color for this theme 
     */
    public Color getPeach() { return peach; }
    /**
     * Gets the muave color for this theme 
     */
    public Color getMauve() { return mauve; }
}
