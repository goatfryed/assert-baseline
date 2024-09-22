package io.github.goatfryed.assert_baseline.core.storage;


import io.github.goatfryed.assert_baseline.core.storage.driver.StorageDriver;
import org.assertj.core.description.Description;
import org.assertj.core.description.TextDescription;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StoredValue {

    @NotNull
    private final ValueDescriptor descriptor;
    @NotNull
    private final StorageDriver driver;
    @NotNull
    private String name = "stored value";

    public StoredValue(
        @NotNull StorageDriver driver,
        @NotNull ValueDescriptor descriptor
    ) {
        this.driver = driver;
        this.descriptor = descriptor;
    }

    public Description asDescription() {
        var innerDescription = driver != null ?
            driver.asDescription(descriptor).toString()
            : "[NO DRIVER SET]";
        return new TextDescription(name + " " + innerDescription);
    }

    public ValueDescriptor getValueDescriptor() {
        return descriptor;
    }

    public StoredValue setName(@NotNull String name) {
        this.name = name;
        return this;
    }

    public InputStream getInputStream() {
        try {
            return driver.getInputStream(descriptor);
        } catch (IOException e) {
            throw new AssertionError("failed to create input stream for " + asDescription(), e);
        }
    }

    public OutputStream getOutputStream() {
        try {
            return driver.getOutputStream(descriptor);
        } catch (IOException e) {
            throw new AssertionError("failed to create output stream for " + asDescription(), e);
        }
    }

    public Object getDriverDescriptor() {
        return driver.getDriverDescriptor(descriptor);
    }
}
