package io.github.goatfryed.assert_baseline.core.storage;

import io.github.goatfryed.assert_baseline.core.Configurer;
import io.github.goatfryed.assert_baseline.core.storage.driver.StorageDriver;
import io.github.goatfryed.assert_baseline.core.storage.location.ConfigurableLocationStrategy;

import java.util.Objects;
import java.util.function.Function;

public class StorageFactory {

    private StorageDriver baselineDriver;
    private StorageDriver actualDriver;

    private Function<StorageConfig, ValueDescriptor> baselineLocationStrategy;
    private Function<StorageConfig, ValueDescriptor> actualLocationStrategy;

    public StorageFactory withBaselineDriver(StorageDriver baselineDriver) {
        this.baselineDriver = Objects.requireNonNull(baselineDriver, "storage driver must not be null");
        return this;
    }

    public StorageFactory withActualDriver(StorageDriver actualDriver) {
        this.actualDriver = Objects.requireNonNull(actualDriver, "storage driver must not be null");
        return this;
    }

    public StorageFactory withBaselineLocationStrategy(Function<StorageConfig, ValueDescriptor> baselineLocationStrategy) {
        this.baselineLocationStrategy = Objects.requireNonNull(baselineLocationStrategy,"location strategy must not be null");
        return this;
    }

    public StorageFactory withActualLocationStrategy(Function<StorageConfig, ValueDescriptor> actualLocationStrategy) {
        this.actualLocationStrategy = Objects.requireNonNull(actualLocationStrategy, "location strategy must not be null");
        return this;
    }

    public StorageFactory baselineLocation(Configurer<ConfigurableLocationStrategy> configurer) {
        if (!(baselineLocationStrategy instanceof ConfigurableLocationStrategy) ) {
            throw new IllegalStateException(
                "The updater shorthand can only be used, if a %s is configured as baseline location strategy"
                    .formatted(ConfigurableLocationStrategy.class.getSimpleName())
            );
        }
        this.baselineLocationStrategy = configurer.apply((ConfigurableLocationStrategy) baselineLocationStrategy);
        return this;
    }

    public StorageFactory actualLocation(Configurer<ConfigurableLocationStrategy> configurer) {
        if (!(actualLocationStrategy instanceof ConfigurableLocationStrategy) ) {
            throw new IllegalStateException(
                "The updater shorthand can only be used, if a %s is configured as actual location strategy"
                    .formatted(ConfigurableLocationStrategy.class.getSimpleName())
            );
        }
        this.actualLocationStrategy = configurer.apply((ConfigurableLocationStrategy) actualLocationStrategy);
        return this;
    }

    public StorageConfig createConfig(String requestedKey) {

        Objects.requireNonNull(baselineDriver, "baseline storage driver must not be null");
        Objects.requireNonNull(actualDriver, "actual storage driver must not be null");
        Objects.requireNonNull(baselineLocationStrategy,"baseline location strategy must not be null");
        Objects.requireNonNull(actualLocationStrategy, "actual location strategy must not be null");

        var config = new StorageConfig(requestedKey, baselineDriver, actualDriver);
        config.setBaseline(baselineLocationStrategy.apply(config));
        config.setActual(actualLocationStrategy.apply(config));

        return config;
    }
}
