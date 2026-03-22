package com.lenewblack.wholesale;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class TestUtils {

    private TestUtils() {}

    public static String loadFixture(String name) {
        String path = "/fixtures/" + name;
        try (InputStream is = TestUtils.class.getResourceAsStream(path)) {
            if (is == null) throw new IllegalArgumentException("Fixture not found: " + path);
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load fixture: " + path, e);
        }
    }
}
