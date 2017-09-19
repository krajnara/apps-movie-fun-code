package org.superbiz.moviefun.albumsapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestOperations;

import java.util.List;

/**
 * Created by 103209 on 19/09/17.
 */
public class AlbumsClient {
    private static final Logger logger = LoggerFactory.getLogger(AlbumsClient.class);

    public String albumsUrl;
    public RestOperations restOperations;

    private AlbumFixtures albumFixtures;

    private static ParameterizedTypeReference<List<AlbumInfo>> albumListType = new ParameterizedTypeReference<List<AlbumInfo>>() {
    };

    public AlbumsClient(String albumsUrl, RestOperations restOperations, AlbumFixtures albumFixtures) {
        this.albumsUrl = albumsUrl;
        this.restOperations = restOperations;
        this.albumFixtures = albumFixtures;
    }

    public void setup() {
        for (AlbumInfo album : albumFixtures.load()) {
            addAlbum(album);
        }
    }

    public void addAlbum(AlbumInfo album) {
        restOperations.postForEntity(albumsUrl, album, null);
    }

    public AlbumInfo find(long id) {
        return restOperations.getForEntity(albumsUrl + "/" + id, AlbumInfo.class).getBody();
    }

    public List<AlbumInfo> getAlbums() {
        return restOperations.exchange(albumsUrl, HttpMethod.GET, null, albumListType).getBody();
    }

    public void deleteAlbum(AlbumInfo album) {
        restOperations.delete(albumsUrl + "/" + album.getId());
    }

//    public void updateAlbum(AlbumInfo album) {
//        entityManager.merge(album);
//    }

}
