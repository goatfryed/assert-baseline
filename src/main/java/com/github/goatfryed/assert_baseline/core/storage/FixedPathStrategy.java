package com.github.goatfryed.assert_baseline.core.storage;

import com.github.goatfryed.assert_baseline.core.BaselineContextFactory;

import java.nio.file.Path;
import java.util.function.Function;

public class FixedPathStrategy implements Function<BaselineContextFactory, StoredValue> {

    private final Path resourceRoot;

    public FixedPathStrategy(Path resourceRoot) {
        this.resourceRoot = resourceRoot;
    }

    @Override
    public StoredValue apply(BaselineContextFactory contextFactory) {
        var actualPath = contextFactory.getActualPath();
        return new FileValue(resourceRoot.resolve(actualPath).toFile());
    }
}
