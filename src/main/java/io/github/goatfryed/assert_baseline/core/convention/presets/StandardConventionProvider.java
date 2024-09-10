package io.github.goatfryed.assert_baseline.core.convention.presets;

import io.github.goatfryed.assert_baseline.core.convention.BaselineConvention;
import io.github.goatfryed.assert_baseline.core.convention.BaselineConventionProvider;
import io.github.goatfryed.assert_baseline.core.convention.ConventionSupport;

import static io.github.goatfryed.assert_baseline.core.convention.ConventionBuilder.builderWithDefaults;
import static io.github.goatfryed.assert_baseline.core.convention.ConventionSupport.locateBaselinesAndInferContextPath;

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
