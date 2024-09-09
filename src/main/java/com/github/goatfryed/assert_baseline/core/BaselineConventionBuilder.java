package com.github.goatfryed.assert_baseline.core;

import com.github.goatfryed.assert_baseline.BaselineConvention;
import com.github.goatfryed.assert_baseline.core.storage.BaselineSiblingStrategy;
import com.github.goatfryed.assert_baseline.core.storage.FileLocatorStrategy;
import com.github.goatfryed.assert_baseline.core.storage.FixedPathStrategy;
import com.github.goatfryed.assert_baseline.core.storage.StoredValue;

import java.nio.file.Path;
import java.util.function.Function;

public class BaselineConventionBuilder {

    private Function<BaselineContextFactory, Path> actualPathStrategy;
    private Function<BaselineContextFactory, StoredValue>  baselineValueStrategy;
    private Function<BaselineContextFactory, StoredValue> actualValueStrategy;

    public static BaselineConventionBuilder createStandard() {
        return new BaselineConventionBuilder()
            .locateBaselines()
            .actualNameWithInfix(StandardConventionSupport.STANDARD_ACTUAL_INFIX)
            .actualCreatedAsSibling()
        ;
    }

    private BaselineConventionBuilder actualNameEqualsBaseline() {
        actualPathStrategy = BaselineContextFactory::getBaselinePath;
        return this;
    }

    private BaselineConventionBuilder actualNameWithInfix(String infix) {
        actualPathStrategy = StandardConventionSupport.siblingPath(infix);
        return this;
    }

    public BaselineConventionBuilder baselinesInPath(String baselineResourceRoot) {
        baselineValueStrategy = new FixedPathStrategy(Path.of(baselineResourceRoot));
        return this;
    }

    public BaselineConventionBuilder locateBaselines() {
        return locateBaselines(StandardConventionSupport.standardBaselineResourceRoots());
    }

    public BaselineConventionBuilder locateBaselines(String[] baselineResourceRoots) {
        baselineValueStrategy = new FileLocatorStrategy(baselineResourceRoots);
        return this;
    }

    public BaselineConventionBuilder withBaselineValueStrategy(Function<BaselineContextFactory, StoredValue> baselineValueStrategy) {
        this.baselineValueStrategy = baselineValueStrategy;
        return this;
    }

    public BaselineConventionBuilder actualInPath(String actualResourceRoot) {
        actualValueStrategy = new FixedPathStrategy(Path.of(actualResourceRoot));
        return this;
    }

    public BaselineConventionBuilder actualCreatedAsSibling() {
        return actualCreatedAsSibling(StandardConventionSupport.guessFallbackBaselineResourceRoot());
    }

    public BaselineConventionBuilder actualCreatedAsSibling(Path rootOnMissingBaseline) {
        var fallback = new FixedPathStrategy(rootOnMissingBaseline);
        actualValueStrategy = new BaselineSiblingStrategy(fallback);
        return this;
    }

    public BaselineConventionBuilder withActualValueStrategy(Function<BaselineContextFactory, StoredValue> actualValueStrategy) {
        this.actualValueStrategy = actualValueStrategy;
        return this;
    }

    public BaselineConvention build() {
        return new BaselineConventionImpl(
            actualPathStrategy,
            baselineValueStrategy,
            actualValueStrategy
        );
    }
}
