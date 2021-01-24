package pl.dtit.io.fileloaders;

import pl.dtit.io.filters.Filter;
import pl.dtit.model.Movie;

import java.util.ArrayList;
import java.util.HashMap;

public class MovieDatabase {
    private static HashMap<String, Movie> ourMovies;


    public static void initialize(String moviefile) {
        if (ourMovies == null) {
            ourMovies = new HashMap<>();
            loadMovies(moviefile);
        }
    }

    private static void initialize() {
        if (ourMovies == null) {
            ourMovies = new HashMap<>();
            loadMovies("data/ratedmoviesfull.csv");
        }
    }

    private static void loadMovies(String filename) {
        FirstRatings firstRatings = new FirstRatings();
        ArrayList<Movie> movies = firstRatings.loadMovies(filename);
        for (Movie movie : movies) {
            ourMovies.put(movie.getID(), movie);
        }
    }

    public static boolean containsID(String id){
        initialize();
        return ourMovies.containsKey(id);
    }

    public static int getYear(String id) {
        initialize();
        return ourMovies.get(id).getYear();
    }

    public static String getGenres(String id) {
        initialize();
        return ourMovies.get(id).getGenres();
    }

    public static String getTitle(String id) {
        initialize();
        return ourMovies.get(id).getTitle();
    }

    public static Movie getMovie(String id) {
        initialize();
        return ourMovies.get(id);
    }

    public static String getPoster(String id) {
        initialize();
        return ourMovies.get(id).getPoster();
    }

    public static int getMinutes(String id) {
        initialize();
        return ourMovies.get(id).getMinutes();
    }

    public static String getCountry(String id) {
        initialize();
        return ourMovies.get(id).getCountry();
    }

    public static String getDirector(String id) {
        initialize();
        return ourMovies.get(id).getDirector();
    }

    public static int size(){
        return ourMovies.size();
    }

    public static ArrayList<String> filterBy(Filter f){
        initialize();
        ArrayList<String> list = new ArrayList<String>();
        for (String id : ourMovies.keySet()) {
            if (f.satisfies(id)) {
                list.add(id);
            }
        }
        return list;
    }

}
