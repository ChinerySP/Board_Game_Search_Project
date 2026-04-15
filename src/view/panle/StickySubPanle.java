package view.panle;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import view.panle.customComponents.*;
import view.panle.colors.*;
import view.*;

/**
 * The panel that always "sticks" to the top of the screen. It holds things that can be useful at all points in the program, like the search bar and a settings button.
 * @author Sam Whitlock
 */
public class StickySubPanle extends Panle {

    /**
     * Creates a new instance of the StickySubPanle
     * @param View The view that owns this panle
     */
    public StickySubPanle(View view) {
        super("sticky", view);

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

        // Removing any other stuff from the icons
        homeButton.setBorder(null);
        settingsButton.setBorder(null);
        homeButton.setContentAreaFilled(false);
        settingsButton.setContentAreaFilled(false);
        homeButton.setBorder(new EmptyBorder(10, 10, 10, 5));
        settingsButton.setBorder(new EmptyBorder(10, 5, 10, 10));
        homeButton.setFocusable(false);
        settingsButton.setFocusable(false);

        // Making teh buttons actually do stuff
        settingsButton.addActionListener(e -> {
            view.showPanle("settings");
        });
        homeButton.addActionListener(e -> {
            view.showPanle("dashboard");
        });

        // Making the search input actually search stuff when they hit enter
        searchInput.addActionListener(e -> this.search());

        // Adding everything to the component box
        componentBox.add(homeButton);
        componentBox.add(searchInput);
        componentBox.add(settingsButton);

        // Adding in the actual box
        this.add(componentBox);

        // Some simple changes to ensure that the panle looks nice
        this.setMaximumSize(new Dimension(this.getMaximumSize().width, 30));

    }
    

    /**
     * An internal function that searches for whatever the user requests,
     * then switches to the searchresults subpanle
     */
    private void search() {

        // Getting and splitting their query by spaces
        String[] query = searchInput.getText().trim().split(" ");

        // Prevent empty searches
        if (query.length != 0) {
            view.search(query);
            view.showPanle("searchresults");
            System.out.print("Searching for: ");
            for (String element : query) {
                System.out.print(element + " ");
            }
            System.out.println();
        }

    }
    
    @Override
    public void updateTheme() {
        searchInput.updateTheme();
        
        // Updating the image
        if (Panle.colors instanceof LightMode) {
            ImageIcon homeIcon = new ImageIcon("resources/HomeLight.png");
            homeButton.setIcon(homeIcon);
            ImageIcon settingsIcon = new ImageIcon("resources/SettingsLight.png");
            settingsButton.setIcon(settingsIcon);
        } else {
            ImageIcon homeIcon = new ImageIcon("resources/Home.png");
            homeButton.setIcon(homeIcon);
            ImageIcon settingsIcon = new ImageIcon("resources/Settings.png");
            settingsButton.setIcon(settingsIcon);
        }

        super.updateTheme();
    }

    // The components that we want on the screen
    private JButton homeButton;
    private JButton settingsButton;
    private PrettyTextInput searchInput;

    private Box componentBox;

}
