package model;
import model.*;

public class Model {
    public User user = new User();

    public String getUser() {
        // Needs getter for the name
        // return user.getName();
        return "temp"; // for testing
    }

    public User verifyLogin(String u, String p) {
        //needs getters and setters from user
        if (u.equals(user.getUserName()) && p.equals(user.getPassword()) {
            return user;
        }
        return null; // if the user isn't verified
    }

    //public User p

    public void setAPI(boolean s) {
        // needs a setter for API
        user.setAPI(s);
    }


}