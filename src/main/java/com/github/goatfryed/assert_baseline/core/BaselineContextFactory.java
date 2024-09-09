package com.github.goatfryed.assert_baseline.core;

import com.github.goatfryed.assert_baseline.core.storage.StoredValue;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Function;

public class BaselineContextFactory {

    private Function<BaselineContextFactory, Path> actualPathSupplier;
    private Function<BaselineContextFactory, Path> baselinePathSupplier;
    private Function<BaselineContextFactory, StoredValue> actualFactory;
    private Function<BaselineContextFactory, StoredValue> baselineFactory;
    private StoredValue actual;
    private StoredValue baseline;

    /**
     * The path of the baseline output is relative to your baseline resource root
     */
    public Path getBaselinePath() {
        return Objects.requireNonNull(baselinePathSupplier.apply(this), "baseline name supplier returned null");
    }

    /**
     * @see #getBaselinePath()
     */
    public BaselineContextFactory setBaselinePathSupplier(Function<BaselineContextFactory, Path> baselinePathSupplier) {
        this.baselinePathSupplier = baselinePathSupplier;
        return this;
    }

    /**
     * The path of the actual output is relative to your baseline resource root
     */
    public Path getActualPath() {
        return Objects.requireNonNull(actualPathSupplier.apply(this), "actual name supplier returned null");
    }

    public BaselineContextFactory setActualPathSupplier(Function<BaselineContextFactory, Path> actualPathSupplier) {
        this.actualPathSupplier = actualPathSupplier;
        return this;
    }

    /**
     * A factory function to create the {@link StoredValue} interface to your actual output.
     * Usually, a factory wants to use {@link #getActualPath()} to create the interface.
     */
    public Function<BaselineContextFactory, StoredValue> getActualFactory() {
        return actualFactory;
    }


    /**
     * @see #getActualFactory()
     */
    public BaselineContextFactory setActualFactory(Function<BaselineContextFactory, StoredValue> actualFactory) {
        this.actualFactory = actualFactory;
        return this;
    }


    /**
     * A factory function to create the {@link StoredValue} interface to your baseline.
     * Usually, a factory wants to use {@link #getBaselinePath()} to create the interface.
     */
    public Function<BaselineContextFactory, StoredValue> getBaselineFactory() {
        return baselineFactory;
    }

    /**
     * @see #getBaselineFactory()
     */
    public BaselineContextFactory setBaselineFactory(Function<BaselineContextFactory, StoredValue> baselineFactory) {
        this.baselineFactory = baselineFactory;
        return this;
    }

    public StoredValue getActual() {
        if (actual == null) {
            if (actualFactory != null) {
                actual = actualFactory.apply(this);
                Objects.requireNonNull(actual, "actual factory returned null");
            }
        }
        return actual;
    }

    public BaselineContextFactory setActual(StoredValue actual) {
        this.actual = actual;
        return this;
    }

    public StoredValue getBaseline() {
        if (baseline == null) {
            if (baselineFactory != null) {
                baseline = baselineFactory.apply(this);
                Objects.requireNonNull(baseline, "baseline factory returned null");
            }
        }
        return baseline;
    }

    public BaselineContextFactory setBaseline(StoredValue baseline) {
        this.baseline = baseline;
        return this;
    }

    public BaselineContext build() {
        var baseline = getBaseline();
        if (baseline == null) {
            throw new IllegalStateException("neither baseline nor baseline factory was defined");
        }
        var actual = getActual();
        if (actual == null) {
            throw new IllegalStateException("neither actual nor actual factory was defined");
        }
        return new BaselineContext(baseline, actual);
    }

    public BaselineContextFactory setBaselinePath(Path baselinePath) {
        setBaselinePathSupplier(ctx -> baselinePath);
        return this;
    }
}
