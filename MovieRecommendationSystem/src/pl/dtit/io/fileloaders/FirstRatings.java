package pl.dtit.io.fileloaders;

import pl.dtit.exceptions.DataImportException;
import pl.dtit.model.EfficientRater;
import pl.dtit.model.Movie;
import pl.dtit.model.Rater;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FirstRatings {
    private ArrayList<Movie> movies;


    public FirstRatings() {
        movies = new ArrayList<>();
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public ArrayList<Movie> loadMovies(String filename) {
        try (
                var fileReader = new FileReader(filename);
                var reader = new BufferedReader(fileReader);
        ) {
            reader.readLine();
            String nextLine = null;
            while ((nextLine = reader.readLine()) != null) {
                String[] splitAll = nextLine.split(",\"");
                Movie movie = createMovieFromString(splitAll);
                movies.add(movie);
            }

        } catch (FileNotFoundException e) {
            throw new DataImportException("File not found " + filename);
        } catch (IOException e) {
            throw new DataImportException("File reading error " + filename);
        }
        return movies;
    }

    private Movie createMovieFromString(String[] splitAll) {
        String id = splitAll[0];
        String title = loadTitle(splitAll);
        int year = loadYear(splitAll);
        String country = splitAll[3];
        String genres = loadGenres(splitAll);
        String director = loadDirector(splitAll);
        int minutes = loadMinutes(splitAll);
        String poster = splitAll[splitAll.length - 1];

        return new Movie(id, title, year, genres, director, country, poster, minutes);
    }

    //helper methods to read all data from file ---->
    private String loadDirector(String[] splitAll) {
        String director = splitAll[splitAll.length - 2].split("\",")[0];
        return director;
    }

    private int loadMinutes(String[] splitAll) {
        String[] splitMinutes = splitAll[splitAll.length - 2].split("\",");
        String minutes = splitMinutes[splitMinutes.length - 1];
        return Integer.parseInt(minutes);
    }

    private int loadYear(String[] splitAll) {
        String[] splitYear = splitAll[1].split("\",");
        String year = splitYear[splitYear.length - 1];
        return Integer.parseInt(year);
    }

    private String loadTitle(String[] splitAll) {
        String title = splitAll[1].split("\",")[0];
        return title;
    }

    private String loadGenres(String[] split) {
        int firstIndexOfGenreMinusYear = 3;
        int lastIndexOfGenreMinusMin = split.length - 2;
        StringBuilder sb = new StringBuilder();
        for (int i = firstIndexOfGenreMinusYear; i < lastIndexOfGenreMinusMin; i++) {
            sb.append(split[i]);
        }
        String genres = sb.toString();
        genres = genres.replace("\"", "");
        return genres;
    }

    //<---- helper methods to read all data from file

    public void testLoadMovies() {
        String filename = "data/ratedmoviesfull.csv";
        loadMovies(filename);
        System.out.println("Number of movies: " + movies.size());
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        String genreName = "Comedy";
        int numberOfMoviesWithGenre = getNumberOfMoviesWithGenre(genreName);
        System.out.println("Number of movies with genre " + genreName + " - " + numberOfMoviesWithGenre);

        int movieLength = 150;
        int numberOfMoviesLongerThan = getNumberOfMoviesLongerThan(movieLength);
        System.out.println("Number of movies longer than " + movieLength + "min - " + numberOfMoviesLongerThan);

        countMoviesByDirector();
    }

    private int getNumberOfMoviesWithGenre(String genre) {
        int counter = 0;
        for (Movie movie : movies) {
            if (movie.getGenres().contains(genre)) {
                counter++;
            }
        }
        return counter;
    }

    private int getNumberOfMoviesLongerThan(int minutes) {
        int counter = 0;
        for (Movie movie : movies) {
            if (movie.getMinutes() > minutes) {
                counter++;
            }
        }
        return counter;
    }

    private void countMoviesByDirector() {
        HashMap<String, Integer> moviesByDirector = new HashMap<>();
        for (Movie movie : movies) {
            String[] directors = movie.getDirector().split(",");

            for (String director : directors) {
                director = director.trim();
                if (!moviesByDirector.containsKey(director)) {
                    moviesByDirector.put(director, 1);
                } else {
                    moviesByDirector.put(director, moviesByDirector.get(director) + 1);
                }
            }
        }

        // Count max number of movies directed by a particular director
        int maxNumOfMovies = 0;
        for (String director : moviesByDirector.keySet()) {
            if (moviesByDirector.get(director) > maxNumOfMovies) {
                maxNumOfMovies = moviesByDirector.get(director);
            }
        }

        // Create an ArrayList with directors from the list that directed max number of movies
        ArrayList<String> directorsList = new ArrayList<>();
        for (String director : moviesByDirector.keySet()) {
            if (moviesByDirector.get(director) == maxNumOfMovies) {
                directorsList.add(director);
            }
        }
        System.out.println("Max number of movies directed by one director: " + maxNumOfMovies);
        System.out.println("Directors who directed that many movies are: ");
        for (String director : directorsList) {
            System.out.println(director);
        }
    }

    public ArrayList<Rater> loadRaters(String filename) {
        ArrayList<Rater> efficientRaters = new ArrayList<>();
        ArrayList<String> listOfIDs = new ArrayList<> ();
        try (
                var fileReader = new FileReader(filename);
                var reader = new BufferedReader(fileReader);
        ) {
            reader.readLine();
            String nextLine = null;
            while ((nextLine = reader.readLine()) != null) {
                String[] split = nextLine.split(",");
                Rater efficientRater = new EfficientRater(split[0]);
                String movieId = split[1];
                double movieRating = Double.parseDouble(split[2]);

                if (!listOfIDs.contains(efficientRater.getID())) {
                    efficientRaters.add(efficientRater);
                    efficientRater.addRating(movieId, movieRating);
                } else {
                    for (Rater r : efficientRaters) {
                        if (r.getID().equals(efficientRater.getID())) {
                            r.addRating(movieId, movieRating);
                        }
                    }
                }
                listOfIDs.add(efficientRater.getID());
            }
        } catch (FileNotFoundException e) {
            throw new DataImportException("File not found " + filename);
        } catch (IOException e) {
            throw new DataImportException("File reading error " + filename);
        }
        return efficientRaters;
    }

    public void testLoadRaters() {
        String filename = "data/ratings.csv";
        ArrayList<Rater> efficientRaters = loadRaters(filename);
        System.out.println("Total number of raters: " + efficientRaters.size());
//        for (Rater rater : raters) {
//            System.out.println(rater);
//        }
        String raterId = "193";
        int numberOfRatings = findNumberOfRatings(efficientRaters, raterId);
        System.out.println("Number of ratings rater with id = " + raterId + ": " + numberOfRatings);

        HashMap<String, HashMap<String, Double>> allRatings = countRatingsByRater(efficientRaters);

        int maxNumberOfRatings = getMaxNumberOfRatings(allRatings);
        ArrayList<String> ratersWithMaxNumberOfMovieRated = getRaterWithMaxNumberOfMovieRated(allRatings, maxNumberOfRatings);
        System.out.println("Rater(s) with the most number of movies rated : " + ratersWithMaxNumberOfMovieRated);
        System.out.println("Number of rater(s) with the most number of movies rated : " + ratersWithMaxNumberOfMovieRated.size());

        String movieId = "1798709";
        int numOfRatings = getNumberOfMovieRating(allRatings, movieId);
        System.out.println("Number of ratings movie " + movieId + " has : " + numOfRatings);

        ArrayList<String> uniqueMovies = getUniqueMovies(allRatings);
        System.out.println("Total number of movies that were rated : " + uniqueMovies.size());
    }

    private int findNumberOfRatings(ArrayList<Rater> efficientRaters, String raterId) {
        int numberOfRatings = 0;
        for (Rater efficientRater : efficientRaters) {
            if (efficientRater.getID().equals(raterId)) {
                numberOfRatings = efficientRater.getItemsRated().size();
            }
        }
        return numberOfRatings;
    }

    private HashMap<String, HashMap<String, Double>> countRatingsByRater(ArrayList<Rater> efficientRaters) {
        HashMap<String, HashMap<String, Double>> allRatings = new HashMap<>();
        for (Rater efficientRater : efficientRaters) {
            HashMap<String, Double> ratings = new HashMap<>();
            ArrayList<String> itemsRated = efficientRater.getItemsRated();

            for (int i = 0; i < itemsRated.size(); i++) {
                String movieID = itemsRated.get(i);
                double movieRating = efficientRater.getRating(movieID);

                ratings.put(movieID, movieRating);
            }
            allRatings.put(efficientRater.getID(), ratings);
        }
        return allRatings;
    }

    private ArrayList<String> getUniqueMovies(HashMap<String, HashMap<String, Double>> hashmap) {
        ArrayList<String> uniqueMovies = new ArrayList<>();
        for (String key : hashmap.keySet()) {
            for (String currMovieID : hashmap.get(key).keySet()) {
                if (!uniqueMovies.contains(currMovieID)) {
                    uniqueMovies.add(currMovieID);
                }
            }
        }
        return uniqueMovies;
    }

    private int getNumberOfMovieRating(HashMap<String, HashMap<String, Double>> hashmap, String movieId) {
        int numOfRatings = 0;
        for (String key : hashmap.keySet()) {
            if (hashmap.get(key).containsKey(movieId)) {
                numOfRatings += 1;
            }
        }
        return numOfRatings;
    }

    private ArrayList<String> getRaterWithMaxNumberOfMovieRated(HashMap<String, HashMap<String, Double>> hashmap, int maxNumberOfRatings) {
        ArrayList<String> raterWithMaxNumOfRatings = new ArrayList<>();
        for (String key : hashmap.keySet()) {
            int currAmountOfRatings = hashmap.get(key).size();

            if (maxNumberOfRatings == currAmountOfRatings) {
                raterWithMaxNumOfRatings.add(key);
            }
        }
        return raterWithMaxNumOfRatings;
    }

    private int getMaxNumberOfRatings(HashMap<String, HashMap<String, Double>> hashmap) {
        int maxNumOfRatings = 0;
        for (String key : hashmap.keySet()) {
            int currAmountOfRatings = hashmap.get(key).size();
            if (currAmountOfRatings > maxNumOfRatings) {
                maxNumOfRatings = currAmountOfRatings;
            }
        }
        System.out.println("Maximum number of ratings by any rater : " + maxNumOfRatings);
        return maxNumOfRatings;
    }


}
