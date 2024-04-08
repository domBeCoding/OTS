import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class VenueManagerController {
    private final MovieService movieService = new MovieService();
    private final DateGenerator dateGenerator = new DateGenerator();

    public VenueManagerController() {
    }

    public void routeOption(String option) {

        switch (option) {
            case "1":
                addController();
                break;
            case "2":
                deleteController();
                break;
            case "3":
                editController();
                break;
            case "4":
                Utility.printMessage("Logging off...");
                break;
            default:
                break;
        }
    }

    public void addController() {

        boolean finishAddingMovie = false;
        List<Movie> movies = new ArrayList<>();

        while (!finishAddingMovie) {
            Utility.printSpace();
            Utility.printMessage("Please enter the (name) of the movie you'd like to add");
            String movieName = InputReader.read();

            Utility.printMessage("Please enter the available (time) showing daily in 'hh:mm' 24h format, seperated by commas");
            String availableTimes = InputReader.read();

            Utility.printMessage("Please enter the start (date) in 'dd-mm-yy' format");
            String startDate = InputReader.read();

            Utility.printMessage("Please enter how the number of days the movie will be showing");
            String airingDays = InputReader.read();

            Utility.printMessage("Please enter the (length) of the movie");
            String length = InputReader.read();

            Utility.printMessage("Please enter the (rating) out of 5");
            String rating = InputReader.read();

            Utility.printMessage("Please enter the (price) in 'xx.xx' format");
            String price = InputReader.read();

            List<String> listOfAvailableTimes =
                    Arrays.stream(availableTimes.trim().split(","))
                            .map(String::trim)
                            .toList();

            try{
                MovieValidator.validateFields(movieName, listOfAvailableTimes, startDate, airingDays, length, rating, price);
            } catch (IllegalArgumentException e) {

                Utility.printSpace();
                Utility.printMessage("Movie added unsuccessfully");
                Utility.printMessage(e.getMessage());

                Utility.printSpace();
                Utility.printMessage("Would you like to add another movie? (y/n)");
                if (!InputReader.readYesOrNo()) {
                    break;
                }
                continue;
            }

            List<String> generatedDates = dateGenerator.generate(startDate, airingDays);

            Movie movie = new Movie(movieName,
                    listOfAvailableTimes,
                    generatedDates,
                    Integer.parseInt(length),
                    Integer.parseInt(rating),
                    Double.parseDouble(price));

            movies.add(movie);

            Utility.printMessage("Would you like to add another movie? (y/n)");
            if (!InputReader.readYesOrNo()) {
                finishAddingMovie = true;
            }
        }

        try {
            movieService.addMovies(movies);
            Utility.printSpace();
            Utility.printMessage("Successfully added movies: ");
            for (Movie movie : movies) {
                Utility.printMessage(String.valueOf(movie));
            }
        } catch (RuntimeException e) {
            Utility.printMessage("Movies added unsuccessfully");
            System.out.println(e.getMessage());
        }
    }

    public void deleteController() {

        boolean finishAddingMovie = false;
        List<String> movies = new ArrayList<>();

        while (!finishAddingMovie) {
            Utility.printSpace();
            Utility.printMessage("Please enter the (name) of the movie you'd like to delete");
            String movieName = InputReader.read();

            try{
                DeleteMovieValidator.validate(movieName);
                movies.add(movieName);
                finishAddingMovie = true;
            } catch (IllegalArgumentException e) {
                Utility.printSpace();
                Utility.printMessage("Movie removed unsuccessfully");
                Utility.printMessage(e.getMessage());

                Utility.printSpace();
                Utility.printMessage("Would you like to remove another movie? (y/n)");
                if (!InputReader.readYesOrNo()) {
                    finishAddingMovie = true;
                }
            }
        }

        try {
            for(String movie : movies) {
                movieService.eraseMovieByName(movie);
            }
            Utility.printSpace();
            Utility.printMessage("Successfully deleted movies: ");
            for (String movie : movies) {
                Utility.printMessage(movie);
            }
        } catch (NoSuchMovieException e) {
            Utility.printMessage("Remove movies unsuccessful.");
            System.out.println(e.getMessage());
        }
    }

    public void editController() {
        boolean finishedEditingMovie = false;

        while (!finishedEditingMovie) {
            Utility.printSpace();
            Utility.printMessage("Please enter the (name) of the movie you'd like to edit");
            String movieName = InputReader.read();

            Utility.printMessage("Please enter the field you'd like to edit (name|length|rating|price)");
            String field = InputReader.read();

            Utility.printMessage("Please enter the new value of " + "(" + field + ")");
            String value = InputReader.read();

            try{

                EditMovieValidator.validate(movieName, field, value);
            } catch (IllegalArgumentException e) {
                Utility.printSpace();
                Utility.printMessage("Movie edited unsuccessfully");
                Utility.printMessage(e.getMessage());

                Utility.printSpace();
                Utility.printMessage("Would you like to edit another Movie? (y/n)");

                if (!InputReader.readYesOrNo()) {
                    finishedEditingMovie = true;
                }
                continue;
            }

            try {
                Utility.printSpace();
                movieService.editMovie(movieName, field, value);
                Utility.printSpace();

                Utility.printMessage("Movie edited successfully.");
            } catch (NoSuchFieldException e) {
                Utility.printMessage("Movie edited unsuccessfully.");
            }

            Utility.printSpace();
            Utility.printMessage("Would you like to edit another Movie? (y/n)");

            if (!InputReader.readYesOrNo()) {
                finishedEditingMovie = true;
            }
        }
    }
}
