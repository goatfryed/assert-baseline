package com.github.goatfryed.assert_baseline.core.storage;

import com.github.goatfryed.assert_baseline.core.BaselineContextFactory;

import java.util.function.Function;

public class BaselineSiblingStrategy implements Function<BaselineContextFactory, StoredValue> {

    private final Function<BaselineContextFactory, StoredValue> fallback;

    public BaselineSiblingStrategy(Function<BaselineContextFactory, StoredValue> fallback) {
        this.fallback = fallback;
    }

    @Override
    public StoredValue apply(BaselineContextFactory contextFactory) {

        var baseline = contextFactory.getBaseline();
        var actualPath = contextFactory.getActualPath();

        if (baseline instanceof AsPath) {
            var asPath = ((AsPath) baseline).asPath();

            var siblingPath = asPath.resolveSibling(actualPath.getFileName());
            if (siblingPath.equals(asPath)) {
                throw new IllegalStateException(
                    "Actual output can only be created as sibling of baseline, if the filename is different." +
                    "\nActual and baseline both named " + actualPath.getFileName()
                );
            }
            return new FileValue(siblingPath.toFile());
        }

        return fallback.apply(contextFactory);
    }

}
