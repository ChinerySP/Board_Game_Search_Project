package model;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import control.*;

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
                restoreUserData();
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
        System.out.println(delete.getUserName());
        for(User i : dataBase.userList) {
            if (i == delete) {
                dataBase.userList.remove(count);
                break;
            }
            count++;
        }
        System.out.println("No user deleted.");
    }

    /**
     * addes the new user to the database of users
     * @param newUser
     */
    public void newUser(User newUser){
        System.out.println("making a new user.");
        newUser.addGameList(new GameList("Favorites"));
        dataBase.userList.add(newUser);
    }

    public void saveData(){
        try {
            FileWriter myWriterUser = new FileWriter("resources/userData.txt");
            FileWriter myWriterData = new FileWriter("resources/userList.txt");
            for(User u : dataBase.userList) {
                myWriterUser.write(u.getUserName() + " " + u.getPassword() + " " + u.getDarkMode() + " " + u.getAPI() + "\n");
            }
            for(User u : dataBase.userList) {
                myWriterData.write(u.getUserName());
                myWriterData.write("~");
                for(GameList g : u.getGameLists()){
                    myWriterData.write(g.getName());
                    for(Game game : g){
                        myWriterData.write(" " + game.getId());
                    }
                    myWriterData.write("~");
                }
                myWriterData.write("\n");
            }
            myWriterUser.close();
            myWriterData.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void restoreUserData(){
        System.out.println("1");
        File myObj = new File("resources/userList.txt");
        // try-with-resources: Scanner will be closed automatically
        try (Scanner myReader = new Scanner(myObj)) {
            System.out.println("2");
            while (myReader.hasNextLine()) {
                System.out.println("3");
                String[] data = myReader.nextLine().split("~");
                System.out.println(data[0]);
                if(data[0].equals(user.getUserName())){
                    System.out.println("4");
                    for(int i = 1; i < data.length; i++ ){
                         String[] splitData = data[i].split(" ");
                        GameList oldList = new GameList(splitData[0]);
                        for(int j = 1; j < splitData.length; j++ ){
                            System.out.println(splitData[j]);
                            int currentID = Integer.parseInt(splitData[j]);
                            oldList.addGame(dataBase.APIparser.retrieveGame(currentID));
                        }
                        user.addGameList(oldList);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }

    private Control controller;
}