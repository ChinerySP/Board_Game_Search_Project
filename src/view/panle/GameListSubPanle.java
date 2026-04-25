package view.panle;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.JComponent;

import view.panle.colors.DarkMode;
import view.panle.customComponents.RoundedPanle;
import view.panle.customComponents.RoundedPopupMenu;
import view.*;
import model.*;


/**
 * A panle that holds a GameList to display to the user
 * @author Sam Whitlock
 */
public class GameListSubPanle extends Panle {

    /**
     * Creates a GameListSubPanle to display the inputted Games
     * @param View The view that owns this panle
     * @param GameList The games to display
     */
    public GameListSubPanle(View view, GameList games) {
        super("gamelist", view);

        // Setting up the internal container
        this.setLayout(new BorderLayout());
        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setOpaque(false);

        // Creating the internal scrollpane  
        scrollPane = new JScrollPane(listContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Creating the title panel with an edit button `
        createTitleSection();
        

        // Making the scrollpane look a bit better
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // A pop up menu that they can use to remove a game from a list
        initPopupMenu();

        // Adding the scrollpane and updating everythign
        this.styleScrollBar(scrollPane);
        this.add(scrollPane, BorderLayout.CENTER);
        this.games = games;
        updateGames();
    }

    /**
     * Creates a blank GameListSubPanle to be populated with games later
     * @param View The view that owns this subpanle
     */
    public GameListSubPanle(View view) {
        super("gamelist", view);
        
        // Initializing the layout and internal container
        this.setLayout(new BorderLayout());
        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setOpaque(false);
    
        // Setting up the scrollpane to attach to the internal conatiner
        scrollPane = new JScrollPane(listContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Defauling to a null list
        this.games = null;
        createTitleSection();

        // Setting teh scrollpane to look a bit nicer
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // A pop up menu that they can use to remove a game from a list
        initPopupMenu();

        // Actually adding the scrollpane and then updating the display
        this.styleScrollBar(scrollPane);
        this.add(scrollPane, BorderLayout.CENTER);
        updateGames();
    }

    /**
     * Updates the visual game panel elements to reflect the GameList associated with this panle
     */
    public void updateGames() {

        // Clearing the current visuals
        listContainer.removeAll();
        gamePanles = new ArrayList<>();

        // If there aren't any lists, then we can default to a simple title saying No Lists
        if (games == null) {

            // A fake game giving them instructions on how to create a list
            RoundedPanle toAdd = new Panle("fakeGame", view);
            toAdd.setLayout(new GridBagLayout());

            // Making it look not terrible
            GridBagConstraints constraints = new GridBagConstraints();
            toAdd.setBorder(BorderFactory.createEmptyBorder(10, 10,10, 10));
            
            // Creating everything just like we did for regular games
            JTextArea name = new JTextArea("You don't have any lists!");
            JTextArea desc = new JTextArea("To create one, you can click the plus sign above your collections on the right.");
            name.setForeground(Panle.colors.getText());
            name.setOpaque(false);
            name.setEditable(false);
            name.setLineWrap(true);
            name.setWrapStyleWord(true);
            name.setFont(new Font("Ariel", Font.BOLD, 17));
            desc.setForeground(Panle.colors.getText());
            desc.setOpaque(false);
            desc.setEditable(false);
            desc.setLineWrap(true);
            desc.setWrapStyleWord(true);
            toAdd.setBackground(Panle.colors.getBase());


            // Adding in the name of the game and the description
            toAdd.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            constraints.fill = GridBagConstraints.BOTH;
            constraints.weighty = 1.0;
            constraints.gridx = 0;
            constraints.weightx = 0.25;
            constraints.insets = new java.awt.Insets(5, 7, 5, 0);
            toAdd.add(name, constraints);
            constraints.gridx = 1;
            constraints.weightx = 0.75;
            constraints.insets = new java.awt.Insets(5, 7, 5, 7);
            toAdd.add(desc, constraints);

            // More stuff to match the rest
            toAdd.setRadius(toAdd.getRadius() - 5);
            toAdd.setPreferredSize(new Dimension(0, GameListSubPanle.PANLE_HEIGHT));
            toAdd.setMaximumSize(new Dimension(Integer.MAX_VALUE, GameListSubPanle.PANLE_HEIGHT));

            listContainer.add(toAdd);

            return;

        }

        // Iterating over the game panels and recreating them
        for (Game game : games) {

            // The new panle that we will be adding to the list
            RoundedPanle toAdd = new Panle("gamePanle", view);

            // Using GridBag again so that I can have a 25/75 split for the name and the description
            toAdd.setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();

            // Fetching the stuff to put on the screen
            JTextArea name = new JTextArea(game.getName());
            JTextArea desc = new JTextArea(game.getDescription());

            // Styling the name and description
            name.setForeground(Panle.colors.getText());
            name.setOpaque(false);
            name.setEditable(false);
            name.setLineWrap(true);
            name.setWrapStyleWord(true);
            name.setFont(new Font("Ariel", Font.BOLD, 17));
            desc.setForeground(Panle.colors.getText());
            desc.setOpaque(false);
            desc.setEditable(false);
            desc.setLineWrap(true);
            desc.setWrapStyleWord(true);
            toAdd.setBackground(Panle.colors.getBase());


            // Adding in some spacing between them all 
            toAdd.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            // Adding in the name of the game and the description
            constraints.fill = GridBagConstraints.BOTH;
            constraints.weighty = 1.0;
            constraints.gridx = 0;
            constraints.weightx = 0.25;
            constraints.insets = new java.awt.Insets(5, 7, 5, 0);
            toAdd.add(name, constraints);
            constraints.gridx = 1;
            constraints.weightx = 0.75;
            constraints.insets = new java.awt.Insets(5, 7, 5, 7);
            if (isDescShowing) { // Making it only show if it is toggled
                toAdd.add(desc, constraints);
            }

            // Because these are generally so small, I am manually decreasing the radius to make it fit nicely
            toAdd.setRadius(toAdd.getRadius() - 5);

            // Updating the height to match the size we want
            toAdd.setPreferredSize(new Dimension(0, PANLE_HEIGHT));
            toAdd.setMaximumSize(new Dimension(Integer.MAX_VALUE, GameListSubPanle.PANLE_HEIGHT));

            // Creating a mouselistener to listen for when a game is clicked
            MouseAdapter clickListener = new MouseAdapter() {

                /**
                 * Overrideing the mouseClicked event for this eventListener
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Only running if it was a left click, otherwise we do nothing (the other methods will catch it (hopefully))
                    if (SwingUtilities.isLeftMouseButton(e) && onGameClicked != null) {
                        if (onGameClicked != null) {
                            onGameClicked.accept(game);
                        }
                    }
                }
                
                @Override
                public void mousePressed(MouseEvent e) { showPopup(e); }

                @Override
                public void mouseReleased(MouseEvent e) { showPopup(e); }

                private void showPopup(MouseEvent e) {
                    if (e.isPopupTrigger()){
                        // Setting eh currently clicked on game to be this one
                        currentlyRightClickedGame = game; 
                        
                        // Showing the game that they want to show
                        sharedPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            };

            // Adding the listener to all of the things (this way, it detects it even if I click the text)
            toAdd.addMouseListener(clickListener);
            name.addMouseListener(clickListener);
            desc.addMouseListener(clickListener);

            // Actually adding it in to the list
            gamePanles.add(toAdd);

        }

        // If there aren't any games, then we need to have a fake game that lets them know that
        if (gamePanles.size() == 0) {

            // The new panle that we will be adding to the list
            RoundedPanle toAdd = new Panle("gamePanle", view);

            // Using GridBag again so that I can have a 25/75 split for the name and the description
            toAdd.setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();

            // Creating stuff ot put on the screen
            JTextArea name = new JTextArea("There aren't any games in this list yet!");
            JTextArea desc = new JTextArea("You can add some by clicking on a game and selecting \"Add to List\"!");

            // Styling the name and description
            name.setForeground(Panle.colors.getText());
            name.setOpaque(false);
            name.setEditable(false);
            name.setLineWrap(true);
            name.setWrapStyleWord(true);
            name.setFont(new Font("Ariel", Font.BOLD, 17));
            desc.setForeground(Panle.colors.getText());
            desc.setOpaque(false);
            desc.setEditable(false);
            desc.setLineWrap(true);
            desc.setWrapStyleWord(true);
            toAdd.setBackground(Panle.colors.getBase());

            // Adding in some spacing between them all 
            toAdd.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            // Adding in the name of the game and the description
            constraints.fill = GridBagConstraints.BOTH;
            constraints.weighty = 1.0;
            constraints.gridx = 0;
            constraints.weightx = 0.25;
            constraints.insets = new java.awt.Insets(5, 7, 5, 0);
            toAdd.add(name, constraints);
            constraints.gridx = 1;
            constraints.weightx = 0.75;
            constraints.insets = new java.awt.Insets(5, 7, 5, 7);
            if (isDescShowing) { // Making it only show if it is toggled
                toAdd.add(desc, constraints);
            }

            // Because these are generally so small, I am manually decreasing the radius to make it fit nicely
            toAdd.setRadius(toAdd.getRadius() - 5);

            // Updating the height to match the size we want
            toAdd.setPreferredSize(new Dimension(0, GameListSubPanle.PANLE_HEIGHT));
            toAdd.setMaximumSize(new Dimension(Integer.MAX_VALUE, GameListSubPanle.PANLE_HEIGHT));

            // Actually adding it in to the list 
            gamePanles.add(toAdd);

        }
        
        // Showing all of the gamePanles on the screen
        for (RoundedPanle p : gamePanles) {
            listContainer.add(p);
        }

        // Recreating the title
        createTitleSection();

        // Telling it to draw
        listContainer.revalidate();
        listContainer.repaint();

    }
    
    /**
     * Updates the scrollbar to be styled nicely
     * This has been moved to a specific method because it got so big
     * @param JScrollPane pane The scrollpane that will be styled
     */
    private void styleScrollBar(JScrollPane pane) {
        pane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {

            /**
             * Overrides the regular JScrollPane up button to not show up
             * @param int orientation Not used, left over from overriding
             */
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            /**
             * Overrides the regular JScrollPane down button to not show up
             * @param int orientation Not used, left over from overriding
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
             * @param Graphics g Not used, left over from overriding
             * @param JComponent c Not used, left over from overriding
             * @param Rectangle trackBounds Not used, left over from overriding
             */
            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                // Setting a color with 0 alpha
                // c.setBackground(new Color(0, 0, 0, 0));
            }

            /**
             * Overrides the thumb (like, the button that goes up and down) to be fancy
             * @param Graphcis g The graphics object to draw on 
             * @param JComponent c The component to be drawing on 
             * @param Rectangle thumbBounds The size of the thumb
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
        pane.getVerticalScrollBar().addAdjustmentListener(e -> pane.repaint());

        // Updating the scroll path to be transparent 
        pane.getVerticalScrollBar().setOpaque(false);
    }

    /**
     * Initializes the shared right-click popup menu.
     * Meant to be called inside the constructor, and should not be called elsewhere
     */
    private void initPopupMenu() {
        // Pretty pretty
        sharedPopupMenu = new RoundedPopupMenu(10);
        sharedPopupMenu.setBackground(Panle.colors.getBase());

        // The option to add to a list
        JMenuItem addToListBtn = new JMenuItem("Add to List...");
        addToListBtn.setForeground(Panle.colors.getText());
        addToListBtn.setOpaque(false);

        // The option to add to a list
        JMenuItem removeFromListBtn = new JMenuItem("Remove from this List");
        removeFromListBtn.setForeground(Panle.colors.getText());
        removeFromListBtn.setOpaque(false);

        // What actually runs when the add to list button is clicked
        addToListBtn.addActionListener(e -> {
            if (currentlyRightClickedGame != null) {

                // Fetching all of the lists that the user can access
                ArrayList<GameList> lists = view.getUser().getGameLists();

                // Creating a list of names that they can pick from
                ArrayList<String> names = new ArrayList<String>();
                for (GameList l : lists) {
                    names.add(l.getName());
                }

                // Adding the option to create a list
                names.add("Create New List");

                // Asking them which one they would like to add it to
                String selection = JOptionPane.showInputDialog(this, "Add to List",
                        "Which list would you like to add " + currentlyRightClickedGame.getName() + " to?",
                        JOptionPane.PLAIN_MESSAGE, null, names.toArray(), names).toString();

                // If they hit cancel, then we get null, so we can just exit
                if (selection == null) {
                    return;
                }

                // Handling the chance that they want to create a new list
                if (selection.equals("Create New List")) {
                    // Pop open a tiny window asking for the name
                    String listName = JOptionPane.showInputDialog(
                            this,
                            "Enter a name for your new list:",
                            "Create New List",
                            JOptionPane.PLAIN_MESSAGE);

                    // If they typed something and didn't hit cancel, send it up the chain
                    if (listName != null && !listName.trim().isEmpty()) {
                        GameList toAdd = new GameList(listName);
                        toAdd.addGame(currentlyRightClickedGame);
                        view.getUser().addGameList(toAdd);
                        System.out.println(String.format("Added to %s", view.getUser().getUserName()));
                    }

                    // Making sure everything updates
                    view.refreshPanles();

                    return;
                }

                // Using the name that they picked to find what they want to do
                for (GameList l : lists) {
                    // This could cause a clash, but it won't happen often and would be an issue anyways because this is the only way we are identifying the lists
                    // Plus, this is a beta, so we roll
                    if (l.getName().equals(selection)) {
                        l.addGame(currentlyRightClickedGame);

                        // Making sure everything shows
                        view.refreshPanles();
                        return;
                    }
                }

            }
        });

        // What actually runs when the remove from list button is clicked
        removeFromListBtn.addActionListener(e -> {
            if (currentlyRightClickedGame != null) {
                games.deleteGame(currentlyRightClickedGame.getId());
                view.refreshPanles();
            }
        });

        sharedPopupMenu.add(addToListBtn);
        sharedPopupMenu.add(removeFromListBtn);
    }

    /**
     * Creates the title section at the top of the listPanle.
     */
    private void createTitleSection() {

        // Making sure that we remove the title section before adding  a new one
        if (titlePanle != null) {
            this.remove(titlePanle);

            // If it isn't null, there is a chance that we want to just remove it and exit
            if (!showTitle) {
                return;
            }

        }

        // Making sure that we have something to display
        if (games == null) {
            titlePanle = new JPanel();
            titlePanle.setOpaque(false);
            titlePanle.setLayout(new BorderLayout());   
            titlePanle.add(new JLabel("No List Selected"));
            return;
        }

        // A simple panle that will hold all the things
        titlePanle = new JPanel();
        titlePanle.setOpaque(false);
        titlePanle.setLayout(new BorderLayout());

        // Adding in the trash button on the left
        String trashIconPath = Panle.colors instanceof DarkMode ? "resources/Trash.png" : "resources/TrashLight";
        ImageIcon trashIcon = new ImageIcon(trashIconPath);
        JButton trashButton = new JButton();
        trashButton.setIcon(trashIcon);
        trashButton.setBorder(null);
        trashButton.setContentAreaFilled(false);
        trashButton.setBorder(new EmptyBorder(10, 10, 10, 5));
        titlePanle.add(trashButton, BorderLayout.WEST);

        // Making the trash ask for confirmation and then delete the gamelist
        trashButton.addActionListener(e -> {

            // Asking for confirmation
            if (JOptionPane.showConfirmDialog(view.getScreen().getFrame(),String.format("Are you sure you would like to delete list %s?", games.getName()), String.format("Confirm Delete %s", games.getName()),
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            
                // If they do, then we can go ahead and delete this list 
                view.getUser().getGameLists().remove(games);
                
                // Trying to pull the next default panle so that we have something to display
                if (view.getUser().getGameLists().size() > 0) {
                    games = view.getUser().getGameLists().get(0);
                } else {
                    games = null;
                }
                
                // Making everything update
                updateGames();
                view.refreshPanles();

            }

        });

        // Adding in the edit button on the right
        String editIconPath = Panle.colors instanceof DarkMode ? "resources/Edit.png" : "resources/EditLight";
        ImageIcon editIcon = new ImageIcon(editIconPath);
        JButton editButton = new JButton();
        editButton.setIcon(editIcon);
        editButton.setBorder(null);
        editButton.setContentAreaFilled(false);
        editButton.setBorder(new EmptyBorder(10, 10, 10, 5));
        titlePanle.add(editButton, BorderLayout.EAST);
        editButton.addActionListener(e -> {

            // Pulling the name so that they can edit it
            String newName = (String) JOptionPane.showInputDialog(
                view.getScreen().getFrame(),
            "Enter the new list name:",
                "Edit List Name",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                games.getName()
            );

            // Making sure they didn't enter nothing or juust spaces
            if (newName != null && !newName.trim().isEmpty()) {
                games.setName(newName);
                view.refreshPanles();
                createTitleSection();

                // Technically these should already be called, but it wasn't working and adding these two lines fixed the bug
                // and it's too close to the deadline to try to find the elegant solution that I know exists
                titlePanle.revalidate();
                titlePanle.repaint();
            }


        });

        // Making the title that will be in the center
        title = new JLabel(games.getName(), SwingConstants.CENTER);
        title.setForeground(Panle.colors.getText());
        title.setFont(new Font("Courier", Font.BOLD, GameListSubPanle.TITLE_FONT_SIZE));
        titlePanle.add(title, BorderLayout.CENTER);

        // Adding the title panle 
        this.add(titlePanle, BorderLayout.NORTH);
        System.out.println("Added in the title panle");
    }

    /**
     * Hides the descriptions of all of the games and then refreshes them so that the description is updated
     */
    public void hideDescriptions() {
        isDescShowing = false;
        updateGames();
    }

     /**
     * Hides the descriptions of all of the games and then refreshes them so that the description is updated
     */
    public void showDescriptions() {
        isDescShowing = true;
        updateGames();
    }

    @Override
    public void updateTheme() {
        super.updateTheme();
        updateGames();
        
        // Recreating the title section with updated themeing
        if (showTitle) {
            createTitleSection();
        }
    }

    /**
     * Sets the gamelist that is being displayed
     * @param GameList The new GameList to display
     */
    public void setGameList(GameList newList) {
        games = newList;
        if (showTitle) {
            createTitleSection();
        }
        updateGames();
    }


    /**
     * Tells the list to hide the title
     * By detault, the title is shown on all GameListSubPanles
     */
    public void hideTitle() {
        showTitle = false;
        this.remove(titlePanle);
    }
    
    /**
     * Tells the list to show the title
     * By detault, the title is shown on all GameListSubPanles
     */
    public void showTitle() {
        showTitle = true;
        createTitleSection();
    }

    /**
     * Sets the action to be performed when a game panel is clicked.
     * @param Consumer<Game> action The action to perform when the game is clicked on 
     */
    public void setOnGameClicked(Consumer<Game> action) {
        this.onGameClicked = action;
    }


    // The games that will be displayed here
    private GameList games;

    // An arraylist that holds the visual elements for each game
    private ArrayList<RoundedPanle> gamePanles;

    // An internal container for the games specifically
    private JPanel listContainer;
    private JScrollPane scrollPane;

    // The label that holds the title of the list
    private JLabel title;
    private JPanel titlePanle;

    // The action to run when a game is clicked
    private Consumer<Game> onGameClicked;

    // Whether or not to show the description of the game (to make the collapsed view look nicer)
    private boolean isDescShowing = true;

    // Whether or not to show the title
    private boolean showTitle = true;

    // Any visual constants
    public static final int PANLE_HEIGHT = 100;
    public static final int TITLE_FONT_SIZE = 30;

    // The popup menus for the lists and for the games
    private RoundedPopupMenu sharedPopupMenu;
    private Game currentlyRightClickedGame = null;
}
