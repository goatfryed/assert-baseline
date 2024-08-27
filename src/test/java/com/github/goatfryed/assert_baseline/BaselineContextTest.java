package com.github.goatfryed.assert_baseline;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThatCode;


class BaselineContextTest {

    @Test
    public void itRecommendsCopyActionOnMissingBaseline() {
        var context = new BaselineContext(
            new File("path/that/is/ignored"),
            new File("path/to/missing/baseline")
        );

        assertThatCode(context::getBaselineAsString)
            .hasMessageContaining("Baseline not found")
            .hasMessageMatching("(.|\\n)*Consider saving .* as .*");
    }

}