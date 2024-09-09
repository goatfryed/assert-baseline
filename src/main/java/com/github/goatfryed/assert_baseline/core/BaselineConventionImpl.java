package com.github.goatfryed.assert_baseline.core;

import com.github.goatfryed.assert_baseline.BaselineConvention;
import com.github.goatfryed.assert_baseline.core.storage.*;

import java.nio.file.Path;
import java.util.function.Function;

public class BaselineConventionImpl implements BaselineConvention {

    private final Function<BaselineContextFactory, Path>  actualPathStrategy;
    private final Function<BaselineContextFactory, StoredValue>  defaultBaselineValueStrategy;
    private final Function<BaselineContextFactory, StoredValue>  defaultActualValueStrategy;

    public BaselineConventionImpl(Function<BaselineContextFactory, Path> actualPathStrategy, Function<BaselineContextFactory, StoredValue> defaultBaselineValueStrategy, Function<BaselineContextFactory, StoredValue> defaultActualValueStrategy) {
        this.actualPathStrategy = actualPathStrategy;
        this.defaultBaselineValueStrategy = defaultBaselineValueStrategy;
        this.defaultActualValueStrategy = defaultActualValueStrategy;
    }

    @Override
    public BaselineContextFactory getBaselineContextFactory() {
        return new BaselineContextFactory()
            .setBaselinePathSupplier(this::throwBaselineNotDefinedError)
            .setActualPathSupplier(actualPathStrategy)
            .setBaselineFactory(defaultBaselineValueStrategy)
            .setActualFactory(defaultActualValueStrategy)
        ;
    }

    private Path throwBaselineNotDefinedError(BaselineContextFactory contextFactory) {
        throw new IllegalStateException("Baseline is not defined. Consider using assertThat[...].isEqualToBaseline(\"myBaseline.format\")");
    }
}
