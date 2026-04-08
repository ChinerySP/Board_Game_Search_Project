package src.view.panle;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import src.view.Panle;
import src.view.panle.customComponents.RoundedPanle;

import javax.swing.plaf.basic.BasicScrollBarUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JComponent;

// For testing
import src.view.Screen;


/**
 * A panle that holds a GameList to display to the user
 * @author Sam Whitlock
 */
public class GameListSubPanle extends Panle {

    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        Screen screen = new Screen();
        screen.showPanle("sticky");
        screen.showPanle("dashboard");
        DashboardPanle dash =  (DashboardPanle) screen.getPanle("dashboard");
        GameListSubPanle right = dash.right;
        GameListSubPanle left = dash.left;
        for (int i = 0; i < 100; i++) {
            right.games.addGame(new main.model.Game());
            left.games.addGame(new main.model.Game());
            
        }
        right.updateGames();
        left.updateGames();
    }

    
    /**
     * Creates a GameListSubPanle to display the inputted Games
     * @param GameList games The games to display
     */
    public GameListSubPanle(GameList games) {
        super("gamelist");

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
        // title = new JLabel(games.getName, SwingConstants.CENTER); 
        title = new JLabel("FakeName", SwingConstants.CENTER); // Currently defaulting to a fake name because this function doesn't exist
        title.setForeground(Panle.TEXT_COLOR);
        title.setFont(new Font("Courier", Font.BOLD, GameListSubPanle.TITLE_FONT_SIZE));
        this.add(title, BorderLayout.NORTH);

        // Making the scrollpane look a bit better
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Adding the scrollpane and updating everythign
        this.styleScrollBar(scrollPane);
        this.add(scrollPane, BorderLayout.CENTER);
        this.games = games;
        updateGames();
    }

    /**
     * Creates a blank GameListSubPanle to be populated with games later
     */
    public GameListSubPanle() {
        super("gamelist");
        
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
        this.games = new main.model.GameList("Un-named List");
        title = new JLabel("Un-named List", SwingConstants.CENTER);
        title.setForeground(Panle.TEXT_COLOR);
        title.setFont(new Font("Courier", Font.BOLD, GameListSubPanle.TITLE_FONT_SIZE));
        this.add(title, BorderLayout.NORTH);

        // Setting teh scrollpane to look a bit nicer
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Actually adding the scrollpane and then updating the display
        this.styleScrollBar(scrollPane);
        this.add(scrollPane, BorderLayout.CENTER);
        updateGames();
    }

    /**
     * Changes the GameList that is being displayed
     * @param GameList newList The new GameList to show
     */
    public void setGameList(main.model.GameList newList) {
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
        for (main.model.Game game : games) {

            // The new panle that we will be adding to the list
            RoundedPanle toAdd = new Panle("gamePanle");

            // Using GridBag again so that I can have a 25/75 split for the name and the description
            toAdd.setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();

            // Fetching the stuff to put on the screen
            JTextArea name = new JTextArea(game.getName());
            JTextArea desc = new JTextArea(game.getDescription());

            // Styling the name and description
            name.setForeground(Panle.TEXT_COLOR);
            name.setOpaque(false);
            name.setEditable(false);
            name.setLineWrap(true);
            name.setWrapStyleWord(true);
            name.setFont(new Font("Ariel", Font.BOLD, 17));
            desc.setForeground(Panle.TEXT_COLOR);
            desc.setOpaque(false);
            desc.setEditable(false);
            desc.setLineWrap(true);
            desc.setWrapStyleWord(true);
            toAdd.setBackground(Panle.BACKGROUND_COLOR);


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

        // If there aren't any games, then we need to have a fake game that lets them know that
        System.out.println(String.format("There are %s games", gamePanles.size()));
        if (gamePanles.size() == 0) {

            // The new panle that we will be adding to the list
            RoundedPanle toAdd = new Panle("gamePanle");

            // Using GridBag again so that I can have a 25/75 split for the name and the description
            toAdd.setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();

            // Creating stuff ot put on the screen
            JTextArea name = new JTextArea("There aren't any games in this list yet!");
            JTextArea desc = new JTextArea("You can add some by clicking on a game and selecting \"Add to List\"!");

            // Styling the name and description
            name.setForeground(Panle.TEXT_COLOR);
            name.setOpaque(false);
            name.setEditable(false);
            name.setLineWrap(true);
            name.setWrapStyleWord(true);
            name.setFont(new Font("Ariel", Font.BOLD, 17));
            desc.setForeground(Panle.TEXT_COLOR);
            desc.setOpaque(false);
            desc.setEditable(false);
            desc.setLineWrap(true);
            desc.setWrapStyleWord(true);
            toAdd.setBackground(Panle.BACKGROUND_COLOR);

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
            System.out.println(toAdd.toString());
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

    // The games that will be displayed here
    // TODO change to pivate
    public main.model.GameList games;

    // An arraylist that holds the visual elements for each game
    private ArrayList<RoundedPanle> gamePanles;

    // An internal container for the games specifically
    private JPanel listContainer;
    private JScrollPane scrollPane;

    // The label that holds the title of the list
    private JLabel title;

    // Any visual constants
    public static final int PANLE_HEIGHT = 100;
    public static final int TITLE_FONT_SIZE = 30;
    
}

// TODO make this open a gameDetailsSubPanle on the opposite side of where a game was clicked