package src.model;

public class Rating {
    double score;
    String review;
    boolean recommended;

    /**
     * Constructer for the raiting
     * @param s The number raiting that the user gave
     * @param r The revirew the user wrote about the game
     */
    public Rating(double s, String r){
        score = s;
        review = r;
    }

    /**
     * Setters for the varables within Rating
     */
    public void setScore(double sc){score = sc;}
    public void setReview(String rev){review = rev;}
    public void setRecommended(boolean rec){recommended = rec;}
}