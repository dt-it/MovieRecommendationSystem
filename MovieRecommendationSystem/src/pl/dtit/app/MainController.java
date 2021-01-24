package pl.dtit.app;

import pl.dtit.exceptions.DataImportException;
import pl.dtit.exceptions.InvalidDataException;
import pl.dtit.exceptions.NoSuchOptionException;
import pl.dtit.io.fileloaders.DataReader;
import pl.dtit.io.fileloaders.MovieDatabase;
import pl.dtit.io.fileloaders.RaterDatabase;
import pl.dtit.io.printers.MovieRunnerSimilarRatings;
import pl.dtit.io.ratings.FourthRatings;

import java.util.InputMismatchException;

public class MainController {
    DataReader dataReader = new DataReader();
    String movieFileNamePath;
    String ratingsFileNamePath;
    FourthRatings fourthRatings;

    public MainController() {
        movieFileNamePath = "data/ratedmoviesfull.csv";
        ratingsFileNamePath = "data/ratings.csv";
        try {
            System.out.println("Loading files...");
            Thread.sleep(1000);
            System.out.println("Loading files......");
            Thread.sleep(1000);
            System.out.println("Loading files.........");
            Thread.sleep(1000);
            fourthRatings = new FourthRatings(ratingsFileNamePath);
            MovieDatabase.initialize(movieFileNamePath);
            System.out.println("Data from files imported.");
            System.out.println("Number of raters - " + RaterDatabase.size());
            System.out.println("Number of movies in the database - " + MovieDatabase.size());
        }catch (DataImportException | InvalidDataException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
        }
    }

    public void controlLoop() {
        Option option;
        do {
            printOptions();
            option = getOption();
            switch (option) {
                case CHECK_SIMILAR_RATINGS:
                    checkSimilarRatings();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    System.out.println("Selected option is invalid. Please try again.");
            }
        } while (option != Option.EXIT);
    }

    private void checkSimilarRatings() {
        MovieRunnerSimilarRatings movieRunnersSimilarRating = new MovieRunnerSimilarRatings(dataReader);
        SimilarRatingOption similarRatingOption;
        do {
            printSimilarRatingsOptions();
            similarRatingOption = getSimilarRatingOption();
            switch (similarRatingOption) {
                case AVG_RATINGS:
                    movieRunnersSimilarRating.printAverageRatings(fourthRatings);
                    break;
                case AVG_RATINGS_BY_YEAR_AFTER_GENRE:
                    movieRunnersSimilarRating.printAverageRatingsByYearAfterAndGenre(fourthRatings);
                    break;
                case SIMILAR_RATINGS:
                    movieRunnersSimilarRating.printSimilarRatings(fourthRatings);
                    break;
                case SIMILAR_RATINGS_BY_GENRE:
                    movieRunnersSimilarRating.printSimilarRatingsByGenre(fourthRatings);
                    break;
                case SIMILAR_RATINGS_BY_DIRECTOR:
                    movieRunnersSimilarRating.printSimilarRatingsByDirector(fourthRatings);
                    break;
                case SIMILAR_RATINGS_BY_GENRE_MINUTES:
                    movieRunnersSimilarRating.printSimilarRatingsByGenreAndMinutes(fourthRatings);
                    break;
                case SIMILAR_RATINGS_BY_YEAR_MINUTES:
                    movieRunnersSimilarRating.printSimilarRatingsByYearAfterAndMinutes(fourthRatings);
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    System.out.println("Selected option is invalid. Please try again.");
            }
        } while (similarRatingOption != similarRatingOption.EXIT);
    }

    private SimilarRatingOption getSimilarRatingOption() {
        boolean noError = false;
        SimilarRatingOption option = null;
        while (!noError) {
            try {
                option = SimilarRatingOption.createFromInt(dataReader.getInt());
                noError = true;
            } catch (NoSuchOptionException e) {
                System.out.println(e.getMessage() + ", try again.");
            } catch (InputMismatchException e) {
                System.out.println("Provided input is not a number, try again.");
            }
        }
        return option;
    }

    private void printSimilarRatingsOptions() {
        System.out.println("Choose an option: ");
        for (SimilarRatingOption option : SimilarRatingOption.values()) {
            System.out.println(option.toString());
        }
    }

    private void printOptions() {
        System.out.println("Choose an option: ");
        for (Option option : Option.values()) {
            System.out.println(option.toString());
        }
    }

    private Option getOption() {
        boolean noError = false;
        Option option = null;
        while (!noError) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                noError = true;
            } catch (NoSuchOptionException e) {
                System.out.println(e.getMessage() + ", try again.");
            } catch (InputMismatchException e) {
                System.out.println("Provided input is not a number, try again.");
            }
        }
        return option;
    }

    private void exit() {
        System.out.println("Closing the App ... See you!");
        dataReader.close();
    }

    private enum Option {
        EXIT(0, "Exit"),
        CHECK_SIMILAR_RATINGS(1, "Check recommended movies");
        private int value;
        private String description;

        Option(int value, String description) {
            this.value = value;
            this.description = description;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("No such option as " + option);
            }
        }
    }


    private enum SimilarRatingOption {
        EXIT(0, "Exit"),
        AVG_RATINGS(1, "Print Average Ratings for all movies"),
        AVG_RATINGS_BY_YEAR_AFTER_GENRE(2, "Print Average Ratings by year after and genre"),
        SIMILAR_RATINGS(3, "Print similar ratings"),
        SIMILAR_RATINGS_BY_GENRE(4, "Print similar ratings by genre"),
        SIMILAR_RATINGS_BY_DIRECTOR(5, "Print similar ratings by director"),
        SIMILAR_RATINGS_BY_GENRE_MINUTES(6, "Print similar ratings by genre and min/max minutes"),
        SIMILAR_RATINGS_BY_YEAR_MINUTES(7, "Print similar ratings by year after and min/max minutes");

        private int value;
        private String description;

        SimilarRatingOption(int value, String description) {
            this.value = value;
            this.description = description;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static SimilarRatingOption createFromInt(int option) throws NoSuchOptionException {
            try {
                return SimilarRatingOption.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("No such option as " + option);
            }
        }
    }
}
