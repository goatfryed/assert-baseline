package io.github.goatfryed.assert_baseline.core.convention;

import io.github.goatfryed.assert_baseline.core.BaselineContext;

public interface BaselineContextValidator {
    /**
     * validate the given storage config
     * @throws IllegalStateException on invalid config given
     */
    void validate(BaselineContext storageConfig);
}
