package org.superbiz.moviefun.albums;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 103209 on 13/09/17.
 */
public class Blob {
    public final String name;
    public final InputStream inputStream;
    public final String contentType;

    public Blob(String name, InputStream inputStream, String contentType) {
        this.name = name;
        this.inputStream = inputStream;
        this.contentType = contentType;
    }
    public byte[] getBytes() throws IOException
    {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();)
        {
            byte[] buffer = new byte[0xFFFF];

            for (int len; (len = inputStream.read(buffer)) != -1;)
                os.write(buffer, 0, len);

            os.flush();

            return os.toByteArray();
        }
    }
}