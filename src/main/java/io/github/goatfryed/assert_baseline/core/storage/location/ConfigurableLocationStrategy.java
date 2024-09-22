package io.github.goatfryed.assert_baseline.core.storage.location;

import io.github.goatfryed.assert_baseline.core.storage.StorageConfig;
import io.github.goatfryed.assert_baseline.core.storage.ValueDescriptor;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ConfigurableLocationStrategy implements Function<StorageConfig, ValueDescriptor> {

    private final Function<StorageConfig, Path> valuePathStrategy;
    private final BiFunction<ValueDescriptor, StorageConfig, Path> rootPathStrategy;

    public ConfigurableLocationStrategy(
        Function<StorageConfig, Path> valuePathStrategy,
        BiFunction<ValueDescriptor, StorageConfig, Path> rootPathStrategy
    ) {
        this.valuePathStrategy = Objects.requireNonNull(valuePathStrategy, "value path strategy cannot be null");
        this.rootPathStrategy = Objects.requireNonNull(rootPathStrategy, "root path strategy cannot be null");
    }

    public ConfigurableLocationStrategy withValuePathStrategy(Function<StorageConfig, Path> valuePathStrategy) {
        return new ConfigurableLocationStrategy(valuePathStrategy, rootPathStrategy);
    }

    public ConfigurableLocationStrategy withRootPathStrategy(BiFunction<ValueDescriptor, StorageConfig, Path> rootPathStrategy) {
        return new ConfigurableLocationStrategy(valuePathStrategy, rootPathStrategy);
    }

    @Override
    public ValueDescriptor apply(StorageConfig storageConfig) {
        var valueDescriptor = new ValueDescriptor();
        valueDescriptor.setValuePath(valuePathStrategy.apply(storageConfig));
        valueDescriptor.setContextPath(rootPathStrategy.apply(valueDescriptor, storageConfig));
        return valueDescriptor;
    }
}
