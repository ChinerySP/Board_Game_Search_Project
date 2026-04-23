package model;
import java.io.FileWriter;
import java.io.IOException;
import model.*;
import control.*;
import view.*;
public class Model {
    public User user = null;
    public DataBase dataBase = new DataBase();

    /**
     * Constructor that takes in the current controller
     * @param controller
     */
    public Model(Control controller){
        // Saving the controller
        this.controller = controller;
    }

    /**
     * verifies that an account exists to be logged into
     * @param u is the user to be checked
     * @param p is the password to be checked
     * @return the user that is correctly logged in
     */
    public User verifyLogin(String u, String p) {
        for (User possibleMatch : dataBase.userList) {
            System.out.println(String.format("Checking %s", possibleMatch.getUserName()));
            if (u.equals(possibleMatch.getUserName()) && p.equals(possibleMatch.getPassword())) {
                setUser(possibleMatch);
                return possibleMatch;
            }
        }
        return null; // if the user isn't verified
    }

    /**
     * sets the user to the input
     * @param u the user to be currently logged in
     */
    public void setUser(User u){
        user = u;
    }

    /**
     * changes what the parser searches
     * @param s new state of the API toggle
     */
    public void setAPI(boolean s) {
        user.setAPI(s);
    }

    /**
     * gets the current database being used
     * @return the database
     */
    public DataBase getDataBase(){return dataBase;}

    /**
     * searches the database for games
     * @param keywords
     * @return list of games
     */
    public GameList search(String[] keywords){
        return dataBase.searchGames(keywords);
    }

    /**
     * checks the database for any user object by its username
     * @param u username to be checked
     * @return the user object assoiceated with that name
     */
    public User getUser(String u){
        for(User i : dataBase.userList){
            if(u.equals(i.getUserName())){return i;}
        }
        return null;
    }

    /**
     * removes that account from the database
     * @param delete the user to be killed
     */
    public void deleteAccount(User delete){
        int count = 0;
        for(User i : dataBase.userList) {
            if (i == delete) {
                dataBase.userList.remove(count);
            }
            count++;
        }
    }

    /**
     * addes the new user to the database of users
     * @param newUser
     */
    public void newUser(User newUser){
        dataBase.userList.add(newUser);
        try {
            FileWriter myWriter = new FileWriter("resources/userData.txt");
            myWriter.append(newUser.getUserName() + " " + newUser.getPassword() + "\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private Control controller;
}