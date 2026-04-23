package model;

import model.Rating;
import java.util.ArrayList;
import java.util.Iterator;
public class Game implements Comparable<Game>, Iterable<Rating>{
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
    public String getName(){return name;}
    public String getDescription(){return description;}
    public String getThumbnail(){return thumbnail;}
    public String getCategories(){return categories;}
    public String getMechanics(){return mechanics;}
    public String getDesigner(){return designer;}
    public String getArtist(){return artist;}
    public int getId(){return id;}
    public int getNumPlayer(){return numPlayer;}
    public int getAgePlayer(){return agePlayer;}
    public int getPlayTime(){return playTime;}
    public int getMinPlayer(){return minPlayer;}
    public int getMaxPlayer(){return maxPlayer;}

    // all the setters
    public void setName(String n){name = n;}
    public void setDescription(String des){description = des;}
    public void setThumbnail(String thum){thumbnail = thum;}
    public void setCategories(String cat){categories = cat;}
    public void setMechanics(String mech){mechanics = mech;}
    public void setDesigner(String desi){designer = desi;}
    public void setArtist(String art){artist = art;}
    public void setId(int i){id = i;}
    public void setNumPlayer(int num){numPlayer = num;}
    public void setAgePlayer(int age){agePlayer = age;}
    public void setPlayTime(int tim){playTime = tim;}
    public void setMinPlayer(int min){minPlayer = min;}
    public void setMaxPlayer(int max){maxPlayer = max;}

    //adds comparability
    @Override
    public int compareTo(Game other) {
        return this.id - other.id;
    }

    /**
     * Defalt constructer to give all the varables an initial value, all of them should eventially
     * over wtriiten to the acual value
     */
    public Game(){
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

    public String toString() {
        return "[" + name + ", BGG ID: " + id + ", Description: " + description.substring(0, 12) + "..." + " Thumbnail: " + thumbnail.substring(0, 10) + "..." + "]";
    }

    /**
     * adds to the arraylist of ratings stored with each game
     * @param newRating Takes in a raiting object to be stored
     */
    public void rate(Rating newRating){ratings.add(newRating);}

    /**
     * the get raiting returns an avrage of all the ratings stored in the array list.
     * @return mean value of all the raitings
     */
    public double getRating(){
        double avgScore = 0;
        double count = 0;
        for(Rating i : ratings){
            avgScore += i.getScore();
            count++;
        }
        return avgScore/count;
    }

    /**
     *Allowes the Raitings to be iterated through
     *@return iterator of ratings
     */
    public Iterator<Rating> iterator() {
        return ratings.iterator();
    }
}
