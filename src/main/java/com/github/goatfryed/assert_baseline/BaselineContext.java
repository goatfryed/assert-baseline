package com.github.goatfryed.assert_baseline;

import org.apache.commons.io.FileUtils;
import org.assertj.core.description.Description;
import org.assertj.core.description.JoinDescription;
import org.assertj.core.description.TextDescription;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class BaselineContext {

    private final File actual;
    private final File baseline;

    public BaselineContext(File actual, File baseline) {
        this.actual = actual;
        this.baseline = baseline;
    }

    public File getActual() {
        return actual;
    }

    public String getBaselineAsString() {
        try {
            return FileUtils.readFileToString(baseline, StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            throw new AssertionError(
                "Baseline not found.\nConsider saving %s as %s".formatted(actual.getAbsolutePath(), baseline.getAbsolutePath()), e
            );
        } catch (IOException e) {
            throw new AssertionError("Failed to read baseline", e);
        }
    }

    public Description asDescription() {
        return new JoinDescription(
            "Baseline Set","",
            List.of(
                new TextDescription("Actual   : %s".formatted(actual.getAbsolutePath())),
                new TextDescription("Baseline : %s".formatted(baseline.getAbsolutePath()))
            )
        );
    }
}
