package io.github.goatfryed.assert_baseline.core.convention.presets;

import io.github.goatfryed.assert_baseline.core.convention.BaselineConvention;
import io.github.goatfryed.assert_baseline.core.convention.BaselineConventionProvider;

import static io.github.goatfryed.assert_baseline.core.convention.ConventionBuilder.builderWithDefaults;

public class StandardConventionProvider implements BaselineConventionProvider {
    @Override
    public BaselineConvention getConvention() {
        return builderWithDefaults()
            .build()
        ;
    }
}
