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

    // Getters
    
    public Color getBase() { return base; }
    public Color getMantle() { return mantle; }
    public Color getCrust() { return crust; }
    
    public Color getText() { return text; }
    public Color getSubtext0() { return subtext0; }
    public Color getSubtext1() { return subtext1; }
    
    public Color getSurface0() { return surface0; }
    public Color getSurface1() { return surface1; }
    public Color getSurface2() { return surface2; }
    
    public Color getBlue() { return blue; }
    public Color getGreen() { return green; }
    public Color getRed() { return red; }
    public Color getYellow() { return yellow; }
    public Color getPeach() { return peach; }
    public Color getMauve() { return mauve; }
}
