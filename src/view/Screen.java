package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.border.Border;

import view.*;
import view.panle.*;

/**
 * The overall screen that will be shown to the user. 
 * 
 * <p>This is the interface for controlling the Panles that will be shown to the user.</p> 
 * @author Sam Whitlock
 */
public class Screen {

    /**
     * Simple contructor that initializes all of the panle and opens the login panle
     * @param View The view that owns this Screen, to allow for communication upwards
     */
    public Screen(View view) {

        // Saving the view
        this.view = view;
        
        // Creating the frame that will actually be displayed
        frame = new JFrame("Amazing Board Game App - Now with API (soon we promise) -");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Making sure we actually have a place to put our panles
        panles = new ArrayList<>();

        // Creating all of the Panles that we can show
        this.panles.add(new LoginPanle(view));
        this.panles.add(new StickySubPanle(view));
        this.panles.add(new DashboardPanle(view));
        this.panles.add(new SettingsPanle(view));
        this.panles.add(new SearchResultsPanle(view));


        // Some eye candy 
        frame.getContentPane().setBackground(Panle.colors.getCrust());

        // Final steps to make sure that the frame shows up
        frame.setSize(1000, 800);
        frame.setVisible(true);

    }

    /**
     * Shows the Panle who's name matches the inputted String
     * @param String name The name of the Panle to show
     */
    public void showPanle(String name) {

        // Grabbing the actual place where the content is stored in the frame
        Container contentPane = frame.getContentPane(); 

        // The sticky subpanle is different than the others because of its location, so we check it first
        if (name.equals("sticky")) {
            // Checking to see if it is already there
            BorderLayout layout = (BorderLayout) contentPane.getLayout();
            Component current = layout.getLayoutComponent(BorderLayout.NORTH);

            // Skipping it if already is there
            if (current != null) return;
           
            // Adding it in because it isn't there
            contentPane.add(getPanle("sticky"), BorderLayout.NORTH);
            return;

        }

        // Saving the layout that we are using so that we can query it
        BorderLayout layout = (BorderLayout) contentPane.getLayout();
        Component current = layout.getLayoutComponent(BorderLayout.CENTER);
        
        // Removing whatever is in the center if there is something there, otherwise exiting
        if (current != null) {
            contentPane.remove(current);
        }

        // If the right one is in the center, we don't have to do anything (makes sure there isn't a flash of nothing)
        if (current != null && current.equals(name)) return; 
        
        // Grabbing the target
        Panle target = getPanle(name);
        if (target == null) return;
        
        // Making sure that the settings panle is only shown when there is a User defined
        if (target instanceof SettingsPanle && !((SettingsPanle) target).hasUser()) {
            return;
        }
        
        // Showing the panle
        contentPane.add(target, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();

    }


    /**
     * Hides the Panle who's name matches the inputted String
     * @param String name The name of the Panle to hide
     */
    public void hidePanle(String name) {
        frame.remove(this.getPanle(name));
    }
    
    // Getters and setters 

    /**
     * Gets the panel that matches the inputted name
     * @param String name The name of the panel to get; This must match one of the Panles, otherwise returns null
     */
    public Panle getPanle(String name) {

        // Iterating over the panles to get the one they want
        for (Panle p : panles) {
            if (name.equals(p.getName())) {
                return p;
            }
        }

        // If we get here, then we didn't have the panle, so we panic
        assert name.equals("alwaysError") : String.format("ERROR: Bad input; getPanle(%s)", name);
        return null;
    }

    /**
     * Redraws and revalidates all of the panles to fix any bugs or update everything
     */
    public void updateTheme() {
        frame.getContentPane().setBackground(Panle.colors.getCrust());
        for (Panle p : panles) {
            p.updateTheme();
        }
    }

    /**
     * Gets the frame that this screen is using
     * @return JFrame 
     */
    public JFrame getFrame() { return frame; }
        
    // The frame that everything is in
    private JFrame frame;

    // The View that owns this Screen
    private View view;


    // The panles that are going to be displayed
    private ArrayList<Panle> panles;
    
}