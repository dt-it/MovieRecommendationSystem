package pl.dtit.io.printers;

import pl.dtit.io.ratings.SecondRatings;
import pl.dtit.model.Rating;

import java.util.ArrayList;
import java.util.Collections;

//old class created during writing application - left for informational purpose
// actual class - MovieRunnerSimilarRatings
public class MovieRunnerAverage {
    private String moviePath = "data/ratedmovies_short.csv";
    private String ratingsPath = "data/ratings_short.csv";

    public void printAverageRatings(){
        SecondRatings secondRatings = new SecondRatings(moviePath,ratingsPath);
        int minNumberOfRatings = 3;
        ArrayList<Rating> averageRatings = secondRatings.getAverageRatings(minNumberOfRatings);
        Collections.sort(averageRatings);
        for (Rating rating : averageRatings) {
            System.out.println(rating.getValue() + " - " + secondRatings.getTitle(rating.getItem()));
        }
        System.out.println("There are " + averageRatings.size() + " movies with " +
                minNumberOfRatings + " or more ratings.");
    }

    public void getAverageRatingOneMovie(){
        SecondRatings secondRatings = new SecondRatings(moviePath,ratingsPath);
        String title = "The Godfather";
        String movieId = secondRatings.getID(title);
        int minNumberOfRatings = 1;
        ArrayList<Rating> averageRatings = secondRatings.getAverageRatings(minNumberOfRatings);
        for (Rating rating : averageRatings) {
            if (rating.getItem().equals(movieId)){
                System.out.println("For movie \"" + title + "\" the average rating is " + rating.getValue()+".");
            }
        }
    }
}
