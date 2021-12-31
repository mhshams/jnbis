package org.jnbis;

import java.net.URL;

public class FileUtils {

    public static String absolute(String name) {
        URL url = FileUtils.class.getClassLoader().getResource(name);
        if (url == null) {
            throw new RuntimeException("unexpected error: Null URL");
        }
        return url.getFile();
    }
}
