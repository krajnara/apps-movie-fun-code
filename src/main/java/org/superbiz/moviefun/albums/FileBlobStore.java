package org.superbiz.moviefun.albums;

import org.apache.tika.Tika;

import java.io.*;
import java.util.Optional;

/**
 * Created by 103209 on 13/09/17.
 */
public class FileBlobStore implements BlobStore {
    private final Tika tika = new Tika();

    @Override
    public void put(Blob blob) throws IOException {
        File targetFile = new File(blob.name);
        targetFile.delete();
        targetFile.getParentFile().mkdirs();
        targetFile.createNewFile();

        try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
            outputStream.write(blob.getBytes());
        }

    }

    @Override
    public Optional<Blob> get(String name) throws IOException {

        File file = new File(name);
        if (!file.exists()) {
            return Optional.empty();
        }

        return Optional.of(new Blob(
                name,
                new FileInputStream(file),
                tika.detect(file)
        ));

    }

}