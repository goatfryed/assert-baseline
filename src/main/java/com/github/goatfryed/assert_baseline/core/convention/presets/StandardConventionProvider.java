package com.github.goatfryed.assert_baseline.core.convention.presets;

import com.github.goatfryed.assert_baseline.core.convention.BaselineConvention;
import com.github.goatfryed.assert_baseline.core.convention.BaselineConventionProvider;
import com.github.goatfryed.assert_baseline.core.convention.ConventionSupport;

import static com.github.goatfryed.assert_baseline.core.convention.ConventionBuilder.builderWithDefaults;
import static com.github.goatfryed.assert_baseline.core.convention.ConventionSupport.locateBaselinesAndInferContextPath;

public class StandardConventionProvider implements BaselineConventionProvider {
    @Override
    public BaselineConvention getConvention() {
        return builderWithDefaults()
            .storing(locateBaselinesAndInferContextPath())
            .withConfigValidator(ConventionSupport::validateDifferentPaths)
            .build()
        ;
    }
}
