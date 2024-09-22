package io.github.goatfryed.assert_baseline.core.storage.location;

import io.github.goatfryed.assert_baseline.core.storage.StorageConfig;
import io.github.goatfryed.assert_baseline.core.storage.ValueDescriptor;

import java.nio.file.Path;
import java.util.function.BiFunction;

public class ActualMatchesBaselineContext implements BiFunction<ValueDescriptor, StorageConfig, Path> {

    private final Path fallbackPath;

    public ActualMatchesBaselineContext(String fallbackPath) {
        this.fallbackPath = Path.of(fallbackPath);
    }

    @Override
    public Path apply(ValueDescriptor descriptor, StorageConfig storageConfig) {
        if (storageConfig.getBaselineDriver().hasValue(storageConfig.getBaseline())) {
            return storageConfig.getBaseline().getContextPath();
        }
        return fallbackPath;
    }
}
