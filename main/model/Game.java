package main.model;

import main.model.Rating;

import java.util.ArrayList;
public class Game implements Comparable<Game>{
    /**
     * Create varables for all the things that make up a game
    */
    String name, description, thumbnail, categories, mechanics, designer, artist;
    int id, numPlayer, agePlayer, playTime, minPlayer, maxPlayer;
    ArrayList<Rating> ratings = new ArrayList<Rating>();

    /**
     * Getters and setters for every varable, allowing for easy access
     */
    // all the getters
    String getName(){return name;}
    String getDescription(){return description;}
    String getThumbnail(){return thumbnail;}
    String getCategories(){return categories;}
    String getMechanics(){return mechanics;}
    String getDesigner(){return designer;}
    String getArtist(){return artist;}
    int getId(){return id;}
    int getNumPlayer(){return numPlayer;}
    int getAgePlayer(){return agePlayer;}
    int getPlayTime(){return playTime;}
    int getMinPlayer(){return minPlayer;}
    int getMaxPlayer(){return maxPlayer;}

    // all the setters
    void setName(String n){name = n;}
    void setDescription(String des){description = des;}
    void setThumbnail(String thum){thumbnail = thum;}
    void setCategories(String cat){categories = cat;}
    void setMechanics(String mech){mechanics = mech;}
    void setDesigner(String desi){designer = desi;}
    void setArtist(String art){artist = art;}
    void setId(int i){id = i;}
    void setNumPlayer(int num){numPlayer = num;}
    void setAgePlayer(int age){agePlayer = age;}
    void setPlayTime(int tim){playTime = tim;}
    void setMinPlayer(int min){minPlayer = min;}
    void setMaxPlayer(int max){maxPlayer = max;}

    //adds comparability
    @Override
    public int compareTo(Game other) {
        return this.id - other.id;
    }

    /**
     * Defalt constructer to give all the varables an initial value, all of them should eventially
     * over wtriiten to the acual value
     */
    Game(){
        this.name = "OH GOD WHAT GAME IS THIS!!!";
        this.description = "I HAVE NO CLUE WHAT THIS GAME IS ABOUT";
        this.thumbnail = "blank.png";
        this.categories = "";
        this.mechanics = "";
        this.designer = "";
        this.artist = "";
        this.id = -1;
        this.numPlayer = -1;
        this.agePlayer = -1;
        this.playTime = -1;
        this.minPlayer = -1;
        this.maxPlayer = -1;
    }

    /**
     * adds to the arraylist of ratings stored with each game
     * the get raiting returns an avrage of all the ratings stored in the array list.
     */
    void rate(Rating newRating){ratings.add(newRating);}
    int getRating(){
        int avgScore = 0;
        int count = 0;
        for(Rating i : ratings){
            avgScore += i.score;
            count++;
        }
        return avgScore/count;
    }
}
