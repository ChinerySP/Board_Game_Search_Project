package view.panle;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import view.*;
import model.*;
import view.panle.colors.DarkMode;
import view.panle.customComponents.*;

/**
 * A panle that holds all of the GameLists of a User
 * @author Sam Whitlock
 */
public class GameListListSubPanle extends Panle {

    /**
     * Creates a GameListSubPanle to display the inputted Games
     * @param View The view that owns this panle
     * @param ArrayList<GameList> The gamelists to display
     */
    public GameListListSubPanle(View view, ArrayList<GameList> lists) {
        super("gamelistlist", view);

        // Setting up the internal container and title
        this.setLayout(new BorderLayout());
        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setOpaque(false);

        // Creating the title (heh duh)
        createTitleSection();

        // Creating the internal scrollpane  
        scrollPane = new JScrollPane(listContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Making the scrollpane look a bit better
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Adding the scrollpane and updating everythign
        this.styleScrollBar(scrollPane);
        this.add(scrollPane, BorderLayout.CENTER);
        this.lists = lists;
        updateLists();
    }

    /**
     * Creates a blank GameListSubPanle to be populated with games later
     * @param View The view that owns this subpanle
     */
    public GameListListSubPanle(View view) {
        super("gamelistlist", view);
        
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
        this.lists = new ArrayList<>();

        createTitleSection();

        // Setting teh scrollpane to look a bit nicer
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Actually adding the scrollpane and then updating the display
        this.styleScrollBar(scrollPane);
        this.add(scrollPane, BorderLayout.CENTER);
        updateTheme();
    }

    /**
     * Changes the GameList that is being displayed
     * @param ArrayList<GameList> newLists The new set of GameLists to show
     */
    public void setGameListList(ArrayList<GameList> newLists) {
        this.lists = newLists;
    }

    /**
     * Updates the visual game panel elements to reflect the GameList associated with this panle
     */
    public void updateLists() {

        // Clearing the current visuals
        listContainer.removeAll();
        listPanles = new ArrayList<>();

        // Iterating over the game panels and recreating them
        for (GameList list : lists) {

            // The new panle that we will be adding to the list
            RoundedPanle toAdd = new Panle("listPanle", view);

            // Using GridBag again so that I can have a 25/75 split for the name and the description
            toAdd.setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();

            // Fetching the stuff to put on the screen
            JTextArea name = new JTextArea(list.getName());
            JTextArea desc = new JTextArea(String.format("Number of games: %d", list.getSize())); // Currently just the size of the list, could be anything though

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
            toAdd.setPreferredSize(new Dimension(0, GameListListSubPanle.PANLE_HEIGHT));
            toAdd.setMaximumSize(new Dimension(Integer.MAX_VALUE, GameListSubPanle.PANLE_HEIGHT));

            // Creating a mouselistener to listen for when a game is clicked
            MouseAdapter clickListener = new MouseAdapter() {

                /**
                 * Overrideing the mouseClicked event for this eventListener
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    // If someone gave us an action to run, run it and pass the clicked list
                    if (onListClicked != null) {
                        onListClicked.accept(list);
                    }
                }
            };

            // Adding the listener to all of the things (this way, it detects it even if I click the text)
            toAdd.addMouseListener(clickListener);
            name.addMouseListener(clickListener);
            desc.addMouseListener(clickListener);

            // Actually adding it in to the list
            listPanles.add(toAdd);

        }

        // If there aren't any lists, then we need to have a fake list that lets them know that
        // This should never happen in the usual flow of the program right now, because there will always be the favorites lists
        // This panle could be reused later, though, and then it would be wanted.
        if (listPanles.size() == 0) {

            // The new panle that we will be adding to the list
            RoundedPanle toAdd = new Panle("gamePanle", view);

            // Using GridBag again so that I can have a 25/75 split for the name and the description
            toAdd.setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();

            // Creating stuff ot put on the screen
            JTextArea name = new JTextArea("There aren't any lists in this collection yet!");
            JTextArea desc = new JTextArea("You can add some by clicking on a game and selecting \"Create new List\"!");

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
            listPanles.add(toAdd);

        }
        
        // Showing all of the gamePanles on the screen
        for (RoundedPanle p : listPanles) {
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
     * Sets the action to be performed when a game panel is clicked.
     * @param Consumer<Game> action The action to perform when the game is clicked on 
     */
    public void setOnListClicked(Consumer<GameList> action) {
        this.onListClicked = action;
    }

    @Override
    public void updateTheme() {
        title.setForeground(Panle.colors.getText());
        super.updateTheme();
        updateLists();
    }

    /**
     * Creates the title section at the top of the listPanle.
     */
    private void createTitleSection() {

        // Making sure that we remove the title section before adding  a new one
        if (titlePanle != null) {
            this.remove(titlePanle);
        }

        // A simple panle that will hold all the things
        titlePanle = new JPanel();
        titlePanle.setOpaque(false);
        titlePanle.setLayout(new BorderLayout());

        // Adding in the trash button on the left
        String addIconPath = Panle.colors instanceof DarkMode ? "resources/Add.png" : "resources/AddLight.png";
        ImageIcon trashIcon = new ImageIcon(addIconPath);
        JButton addButton = new JButton();
        addButton.setIcon(trashIcon);
        addButton.setBorder(null);
        addButton.setContentAreaFilled(false);
        addButton.setBorder(new EmptyBorder(10, 10, 10, 5));
        titlePanle.add(addButton, BorderLayout.WEST);

        // Making the trash ask for confirmation and then delete the gamelist
        addButton.addActionListener(e -> {

            // Pop open a tiny window asking for the name
            String listName = JOptionPane.showInputDialog(
                    this,
                    "Enter a name for your new list:",
                    "Create New List",
                    JOptionPane.PLAIN_MESSAGE);

            // If they typed something and didn't hit cancel, send it up the chain
            if (listName != null && !listName.trim().isEmpty()) {
                GameList toAdd = new GameList(listName);
                view.getUser().addGameList(toAdd);
            }

            // Making sure everything updates
            view.refreshPanles();


        });

        // Making the title that will be in the center
        title = new JLabel("Your Lists", SwingConstants.CENTER);
        title.setForeground(Panle.colors.getText());
        title.setFont(new Font("Courier", Font.BOLD, GameListSubPanle.TITLE_FONT_SIZE));
        titlePanle.add(title, BorderLayout.CENTER);

        // Adding the title panle 
        this.add(titlePanle, BorderLayout.NORTH);
    }

    // The label that holds the title of the list
    private JLabel title;
    private JPanel titlePanle;

    // The lists that will be displayed here
    public ArrayList<GameList> lists;

    // An arraylist that holds the visual elements for each game
    private ArrayList<RoundedPanle> listPanles;

    // An internal container for the games specifically
    private JPanel listContainer;
    private JScrollPane scrollPane;

    // The action to run when a game is clicked
    private Consumer<GameList> onListClicked;

    // Any visual constants
    public static final int PANLE_HEIGHT = 100;
    public static final int TITLE_FONT_SIZE = 30;
    
}
