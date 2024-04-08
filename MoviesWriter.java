

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

public class MoviesWriter extends FileWriter<Movie> {

    public MoviesWriter() {
        super("/Users/DPU09/Documents/University/online-ticketing-service/src/main/resources/movies.json", Movie.class);
    }

    public void edit(String movieName, String field, String value) {

        try {
            list = mapper.readValue(new File(filePath), listType);

            list.stream()
                    .filter(x -> x.getName().equals(movieName))
                    .findFirst()
                    .ifPresentOrElse(x -> {
                                switch (field) {
                                    case "name":
                                        x.setName(value);
                                        break;
                                    case "rating":
                                        x.setRating(Integer.parseInt(value));
                                        break;
                                    case "price":
                                        x.setPrice(Double.parseDouble(value));
                                        break;
                                    case "length":
                                        x.setLength(Integer.parseInt(value));
                                        break;
                                    default:
                                        throw new NoSuchParameterException("Field not valid: " + field);
                                }
                            },
                            () -> {
                                throw new NoSuchMovieException("Movie not found: " + movieName);
                            }
                    );

            mapper.writeValue(new File(filePath), list);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
