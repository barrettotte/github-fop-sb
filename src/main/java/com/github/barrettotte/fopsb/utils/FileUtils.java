package com.github.barrettotte.fopsb.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class FileUtils {

    // read file in src/main/resources into string
    public static String getResourceFileAsString(final String fileName) throws IOException {
        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (final InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) {
                return null;
            }
            try (final InputStreamReader isr = new InputStreamReader(is);
                final BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

    // get file from src/main/resources
    public static File getResourceFile(final String fileName) throws IOException, URISyntaxException {
        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        return Paths.get(classLoader.getResource(fileName).toURI()).toFile();
    }
}
