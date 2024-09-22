package io.github.goatfryed.assert_baseline.core;

import io.github.goatfryed.assert_baseline.core.storage.*;

import java.util.Objects;

public class BaselineContextFactory {

    private StorageFactory storageFactory = new StorageFactory();

    public BaselineContextFactory usingStorage(Configurer<StorageFactory> storageConfigurer) {
        this.storageFactory = storageConfigurer.apply(storageFactory);
        return this;
    }

    public BaselineContext build(String requestedKey) {

        var storageConfig = storageFactory.createConfig(requestedKey);
        var baseline = storageConfig.getBaselineDriver().resolve(storageConfig.getBaseline());
        var actual = storageConfig.getActualDriver().resolve(storageConfig.getActual());

        validateDifferentPaths(baseline, actual);

        return new BaselineContext(baseline, actual);
    }

    private void validateDifferentPaths(StoredValue baseline, StoredValue actual) {
        var baselineDriverDescriptor = baseline.getDriverDescriptor();
        var actualDriverDescriptor = actual.getDriverDescriptor();
        if (Objects.equals(baselineDriverDescriptor, actualDriverDescriptor)) {
            throw new IllegalStateException(
                "Actual and baseline both resolve to " + actualDriverDescriptor
                    + "\nBoth should have different names, be placed in different paths or on different systems."
            );
        }
    }
}
