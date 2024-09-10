package com.github.goatfryed.assert_baseline.core.convention;

import com.github.goatfryed.assert_baseline.core.BaselineContextFactory;
import com.github.goatfryed.assert_baseline.core.storage.*;

import java.util.function.Function;

import static com.github.goatfryed.assert_baseline.core.convention.ConventionSupport.actualAsSibling;
import static com.github.goatfryed.assert_baseline.core.storage.StorageConfigUtils.baselineAsRequested;

public class ConventionBuilder {

    private ConfigurableStorageConfigurer storageConfigurer = new ConfigurableStorageConfigurer();
    private StoredValueStrategy baselineValueStrategy;
    private StoredValueStrategy actualValueStrategy;
    private StorageConfigValidator storageConfigValidator;

    public static ConventionBuilder builderWithDefaults() {
        return new ConventionBuilder()
            .storing(baselineAsRequested())
            .storing(actualAsSibling())
            .withActualValueStrategy(new FileStorageStrategy(StorageConfig::getActualPath))
            .withBaselineValueStrategy(new FileStorageStrategy(StorageConfig::getBaselinePath));
    }

    public ConventionBuilder storing(Function<ConfigurableStorageConfigurer,ConfigurableStorageConfigurer> configurer) {
        storageConfigurer = configurer.apply(storageConfigurer);
        return this;
    }

    public ConventionBuilder withBaselineValueStrategy(StoredValueStrategy baselineValueStrategy) {
        this.baselineValueStrategy = baselineValueStrategy;
        return this;
    }

    public ConventionBuilder withActualValueStrategy(StoredValueStrategy actualValueStrategy) {
        this.actualValueStrategy = actualValueStrategy;
        return this;
    }

    public BaselineConvention build() {
        return new ConventionImpl(this::createBaselineContextFactory);
    }

    private BaselineContextFactory createBaselineContextFactory() {
        var factory = new BaselineContextFactory()
            .setStorageConfigurator(storageConfigurer.copy())
            .setBaselineFactory(baselineValueStrategy)
            .setActualFactory(actualValueStrategy);
        if (storageConfigValidator != null) {
            factory.setValidator(storageConfigValidator);
        }
        return factory;
    }

    public ConventionBuilder withConfigValidator(StorageConfigValidator storageConfigValidator) {
        this.storageConfigValidator = storageConfigValidator;
        return this;
    }
}
