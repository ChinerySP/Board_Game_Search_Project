package model;

public class Rating {
    private int score;
    private String review;
    private boolean recommended;

    /**
     * Constructer for the raiting
     * @param s The number raiting that the user gave
     * @param r The revirew the user wrote about the game
     */
    public Rating(int scr, String rev, Boolean rec){
        score = scr;
        review = rev;
        recommended = rec;
    }


    /**
     * Getters for the varables within Rating
     */
    public int getScore(){return score;}
    public String getReview(){return review;}
    public boolean getRecommended(){return recommended;}

    /**
     * Setters for the varables within Rating
     */
    public void setScore(int sc){score = sc;}
    public void setReview(String rev){review = rev;}
    public void setRecommended(boolean rec){recommended = rec;}
}