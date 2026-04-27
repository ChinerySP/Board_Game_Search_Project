package model;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
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
            if (u.equals(possibleMatch.getUserName()) && p.equals(possibleMatch.getPassword())) {
                setUser(possibleMatch);
                if(user.getGameLists().isEmpty())
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
     * @param keywords The list of keywords to search for
     * @param useAPI Whether or not to use the API
     * @return list of games
     */
    public GameList search(String[] keywords, boolean useAPI){
        return dataBase.searchGames(keywords, useAPI);
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
                break;
            }
            count++;
        }
    }

    /**
     * addes the new user to the database of users
     * @param newUser
     */
    public void newUser(User newUser){
        newUser.addGameList(new GameList("Favorites"));
        dataBase.userList.add(newUser);
    }

    /**
     * saves users information to be restored when the program is relaunched
     */
    public void saveData(){
        try {
            for(User u : dataBase.userList)
                if(u.getGameLists().isEmpty())
                    restoreUserData(u);
            FileWriter myWriterUser = new FileWriter("resources/userData.txt");
            FileWriter myWriterData = new FileWriter("resources/userList.txt");
            FileWriter myWriterRev = new FileWriter("resources/userRev.txt");
            for(User u : dataBase.userList) {
                myWriterUser.write(u.getUserName() + "\n" + u.getPassword() + "\n" + u.getDarkMode() + "\n" + u.getAPI() + "\n");
            } myWriterUser.close();
            for(User u : dataBase.userList) {
                myWriterData.write("\""+ u.getUserName()+ "\"");
                myWriterData.write("}");
                for(GameList g : u.getGameLists()){
                    myWriterData.write(g.getName());
                    for(Game game : g){
                        myWriterData.write("]" + game.getId());
                    }
                    myWriterData.write("}");
                }
                myWriterData.write("\n");
            } myWriterData.close();
            for(User u : dataBase.userList) {
                myWriterRev.write("\""+ u.getUserName()+ "\"}");
                Map<Integer, Rating> map = new HashMap<Integer, Rating>();
                for (Map.Entry<Integer, Rating> entry : u.userRatings.entrySet()) {
                    Integer key = entry.getKey();
                    Rating value = entry.getValue();
                    myWriterRev.write(key + "]" + value.getScore() + "]" + value.getReview().replace("\n", ">") + "]" + value.getRecommended() +"}");
                }
                myWriterRev.write("\n");
            } myWriterRev.close();
        } catch (IOException e) {
            System.out.println("An error occurred when saving the data.");
            e.printStackTrace();
        }
    }

    /**
     * restores users information form a saved text document
     */
    public void restoreUserData(){
        File myObj = new File("resources/userList.txt");
        // try-with-resources: Scanner will be closed automatically
        try (Scanner myReader = new Scanner(myObj)) {
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split("}");
                if(data[0].equals("\"" + user.getUserName() + "\"")){
                    for(int i = 1; i < data.length; i++ ){
                        String[] splitData = data[i].split("]");
                        GameList oldList = new GameList(splitData[0]);
                        for(int j = 1; j < splitData.length; j++ ){
                            int currentID = Integer.parseInt(splitData[j]);
                            oldList.addGame(dataBase.APIparser.retrieveGame(currentID));
                        }
                        user.addGameList(oldList);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred when restoring data: File could not be found.");
        }
        File myObjRev = new File("resources/userRev.txt");
        try (Scanner myReader = new Scanner(myObjRev)){
            while (myReader.hasNextLine()){
                String[] data = myReader.nextLine().split("}");
                if(data[0].equals("\"" + user.getUserName() + "\"")){
                    for(int i = 1; i < data.length; i++ ){
                        String[] splitData = data[i].split("]");
                        int keyInt = Integer.parseInt(splitData[0]);
                        int scoreInt = Integer.parseInt(splitData[1]);
                        String revToken = splitData[2].replace(">", "\n");
                        Boolean recBool = Boolean.parseBoolean(splitData[3]);
                        Rating oldRating = new Rating(scoreInt, revToken, recBool);
                        user.setRating(keyInt, oldRating);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred when restoring user data: File userRev.txt not found.");
        }

    }
    /**
     * restores users information form a saved text document
     */
    public void restoreUserData(User use){
        File myObj = new File("resources/userList.txt");
        // try-with-resources: Scanner will be closed automatically
        try (Scanner myReader = new Scanner(myObj)) {
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split("}");
                if(data[0].equals("\"" + use.getUserName() + "\"")){
                    for(int i = 1; i < data.length; i++ ){
                        String[] splitData = data[i].split("]");
                        GameList oldList = new GameList(splitData[0]);
                        for(int j = 1; j < splitData.length; j++ ){
                            int currentID = Integer.parseInt(splitData[j]);
                            oldList.addGame(dataBase.APIparser.retrieveGame(currentID));
                        }
                        use.addGameList(oldList);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred when restoring user data: File userList.txt not found.");
        }
        File myObjRev = new File("resources/userRev.txt");
        try (Scanner myReader = new Scanner(myObjRev)){
            while (myReader.hasNextLine()){
                String[] data = myReader.nextLine().split("}");
                if(data[0].equals("\"" + use.getUserName() + "\"")){
                    for(int i = 1; i < data.length; i++ ){
                        String[] splitData = data[i].split("]");
                        int keyInt = Integer.parseInt(splitData[0]);
                        int scoreInt = Integer.parseInt(splitData[1]);
                        String revToken = splitData[2].replace(">", "\n");
                        Boolean recBool = Boolean.parseBoolean(splitData[3]);
                        Rating oldRating = new Rating(scoreInt, revToken, recBool);
                        use.setRating(keyInt, oldRating);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred when restoring user data: File userRev.txt not found.");
        }
    }

    private Control controller;
}