package com.github.goatfryed.assert_baseline.core.storage;

import org.assertj.core.description.Description;
import org.assertj.core.description.TextDescription;

import java.io.*;
import java.nio.file.Path;

public class FileValue implements StoredValue, AsPath {

    private final File file;

    public FileValue(File file) {
        this.file = file;
    }

    @Override
    public Description asDescription() {
        return new TextDescription(file.getAbsolutePath());
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }

    @Override
    public OutputStream getOutputStream() throws FileNotFoundException {
        //noinspection ResultOfMethodCallIgnored
        file.getParentFile().mkdirs();
        return new FileOutputStream(file);
    }

    @Override
    public Path asPath() {
        return file.toPath();
    }
}
