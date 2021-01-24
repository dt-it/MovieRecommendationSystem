package pl.dtit.io.ratings;

import pl.dtit.io.fileloaders.FirstRatings;
import pl.dtit.io.fileloaders.MovieDatabase;
import pl.dtit.io.filters.Filter;
import pl.dtit.io.filters.TrueFilter;
import pl.dtit.model.Rater;
import pl.dtit.model.Rating;

import java.util.ArrayList;

public class ThirdRatings {
    private ArrayList<Rater> myRaters;

    public ThirdRatings(String ratingsfile) {
        FirstRatings firstRatings = new FirstRatings();
        myRaters = firstRatings.loadRaters(ratingsfile);
    }

    public int getRaterSize() {
        return myRaters.size();
    }

    private double getAverageByID(String movieId, int minimalRaters) {
        double ratingsSum = 0;
        int counter = 0;
        for (Rater rater : myRaters) {
            if (rater.hasRating(movieId)) {
                ratingsSum += rater.getRating(movieId);
                counter++;
            }
        }
        if (counter >= minimalRaters) {
            return ratingsSum / counter;
        } else {
            return 0;
        }
    }

    public ArrayList<Rating>  getAverageRatings (int minimalRaters){
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<Rating> averageRatings = new ArrayList<> ();
        for (String movieId : movies) {
            double average = getAverageByID(movieId, minimalRaters);
            if (average != 0) {
                Rating rating = new Rating (movieId, average);
                averageRatings.add(rating);
            }
        }
        return averageRatings;
    }

    public ArrayList<Rating> getAverageRatingsByFilter (int minimalRaters, Filter filterCriteria){
        ArrayList<Rating> moviesWithMinimalRaters = new ArrayList<>();
        ArrayList<String> filteredMovies = MovieDatabase.filterBy(filterCriteria);

        for (String movieId : filteredMovies) {
            double averageByID = getAverageByID(movieId, minimalRaters);
            if (averageByID!=0){
                moviesWithMinimalRaters.add(new Rating(movieId, averageByID));
            }
        }
        return moviesWithMinimalRaters;
    }



}
