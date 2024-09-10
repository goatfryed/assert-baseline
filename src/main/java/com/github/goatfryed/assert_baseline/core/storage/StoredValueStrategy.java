package com.github.goatfryed.assert_baseline.core.storage;

import java.util.function.Function;

@FunctionalInterface
public interface StoredValueStrategy extends Function<StorageConfig, StoredValue> { }
