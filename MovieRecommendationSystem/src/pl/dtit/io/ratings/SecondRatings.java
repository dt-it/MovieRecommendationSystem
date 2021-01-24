package pl.dtit.io.ratings;

import pl.dtit.io.fileloaders.FirstRatings;
import pl.dtit.model.Movie;
import pl.dtit.model.Rater;
import pl.dtit.model.Rating;

import java.util.ArrayList;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;

    public SecondRatings(String moviefile, String ratingsfile) {
        FirstRatings firstRatings = new FirstRatings();
        firstRatings.loadMovies(moviefile);
        myMovies = firstRatings.getMovies();
        myRaters = firstRatings.loadRaters(ratingsfile);
    }

    public int getMovieSize() {
        return myMovies.size();
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
        ArrayList<Rating> averageRatings = new ArrayList<> ();
        for (Movie movie : myMovies) {
            String movieID = movie.getID();
            double average = getAverageByID(movieID, minimalRaters);
            if (average != 0) {
                Rating rating = new Rating (movieID, average);
                averageRatings.add(rating);
            }
        }
        return averageRatings;
    }

    public String getTitle(String movieId) {
        String title = "";
        for (Movie movie : myMovies) {
            if (movie.getID().equals(movieId)) {
                title = movie.getTitle();
            }
        }
        if (title != null) {
            return title;
        } else {
            return "Movie with ID " + movieId + " was not found";
        }
    }

    public String getID (String title) {
        String movieId = "";
        for (Movie movie : myMovies) {
            if (movie.getTitle().equals(title)) {
                movieId = movie.getID();
            }
        }
        if (movieId != null) {
            return movieId;
        } else {
            return "No such title like \"" + title + "\"";
        }
    }


}
