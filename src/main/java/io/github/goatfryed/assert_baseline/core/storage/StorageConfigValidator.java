package io.github.goatfryed.assert_baseline.core.storage;

public interface StorageConfigValidator {
    /**
     * validate the given storage config
     * @throws IllegalStateException on invalid config given
     */
    void validate(StorageConfig storageConfig);
}
