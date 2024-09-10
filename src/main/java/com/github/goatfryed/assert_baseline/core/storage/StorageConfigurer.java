package com.github.goatfryed.assert_baseline.core.storage;

public interface StorageConfigurer {
    StorageConfig createConfig(String requestedKey);
}
