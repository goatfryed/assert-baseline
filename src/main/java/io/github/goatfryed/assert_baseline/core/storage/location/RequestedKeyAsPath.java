package io.github.goatfryed.assert_baseline.core.storage.location;

import io.github.goatfryed.assert_baseline.core.storage.StorageConfig;

import java.nio.file.Path;
import java.util.function.Function;

public class RequestedKeyAsPath implements Function<StorageConfig, Path> {
    @Override
    public Path apply(StorageConfig storageConfig) {
        return Path.of(storageConfig.getKey());
    }
}
