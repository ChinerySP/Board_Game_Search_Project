package view.panle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.Image;

// TODO prune
import model.*;

/**
 * A subpanle that shows the details of any arbitrary game.
 * @author Sam Whitlock
 */
public class GameDetailsSubPanle extends Panle {

    /**
     * Creates a new GameDetailsSubPanle that shows the details of the inputted game
     * @param Game game The game to display
     */
    public GameDetailsSubPanle() {
        super("gamedetails");

        // This panle will use GridBag so that we can make things spread across the screen or be split at our leisure
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        // In the top left, we have a bunch of information about the game stacked vertically in an InfoPanle
        JPanel infoPanle = new JPanel();
        infoPanle.setLayout(new BoxLayout(infoPanle, BoxLayout.Y_AXIS));
        infoPanle.setOpaque(false);

        titleLabel = new JLabel("Game Name");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Panle.TEXT_COLOR);

        playersLabel = new JLabel("Number of Players: -");
        playersLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        playersLabel.setForeground(Panle.TEXT_COLOR);

        categoryLabel = new JLabel("Category: -");
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        categoryLabel.setForeground(Panle.TEXT_COLOR);

        addToListButton = new JButton("Add to List");
        addToListButton.setFont(new Font("Arial", Font.PLAIN, 14));
        addToListButton.setFocusPainted(false);

        // Add some vertical spacing between elements in the info panel
        infoPanle.add(titleLabel);
        infoPanle.add(Box.createRigidArea(new Dimension(0, 10))); 
        infoPanle.add(playersLabel);
        infoPanle.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanle.add(categoryLabel);
        infoPanle.add(Box.createRigidArea(new Dimension(0, 15)));
        infoPanle.add(addToListButton);

        // Add infoPanle to the main layout
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.6; // Take up 60% of the top width
        constraints.weighty = 0.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        this.add(infoPanle, constraints);


        // In the top right, we will have whatever thumbnail the API gave us for the picture
        ImageIcon placeHolder = new ImageIcon("resources/ImageNotFound.png");
        imagePlaceholder = new JLabel(placeHolder, SwingConstants.CENTER);
        imagePlaceholder.setPreferredSize(new Dimension(150, 150));
        imagePlaceholder.setMinimumSize(new Dimension(150, 150));
        imagePlaceholder.setOpaque(false);

        // Adding the image to the layout
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.4;
        constraints.weighty = 0.0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTHEAST;
        this.add(imagePlaceholder, constraints);


        // Lastly, we have a description area at the bottom
        descriptionArea = new JTextArea("Game description will appear here...");
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setForeground(Panle.TEXT_COLOR);
        descriptionArea.setOpaque(false);
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        // Add description to the main layout, spanning both columns
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0; 
        constraints.fill = GridBagConstraints.BOTH; 
        this.add(descriptionArea, constraints);
        
        // Actually displaying everything
        this.update();

    }
    
    /**
     * Updates everything to reflect the current game
     */
    public void update() {
        if (toDisplay == null) {
            // Hide everything or show default text if no game is selected yet
            titleLabel.setText("No Game Selected");
            playersLabel.setText("");
            categoryLabel.setText("");
            descriptionArea.setText("Click on a game from the list to view its details here.");
            addToListButton.setVisible(false);
            imagePlaceholder.setVisible(false);
            return;
        }

        // Show components in case they were hidden
        addToListButton.setVisible(true);
        imagePlaceholder.setVisible(true);

        // Update title
        titleLabel.setText(toDisplay.getName());
        
        // Format player count (e.g., "2 - 4" or just "2" if min and max are the same)
        if (toDisplay.getMinPlayer() == toDisplay.getMaxPlayer()) {
            playersLabel.setText("Number of Players: " + Math.max(1, toDisplay.getNumPlayer()));
        } else {
            playersLabel.setText("Number of Players: " + toDisplay.getMinPlayer() + " - " + toDisplay.getMaxPlayer());
        }

        // Handle categories (mmmm I miss my cats)
        String cats = toDisplay.getCategories();
        if (cats == null || cats.trim().isEmpty()) {
            categoryLabel.setText("Category: Unspecified");
        } else {
            categoryLabel.setText("Category: " + cats);
        }

        // Update description
        descriptionArea.setText(toDisplay.getDescription());

        // TODO add a try catch to see if we can load the thumbnail
        try {
            // Getting the URL of the image from the game
            URL imageUrl = new URL(toDisplay.getThumbnail());
            
            // Trying to read the image over the network
            Image webImage = ImageIO.read(imageUrl);
            
            // If it works, we use it, otherwise, we can't use it, so we jump to a default
            if (webImage != null) {
                // 3. Scale the image to fit your 150x150 placeholder nicely
                Image scaledImage = webImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                imagePlaceholder.setIcon(new ImageIcon(scaledImage));
                imagePlaceholder.setText(""); // Clear the text
            } else {
                // If the image is null, force it into the catch block
                throw new Exception("Image data was null");
            }

        } catch (Exception e) {
            
            // Load the local fallback image
            ImageIcon fallbackIcon = new ImageIcon("resources/ImageNotFound.png");
            
            // Scale the fallback 
            Image scaledFallback = fallbackIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            
            imagePlaceholder.setIcon(new ImageIcon(scaledFallback));
        }

        // Force a redraw just in case the size of the text drastically changed
        this.revalidate();
        this.repaint();

    }

    /**
     * Updates the game that is being displayed
     * @param gameToDisplay
     */
    public void setGame(Game gameToDisplay) {
        toDisplay = gameToDisplay;
        // Automatically updating so that it is instantly displayed
        update();
    }

    /**
     * Returns the game that is currently being displayed
     * @return Game The Game that is currently being displayed
     */
    public Game getGame() {
        return toDisplay;
    }


    // Visual components
    private JLabel titleLabel;
    private JLabel playersLabel;
    private JLabel categoryLabel;
    private JButton addToListButton;
    private JLabel imagePlaceholder;
    private JTextArea descriptionArea;

    // The game that is going to be displayed
    private Game toDisplay;

}
