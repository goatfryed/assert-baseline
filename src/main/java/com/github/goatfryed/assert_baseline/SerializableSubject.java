package com.github.goatfryed.assert_baseline;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public record SerializableSubject(String actual) {

    public String serialized() {
        return actual;
    }

    public void writeTo(File file) {
        try {
            FileUtils.write(file, serialized(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AssertionError("couldn't write actual to file", e);
        }
    }
}
