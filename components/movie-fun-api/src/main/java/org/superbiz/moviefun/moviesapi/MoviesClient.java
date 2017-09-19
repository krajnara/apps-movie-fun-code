package org.superbiz.moviefun.moviesapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import static org.springframework.http.HttpMethod.GET;

/**
 * Created by 103209 on 18/09/17.
 */
public class MoviesClient {
    private static final Logger logger = LoggerFactory.getLogger(MoviesClient.class);

    public String moviesUrl;
    public RestOperations restOperations;

    private MovieFixtures movieFixtures;

    private static ParameterizedTypeReference<List<MovieInfo>> movieListType = new ParameterizedTypeReference<List<MovieInfo>>() {
    };

    public MoviesClient(String moviesUrl, RestOperations restOperations, MovieFixtures movieFixtures) {
        this.moviesUrl = moviesUrl;
        this.restOperations = restOperations;
        this.movieFixtures = movieFixtures;
    }

    public void setup() {
        for (MovieInfo movie : movieFixtures.load()) {
            addMovie(movie);
        }
    }

//    public ResponseEntity<MovieInfo> find(Long id) {
//        return restOperations.getForEntity(moviesUrl, MovieInfo.class);
//    }

    public void addMovie(MovieInfo movie) {
        logger.debug("Creating movie with title {}, and year {}", movie.getTitle(), movie.getYear());

        restOperations.postForEntity(moviesUrl, movie, null);
    }

//    public void updateMovie(Movie movie) {
//        entityManager.merge(movie);
//    }
//
//    public void deleteMovie(MovieInfo movie) {
//        restOperations.delete(moviesUrl, movie);
//    }

    public void deleteMovieId(long id) {
        restOperations.delete(moviesUrl + "/" + id);
    }

    public List<MovieInfo> findAll(int start, int pageSize) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl)
                .queryParam("start", start)
                .queryParam("pageSize", pageSize);

        return restOperations.exchange(builder.toUriString(), GET, null, movieListType).getBody();
    }

    public List<MovieInfo> findRange(String field, String key, int start, int pageSize) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl)
                .queryParam("field", field)
                .queryParam("key", key)
                .queryParam("start", start)
                .queryParam("pageSize", pageSize);

        return restOperations.exchange(builder.toUriString(), GET, null, movieListType).getBody();
    }

    public List<MovieInfo> getMovies() {
        return restOperations.exchange(moviesUrl, GET, null, movieListType).getBody();
    }

    public int countAll() {
        return restOperations.getForObject(moviesUrl + "/count", Integer.class);
    }

    public int count(String field, String searchTerm) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesUrl + "/count")
                .queryParam("field", field)
                .queryParam("key", searchTerm);

        return restOperations.getForObject(builder.toUriString(), Integer.class);
    }

//    public void clean() {
//        entityManager.createQuery("delete from Movie").executeUpdate();
//    }

}
