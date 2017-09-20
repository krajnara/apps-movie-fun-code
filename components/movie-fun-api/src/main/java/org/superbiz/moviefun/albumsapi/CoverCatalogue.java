package org.superbiz.moviefun.albumsapi;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.superbiz.moviefun.blobstore.Blob;
import org.superbiz.moviefun.blobstore.BlobStore;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Optional;

import static java.lang.String.format;

/**
 * Created by 103209 on 20/09/17.
 */
@Component
public class CoverCatalogue {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BlobStore blobStore;

    public CoverCatalogue(BlobStore blobStore){
        this.blobStore = blobStore;

    }

    public String uploadCover(Long albumId, MultipartFile uploadedFile) {
        logger.debug("Uploading cover for album with id {}", albumId);

        if (uploadedFile.getSize() > 0) {
            try {
                tryToUploadCover(albumId, uploadedFile);

            } catch (IOException e) {
                logger.warn("Error while uploading album cover", e);
            }
        }

        return format("redirect:/albums/%d", albumId);
    }


    private void tryToUploadCover(Long albumId, MultipartFile uploadedFile) throws IOException {
        Blob coverBlob = new Blob(
                getCoverBlobName(albumId),
                uploadedFile.getInputStream(),
                uploadedFile.getContentType()
        );

        blobStore.put(coverBlob);
    }

    private Blob buildDefaultCoverBlob() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream input = classLoader.getResourceAsStream("default-cover.jpg");

        return new Blob("default-cover", input, MediaType.IMAGE_JPEG_VALUE);
    }

    private String getCoverBlobName(long albumId) {
        return format("covers/%d", albumId);
    }

    @HystrixCommand(fallbackMethod = "getDefaultCover")
    public Blob getCover(long albumId) throws IOException, URISyntaxException {
        Optional<Blob> maybeCoverBlob = blobStore.get(getCoverBlobName(albumId));
        Blob coverBlob = maybeCoverBlob.orElseGet(this::buildDefaultCoverBlob);

        return coverBlob;
    }

    public Blob getDefaultCover(long albumId) throws IOException, URISyntaxException {
        return buildDefaultCoverBlob();

    }

}
