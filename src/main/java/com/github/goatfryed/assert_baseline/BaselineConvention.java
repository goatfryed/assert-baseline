package com.github.goatfryed.assert_baseline;

import com.github.goatfryed.assert_baseline.core.BaselineContext;
import com.github.goatfryed.assert_baseline.core.BaselineContextFactory;

import java.nio.file.Path;

public interface BaselineConvention {

    BaselineContextFactory getBaselineContextFactory();

    default BaselineContext createContext(String baseline) {
        return getBaselineContextFactory()
            .setBaselinePath(Path.of(baseline))
            .build();
    }
}
