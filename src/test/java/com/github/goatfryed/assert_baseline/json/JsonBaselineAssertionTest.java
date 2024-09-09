package com.github.goatfryed.assert_baseline.json;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static com.github.goatfryed.assert_baseline.BaselineAssertions.assertThatJson;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonBaselineAssertionTest {

    public final static String BASE_PATH = "json/event.%s.json";
    public final static String ALTERED_PATH = BASE_PATH.formatted("altered");
    public final static String ACTUAL_PATH = BASE_PATH.formatted("actual");
    public final static String BASELINE_PATH = BASE_PATH.formatted("baseline");
    public final static Path RESOURCE_ROOT = Path.of("src/test/resources");
    public final static File ACTUAL_FILE = RESOURCE_ROOT.resolve(ACTUAL_PATH).toFile();
    public final static File ALTERED_FILE = RESOURCE_ROOT.resolve(ALTERED_PATH).toFile();
    public final static File BASELINE_FILE = RESOURCE_ROOT.resolve(BASELINE_PATH).toFile();

    @BeforeEach
    public void cleanup() {
        FileUtils.deleteQuietly(ACTUAL_FILE);
    }

    @Test
    public void itPassesSameContent() throws Exception {
        var jsonContent = FileUtils.readFileToString(BASELINE_FILE, StandardCharsets.UTF_8);

        assertThatJson(jsonContent)
            .isEqualToBaseline(BASELINE_PATH);
    }

    @Test
    public void itWritesActualToDisk() throws Exception {
        var jsonContent = FileUtils.readFileToString(BASELINE_FILE, StandardCharsets.UTF_8);

        assertFalse(ACTUAL_FILE.exists(), "actual file was present before testing. Is test cleanup not working?");
        assertThatJson(jsonContent)
            .isEqualToBaseline(BASELINE_PATH);
        assertTrue(ACTUAL_FILE.exists(), "Actual file not written to %s".formatted(ACTUAL_FILE.getAbsolutePath()));
    }

    @Test
    public void itFailsViolations() throws Exception {
        var jsonContent = FileUtils.readFileToString(ALTERED_FILE, StandardCharsets.UTF_8);

        assertThatCode(
                () -> assertThatJson(jsonContent)
                    .isEqualToBaseline(BASELINE_PATH)
            ).isInstanceOf(AssertionError.class)
            .hasMessageContaining("JSON documents are different");
    }

    @Test
    public void itPassesAllowedDifferences() throws Exception {
        var jsonContent = FileUtils.readFileToString(ALTERED_FILE, StandardCharsets.UTF_8);

        assertThatJson(jsonContent)
            .usingJsonComparator(diff -> diff
                .whenIgnoringPaths(
                    "$.event.id",
                    "$.event.timestamp"
                )
            ).isEqualToBaseline(BASELINE_PATH);
    }

    @Test
    public void itInteropsWithJsonUnit() throws Exception {
        var jsonContent = FileUtils.readFileToString(BASELINE_FILE, StandardCharsets.UTF_8);

        assertThatJson(jsonContent)
            .jsonSatisfies(json -> json
                .isObject()
                .node("event")
                .isNotNull()
            );
    }
}