package io.github.goatfryed.assert_baseline.core.convention;

import io.github.goatfryed.assert_baseline.core.storage.FileValue;
import io.github.goatfryed.assert_baseline.core.storage.StorageConfig;
import io.github.goatfryed.assert_baseline.core.storage.StoredValue;
import io.github.goatfryed.assert_baseline.core.storage.StoredValueStrategy;

import java.nio.file.Path;
import java.util.function.Function;

public class FileStorageStrategy implements StoredValueStrategy {

    private final Function<StorageConfig, Path> pathAccessor;

    public FileStorageStrategy(Function<StorageConfig, Path> pathAccessor) {
        this.pathAccessor = pathAccessor;
    }

    @Override
    public StoredValue apply(StorageConfig config) {
        var resourcePath = config.getContextPath().resolve(pathAccessor.apply(config));
        return new FileValue(resourcePath.toFile());
    }
}
