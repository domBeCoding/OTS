

public class MoviesReader extends FileReader<Movie> {
    public MoviesReader() {
        super(Movie.class);
    }

    public Movie get(String movieName) {
        read();
        return objectList.stream()
                .filter(x -> x.getName().equals(movieName))
                .findFirst()
                .orElseThrow(() -> new NoSuchMovieException("Movie not found: " + movieName));
    }
}
