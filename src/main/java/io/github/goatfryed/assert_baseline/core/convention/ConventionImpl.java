package io.github.goatfryed.assert_baseline.core.convention;

import io.github.goatfryed.assert_baseline.core.BaselineContextFactory;

import java.util.function.Supplier;

public class ConventionImpl implements BaselineConvention {

    private final Supplier<BaselineContextFactory> baselineContextFactory;

    public ConventionImpl(
        Supplier<BaselineContextFactory> baselineContextFactory
    ) {
        this.baselineContextFactory = baselineContextFactory;
    }

    @Override
    public BaselineContextFactory getBaselineContextFactory() {
        return baselineContextFactory.get();
    }
}
