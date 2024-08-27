package com.github.goatfryed.assert_baseline;

import com.github.goatfryed.assert_baseline.convention.AbstractConvention;

import java.nio.file.Path;

/**
 * This convention is for test only and uses fixed files for actual and baseline content.
 */
public class TestConvention extends AbstractConvention {

    public static Path FIXED_ACTUAL_PATH = Path.of("src/testSpi/resources/produced-content.txt");
    public static Path FIXED_BASELINE_PATH = Path.of("src/testSpi/resources/expected-content.txt");

    @Override
    public String resolveActualPath(String requestedBaselinePath) {
        return FIXED_ACTUAL_PATH.toString();
    }

    @Override
    public String resolveBaselinePath(String requestedBaselinePath) {
        return FIXED_BASELINE_PATH.toString();
    }
}
