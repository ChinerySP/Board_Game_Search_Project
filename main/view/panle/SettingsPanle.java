package main.view.panle;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import main.model.User;
import main.view.Panle;

// For testing specifically
import main.view.Screen;

/**
 * The Panle that allows for changing the settings of the program.
 * @author Sam Whitlock
 */
public class SettingsPanle extends Panle {

    
    /**
     * Simple tester for the visuals of the settings panle
     */
    public static void main(String[] args) {
        System.out.println("Running Settings Panle testing function.");
        Screen screen = new Screen();
        screen.showPanle("settings");
    }
   
    /**
     * Creates a new settings panle
     */
    public SettingsPanle() {
        super("settings");
        user = null;

        // The settings panle holds everything in a grid layout
        // This way, we can have the settings on one side and a meme on the other
        this.setLayout(new GridLayout());

        // Each side is going to be a gridbag layout so that everything is centered
        // Here are the constraints so that everything is centered
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 0.0;
        constraints.weightx = 0.0;


        // The right side, which currently just holds our mascot
        right = new JPanel();
        right.setLayout(new GridLayout());
        ImageIcon tjs = new ImageIcon("resources/TJS.png");
        right.add(new JLabel(tjs), constraints);
       
        // The left side, which holds the different options
        apiToggleButton = new JToggleButton("API On");
        deleteAccount = new JButton("Delete Account");
        darkModeLabel = new JLabel("Dark Mode");
        darkModeCheckBox = new JCheckBox();
        resetPasswordButton = new JButton("Reset Password");
        left = new JPanel();
        left.setLayout(new GridBagLayout());
        constraints.gridy = 0;
        left.add(apiToggleButton, constraints);
        constraints.gridy = 1;
        left.add(deleteAccount, constraints);
        constraints.gridy = 2;
        JPanel darkModePanel = new JPanel();
        darkModePanel.add(darkModeCheckBox);
        darkModePanel.add(darkModeLabel);
        left.add(darkModePanel, constraints);
        constraints.gridy = 3;
        left.add(resetPasswordButton, constraints);


        this.add(left);
        this.add(right);

        this.setVisible(true);
        this.setOpaque(true);

        
    }
    

    /**
     * Shows the panle for the stored User
     * Precondition: The user for this settings panel must already have been set
     */
    @Override
    public void showPanle() {

        // Making sure that we have an actual user to display
        assert user != null : "Error: No user set to display when calling the SettingsPanle.show() method";
        
        // TODO Show the user data on the panle

        // Displaying like normal
        super.showPanle();

    }
    
    /**
     * Shows the panle for the inputted user
     * @param User user The User to display the settings panle for
     */
    public void showPanle(User user) {
        this.user = user;
        super.showPanle();


        
        // TODO Show the user data on the panle
    }

    /**
     * Sets the user that this settings panle will display
     * @param User user The user to be stored in this settings panle
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    // The user to diplay
    private User user;

    // Display elements
    private JPanel left;
    private JPanel right;
    private JButton resetPasswordButton;
    private JLabel darkModeLabel;
    private JButton deleteAccount;
    private JCheckBox darkModeCheckBox;
    private JToggleButton apiToggleButton;



}
