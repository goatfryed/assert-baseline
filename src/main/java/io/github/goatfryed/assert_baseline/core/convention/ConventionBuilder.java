package io.github.goatfryed.assert_baseline.core.convention;

import io.github.goatfryed.assert_baseline.core.BaselineContextFactory;
import io.github.goatfryed.assert_baseline.core.Configurer;
import io.github.goatfryed.assert_baseline.core.storage.StorageFactory;
import io.github.goatfryed.assert_baseline.core.storage.driver.FileSystemDriver;

import static io.github.goatfryed.assert_baseline.core.convention.Conventions.*;

public class ConventionBuilder {

    private Configurer<StorageFactory> storageConfigurer = Configurer.noOp();

    public static ConventionBuilder builderWithDefaults() {
        return new ConventionBuilder()
            .usingStorage(config -> config
                .withBaselineDriver(new FileSystemDriver())
                .withBaselineLocationStrategy(recommendBaselineLocationStrategy())
                .withActualDriver(new FileSystemDriver())
                .withActualLocationStrategy(recommendActualLocationStrategy())
            );
    }

    public ConventionBuilder usingStorage(Configurer<StorageFactory> configurer) {
        storageConfigurer = storageConfigurer.and(configurer);
        return this;
    }

    public BaselineConvention build() {
        return new ConventionImpl(this::createBaselineContextFactory);
    }

    private BaselineContextFactory createBaselineContextFactory() {
        return new BaselineContextFactory()
            .usingStorage(storageConfigurer);
    }
}
