/**
 * Represents a single Game with data from BoardGameGeek.com
 * This is a very primitive game class, used to illustrate how to gather
 * information from the Java Document Object Model (DOM).
 * See main program for description of file contents
 */
public class Game {
    /**
     * Constructor to populate a new Game object with retrieved data
     * @param title  The full title of the game
     * @param tnuri  A URI with the location of the game's thumbnail image file
     * @param pubyear Publication Year
     * @param rank BGG rank
     * @param id BGG id key
     */
    public Game(String title, String tnuri, Integer pubyear, Integer rank, String id) {
        this.title = title;
        this.thumbnailUri = tnuri;
        this.publicationYear = pubyear;
        this.rank = rank;
        this.id = id;
    }

    /**
     * @return A printable string containing the Game's field values
     */
    public String toString() {
        return "[" + title + ", "+ publicationYear + ", BGG Rank: " + rank + ", BGG ID: " + id + "]";
    }

    //----------- private attributes of a Game ------------------------------------

    private String  title;           // game title
    private String  thumbnailUri;    // Link / address of game image thumbnail, not used yet
    private Integer publicationYear; // date of publication
    private Integer rank;            // board game geek current rank
    private String  id;              // board game geek unique game id key
}
