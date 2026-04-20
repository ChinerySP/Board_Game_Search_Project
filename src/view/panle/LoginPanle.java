package view.panle;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import model.*;
import view.*;
import view.panle.customComponents.*;

/**
 * The panle that allows the user to sign in.
 * @author Sam Whitlock
 */
public class LoginPanle extends Panle {

    /**
     * Creates a new instance of the LoginPanle
     * @param View The view that owns this panle
     */
    public LoginPanle(View view) {
        super("login", view);

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
        // Making everything have padding and margin 
        constraints.insets = new Insets(8, 20, 8, 20);

        // The actual components
        usernameInput = new PrettyTextInput();
        passwordInput = new PrettyPasswordField();
        forgotPasswordButton = new PrettyButton("Forgot Password");
        loginButton = new PrettyButton("Login");
        signUpButton = new PrettyButton("Sign Up");
        titleLabel = new JLabel("Welcome to TJS", SwingConstants.CENTER);

        // Putting it all together into a pretty layout (I'm using pretty pretty loosley here)

        // Title (spanning two column)
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        this.add(titleLabel, constraints);

        // Username row
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridx = 0; // On the left
        constraints.anchor = GridBagConstraints.EAST; // Aligned to the right
        userNameLabel = new JLabel("Username", SwingConstants.RIGHT);
        this.add(userNameLabel, constraints);
        constraints.gridx = 1; // On the right
        constraints.anchor = GridBagConstraints.WEST; // Aligned to the left
        this.add(usernameInput, constraints);

        // Password row
        constraints.gridy = 2;
        constraints.gridx = 0; // On the left
        constraints.anchor = GridBagConstraints.EAST; // Aligned to the right
        passwordLabel = new JLabel("Password", SwingConstants.RIGHT);
        this.add(passwordLabel, constraints);
        constraints.gridx = 1; // On the right
        constraints.anchor = GridBagConstraints.WEST; // Aligned to the left
        this.add(passwordInput, constraints);

        // Login button (centered)
        constraints.gridy = 3;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        this.add(loginButton, constraints);

        // Forgot password button, also centered
        constraints.gridy = 4;
        this.add(forgotPasswordButton, constraints);

        // A way to actually make a user when you first use the program
        constraints.gridy = 5;
        this.add(signUpButton, constraints);

        // Some simple changes to ensure that the panle looks nice
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameInput.setPreferredSize(new Dimension(150, 20));
        passwordInput.setPreferredSize(new Dimension(150, 20));
        loginButton.setPreferredSize(new Dimension(100, 20));
        forgotPasswordButton.setPreferredSize(new Dimension(100, 20));

        // Matching the color sceme
        titleLabel.setForeground(Panle.colors.getText());
        titleLabel.setFont(new Font("Courier", Font.BOLD, 40));
        userNameLabel.setForeground(Panle.colors.getText());
        userNameLabel.setFont(new Font("Courier", Font.BOLD, 15));
        passwordLabel.setForeground(Panle.colors.getText());
        passwordLabel.setFont(new Font("Courier", Font.BOLD, 15));

        // Making the buttons call their specific functions
        forgotPasswordButton.addActionListener(e -> this.forgotPassword());
        loginButton.addActionListener(e -> this.login());
        signUpButton.addActionListener(e -> this.newUser());

    }
    

    /**
     * Allows a user to log in even if they forgot their password (alongside some shenanegians)
     */
    private void forgotPassword() {

        // Adding in some security (that is totally what this is)
        String[] options = { "Yes", "No" };
        int selection = JOptionPane.showOptionDialog(
            this,
            "Do you pinky promise that you are who you say you are?",
            "Account Ownership Verification",
            0,               
            3,
            null,
            options,
                options[1]);
            
        // If they said no, then we mess with them
        if (selection == 1) {
            JOptionPane.showMessageDialog(this, "HOW DARE YOU??????!?!?!!?!?!", "ANGER", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(this, "Deleting System32...", "PUNISHMENT", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(this, "Just kidding.");
            JOptionPane.showMessageDialog(this,  "I'll let you in anyways because I am such a nice program :)");
        }

        // Pulling who they said that they were
        String username = JOptionPane.showInputDialog(
                this,
                "Please input your username: ",
                "Forgot Password",
                JOptionPane.PLAIN_MESSAGE);

        // Actually putting in what they said (if they didn't hit cancel)
        if (username == null) return;
        view.forgotPassword(username);

    }

    /**
     * Internal method to get username and password for a new user
     */
    private void newUser() {

        // Getting their username
        String username = JOptionPane.showInputDialog(
                this,
                "Please input your username:",
                "Grabbing Information",
                JOptionPane.PLAIN_MESSAGE);

        // Getting their password
        String password = JOptionPane.showInputDialog(
                this,
                "Please input your password: ",
                "Grabbing Information",
                JOptionPane.PLAIN_MESSAGE);

        view.newUser(new User(username, password));

    }

    
    @Override
    public void updateTheme() {
        super.updateTheme();
        usernameInput.updateTheme();
        passwordInput.updateTheme();
        passwordLabel.setForeground(Panle.colors.getText());
        userNameLabel.setForeground(Panle.colors.getText());
        loginButton.updateTheme();
        forgotPasswordButton.updateTheme();
        titleLabel.setForeground(Panle.colors.getText());
    }
    

    /**
     * Logs in the user that currently has their username in the username slot
     */
    public void login() {
        view.submitLogin(usernameInput.getText(), passwordInput.getText());
    }
    
    

    private JLabel titleLabel;
    private JLabel passwordLabel;
    private JLabel userNameLabel;
    private PrettyTextInput usernameInput;
    private PrettyPasswordField passwordInput;
    private PrettyButton forgotPasswordButton;
    private PrettyButton loginButton;
    private PrettyButton signUpButton;

}
