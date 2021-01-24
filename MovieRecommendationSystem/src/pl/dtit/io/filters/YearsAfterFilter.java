package pl.dtit.io.filters;

import pl.dtit.io.fileloaders.MovieDatabase;

public class YearsAfterFilter implements Filter{
    private int myYear;

    public YearsAfterFilter(int myYear) {
        this.myYear = myYear;
    }

    @Override
    public boolean satisfies(String id) {
        return MovieDatabase.getYear(id) >= myYear;
    }
}
