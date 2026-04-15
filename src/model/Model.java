package model;
import control.Control;
import model.*;
import control.*;
import view.*;
public class Model {
    public User user = new User();

    public Model(Control controller){
        // Saving the controller
        this.controller = controller;
    }

    public String getUser() {
        // User needs to store a profile name
        // return user.getName();
        return "temp"; // for testing
    }

    public User verifyLogin(String u, String p) {
        //needs getters and setters from user
        if (u.equals(user.getUserName()) && p.equals(user.getPassword())) {
            return user;
        }
        return null; // if the user isn't verified
    }
    public User getUser(String u){
        //for(User i : )
        return null; // working on it
    }

    public void setAPI(boolean s) {
        // needs a setter for API
        user.setAPI(s);
    }

    private Control controller;
}