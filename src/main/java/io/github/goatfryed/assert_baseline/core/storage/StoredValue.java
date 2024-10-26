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
        var innerDescription = driver.asDescription(descriptor).toString();
        return new TextDescription(name + " " + innerDescription);
    }

    public ValueDescriptor getValueDescriptor() {
        return descriptor;
    }

    public StoredValue setName(@NotNull String name) {
        this.name = name;
        return this;
    }

    public InputStream getInputStream() throws IOException {
        return driver.getInputStream(descriptor);
    }

    public OutputStream getOutputStream() throws IOException {
        return driver.getOutputStream(descriptor);
    }

    public Object getDriverDescriptor() {
        return driver.getDriverDescriptor(descriptor);
    }
}
