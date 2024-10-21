package io.github.goatfryed.assert_baseline.core;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.github.goatfryed.assert_baseline.BaselineAssertions.assertThatText;
import static io.github.goatfryed.assert_baseline.BaselineConfigurations.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaselineOverwriteTest {

    @Test
    public void itCanForceBaselineWriteFromCli() throws IOException {
        var actual = "src/test/resources/CliOptionsTest.itCanForceBaselineWrite.actually.actual.test";
        var baseline = "src/test/resources/CliOptionsTest.itCanForceBaselineWrite.actual.test";

        var baselinePath = Path.of(baseline);
        Files.writeString(baselinePath, "false");

        System.setProperty(
            Options.StandardOptions.FORCE_BASELINE_UPDATE.cliOption().get().systemProperty(),
            ""
        );

        try {
            assertThatText("true")
                .usingStorage(
                    commonRootPath("")
                        .and(baselinePath(baseline))
                        .and(actualPath(actual))
                ).isEqualToBaseline("null");
        } finally {
            System.clearProperty(
                Options.StandardOptions.FORCE_BASELINE_UPDATE.cliOption().get().systemProperty()
            );
        }

        assertEquals(
            Files.readString(baselinePath),
            "true"
        );

        Files.writeString(baselinePath, "false");
    }
}
