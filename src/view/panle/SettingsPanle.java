package view.panle;

import model.User;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import view.*;
import view.panle.customComponents.*;
import view.panle.colors.*;




/**
 * The Panle that allows for changing the settings of the program.
 * @author Sam Whitlock
 */
public class SettingsPanle extends Panle {

   
    /**
     * Creates a new settings panle
     * @param View The view that owns this panle
     */
    public SettingsPanle(View view) {
        super("settings", view);
        user = null;

        // The settings panle holds everything in a gridbag layout 
        // This way, we can have the settings on one side and a meme on the other with a spacer between
        this.setLayout(new GridBagLayout());

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
        right = new Panle("rightSettings", view);
        right.setLayout(new GridLayout());
        ImageIcon tjs = new ImageIcon("resources/TJS.png");
        Image scaled = tjs.getImage().getScaledInstance(250, -1, Image.SCALE_SMOOTH);
        right.add(new JLabel(new ImageIcon(scaled)), constraints);

        // The left side, which holds the different options
        apiToggleButton = new PrettyToggleButton("API On");
        apiToggleButton.addActionListener(e -> this.toggleApi());
        darkModeToggleButton = new PrettyToggleButton("Dark Mode On");
        darkModeToggleButton.addActionListener(e -> this.toggleColorMode());
        deleteAccount = new PrettyButton("Delete Account");
        deleteAccount.addActionListener(e -> this.deleteAccount());
        resetPasswordButton = new PrettyButton("Reset Password");
        resetPasswordButton.addActionListener(e -> this.resetPassword());
        logOutButton = new PrettyButton("Log Out");
        logOutButton.addActionListener(e -> this.logOut());
        left = new Panle("rightSettings", view);
        left.setLayout(new GridBagLayout());
        constraints.gridy = 0;
        left.add(apiToggleButton, constraints);
        constraints.gridy = 1;
        left.add(deleteAccount, constraints);
        constraints.gridy = 2;
        left.add(darkModeToggleButton, constraints);
        constraints.gridy = 3;
        left.add(resetPasswordButton, constraints);
        constraints.gridy = 4;
        left.add(logOutButton, constraints);

        // Adding everything in
        GridBagConstraints outerConstraints = new GridBagConstraints();
        outerConstraints.weighty = 1.0;
        outerConstraints.fill = GridBagConstraints.BOTH;

        // Adding in the left with a weight of 0.5
        outerConstraints.weightx = 0.5;
        outerConstraints.gridx = 0;
        left.setPreferredSize(new Dimension(0, 0));
        this.add(left, outerConstraints);

        // Adding in the spacer with a weight of 0.0 (to use the preferredSize)
        outerConstraints.weightx = 0.0;
        outerConstraints.fill = GridBagConstraints.VERTICAL;
        outerConstraints.gridx = 1;
        this.add(Box.createHorizontalStrut(Panle.DISTANCE_BETWEEN_COMPONENTS), outerConstraints);

        // Adding in the right with a weight of 0.5 to match the left
        outerConstraints.weightx = 0.5;
        outerConstraints.fill = GridBagConstraints.BOTH;
        outerConstraints.gridx = 2;
        right.setPreferredSize(new Dimension(0, 0));
        this.add(right, outerConstraints);

        // Making sure everything is visible
        this.setVisible(true);
        this.setOpaque(false);

        System.out.println("A settings panle has been made");

    }
    
    /**
     * Internal function to toggle the API, called whenever the API toggle button has been clicked
     */
    private void toggleApi() {

        if (apiToggleButton.isSelected()) {

            // Updating the text to say on
            apiToggleButton.setText("API on");

            // Telling the rest of the system to turn off the API
            view.setAPI(true);

        } else {

            // Updating the text to say off
            apiToggleButton.setText("API off");

            // Telling the rest of the system to turn on the API            
            view.setAPI(false);

        }

    }
    
    /**
     * Internal function that allows the user to change their password
     */
    private void resetPassword() {
        // Extra checking to ensure there are no null pointer exceptions
        if (view.getUser() == null)
            return;

        view.getUser().resetPassword(
                JOptionPane.showInputDialog(
                        this,
                        "Enter your new password:",
                        "Reset Password",
                        JOptionPane.PLAIN_MESSAGE));

    }

    /**
     * Internal function to ensure that users want to delete their account before we actually do
     */
    private void deleteAccount() {

        // First, we check to make sure that they want to delete their account for sure
        String confirmation = JOptionPane.showInputDialog(
                this,
                "THIS WILL PERMANENTLY DELETE YOUR ACCOUNT.\nIf you are sure, then please type \"I understand\"",
                "Delete Account",
                JOptionPane.WARNING_MESSAGE);

        // Checking their confirmation
        if (confirmation.equals("I understand")) {

            if (user == null) {
                System.out.println("Ayooo why is the user signed into the settings null?????");
            }

            // Deleting the account
            view.deleteAccount(user);

            // Kicking the user back out to the login screen
            view.promptLogin();

        }

    }
    
    /**
     * Changes the color mode of the display
     * @param boolean Whether or not darkmode should be set (dark is default)
     */
    public void setColorMode(boolean isDarkMode) {
        if (isDarkMode) {
            Panle.colors = new DarkMode();
        } else {
            Panle.colors = new LightMode();
        }
        view.updateTheme();
    }


    /**
     * Internal function to update the color mode when the toggle button is pressed
     */
    private void toggleColorMode() {
        if (darkModeToggleButton.isSelected()) {
            setColorMode(false);
            user.setDarkMode(false);
            darkModeToggleButton.setText("Dark Mode Off");
        } else {
            setColorMode(true);
            user.setDarkMode(true);
            darkModeToggleButton.setText("Dark Mode On");
        }
    }

    /**
     * Internal function to log out of the program
     */
    private void logOut() {
        view.logOut();
    }

    @Override
    public void updateTheme() {
        super.updateTheme();
        left.updateTheme();
        right.updateTheme();
        resetPasswordButton.updateTheme();
        deleteAccount.updateTheme();
        logOutButton.updateTheme();
    }


    /**
     * Shows the panle for the inputted user
     * @return boolean Whether or not this panle has a user set 
     */
    public boolean hasUser() {
        return this.user != null;
    }

    /**
     * Sets the user that this settings panle will display
     * @param User user The user to be stored in this settings panle
     */
    public void setUser(User user) {
        System.out.println("Settings now has a user");
        this.user = user;
    }

    /**
     * Gets the user that this settings panle is displaying
     * @return User user The user to be stored in this settings panle
     */
    public User getUser() {
        return user;
    }
    
    // The user to diplay
    private User user;

    // Display elements
    private Panle left;
    private Panle right;
    private PrettyButton resetPasswordButton;
    private PrettyButton deleteAccount;
    private PrettyToggleButton apiToggleButton;
    private PrettyToggleButton darkModeToggleButton;
    private PrettyButton logOutButton;

}
