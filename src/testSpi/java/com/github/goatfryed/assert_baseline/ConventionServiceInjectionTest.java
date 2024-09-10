package com.github.goatfryed.assert_baseline;

import com.github.goatfryed.assert_baseline.core.convention.BaselineConvention;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.nio.file.Files;

import static com.github.goatfryed.assert_baseline.BaselineAssertions.assertThatText;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test that Baseline Conventions can be controlled via SPI of {@link BaselineConvention} Interface
 */
class ConventionServiceInjectionTest {

    @BeforeEach
    public void prepare() throws IOException {
        Files.deleteIfExists(TestConventionProvider.ACTUAL_FILE);
    }

    @Test
    public void itUsesTestConventionToDetermineFiles() {

        assertThatText("Chuck Norris")
            .isEqualToBaseline("i-am-ignored");

        assertTrue(
            Files.exists(TestConventionProvider.ACTUAL_FILE),
            "Expected actual file was not generated"
        );
    }
}