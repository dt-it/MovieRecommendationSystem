package pl.dtit.io.filters;

import pl.dtit.io.fileloaders.MovieDatabase;

public class MinutesFilter implements Filter{
    private int minMinutes;
    private int maxMinutes;

    public MinutesFilter(int minMinutes, int maxMinutes) {
        this.minMinutes = minMinutes;
        this.maxMinutes = maxMinutes;
    }

    @Override
    public boolean satisfies(String id) {
        if (MovieDatabase.getMinutes(id) >=minMinutes && MovieDatabase.getMinutes(id)<=maxMinutes){
            return true;
        }
        return false;
    }
}
