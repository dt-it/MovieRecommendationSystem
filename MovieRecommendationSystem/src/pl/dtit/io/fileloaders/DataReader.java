package pl.dtit.io.fileloaders;

import java.util.Scanner;

public class DataReader {
    private Scanner scanner = new Scanner(System.in);

    public int getInt() {
        try {
            return scanner.nextInt();
        } finally {
            scanner.nextLine();
        }
    }

    public String getString() {
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }

    public int getMinNumberOfRatingsFromUser(){
        System.out.println("Please provide minimum number of ratings for a movie");
        int minNumberOfRatings = scanner.nextInt();
        scanner.nextLine();
        return minNumberOfRatings;
    }

    public int getMinNumberOfSimilarRaters(){
        System.out.println("Please provide minimum number of similar ratings");
        int minNumberOfSimilarRaters = scanner.nextInt();
        scanner.nextLine();
        return minNumberOfSimilarRaters;
    }

    public int getYearAfter(){
        System.out.println("Please provide year after which the results will be displayed");
        int year = scanner.nextInt();
        scanner.nextLine();
        return year;
    }

    public String getGenre(){
        System.out.println("Please provide preferred genre of the movies");
        String genre = scanner.nextLine();
        return genre;
    }

    public String getRaterId(){
        System.out.println("Please provide your id to display recommended movies");
        String raterId = scanner.nextLine();
        return raterId;
    }

    public String getDirectors(){
        System.out.println("Please provide preferred directors of the movies");
        String directors = scanner.nextLine();
        return directors;
    }

    public int getMinMinutes(){
        System.out.println("Please provide minimum number of minutes for movie");
        int minMinutes = scanner.nextInt();
        scanner.nextLine();
        return minMinutes;
    }

    public int getMaxMinutes(){
        System.out.println("Please provide maximum number of minutes for movie");
        int maxMinutes = scanner.nextInt();
        scanner.nextLine();
        return maxMinutes;
    }
}
