package org.superbiz.moviefun.albums;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.tika.Tika;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

/**
 * Created by 103209 on 13/09/17.
 */
public class S3BlobStore implements BlobStore {
    private final Tika tika = new Tika();
    private AmazonS3Client s3Client;
    private String s3BucketName;

    public S3BlobStore(AmazonS3Client s3Client, String s3BucketName) {

        this.s3BucketName = s3BucketName;
        this.s3Client = s3Client;
    }

    @Override
    public void put(Blob blob) throws IOException {
        ObjectMetadata md = new ObjectMetadata();
        md.setContentType(IMAGE_JPEG_VALUE);
        s3Client.putObject(s3BucketName, blob.name, blob.inputStream, md);
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {
        Optional<Blob> result;
        S3Object obj;

        try {
            obj = s3Client.getObject(s3BucketName, name);
        }
        catch(Exception e) {
            e.printStackTrace(System.err);
            return Optional.empty();
        }

        S3ObjectInputStream stream = obj.getObjectContent();
        if(stream != null) {
            result = Optional.of(new Blob(
               name, stream, obj.getObjectMetadata().getContentType()
            ));
        }
        else {
            result = Optional.empty();
        }

        return result;
    }
}
