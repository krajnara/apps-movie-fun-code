package org.superbiz.moviefun;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.superbiz.moviefun.albumsapi.AlbumFixtures;
import org.superbiz.moviefun.albumsapi.AlbumsClient;
import org.superbiz.moviefun.moviesapi.MovieFixtures;
import org.superbiz.moviefun.moviesapi.MoviesClient;

/**
 * Created by 103209 on 18/09/17.
 */
@Configuration
public class ClientConfiguration {

    @Value("${movies.url}") String moviesUrl;
    @Value("${albums.url}") String albumsUrl;

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }

    @Bean
    public MoviesClient moviesClient(RestOperations restOperations, MovieFixtures movieFixtures) {
        return new MoviesClient(moviesUrl, restOperations, movieFixtures);
    }


    @Bean
    public AlbumsClient albumsClient(RestOperations restOperations, AlbumFixtures albumFixtures) {
        return new AlbumsClient(albumsUrl, restOperations, albumFixtures);
    }
}