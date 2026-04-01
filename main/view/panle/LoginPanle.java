package main.view.panle;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main.view.panle.customComponents.PrettyButton;
import main.view.Panle;

// For testing
import main.view.Screen;

/**
 * The panle that allows the user to sign in.
 * @author Sam Whitlock
 */
public class LoginPanle extends Panle {

    /**
     * A method simply for testing the login panle
     */
    public static void main(String[] args) {
        Screen screen = new Screen();
        screen.showPanle("sticky");
        screen.showPanle("login");
    }


    /**
     * Creates a new instance of the LoginPanle
     */
    public LoginPanle() {
        super("login");

        // The login panle will a gridbag layout to center everything, then a Box to hold the components in the center
        this.setLayout(new GridBagLayout());

        // Making sure everything will be centered
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        
        // Centering the everything
        constraints.weightx = 0.0;
        constraints.weightx = 0.0;

        // Giving them some padding between each other
        constraints.ipadx = 40; 
        constraints.ipady = 10;

        // Making the components stack vertically (says that they are all in one column)
        constraints.gridx = 0;
        
        // Making everything have padding and margin 
        constraints.insets = new Insets(8, 20, 8, 20);

        // The actual components
        usernameInput = new JTextField("Username");
        passwordInput = new JTextField("Password");
        forgotPasswordButton = new PrettyButton("Forgot Password");
        loginButton = new PrettyButton("Login");
        titleLabel = new JLabel("Welcome to TJS", SwingConstants.CENTER);

        // Putting it all together into a pretty layout (I'm using pretty loosley here)
        this.add(titleLabel, constraints);
        this.add(usernameInput, constraints);
        this.add(passwordInput, constraints);
        this.add(loginButton, constraints);
        this.add(forgotPasswordButton, constraints);

        // Some simple changes to ensure that the panle looks nice
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameInput.setPreferredSize(new Dimension(100, 20));
        passwordInput.setPreferredSize(new Dimension(100, 20));
        loginButton.setPreferredSize(new Dimension(50, 20));
        forgotPasswordButton.setPreferredSize(new Dimension(50, 20));

        // Matching the color sceme
        titleLabel.setForeground(Panle.TEXT_COLOR);
        titleLabel.setFont(new Font("Courier", Font.BOLD, 40));


    }
    
    

    private JLabel titleLabel;
    private JTextField usernameInput;
    private JTextField passwordInput;
    private PrettyButton forgotPasswordButton;
    private PrettyButton loginButton;

}
