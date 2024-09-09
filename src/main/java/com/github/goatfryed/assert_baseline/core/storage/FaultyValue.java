package com.github.goatfryed.assert_baseline.core.storage;

import org.assertj.core.description.Description;
import org.assertj.core.description.TextDescription;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Supplier;

public class FaultyValue implements StoredValue {

    private final Description description;
    private final Supplier<AssertionError> error;

    public FaultyValue(String description, Supplier<AssertionError> error) {
        this(new TextDescription(description), error);
    }

    public FaultyValue(Description description, Supplier<AssertionError> error) {
        this.description = description;
        this.error = error;
    }

    @Override
    public Description asDescription() {
        return description;
    }

    @Override
    public InputStream getInputStream() {
        throw error.get();
    }

    @Override
    public OutputStream getOutputStream() {
        throw error.get();
    }
}
