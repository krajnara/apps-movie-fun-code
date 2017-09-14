package org.superbiz.moviefun.albums;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by 103209 on 13/09/17.
 */
public interface BlobStore {

    void put(Blob blob) throws IOException;

    Optional<Blob> get(String name) throws IOException;
}