package pl.dtit.io.filters;

public class TrueFilter implements Filter{
    @Override
    public boolean satisfies(String id) {
        return true;
    }
}
