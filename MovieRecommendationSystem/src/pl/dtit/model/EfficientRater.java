package pl.dtit.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class EfficientRater implements Rater {
    private String myID;
    private HashMap<String,Rating> myRatings;

    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<>();
    }

    @Override
    public void addRating(String item, double rating) {
        myRatings.put(item, new Rating(item,rating));
    }

    @Override
    public boolean hasRating(String item) {
        if ((myRatings.containsKey(item))){
            return true;
        }
        return false;
    }

    @Override
    public String getID() {
        return myID;
    }

    @Override
    public double getRating(String item) {
        for (String movieId : myRatings.keySet()) {
            if (movieId.equals(item)){
                return myRatings.get(movieId).getValue();
            }
        }
        return -1;
    }

    @Override
    public int numRatings() {
        return myRatings.size();
    }

    @Override
    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<> ();
        for (String movieID : myRatings.keySet()) {
            list.add(myRatings.get(movieID).getItem());
        }
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EfficientRater that = (EfficientRater) o;
        return Objects.equals(myID, that.myID) && Objects.equals(myRatings, that.myRatings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myID, myRatings);
    }

    @Override
    public String toString() {
        return "ID: " + myID + ", ratings: " + myRatings;
    }
}
