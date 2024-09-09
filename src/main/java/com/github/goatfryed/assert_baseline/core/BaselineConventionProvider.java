package com.github.goatfryed.assert_baseline.core;

import com.github.goatfryed.assert_baseline.BaselineConvention;

import java.util.ServiceLoader;

public class BaselineConventionProvider {

    private BaselineConventionProvider() {}

    private static final BaselineConvention convention;

    static {
        convention = ServiceLoader.load(BaselineConvention.class).findFirst()
            .orElseGet(() -> BaselineConventionBuilder.createStandard().build());
    }

    public static BaselineConvention getConvention() {
        return convention;
    }
}
