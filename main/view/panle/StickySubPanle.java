package main.view.panle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.view.Panle;
import main.view.panle.customComponents.*;


/**
 * The panel that always "sticks" to the top of the screen. It holds things that can be useful at all points in the program, like the search bar and a settings button.
 * @author Sam Whitlock
 */
public class StickySubPanle extends Panle {

    /**
     * Creates a new instance of the StickySubPanle
     */
    public StickySubPanle() {
        super("sticky");
      
        // The sticky subpanle will use the box layout to hold all components horizontally
        componentBox = Box.createHorizontalBox();

        // Making the buttons have their icons
        ImageIcon homeIcon = new ImageIcon("resources/Home.png");
        homeButton = new JButton();
        homeButton.setIcon(homeIcon);
        ImageIcon settingsIcon = new ImageIcon("resources/Settings.png");
        settingsButton = new JButton();
        settingsButton.setIcon(settingsIcon);

        // Creating the textarea for input
        searchInput = new PrettyTextInput();
        searchInput.setBorder(new EmptyBorder(0, 5, 0, 5));
        // searchInput.setBorder(null);
        searchInput.setBackground(new Color(54, 58, 79));
        searchInput.setForeground(Panle.TEXT_COLOR);
        searchInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));

        // Removing any other stuff from the icons
        homeButton.setBorder(null);
        settingsButton.setBorder(null);
        homeButton.setContentAreaFilled(false);
        settingsButton.setContentAreaFilled(false);
        homeButton.setBorder(new EmptyBorder(10, 10, 10, 5));
        settingsButton.setBorder(new EmptyBorder(10, 5, 10, 10));
        homeButton.setFocusable(false);
        settingsButton.setFocusable(false);
        



        // Adding everything to the component box
        componentBox.add(homeButton);
        componentBox.add(searchInput);
        componentBox.add(settingsButton);

        // Adding in the actual box
        this.add(componentBox);
         
        // Some simple changes to ensure that the panle looks nice
        this.setMaximumSize(new Dimension(this.getMaximumSize().width, 30));

    }

    // The components that we want on the screen
    private JButton homeButton;
    private JButton settingsButton;
    private PrettyTextInput searchInput;

    private Box componentBox;

}
