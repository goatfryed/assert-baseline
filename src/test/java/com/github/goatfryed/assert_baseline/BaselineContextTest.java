package com.github.goatfryed.assert_baseline;

import com.github.goatfryed.assert_baseline.core.BaselineConventionBuilder;
import com.github.goatfryed.assert_baseline.core.BaselineConventionImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;


class BaselineContextTest {

    @Test
    public void itRecommendsCopyActionOnMissingBaseline() {
        var defaultConvention = BaselineConventionBuilder.createStandard().build();
        var context = defaultConvention.createContext("baseline.txt");

        assertThatCode(context::getBaselineInputStream)
            .hasMessageContaining("No baseline found")
            .hasMessageMatching("(.|\\n)*Consider saving .* as .*(.|\\n)*");
    }

}