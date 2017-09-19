package org.superbiz.moviefun;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.superbiz.moviefun.albumsapi.AlbumsClient;
import org.superbiz.moviefun.moviesapi.MoviesClient;

import java.util.Map;

@Controller
public class HomeController {

    private final MoviesClient client;
    private final AlbumsClient aClient;

    public HomeController(MoviesClient client, AlbumsClient aClient) {
        this.client = client;
        this.aClient = aClient;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/setup")
    public String setup(Map<String, Object> model) {
        client.setup();
        aClient.setup();

        model.put("movies", client.getMovies());
        model.put("albums", aClient.getAlbums());

        return "setup";
    }
}
