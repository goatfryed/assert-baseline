package io.github.goatfryed.assert_baseline;

import io.github.goatfryed.assert_baseline.core.convention.BaselineConvention;
import io.github.goatfryed.assert_baseline.core.convention.BaselineConventionProvider;

import java.nio.file.Path;

import static io.github.goatfryed.assert_baseline.core.convention.ConventionBuilder.builderWithDefaults;
import static io.github.goatfryed.assert_baseline.core.storage.StorageConfigUtils.*;

/**
 * This convention is for test only and uses fixed files for actual and baseline content.
 */
public class TestConventionProvider implements BaselineConventionProvider {

    public static final String CONTEXT_PATH_PART = "src/testSpi/resources";
    public static final String BASELINE_PATH_PART = "produced-content.txt";
    public static final String ACTUAL_PATH_PART = "produced-content.txt";
    public static final Path ACTUAL_FILE = Path.of(CONTEXT_PATH_PART, ACTUAL_PATH_PART);


    @Override
    public BaselineConvention getConvention() {
        return builderWithDefaults()
            .storing(inContextPath(CONTEXT_PATH_PART))
            .storing(baselineAs(BASELINE_PATH_PART))
            .storing(actualAs(ACTUAL_PATH_PART))
            .build();
    }
}
