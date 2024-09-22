package io.github.goatfryed.assert_baseline.core.storage.location;

import io.github.goatfryed.assert_baseline.core.storage.StorageConfig;

import java.nio.file.Path;
import java.util.function.Function;

public class FixedValuePath implements Function<StorageConfig, Path> {

    private final Path path;

    public FixedValuePath(String path) {
        this.path = Path.of(path);
    }

    @Override
    public Path apply(StorageConfig storageConfig) {
        return path;
    }
}
