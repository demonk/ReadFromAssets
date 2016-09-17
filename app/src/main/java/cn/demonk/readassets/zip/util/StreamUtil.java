package cn.demonk.readassets.zip.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by ligs on 11/16/16.
 */
public class StreamUtil {

    public static final void close(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
