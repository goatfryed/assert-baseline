package io.github.goatfryed.assert_baseline;

import io.github.goatfryed.assert_baseline.core.convention.presets.StandardConventionProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;


class BaselineContextTest {

    @Test
    public void itRecommendsCopyActionOnMissingBaseline() {
        var defaultConvention = new StandardConventionProvider().getConvention();
        var context = defaultConvention.createContext("baseline.txt");

        assertThatCode(context::getBaselineInputStream)
            .hasMessageContaining("No baseline found")
            .hasMessageMatching("(.|\\n)*Consider saving .* as .*(.|\\n)*");
    }

}