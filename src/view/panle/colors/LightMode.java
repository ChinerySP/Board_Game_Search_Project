package view.panle.colors;

import java.awt.Color;

/**
 * Light mode palette using Catppuccin Latte
 * @author Sam Whitlock
 */
public class LightMode extends Colors {
    
    public LightMode() {
        // Backgrounds
        this.base = Color.decode("#eff1f5");
        this.mantle = Color.decode("#e6e9ef");
        this.crust = Color.decode("#dce0e8");
        
        // Typography
        this.text = Color.decode("#4c4f69");
        this.subtext1 = Color.decode("#5c5f77");
        this.subtext0 = Color.decode("#6c6f85");
        
        // Surfaces
        this.surface2 = Color.decode("#acb0be");
        this.surface1 = Color.decode("#bcc0cc");
        this.surface0 = Color.decode("#ccd0da");
        
        // Accents
        this.blue = Color.decode("#1e66f5");
        this.green = Color.decode("#40a02b");
        this.red = Color.decode("#d20f39");
        this.yellow = Color.decode("#df8e1d");
        this.peach = Color.decode("#fe640b");
        this.mauve = Color.decode("#8839ef");
    }
}