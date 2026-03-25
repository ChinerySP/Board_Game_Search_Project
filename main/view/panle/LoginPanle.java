package main.view.panle;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main.view.Panle;

/**
 * The panle that allows the user to sign in.
 * @author Sam Whitlock
 */
public class LoginPanle extends Panle {

    /**
     * Creates a new instance of the LoginPanle
     */
    public LoginPanle() {
        super("login");

        // The login panle will a gridbag layout to center everything, then a Box to hold the components in the center
        this.setLayout(new GridBagLayout());

        // Making sure everything will be centeredd
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 0.0;
        constraints.weightx = 0.0;

        // The login stuff
        usernameInput = new JTextField("Username");
        passwordInput = new JTextField("Password");
        forgotPasswordButton = new JButton("Forgot Password");
        loginButton = new JButton("Login");
        titleLabel = new JLabel("TJS", SwingConstants.CENTER);

        // Putting it all together into a pretty layout (I'm using pretty loosley here)
        componentBox = Box.createVerticalBox();
        componentBox.add(titleLabel);
        componentBox.add(usernameInput);
        componentBox.add(passwordInput);
        componentBox.add(loginButton);
        componentBox.add(forgotPasswordButton);
        this.add(componentBox, constraints);

        // Some simple changes to ensure that the panle looks nice
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setPreferredSize(new Dimension(100, 20));
        usernameInput.setPreferredSize(new Dimension(100, 20));
        passwordInput.setPreferredSize(new Dimension(100, 20));
        loginButton.setPreferredSize(new Dimension(50, 20));
        forgotPasswordButton.setPreferredSize(new Dimension(50, 20));

    }
    
    

    private JLabel titleLabel;
    private JTextField usernameInput;
    private JTextField passwordInput;
    private JButton forgotPasswordButton;
    private JButton loginButton;
    private Box componentBox;

}
