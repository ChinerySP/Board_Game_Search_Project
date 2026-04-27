package view.panle.colors;

import java.awt.Color;

/**
 * Dark mode palette using Catppuccin Macchiato
 */
public class DarkMode extends Colors {

    /**
     * Creates a new Dark Mode that can be used by Panles to change their theme
     */
    public DarkMode() {
        // Backgrounds
        this.base = Color.decode("#24273a");
        this.mantle = Color.decode("#1e2030");
        this.crust = Color.decode("#181926");
        
        // Typography
        this.text = Color.decode("#cad3f5");
        this.subtext1 = Color.decode("#b8c0e0");
        this.subtext0 = Color.decode("#a5adcb");
        
        // Surfaces
        this.surface2 = Color.decode("#5b6078");
        this.surface1 = Color.decode("#494d64");
        this.surface0 = Color.decode("#363a4f");
        
        // Accents
        this.blue = Color.decode("#8aadf4");
        this.green = Color.decode("#a6da95");
        this.red = Color.decode("#ed8796");
        this.yellow = Color.decode("#eed49f");
        this.peach = Color.decode("#f5a97f");
        this.mauve = Color.decode("#c6a0f6");
    }
}