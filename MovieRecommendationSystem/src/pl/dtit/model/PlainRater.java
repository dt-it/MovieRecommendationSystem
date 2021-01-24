package pl.dtit.model;

import java.util.*;

public class PlainRater implements Rater {
    private String myID;
    private ArrayList<Rating> myRatings;

    public PlainRater(String id) {
        myID = id;
        myRatings = new ArrayList<>();
    }

    @Override
    public void addRating(String item, double rating) {
        myRatings.add(new Rating(item,rating));
    }

    @Override
    public boolean hasRating(String item) {
        for(int i=0; i < myRatings.size(); i++){
            if (myRatings.get(i).getItem().equals(item)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String getID() {
        return myID;
    }

    @Override
    public double getRating(String item) {
        for(int i=0; i < myRatings.size(); i++){
            if (myRatings.get(i).getItem().equals(item)){
                return myRatings.get(i).getValue();
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
        ArrayList<String> list = new ArrayList<>();
        for(int k=0; k < myRatings.size(); k++){
            list.add(myRatings.get(k).getItem());
        }
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlainRater plainRater = (PlainRater) o;
        return Objects.equals(myID, plainRater.myID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myID);
    }

    @Override
    public String toString() {
        return "ID: " + myID + ", ratings: " + myRatings;
    }
}
