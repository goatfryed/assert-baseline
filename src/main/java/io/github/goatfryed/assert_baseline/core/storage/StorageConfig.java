package io.github.goatfryed.assert_baseline.core.storage;

import io.github.goatfryed.assert_baseline.core.storage.driver.StorageDriver;

import java.util.Objects;

public class StorageConfig {

    private final String key;
    private final StorageDriver baselineDriver;
    private final StorageDriver actualDriver;
    private ValueDescriptor baseline;
    private ValueDescriptor actual;

    public StorageConfig(
        String key,
        StorageDriver baselineDriver,
        StorageDriver actualDriver
    ) {
        this.key = key;
        this.baselineDriver = Objects.requireNonNull(baselineDriver, "baseline driver must not be null");
        this.actualDriver = Objects.requireNonNull(actualDriver, "actual driver must not be null");
        baseline = new ValueDescriptor();
        actual = new ValueDescriptor();
    }

    public String getKey() {
        return key;
    }

    public StorageDriver getBaselineDriver() {
        return baselineDriver;
    }

    public StorageDriver getActualDriver() {
        return actualDriver;
    }

    public ValueDescriptor getBaseline() {
        return baseline;
    }

    public StorageConfig setBaseline(ValueDescriptor baseline) {
        this.baseline = Objects.requireNonNull(baseline, "baseline descriptor must not be null");
        return this;
    }

    public ValueDescriptor getActual() {
        return actual;
    }

    public StorageConfig setActual(ValueDescriptor actual) {
        this.actual = Objects.requireNonNull(actual, "actual descriptor must not be null");
        return this;
    }
}
