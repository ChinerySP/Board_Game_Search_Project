package model;
import control.Control;
import model.*;
import control.*;
import view.*;
public class Model {
    public User user = new User();
    public DataBase dataBase = new DataBase();
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
        // if (u == user.getName() && p == user.getPassword()){
        //  return user;
        return null; // if the user isn't verified
    }
    public User getUser(String u){
        for(User i : dataBase.userList){
            //if(u == i.getName()){return i}
        }
        return null;
    }

    public void setAPI(boolean s) {
        // needs a setter for API
        // user.setAPI(s);
    }

    //public DataBase getDataBase(){return dataBase;}

    public GameList search(String[] keywords){
        return dataBase.searchGames(keywords);
    }

    private Control controller;
}