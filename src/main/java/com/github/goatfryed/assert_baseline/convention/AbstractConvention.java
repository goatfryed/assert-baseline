package com.github.goatfryed.assert_baseline.convention;

import com.github.goatfryed.assert_baseline.BaselineContext;
import com.github.goatfryed.assert_baseline.Convention;

import java.io.File;

public abstract class AbstractConvention implements Convention {

    @Override
    public BaselineContext createContext(String baseline) {
        return new BaselineContext(
            new File(resolveActualPath(baseline)),
            new File(resolveBaselinePath(baseline))
        );
    }

    public abstract String resolveActualPath(String requestedBaselinePath);

    public String resolveBaselinePath(String requestedBaselinePath) {
        return requestedBaselinePath;
    }
}
