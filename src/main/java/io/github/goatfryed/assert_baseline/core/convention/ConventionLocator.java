package io.github.goatfryed.assert_baseline.core.convention;

import io.github.goatfryed.assert_baseline.core.convention.presets.StandardConventionProvider;

import java.util.ServiceLoader;

public class ConventionLocator {

    private ConventionLocator() {}

    private static final BaselineConvention convention;

    static {
        convention = ServiceLoader.load(BaselineConventionProvider.class).findFirst()
            .orElseGet(StandardConventionProvider::new)
            .getConvention();
    }

    public static BaselineConvention getConvention() {
        return convention;
    }
}
