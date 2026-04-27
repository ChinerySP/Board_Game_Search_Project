package view.panle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JMenuItem;
import javax.imageio.ImageIO;
import java.net.URI;
import java.net.URL;
import model.*;
import view.*;
import view.panle.customComponents.RoundedPanle;

/**
 * A subpanle that shows the details of any arbitrary game.
 * @author Sam Whitlock
 */
public class GameDetailsSubPanle extends Panle {

    /**
     * Creates a new GameDetailsSubPanle that shows the details of the inputted game
     * @param view The view that owns this panle
     * 
     */
    public GameDetailsSubPanle(View view) {
        super("gamedetails", view);

        // Creating a simple internal content panle so that we can use a scorllbar
        this.setLayout(new java.awt.BorderLayout());
        contentPanel = new JPanel(new GridBagLayout());
        // contentPanel.setPreferredSize(new Dimension(10, 10));
        contentPanel.setOpaque(false);

        // This panle will use GridBag so that we can make things spread across the screen or be split at our leisure
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        // In the top left, we have a bunch of information about the game stacked vertically in an InfoPanle
        JPanel infoPanle = new JPanel();
        infoPanle.setLayout(new BoxLayout(infoPanle, BoxLayout.Y_AXIS));
        infoPanle.setOpaque(false);

        titleLabel = new JLabel("Game Name");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setPreferredSize(new Dimension(this.getWidth() - 150, 28));
        titleLabel.setForeground(Panle.colors.getText());

        playersLabel = new JLabel("Number of Players: -");
        playersLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        playersLabel.setForeground(Panle.colors.getText());

        categoryLabel = new JLabel("Category: -");
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        categoryLabel.setForeground(Panle.colors.getText());

        addToListButton = new JButton("Add to List");
        addToListButton.setFont(new Font("Arial", Font.PLAIN, 14));
        addToListButton.setFocusPainted(false);

        // Giving the button something to do
        addToListButton.addActionListener(e -> {
            if (toDisplay == null)
                return;

            // Love pop up menus 
            JPopupMenu popupMenu = new JPopupMenu();

            // A button to add a new list
            JMenuItem createNewListItem = new JMenuItem("+ Create New List");
            createNewListItem.addActionListener(event -> {
                // Pop open a tiny window asking for the name
                String listName = JOptionPane.showInputDialog(
                        this,
                        "Enter a name for your new list:",
                        "Create New List",
                        JOptionPane.PLAIN_MESSAGE);

                // If they typed something and didn't hit cancel, send it up the chain
                if (listName != null && !listName.trim().isEmpty()) {
                    GameList toAdd = new GameList(listName);
                    toAdd.addGame(toDisplay);
                    view.getUser().addGameList(toAdd);
                }
            });
            popupMenu.add(createNewListItem);
            popupMenu.addSeparator();

            // Making sure not to do anything weird if they don't have any lists
            if (user != null && !user.getGameLists().isEmpty()) {

                // Loop through their lists and add them to the dropdown
                for (GameList list : user.getGameLists()) {
                    JMenuItem item = new JMenuItem(list.getName());

                    // When a specific list is clicked, trigger the callback
                    item.addActionListener(event -> {
                        if (toDisplay != null) {
                            list.addGame(toDisplay);
                            view.refreshPanles();
                        } 
                    });
                    popupMenu.add(item);
                }

            }

            // Show the menu exactly positioned underneath the button
            popupMenu.show(addToListButton, 0, addToListButton.getHeight());
        });

        // Adding in a scroll panle so that if it gets long you can see it
        createScrollPane();

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
        contentPanel.add(infoPanle, constraints);

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
        contentPanel.add(imagePlaceholder, constraints);

        // Lastly, we have a description area at the bottom
        descriptionArea = new JTextArea("Game description will appear here...");
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setForeground(Panle.colors.getText());
        descriptionArea.setOpaque(false);
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setMinimumSize(new Dimension(10, 10));

        // Add description to the main layout, spanning both columns
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        contentPanel.add(descriptionArea, constraints);

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

        // Creating the review information
        createRatingSection();

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

        try {
            // Getting the URL of the image from the game
            URL imageUrl = new URI(toDisplay.getThumbnail()).toURL();

            // Trying to read the image over the network
            Image webImage = ImageIO.read(imageUrl);

            // If it works, we use it, otherwise, we can't use it, so we jump to a default
            if (webImage != null) {
                // Scale the image to fit 150x150 placeholder 
                Image scaledImage = webImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                imagePlaceholder.setIcon(new ImageIcon(scaledImage));
                imagePlaceholder.setText("");
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

        // Making sure that they aren't dragged to the bottom of the screen for no reason
        descriptionArea.setCaretPosition(0);
        javax.swing.SwingUtilities.invokeLater(() -> {
            if (scrollPane != null && scrollPane.getVerticalScrollBar() != null) {
                scrollPane.getVerticalScrollBar().setValue(0);
            }
        });

    }

    /**
     * Updates the game that is being displayed
     * @param gameToDisplay The game that will be displayed
     */
    public void setGame(Game gameToDisplay) {
        toDisplay = gameToDisplay;
        // Automatically updating so that it is instantly displayed
        update();
    }

    /**
     * Creates and adds a scrollpane to this gamedetailssubpanle
     * Intended as an internal function to only be called by the constructor.
     */
    private void createScrollPane() {

        // Creating the thin
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Styling it
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {

                /**
                 * Overrides the regular JScrollPane up button to not show up
                 * @param orientation Not used, left over from overriding
                 */
                @Override
                protected JButton createDecreaseButton(int orientation) {
                    return createZeroButton();
                }

                /**
                 * Overrides the regular JScrollPane down button to not show up
                 * @param orientation Not used, left over from overriding
                 */
                @Override
                protected JButton createIncreaseButton(int orientation) {
                    return createZeroButton();
                }

                /**
                 * Creates a fake button that doesn't show up
                 */
                private JButton createZeroButton() {
                    JButton button = new JButton();
                    Dimension zero = new Dimension(0, 0);
                    button.setPreferredSize(zero);
                    button.setMinimumSize(zero);
                    button.setMaximumSize(zero);
                    button.setOpaque(false);
                    button.setContentAreaFilled(false);
                    button.setBorderPainted(false);
                    button.setFocusable(false);
                    return button;
                }

                /**
                 * Overrides the track to be clear
                 * @param g Not used, left over from overriding
                 * @param c Not used, left over from overriding
                 * @param trackBounds Not used, left over from overriding
                 */
                @Override
                protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                    // Setting a color with 0 alpha
                    // c.setBackground(new Color(0, 0, 0, 0));
                }

                /**
                 * Overrides the thumb (like, the button that goes up and down) to be fancy
                 * @param g The graphics object to draw on 
                 * @param c The component to be drawing on 
                 * @param thumbBounds The size of the thumb
                 */
                @Override
                protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                    // Normal stuff to make sure it should run and look right
                    if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
                        return;
                    }
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Setting the color to be a semi-transparent grey
                    g2.setColor(new Color(150, 150, 150, 100));

                    // Making a nice little rounded rectangle
                    g2.fillRoundRect(thumbBounds.x + 3, thumbBounds.y + 2, thumbBounds.width - 6, thumbBounds.height - 4,
                            8, 8);
                    g2.dispose();
                }

            });

        // It kept duplicating the thing because it wasn't updating, so I went ahead and cretaed an eventListener
        // That makes it repaint whenever it gets moved
        scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> scrollPane.repaint());

        // Updating the scroll path to be transparent 
        scrollPane.getVerticalScrollBar().setOpaque(false);

        // Speed. I am speed
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Making sure it actually exists
        this.add(scrollPane, BorderLayout.CENTER);

    }

    /**
     * Updates the theme of this panle
     */
    @Override
    public void updateTheme() {
        super.updateTheme();
        titleLabel.setForeground(Panle.colors.getText());
        playersLabel.setForeground(Panle.colors.getText());
        categoryLabel.setForeground(Panle.colors.getText());
        descriptionArea.setForeground(Panle.colors.getText());

        // Recreate the rating section to ensure colors match
        if (toDisplay != null) {
            createRatingSection();
        }
    }

    /**
     * Returns the game that is currently being displayed
     * @return Game The Game that is currently being displayed
     */
    public Game getGame() {
        return toDisplay;
    }

    /**
     * Sets the User that is currently using this panle
     * @param user The user that will be displayed
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Creates the review section of this game.
     * To be called internally by the constructor
     */
    private void createRatingSection() {

        // We can only actually create it if we have a user, so I'll check to make sure
        if (user == null) {
            // Defaulting to asking the view which user is signed in
            user = view.getUser();
        }

        // Now making sure we have a rating to dhow
        rating = user.getRating(toDisplay);

        // Making sure we don't recreate stuff and make it weird
        if (ratingPanle != null) contentPanel.remove(ratingPanle);
        
        // Using gridbag so that I can have different sizes and everything
        ratingPanle = new RoundedPanle(15);
        ratingPanle.setLayout(new GridBagLayout());
        ratingPanle.setBackground(Panle.colors.getMantle());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // The top left section, a small square that is whether or not they recommend the game
        JButton recommendButton = new JButton(rating.getRecommended() ? "👍" : "👎" ); 
        recommendButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        recommendButton.setForeground(Panle.colors.getText());
        recommendButton.setToolTipText("Do you recommend this game?");
        recommendButton.setFocusPainted(false);
        recommendButton.setContentAreaFilled(false);
        recommendButton.setBorderPainted(false);

        // Making the thumb update based on the user's touch
        recommendButton.addActionListener(e -> {
            if (recommendButton.getText().equals("👍")) {
                recommendButton.setText("👎");
                rating.setRecommended(false);
            } else {
                recommendButton.setText("👍");
                rating.setRecommended(true);
            }
        });

        // Adding it in 
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0; 
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        ratingPanle.add(recommendButton, gbc);

        // The top right section, that has the number of stars out of five
        JPanel starPanel = new JPanel();
        starPanel.setOpaque(false);
        starPanel.setLayout(new BoxLayout(starPanel, BoxLayout.X_AXIS));

        // The stars themselves
        JLabel[] stars = new JLabel[5];
        for (int i = 0; i < 5; i++) {

            // A final variable because it needs to be final to be used in the mouse listener
            final int starValue = i;

            // Starting out with only blank stars
            stars[i] = new JLabel("☆");
            stars[i].setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
            stars[i].setForeground(new Color(255, 215, 0));

            // Add a click listener to each star
            stars[i].addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {

                    // Saving the one that they currently are so that they can use it to save later
                    rating.setScore(starValue);

                    // Looping through all of the stars
                    for (int j = 0; j < 5; j++) {
                        if (j <= rating.getScore()) {
                            stars[j].setText("★");
                        } else {
                            stars[j].setText("☆");
                        }
                    }
                }
            });
            starPanel.add(stars[i]);
        }

        // Setting the number of stars for the rating before doing anything else
        for (int j = 0; j < 5; j++) {
            if (j <= rating.getScore()) {
                stars[j].setText("★");
            } else {
                stars[j].setText("☆");
            }
        }
        
        // Adding them in 
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.0; 
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 0, 20);
        ratingPanle.add(starPanel, gbc);

        // A spacer between the stars and the thumb
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0; 
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        ratingPanle.add(new JLabel(), gbc);
    
        // Finally, the bottom section, that is just the review
        JTextArea reviewTextArea = new JTextArea(rating.getReview());
        reviewTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        reviewTextArea.setForeground(Panle.colors.getText());
        reviewTextArea.setOpaque(false);
        reviewTextArea.setLineWrap(true);
        reviewTextArea.setWrapStyleWord(true);
        reviewTextArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateData(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateData(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateData(); }

            private void updateData() {
                rating.setReview(reviewTextArea.getText());
            }
        });
        reviewTextArea.setRows(5);
        // reviewTextArea.setPreferredSize(new Dimension(ratingPanle.getWidth(), ratingPanle.getHeight()));

        // I wanted a review border so that the user could see where they were meant to type
        Border reviewBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Panle.colors.getSubtext1()), "Review", TitledBorder.LEADING, TitledBorder.TOP, null, Panle.colors.getText());
        reviewTextArea.setBorder(reviewBorder);

        // Adding it in 
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        ratingPanle.add(reviewTextArea, gbc);

        // Adding the rating to the contentpanle
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(ratingPanle, constraints);

    }

    // Storing the lists that the user has so that we can add to them or remove from them
    private User user;

    // A scollpane. So you can... y'know... scroll
    private JScrollPane scrollPane;

    // The review that belongs to this game for this user
    private Rating rating;
    private RoundedPanle ratingPanle;

    // A content panle that holds all of the content
    private JPanel contentPanel;

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
