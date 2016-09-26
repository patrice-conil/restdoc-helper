package com.orange.otml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Utilities.
 */
public class Utils {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    /**
     * Load a resource as an UTF-8 string.
     *
     * @param name name of the resource
     * @return the content of the resource
     */
     public static String loadResource(String name) {
        try {
            URL url = Utils.class.getClassLoader().getResource(name);
            if (url == null) {
                return "";
            }
            return new String(Files.readAllBytes(Paths.get(url.toURI())), "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }


}
