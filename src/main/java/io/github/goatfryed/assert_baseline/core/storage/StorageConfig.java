package io.github.goatfryed.assert_baseline.core.storage;

import io.github.goatfryed.assert_baseline.core.storage.driver.StorageDriver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class StorageConfig {

    @Nullable
    private final String key;
    @NotNull
    private final StorageDriver baselineDriver;
    @NotNull
    private final StorageDriver actualDriver;
    @NotNull
    private ValueDescriptor baseline;
    @NotNull
    private ValueDescriptor actual;

    public StorageConfig(
        @Nullable String key,
        @NotNull StorageDriver baselineDriver,
        @NotNull StorageDriver actualDriver
    ) {
        this.key = key;
        this.baselineDriver = Objects.requireNonNull(baselineDriver, "baseline driver must not be null");
        this.actualDriver = Objects.requireNonNull(actualDriver, "actual driver must not be null");
        baseline = new ValueDescriptor();
        actual = new ValueDescriptor();
    }

    public @Nullable String getKey() {
        return key;
    }

    public @NotNull StorageDriver getBaselineDriver() {
        return baselineDriver;
    }

    public @NotNull StorageDriver getActualDriver() {
        return actualDriver;
    }

    public @NotNull ValueDescriptor getBaseline() {
        return baseline;
    }

    public StorageConfig setBaseline(@NotNull ValueDescriptor baseline) {
        this.baseline = Objects.requireNonNull(baseline, "baseline descriptor must not be null");
        return this;
    }

    public @NotNull ValueDescriptor getActual() {
        return actual;
    }

    public StorageConfig setActual(@NotNull ValueDescriptor actual) {
        this.actual = Objects.requireNonNull(actual, "actual descriptor must not be null");
        return this;
    }
}
