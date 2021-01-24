package pl.dtit.io.printers;

import pl.dtit.io.ratings.ThirdRatings;
import pl.dtit.io.fileloaders.MovieDatabase;
import pl.dtit.io.filters.*;
import pl.dtit.model.Rating;

import java.util.ArrayList;
import java.util.Collections;

//old class created during writing application - left for informational purpose
// actual class - MovieRunnerSimilarRatings
public class MovieRunnerWithFilters {
    private String moviePath = "data/ratedmovies_short.csv";
    private String ratingsPath = "data/ratings_short.csv";

    public void printAverageRatings(){
        ThirdRatings thirdRatings = new ThirdRatings(ratingsPath);
        MovieDatabase.initialize(moviePath);

        System.out.println("Number of raters - " + thirdRatings.getRaterSize());
        System.out.println("Number of movies in the database - " + MovieDatabase.size());

        int minNumberOfRatings = 1;
        ArrayList<Rating> averageRatings = thirdRatings.getAverageRatings(minNumberOfRatings);
        Collections.sort(averageRatings);
        for (Rating rating : averageRatings) {
            System.out.println(rating.getValue() + " - " + MovieDatabase.getTitle(rating.getItem()));
        }
        System.out.println("There are " + averageRatings.size() + " movies with " +
                minNumberOfRatings + " or more ratings.");
    }

    public void printAverageRatingsByYear(){
        ThirdRatings thirdRatings = new ThirdRatings(ratingsPath);
        MovieDatabase.initialize(moviePath);
        int year = 2000;
        int minNumberOfRaters = 1;
        Filter yearsAfterFilter = new YearsAfterFilter(year);
        ArrayList<Rating> averageRatingsByYearAfter = thirdRatings.getAverageRatingsByFilter(minNumberOfRaters, yearsAfterFilter);

        System.out.println("Number of raters - " + thirdRatings.getRaterSize());
        System.out.println("Number of movies in the database - " + MovieDatabase.size());

        Collections.sort(averageRatingsByYearAfter);
        for (Rating rating : averageRatingsByYearAfter) {
            System.out.println(rating.getValue() + " - " + MovieDatabase.getTitle(rating.getItem())
                    + ", year " + MovieDatabase.getYear(rating.getItem()));
        }
        System.out.println("There are " + averageRatingsByYearAfter.size() + " movies with " +
                minNumberOfRaters + " or more ratings and with yearAfterFilter of year " + year);
    }

    public void printAverageRatingsByGenre (){
        ThirdRatings thirdRatings = new ThirdRatings(ratingsPath);
        MovieDatabase.initialize(moviePath);
        String genre = "Crime";
        int minNumberOfRaters = 1;
        Filter genreFilter = new GenreFilter(genre);
        ArrayList<Rating> averageRatingsByGenre = thirdRatings.getAverageRatingsByFilter(minNumberOfRaters, genreFilter);

        System.out.println("Number of raters - " + thirdRatings.getRaterSize());
        System.out.println("Number of movies in the database - " + MovieDatabase.size());

        Collections.sort(averageRatingsByGenre);
        for (Rating rating : averageRatingsByGenre) {
            System.out.println(rating.getValue() + " - " + MovieDatabase.getTitle(rating.getItem())
                    + ", \n\t\tgenre: " + MovieDatabase.getGenres(rating.getItem()));
        }
        System.out.println("There are " + averageRatingsByGenre.size() + " movies with " +
                minNumberOfRaters + " or more ratings and with genre " + genre);
    }

    public void printAverageRatingsByMinutes (){
        ThirdRatings thirdRatings = new ThirdRatings(ratingsPath);
        MovieDatabase.initialize(moviePath);
        int minMinutes = 110;
        int maxMinutes = 170;
        int minNumberOfRaters = 1;
        Filter minutesFilter = new MinutesFilter(minMinutes, maxMinutes);
        ArrayList<Rating> averageRatingsByMinutes = thirdRatings.getAverageRatingsByFilter(minNumberOfRaters, minutesFilter);

        System.out.println("Number of raters - " + thirdRatings.getRaterSize());
        System.out.println("Number of movies in the database - " + MovieDatabase.size());

        Collections.sort(averageRatingsByMinutes);
        for (Rating rating : averageRatingsByMinutes) {
            System.out.println(rating.getValue() + " - " + MovieDatabase.getTitle(rating.getItem())
                    + ", time: " + MovieDatabase.getMinutes(rating.getItem()));
        }
        System.out.println("There are " + averageRatingsByMinutes.size() + " movies with " +
                minNumberOfRaters + " or more ratings and with time at least " + minMinutes
        + " and no more than " + maxMinutes);
    }

    public void printAverageRatingsByDirectors  (){
        ThirdRatings thirdRatings = new ThirdRatings(ratingsPath);
        MovieDatabase.initialize(moviePath);
        String directors = "Charles Chaplin,Michael Mann,Spike Jonze";
        int minNumberOfRaters = 1;
        Filter directorsFilter = new DirectorsFilter(directors);
        ArrayList<Rating> averageRatingsByMinutes = thirdRatings.getAverageRatingsByFilter(minNumberOfRaters, directorsFilter);

        System.out.println("Number of raters - " + thirdRatings.getRaterSize());
        System.out.println("Number of movies in the database - " + MovieDatabase.size());

        Collections.sort(averageRatingsByMinutes);
        for (Rating rating : averageRatingsByMinutes) {
            System.out.println(rating.getValue() + " - " + MovieDatabase.getTitle(rating.getItem())
                    + ", \n\tdirector: " + MovieDatabase.getDirector(rating.getItem()));
        }
        System.out.println("There are " + averageRatingsByMinutes.size() + " movies with " +
                minNumberOfRaters + " or more ratings and created by one of directors: " + directors);
    }

    public void printAverageRatingsByYearAfterAndGenre () {
        ThirdRatings thirdRatings = new ThirdRatings(ratingsPath);
        MovieDatabase.initialize(moviePath);
        System.out.println("Number of raters - " + thirdRatings.getRaterSize());
        System.out.println("Number of movies in the database - " + MovieDatabase.size());

        int year = 1990;
        Filter yaf = new YearsAfterFilter (year);

        String genre = "Romance";
        GenreFilter gf = new GenreFilter (genre);

        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(yaf);
        allFilters.addFilter(gf);

        int minNumOfRatings = 1;
        ArrayList<Rating> avgRatings = thirdRatings.getAverageRatingsByFilter(minNumOfRatings, allFilters);
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
    public void printAverageRatingsByDirectorsAndMinutes () {
        ThirdRatings thirdRatings = new ThirdRatings(ratingsPath);
        MovieDatabase.initialize(moviePath);
        System.out.println("Number of raters - " + thirdRatings.getRaterSize());
        System.out.println("Number of movies in the database - " + MovieDatabase.size());


        String directorsList = "Spike Jonze,Michael Mann,Charles Chaplin,Francis Ford Coppola";
        DirectorsFilter df = new DirectorsFilter (directorsList);

        int minMinutes = 30;
        int maxMinutes = 170;
        MinutesFilter mf = new MinutesFilter (minMinutes, maxMinutes);

        AllFilters addFilter = new AllFilters();
        addFilter.addFilter(df);
        addFilter.addFilter(mf);

        int minNumOfRatings = 1;
        ArrayList<Rating> avgRatings = thirdRatings.getAverageRatingsByFilter(minNumOfRatings, addFilter);
        System.out.println("There is(are) " + avgRatings.size() + " movie(s) that were filmed by"
                + " either one of these directors : " + directorsList + "; and between "
                + minMinutes + " and " + maxMinutes + " in length, with " + minNumOfRatings
                + " or more rating(s) :");

        Collections.sort(avgRatings);
        for (Rating rating : avgRatings) {
            System.out.println(rating.getValue() + " Time: " + MovieDatabase.getMinutes(rating.getItem())
                    + " " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("Directed by : " + MovieDatabase.getDirector(rating.getItem()));
        }
    }
}
