package pl.dtit.io.filters;

import pl.dtit.io.fileloaders.MovieDatabase;

public class GenreFilter implements Filter{
    private String myGenre;

    public GenreFilter(String myGenre) {
        this.myGenre = myGenre;
    }

    @Override
    public boolean satisfies(String id) {
        return MovieDatabase.getGenres(id).contains(myGenre);
    }
}
