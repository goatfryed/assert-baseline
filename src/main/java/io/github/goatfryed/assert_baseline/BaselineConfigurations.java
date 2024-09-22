package io.github.goatfryed.assert_baseline;

import io.github.goatfryed.assert_baseline.core.Configurer;
import io.github.goatfryed.assert_baseline.core.storage.StorageFactory;
import io.github.goatfryed.assert_baseline.core.storage.location.FixedRootPath;
import io.github.goatfryed.assert_baseline.core.storage.location.FixedValuePath;

public interface BaselineConfigurations {

    static Configurer<StorageFactory> commonRootPath(String rootPath) {
        return storageFactory -> storageFactory
            .baselineLocation(strategy -> strategy.withRootPathStrategy(new FixedRootPath(rootPath)))
            .actualLocation(strategy -> strategy.withRootPathStrategy(new FixedRootPath(rootPath)))
        ;
    }

    static Configurer<StorageFactory> actualPath(String actualPath) {
        return storageFactory -> storageFactory
            .actualLocation(strategy -> strategy.withValuePathStrategy(new FixedValuePath(actualPath)));
    }

    static Configurer<StorageFactory> baselinePath(String baselinePath) {
        return storageFactory -> storageFactory
            .baselineLocation(strategy -> strategy.withValuePathStrategy(new FixedValuePath(baselinePath)));
    }

}
