package com.github.goatfryed.assert_baseline;

import com.github.goatfryed.assert_baseline.convention.SimpleConvention;

import java.util.ServiceLoader;

public class BaselineUtils {

    private BaselineUtils() {}

    private static final Convention convention;

    static {
        convention = ServiceLoader.load(Convention.class).findFirst()
            .orElseGet(SimpleConvention::getInstance);
    }

    public static Convention getConvention() {
        return convention;
    }
}
