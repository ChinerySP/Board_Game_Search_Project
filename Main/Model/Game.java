import java.util.ArrayList;
public class Game implements Comparable<Game>{
    String name, description, categories, mechanics, designer, artist;
    int id, numPlayer, agePlayer, playTime, minPlayer, maxPlayer;
    ArrayList<Rating> ratings = new ArrayList<Rating>();
    //thumbnail

    // all the geters
    String getName(){return name;}
    String getDescription(){return description;}
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
    void setName(String x){name = x;}
    void setDescription(String x){description = x;}
    void setCategories(String x){categories = x;}
    void setMechanics(String x){mechanics = x;}
    void setDesigner(String x){designer = x;}
    void setArtist(String x){artist = x;}
    void setId(int x){id = x;}
    void setNumPlayer(int x){numPlayer = x;}
    void setAgePlayer(int x){agePlayer = x;}
    void setPlayTime(int x){playTime = x;}
    void setMinPlayer(int x){minPlayer = x;}
    void setMaxPlayer(int x){maxPlayer = x;}

    //adds comparability
    @Override
    public int compareTo(Game other) {
        return this.id - other.id;
    }

    //Raiting stuff
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
