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
        return user.getUserName();
    }

    public User verifyLogin(String u, String p) {
        for (User possibleMatch : dataBase.userList) {
            System.out.println(String.format("Checking %s", possibleMatch.getUserName()));
            if (u.equals(possibleMatch.getUserName()) && p.equals(possibleMatch.getPassword())) {
                return possibleMatch;
            }
        }
        return null; // if the user isn't verified
    }

    public void setAPI(boolean s) {
        // needs a setter for API
        user.setAPI(s);
    }

    public DataBase getDataBase(){return dataBase;}

    public GameList search(String[] keywords){
        return dataBase.searchGames(keywords);
    }

    public User getUser(String u){
        for(User i : dataBase.userList){
            if(u.equals(i.getUserName())){return i;}
        }
        return null;
    }

    public void deleteAccount(User delete){
        int count = 0;
        for(User i : dataBase.userList) {
            if (i == delete) {
                dataBase.userList.remove(count);
            }
            count++;
        }
    }

    public void newUser(User newUser){dataBase.userList.add(newUser);}

    private Control controller;
}