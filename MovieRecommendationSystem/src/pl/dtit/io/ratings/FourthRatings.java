package pl.dtit.io.ratings;

import pl.dtit.io.fileloaders.MovieDatabase;
import pl.dtit.io.fileloaders.RaterDatabase;
import pl.dtit.io.filters.Filter;
import pl.dtit.io.filters.TrueFilter;
import pl.dtit.model.Rater;
import pl.dtit.model.Rating;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FourthRatings {


    public FourthRatings(String ratingsfile) {
        RaterDatabase.initialize(ratingsfile);
    }

    private double getAverageByID(String movieId, int minimalRaters) {
        double ratingsSum = 0;
        int counter = 0;
        for (Rater rater : RaterDatabase.getRaters()) {
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

    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<Rating> averageRatings = new ArrayList<>();
        for (String movieId : movies) {
            double average = getAverageByID(movieId, minimalRaters);
            if (average != 0) {
                Rating rating = new Rating(movieId, average);
                averageRatings.add(rating);
            }
        }
        return averageRatings;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> moviesWithMinimalRaters = new ArrayList<>();
        ArrayList<String> filteredMovies = MovieDatabase.filterBy(filterCriteria);

        for (String movieId : filteredMovies) {
            double averageByID = getAverageByID(movieId, minimalRaters);
            if (averageByID != 0) {
                moviesWithMinimalRaters.add(new Rating(movieId, averageByID));
            }
        }
        return moviesWithMinimalRaters;
    }

    //Write the private helper method named dotProduct, which has two parameters, a Rater named me
    // and a Rater named r. This method should first translate a rating from the scale 0 to 10 to
    // the scale -5 to 5 and return the dot product of the ratings of movies that they both rated.
    // This method will be called by getSimilarities.
    private double dotProduct(Rater me, Rater r) {
        double rating = 0;
        for (String item : me.getItemsRated()) {
            if (r.getItemsRated().contains(item)) {
                double ratingR = r.getRating(item);
                double ratingMe = me.getRating(item);

                rating += (ratingR - 5) * (ratingMe - 5);
            }
        }
        return rating;
    }

    //Write the private method named getSimilarities, which has one String parameter named id—this method
    // a similarity rating for each rater in the RaterDatabase (except the rater with the ID given by
    // the parameter) to see how similar they are to the Rater whose ID is the parameter to getSimilarities.
    // This method returns an ArrayList of type Rating sorted by ratings from highest to lowest rating
    // with the highest rating first and only including those raters who have a positive similarity
    // rating since those with negative values are not similar in any way. Note that in each Rating
    // object the item field is a rater’s ID, and the value field is the dot product comparison
    // between that rater and the rater whose ID is the parameter to getSimilarities.
    private ArrayList<Rating> getSimilarities(String id) {
        ArrayList<Rating> similarities = new ArrayList<>();
        Rater me = RaterDatabase.getRater(id);
        for (Rater rater : RaterDatabase.getRaters()) {
            if (!rater.getID().equals(id)) {
                double rating = dotProduct(me, rater);
                if (rating >= 0) {
                    similarities.add(new Rating(rater.getID(), rating));
                }
            }
        }
        Collections.sort(similarities, Collections.reverseOrder());
        return similarities;
    }

    //Write the public method named getSimilarRatings, which has three parameters: a String named id
    // representing a rater ID, an integer named numSimilarRaters, and an integer named minimalRaters.
    // This method should return an ArrayList of type Rating, of movies and their weighted average ratings
    // using only the top numSimilarRaters with positive ratings and including only those movies that have
    // at least minimalRaters ratings from those most similar raters (not just minimalRaters ratings overall).
    // For example, if minimalRaters is 3 and a movie has 4 ratings but only 2 of those ratings were
    // made by raters in the top numSimilarRaters, that movie should not be included.
    // These Rating objects should be returned in sorted order by weighted average rating from largest
    // to smallest ratings.
    public ArrayList<Rating> getSimilarRatings(String raterId, int numSimilarRaters, int minimalRaters){
        ArrayList<Rating> similarList = getSimilarities(raterId);

        //Map with movies and info with userId and rate for that movie
        // Key: Movies' IDs.  Value: RaterID and rating value.
        HashMap<String, HashMap<String, Double>> moviesWithRatingMap = new HashMap<>();

        createMapWithMoviesIdAndRateValueFromRater(similarList, moviesWithRatingMap, numSimilarRaters);

        ArrayList<Rating> result = new ArrayList<>();
        for (String movieId : moviesWithRatingMap.keySet()){
            HashMap<String, Double> raterIdRatingValueMap = moviesWithRatingMap.get(movieId);
            if (raterIdRatingValueMap.size() >= minimalRaters){
                double total = 0;
                for (String currRaterId: raterIdRatingValueMap.keySet()){
                    double currSimilarRating = 0.0;
                    // Find similar rating for the currRater.
                    for (Rating r: similarList){
                        if (r.getItem().equals(currRaterId)){
                            currSimilarRating = r.getValue();
                        }
                    }
                    total += raterIdRatingValueMap.get(currRaterId)*currSimilarRating;
                }
                double weightedAverage = total/raterIdRatingValueMap.size();
                result.add(new Rating(movieId, weightedAverage));
            }
        }
        Collections.sort(result, Collections.reverseOrder());
        return result;
    }

    private void createMapWithMoviesIdAndRateValueFromRater(ArrayList<Rating> similarList,
                                                            HashMap<String, HashMap<String, Double>> moviesWithRatingMap,
                                                            int numSimilarRaters) {
        for (int i=0; i<numSimilarRaters; i++){
            String currRaterId = similarList.get(i).getItem();
            Rater currRater = RaterDatabase.getRater(currRaterId);
            ArrayList<String> ratedMovies = currRater.getItemsRated();
            for (String movieId: ratedMovies){
                if (!moviesWithRatingMap.containsKey(movieId)){
                    HashMap<String, Double> first = new HashMap<>();
                    first.put(currRaterId, currRater.getRating(movieId));
                    moviesWithRatingMap.put(movieId, first);
                } else {
                    moviesWithRatingMap.get(movieId).put(currRaterId, currRater.getRating(movieId));
                }
            }
        }
    }

}
