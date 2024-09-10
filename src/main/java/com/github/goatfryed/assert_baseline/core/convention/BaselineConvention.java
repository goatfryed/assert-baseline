package com.github.goatfryed.assert_baseline.core.convention;

import com.github.goatfryed.assert_baseline.core.BaselineContext;
import com.github.goatfryed.assert_baseline.core.BaselineContextFactory;

public interface BaselineConvention {

    BaselineContextFactory getBaselineContextFactory();

    default BaselineContext createContext(String baseline) {
        return getBaselineContextFactory()
            .build(baseline);
    }
}
