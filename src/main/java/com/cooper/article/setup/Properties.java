package com.cooper.article.setup;

import java.io.IOException;
import java.util.Optional;

/**
 * Simple class to encapsulate loading and reading
 * of application properties
 */
public class Properties {

    private final static String PROP_FILE = "application.properties";
    private final static java.util.Properties props = new java.util.Properties();
    private static boolean loaded;

    static {

        try(var inputStream = Properties.class.getClassLoader().getResourceAsStream(PROP_FILE)) {
            props.load(inputStream);
            loaded = true;
        } catch (IOException e) {
            // log
            e.printStackTrace();
            loaded = false;
        }
    }

    public Optional<String> getProperty(String key){

        var str = props.getProperty(key);

        if (str == null)
            return Optional.empty();

        return Optional.of(str);
    }

    public static boolean isLoaded() {
        return loaded;
    }
}
