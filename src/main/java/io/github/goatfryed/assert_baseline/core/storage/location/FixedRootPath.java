package io.github.goatfryed.assert_baseline.core.storage.location;

import io.github.goatfryed.assert_baseline.core.storage.StorageConfig;
import io.github.goatfryed.assert_baseline.core.storage.ValueDescriptor;

import java.nio.file.Path;
import java.util.function.BiFunction;

public class FixedRootPath implements BiFunction<ValueDescriptor, StorageConfig, Path> {

    private final Path path;

    public FixedRootPath(String path) {
        this.path = Path.of(path);
    }

    @Override
    public Path apply(ValueDescriptor descriptor, StorageConfig storageConfig) {
        return path;
    }
}
