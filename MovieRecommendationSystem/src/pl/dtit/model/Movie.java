package pl.dtit.model;

public class Movie {
    private String id;
    private String title;
    private int year;
    private String genres;
    private String director;
    private String country;
    private String poster;
    private int minutes;

    public Movie(String id, String title, int year, String genres) {
        this.id = id.trim();
        this.title = title.trim();
        this.year = year;
        this.genres = genres;
    }

    public Movie(String id, String title, int year, String genres, String director,
                 String country, String poster, int minutes) {
        this.id = id.trim();
        this.title = title.trim();
        this.year = year;
        this.genres = genres;
        this.director = director;
        this.country = country;
        this.poster = poster;
        this.minutes = minutes;
    }

    public String getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getGenres() {
        return genres;
    }

    public String getCountry() {
        return country;
    }

    public String getDirector() {
        return director;
    }

    public String getPoster() {
        return poster;
    }

    public int getMinutes() {
        return minutes;
    }

    public String toString() {
        String result = "Movie (id: " + id + ", title: " + title + ", year: " + year + ", genres: " + genres +
                ", director: " + director + ", " + minutes + " min, poster: " + poster + ")";
        return result;
    }
}
