package main.view.panle;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import main.model.User;
import main.view.Panle;
import main.view.Screen;

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
        screen.showPanle("sticky");
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

        // Centering the everything (that was a mistype, but I think it's funny so I kept it)
        constraints.weightx = 0.0;
        constraints.weightx = 0.0;

        // Giving them some padding between each other
        constraints.ipadx = 40; 
        constraints.ipady = 10;

        // Giving everything an external margin
        constraints.insets = new Insets(8, 20, 8, 20);


        // The right side, which currently just holds our mascot
        // The image is not scaled at all to retain aspect ratio, which is for two reasons: one, it is easier, and two, it is funny.
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
     * Shows the panle for the inputted user
     * @return boolean Whether or not this panle has a user set 
     */
    public boolean hasUser() {
        return this.user == null;
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
