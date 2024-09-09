package com.github.goatfryed.assert_baseline;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static com.github.goatfryed.assert_baseline.BaselineAssertions.assertThatText;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test that Baseline Conventions can be controlled via SPI of {@link BaselineConvention} Interface
 */
class ConventionServiceInjectionTest {

    @BeforeEach
    public void prepare() {
        //noinspection ResultOfMethodCallIgnored // delete if exists
        TestConvention.FIXED_ACTUAL_PATH.toFile().delete();
    }

    @Test
    public void itUsesTestConventionToDetermineFiles() {

        assertThatText("Chuck Norris")
            .isEqualToBaseline("i-am-ignored");

        assertTrue(
            TestConvention.FIXED_ACTUAL_PATH.toFile().exists(),
            "Expected actual file was not generated"
        );
    }
}