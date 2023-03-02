import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter a cast member you want to search for: ");
        String castTerm = scanner.nextLine();

        ArrayList<String> castMembers = new ArrayList<String>();
        for (int i = 0; i < movies.size(); i++)
        {
            String cast = movies.get(i).getCast();
            cast = cast.toLowerCase();

            int ind = cast.indexOf("|");
            while(ind!=-1){
                if(!castMembers.contains(cast.substring(0, ind)) && cast.substring(0,ind).indexOf(castTerm)!=-1){
                    castMembers.add(cast.substring(0,ind));
                }
                cast = cast.substring(ind+1);
                ind = cast.indexOf("|");
            }
        }
        Collections.sort(castMembers);
        for (int i = 0; i < castMembers.size(); i++)
        {
            String member = castMembers.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + member);
        }

        System.out.println("Which member would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String member = castMembers.get(choice - 1);
        System.out.println("Movies starred in:");
        ArrayList<Movie> results = new ArrayList<Movie>();
        for(int i = 0; i<movies.size(); i++){
            if (movies.get(i).getCast().toLowerCase().indexOf(member)!=-1){
                results.add(movies.get(i));
            }
        }
        for (int i = 0; i < results.size(); i++)
        {
            Movie m = results.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + m);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int ch = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(ch - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String keyTerm = scanner.nextLine();

        // prevent case sensitivity
        keyTerm = keyTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String keywords = movies.get(i).getKeywords();
            keywords = keywords.toLowerCase();

            if (keywords.indexOf(keyTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        ArrayList<String> genres = new ArrayList<String>();
        for (int i = 0; i < movies.size(); i++)
        {
            String genre = movies.get(i).getGenres();
            genre = genre.toLowerCase();

            int ind = genre.indexOf("|");
            while(ind!=-1){
                if(!genres.contains(genre.substring(0, ind))){
                    genres.add(genre.substring(0,ind));
                }
                genre = genre.substring(ind+1);
                ind = genre.indexOf("|");
            }
        }
        Collections.sort(genres);
        for (int i = 0; i < genres.size(); i++)
        {
            String g = genres.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + g);
        }

        System.out.println("Select a genre to learn more about.");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String genre = genres.get(choice - 1);
        System.out.println("Movies:");
        ArrayList<Movie> results = new ArrayList<Movie>();
        for(int i = 0; i<movies.size(); i++){
            if (movies.get(i).getGenres().toLowerCase().indexOf(genre)!=-1){
                results.add(movies.get(i));
            }
        }
        for (int i = 0; i < results.size(); i++)
        {
            Movie m = results.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + m);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int ch = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(ch - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        ArrayList<Movie> sortedMovies = new ArrayList<Movie>();
        sortedMovies.add(movies.get(0));
        for(int i = 1; i<movies.size(); i++){
            int index = 0;
            for (int j = 0; i<sortedMovies.size(); j++){
                if(movies.get(i).getUserRating()>sortedMovies.get(j).getUserRating()){
                    index = j;
                }
            }
            sortedMovies.add(index, movies.get(i));
        }
        for (int i = 0; i < sortedMovies.size(); i++)
        {
            Movie m = sortedMovies.get(i);

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + m);
        }
        /*
        ArrayList<Double> allRatings = new ArrayList<Double>();
        for (int i = 0; i<movies.size(); i++){
            allRatings.add(movies.get(i).getUserRating());
        }
        Collections.sort(allRatings, Collections.reverseOrder());

        for(int i = 0; i<50; i++){
            Double ret = allRatings.get(i);
            int num = i+1;
            System.out.println(""+num + ". "+ ret);
        }
         */
    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> highestRevenue = new ArrayList<Movie>();
        for (int i = 0; i<50; i++){
            highestRevenue.add(movies.get(i));
        }
        System.out.println("Highest Grossing Movies: ");
        for (int i = 0; i<highestRevenue.size(); i++){
            String ret = "Title: " + highestRevenue.get(i).getTitle() + " | Revenue: " + highestRevenue.get(i).getRevenue();
            int num = i+1;
            System.out.println(""+num+". " + ret);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int ch = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = highestRevenue.get(ch - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}