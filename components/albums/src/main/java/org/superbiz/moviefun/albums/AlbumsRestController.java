package org.superbiz.moviefun.albums;

import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 103209 on 19/09/17.
 */
@RestController
@RequestMapping(path="/albumsRest")
public class AlbumsRestController {
    private AlbumsRepository albumsRepository;

    public AlbumsRestController(AlbumsRepository albumsRepository) {
        this.albumsRepository = albumsRepository;
    }

    @PostMapping
    public void addAlbum(Album album) {
        albumsRepository.addAlbum(album);
    }

    @GetMapping("/{id}")
    public Album find(long id) {
        return albumsRepository.find(id);
    }

    @GetMapping
    public List<Album> getAlbums() {
        return albumsRepository.getAlbums();
    }

    @DeleteMapping("/{id}")
    public void deleteAlbum(long id) {
        Album album = albumsRepository.find(id);
        albumsRepository.deleteAlbum(album);
    }

}
