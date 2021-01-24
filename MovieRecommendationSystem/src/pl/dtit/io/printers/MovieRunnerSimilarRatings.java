package pl.dtit.io.printers;

import pl.dtit.io.fileloaders.DataReader;
import pl.dtit.io.fileloaders.MovieDatabase;
import pl.dtit.io.filters.*;
import pl.dtit.io.ratings.FourthRatings;
import pl.dtit.model.Rating;

import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerSimilarRatings {
    DataReader dataReader;

    public MovieRunnerSimilarRatings(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    public void printAverageRatings(FourthRatings fourthRatings) {
        int minNumberOfRatings = dataReader.getMinNumberOfRatingsFromUser();
        ArrayList<Rating> averageRatings = fourthRatings.getAverageRatings(minNumberOfRatings);
        Collections.sort(averageRatings);
        for (Rating rating : averageRatings) {
            System.out.println(rating.getValue() + " - " + MovieDatabase.getTitle(rating.getItem()));
        }
        System.out.println("There are " + averageRatings.size() + " movies with " +
                minNumberOfRatings + " or more ratings.");
    }

    public void printAverageRatingsByYearAfterAndGenre(FourthRatings fourthRatings) {
        int year = dataReader.getYearAfter();
        Filter yaf = new YearsAfterFilter(year);
        String genre = dataReader.getGenre();
        GenreFilter gf = new GenreFilter(genre);

        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(yaf);
        allFilters.addFilter(gf);

        int minNumOfRatings = 1;
        ArrayList<Rating> avgRatings = fourthRatings.getAverageRatingsByFilter(minNumOfRatings, allFilters);
        System.out.println("There is(are) " + avgRatings.size() + " movie(s)  in genre of \""
                + genre + "\" that was(were) directed after " + year + " with " + minNumOfRatings
                + " or more rating(s) :");

        Collections.sort(avgRatings);
        for (Rating rating : avgRatings) {
            System.out.println(rating.getValue() + ", year " + MovieDatabase.getYear(rating.getItem())
                    + " " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("Genre : " + MovieDatabase.getGenres(rating.getItem()));
        }
    }

    public void printSimilarRatings(FourthRatings fourthRatings) {
        String raterId = dataReader.getRaterId();
        int minimalRaters = dataReader.getMinNumberOfRatingsFromUser();
        int numSimilarRaters = dataReader.getMinNumberOfSimilarRaters();
        ArrayList<Rating> similarRatings = fourthRatings.getSimilarRatings(raterId, numSimilarRaters, minimalRaters);
        System.out.println("There is(are) " + similarRatings.size() + " movie(s) that is(are) "
                + "recommended for the rater with ID " + raterId + " with " + minimalRaters
                + " or more rating(s). " + numSimilarRaters + " closest raters were considered.");
        System.out.println("Movie with the top rated average is " + MovieDatabase.getTitle(similarRatings.get(0).getItem()));
        for (Rating similarRating : similarRatings) {
            System.out.println(similarRating.getValue() + ", year " + MovieDatabase.getYear(similarRating.getItem())
                    + " " + MovieDatabase.getTitle(similarRating.getItem()));
        }
    }

    public void printSimilarRatingsByGenre(FourthRatings fourthRatings) {
        String raterId = dataReader.getRaterId();
        int minimalRaters = dataReader.getMinNumberOfRatingsFromUser();
        int numSimilarRaters = dataReader.getMinNumberOfSimilarRaters();
        String genre = dataReader.getGenre();

        GenreFilter filter = new GenreFilter(genre);
        ArrayList<String> filteredMovieIds = MovieDatabase.filterBy(filter);
        ArrayList<Rating> similarRatings = fourthRatings.getSimilarRatings(raterId, numSimilarRaters, minimalRaters);

        int num = 0;
        for (Rating r: similarRatings){
            if (filteredMovieIds.contains(r.getItem())){
                System.out.println(MovieDatabase.getTitle(r.getItem()) + " : " + r.getValue());
                System.out.println("    " + MovieDatabase.getGenres(r.getItem()));
                num += 1;
            }
        }
        System.out.println("\nThere are " + num + " recommended movies were found.");
    }

    public void printSimilarRatingsByDirector (FourthRatings fourthRatings) {
        String raterId = dataReader.getRaterId();
        int minimalRaters = dataReader.getMinNumberOfRatingsFromUser();
        int numSimilarRaters = dataReader.getMinNumberOfSimilarRaters();
        String directors = dataReader.getDirectors();

        DirectorsFilter filter = new DirectorsFilter(directors);
        ArrayList<String> filteredMovieIds = MovieDatabase.filterBy(filter);
        ArrayList<Rating> similarRatings = fourthRatings.getSimilarRatings(raterId, numSimilarRaters, minimalRaters);

        int num = 0;
        for (Rating r: similarRatings){
            if (filteredMovieIds.contains(r.getItem())){
                System.out.println(MovieDatabase.getTitle(r.getItem()) + " : " + r.getValue());
                System.out.println("    " + MovieDatabase.getGenres(r.getItem()));
                num += 1;
            }
        }
        System.out.println("\nThere are " + num + " recommended movies were found.");
    }

    public void printSimilarRatingsByGenreAndMinutes  (FourthRatings fourthRatings) {
        String raterId = dataReader.getRaterId();
        int minimalRaters = dataReader.getMinNumberOfRatingsFromUser();
        int numSimilarRaters = dataReader.getMinNumberOfSimilarRaters();
        String genre = dataReader.getGenre();
        int minMinutes = dataReader.getMinMinutes();
        int maxMinutes = dataReader.getMaxMinutes();

        GenreFilter gf = new GenreFilter (genre);
        MinutesFilter mf = new MinutesFilter(minMinutes, maxMinutes);
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(gf);
        allFilters.addFilter(mf);
        ArrayList<String> filteredMovieIds = MovieDatabase.filterBy(allFilters);
        ArrayList<Rating> similarRatings = fourthRatings.getSimilarRatings(raterId, numSimilarRaters, minimalRaters);

        int num = 0;
        for (Rating r: similarRatings){
            if (filteredMovieIds.contains(r.getItem())){
                System.out.println(MovieDatabase.getTitle(r.getItem()) + " : " + r.getValue());
                System.out.println("    " + MovieDatabase.getGenres(r.getItem()));
                num += 1;
            }
        }
        System.out.println("\nThere are " + num + " recommended movies were found.");
    }

    public void printSimilarRatingsByYearAfterAndMinutes (FourthRatings fourthRatings) {
        String raterId = dataReader.getRaterId();
        int minimalRaters = dataReader.getMinNumberOfRatingsFromUser();
        int numSimilarRaters = dataReader.getMinNumberOfSimilarRaters();
        int yearAfter = dataReader.getYearAfter();
        int minMinutes = dataReader.getMinMinutes();
        int maxMinutes = dataReader.getMaxMinutes();

        YearsAfterFilter yf = new YearsAfterFilter (yearAfter);
        MinutesFilter mf = new MinutesFilter(minMinutes, maxMinutes);
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(yf);
        allFilters.addFilter(mf);
        ArrayList<String> filteredMovieIds = MovieDatabase.filterBy(allFilters);
        ArrayList<Rating> similarRatings = fourthRatings.getSimilarRatings(raterId, numSimilarRaters, minimalRaters);

        int num = 0;
        for (Rating r: similarRatings){
            if (filteredMovieIds.contains(r.getItem())){
                System.out.println(MovieDatabase.getTitle(r.getItem()) + " : " + r.getValue());
                System.out.println("    " + MovieDatabase.getGenres(r.getItem()));
                num += 1;
            }
        }
        System.out.println("\nThere are " + num + " recommended movies were found.");
    }
}
