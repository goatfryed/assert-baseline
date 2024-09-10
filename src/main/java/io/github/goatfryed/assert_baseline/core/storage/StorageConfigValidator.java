package io.github.goatfryed.assert_baseline.core.storage;

public interface StorageConfigValidator {
    /**
     * validate the given storage config
     * @throws IllegalStateException, if the given config is invalid
     */
    void validate(StorageConfig storageConfig);
}
