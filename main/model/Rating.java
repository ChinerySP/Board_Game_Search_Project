package main.model;

public class Rating{
    double score;
    String review;
    boolean recommended;

    Rating(double s, String r){
        score = s;
        review = r;
    }

    void setScore(double x){score = x;}
    void setReview(String x){review = x;}
    void setRecommended(boolean x){recommended = x;}
}