package com.github.goatfryed.assert_baseline.core;

import com.github.goatfryed.assert_baseline.core.storage.*;

import java.util.function.Function;

public class BaselineContextFactory {

    private StorageConfigurer storageConfigurator;
    private StoredValueStrategy actualFactory;
    private StoredValueStrategy baselineFactory;

    // nullable
    private StorageConfigValidator configValidator;

    public BaselineContextFactory storage(Function<StorageConfigurer, StorageConfigurer> configureStorage) {
        var prev = storageConfigurator != null ? storageConfigurator : new ConfigurableStorageConfigurer();
        this.storageConfigurator = configureStorage.apply(prev);
        return this;
    }

    public BaselineContextFactory setStorageConfigurator(StorageConfigurer storageConfigurator) {
        this.storageConfigurator = storageConfigurator;
        return this;
    }

    public BaselineContextFactory setStorageConfig(StorageConfig storageConfig) {
        setStorageConfigurator(_key -> storageConfig);
        return this;
    }

    /**
     * A factory function to create the {@link StoredValue} interface to your actual output.
     */
    public BaselineContextFactory setActualFactory(StoredValueStrategy actualFactory) {
        this.actualFactory = actualFactory;
        return this;
    }

    /**
     * A factory function to create the {@link StoredValue} interface to your baseline.
     */
    public BaselineContextFactory setBaselineFactory(StoredValueStrategy baselineFactory) {
        this.baselineFactory = baselineFactory;
        return this;
    }

    public BaselineContext build(String requestedKey) {
        var config = storageConfigurator.createConfig(requestedKey);

        if (configValidator != null) {
            configValidator.validate(config);
        }

        var baseline = baselineFactory.apply(config);
        if (baseline == null) {
            throw new IllegalStateException("neither baseline nor baseline factory was defined");
        }

        var actual = actualFactory.apply(config);
        if (actual == null) {
            throw new IllegalStateException("neither actual nor actual factory was defined");
        }

        return new BaselineContext(baseline, actual);
    }

    public BaselineContextFactory setValidator(StorageConfigValidator configValidator) {
        this.configValidator = configValidator;
        return this;
    }
}
