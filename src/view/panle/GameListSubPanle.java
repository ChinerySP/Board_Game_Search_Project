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
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.JComponent;
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

        // Setting up the internal container and title
        this.setLayout(new BorderLayout());
        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setOpaque(false);

        // Creating the internal scrollpane  
        scrollPane = new JScrollPane(listContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Creating the title
        title = new JLabel(games.getName(), SwingConstants.CENTER); 
        // title = new JLabel("FakeName", SwingConstants.CENTER); // Currently defaulting to a fake name because this function doesn't exist
        title.setForeground(Panle.colors.getText());
        title.setFont(new Font("Courier", Font.BOLD, GameListSubPanle.TITLE_FONT_SIZE));
        this.add(title, BorderLayout.NORTH);

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
        
        // Defauling to an unnamed list
        this.games = new GameList("Un-named List");
        title = new JLabel("Un-named List", SwingConstants.CENTER);
        title.setForeground(Panle.colors.getText());
        title.setFont(new Font("Courier", Font.BOLD, GameListSubPanle.TITLE_FONT_SIZE));
        this.add(title, BorderLayout.NORTH);

        // Setting teh scrollpane to look a bit nicer
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // A pop up menu that they can use to remove a game from a list
        initPopupMenu();

        // Actually adding the scrollpane and then updating the display
        this.styleScrollBar(scrollPane);
        this.add(scrollPane, BorderLayout.CENTER);
        updateGames();
    }

    /**
     * Changes the GameList that is being displayed
     * @param GameList newList The new GameList to show
     */
    public void setGameList(GameList newList) {
        this.games = newList;
    }

    /**
     * Updates the visual game panel elements to reflect the GameList associated with this panle
     */
    public void updateGames() {

        // Clearing the current visuals
        listContainer.removeAll();
        gamePanles = new ArrayList<>();

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
            toAdd.add(desc, constraints);

            // Because these are generally so small, I am manually decreasing the radius to make it fit nicely
            toAdd.setRadius(toAdd.getRadius() - 5);

            // Updating the height to match the size we want
            toAdd.setPreferredSize(new Dimension(0, GameListSubPanle.PANLE_HEIGHT));
            toAdd.setMaximumSize(new Dimension(Integer.MAX_VALUE, GameListSubPanle.PANLE_HEIGHT));

            // Creating a mouselistener to listen for when a game is clicked
            MouseAdapter clickListener = new MouseAdapter() {

                /**
                 * Overrideing the mouseClicked event for this eventListener
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("MouseClicked detected!");
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
                    System.out.println("ShowPopup detected");
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
            toAdd.add(desc, constraints);

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
        addToListBtn.setForeground(Panle.colors.getText());
        addToListBtn.setOpaque(false); 

        // What actually runs when the add to list button is clicked
        addToListBtn.addActionListener(e -> {
            if (currentlyRightClickedGame != null) {
                // TODO: Trigger your "Add to List" logic or popup here!
                System.out.println("Opening list menu for: " + currentlyRightClickedGame.getName());
            }
        });

        // What actually runs when the remove from list button is clicked
        removeFromListBtn.addActionListener(e -> {
            if (currentlyRightClickedGame != null) {



                // TODO: Trigger your "Add to List" logic or popup here!
                System.out.println("Opening list menu for: " + currentlyRightClickedGame.getName());
            }
        });

        sharedPopupMenu.add(addToListBtn);
        sharedPopupMenu.add(removeFromListBtn);
    }

    @Override
    public void updateTheme() {
        super.updateTheme();
        updateGames();
        title.setForeground(Panle.colors.getText());
    }
    
    /**
     * Sets the action to be performed when a game panel is clicked.
     * @param Consumer<Game> action The action to perform when the game is clicked on 
     */
    public void setOnGameClicked(Consumer<Game> action) {
        this.onGameClicked = action;
    }

    // The games that will be displayed here
    // TODO change to pivate
    public GameList games;

    // An arraylist that holds the visual elements for each game
    private ArrayList<RoundedPanle> gamePanles;

    // An internal container for the games specifically
    private JPanel listContainer;
    private JScrollPane scrollPane;

    // The label that holds the title of the list
    private JLabel title;

    // The action to run when a game is clicked
    private Consumer<Game> onGameClicked;

    // Any visual constants
    public static final int PANLE_HEIGHT = 100;
    public static final int TITLE_FONT_SIZE = 30;

    // The popup menus for the lists and for the games
    private RoundedPopupMenu sharedPopupMenu;
    private Game currentlyRightClickedGame = null;
}
