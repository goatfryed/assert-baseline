package com.github.goatfryed.assert_baseline.core;

import com.github.goatfryed.assert_baseline.core.storage.StoredValue;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.description.Description;
import org.assertj.core.description.JoinDescription;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class BaselineContext {

    private final StoredValue actual;
    private final StoredValue baseline;

    public BaselineContext(StoredValue baseline, StoredValue actual) {
        this.actual = actual;
        this.baseline = baseline;
    }

    public StoredValue getActual() {
        return actual;
    }

    public OutputStream getActualOutputStream() {
        try {
            return actual.getOutputStream();
        } catch (IOException e) {
            throw new AssertionError("failed to create output stream for actual", e);
        }
    }

    public StoredValue getBaseline() {
        return baseline;
    }

    public InputStream getBaselineInputStream() {
        try {
            return baseline.getInputStream();
        } catch (FileNotFoundException e) {
            throw new AssertionError(
                "No baseline found. Consider saving %s as baseline.".formatted(
                    actual.asDescription()
                ),
                e
            );
        } catch (IOException e) {
            throw new AssertionError("failed to create input stream for baseline", e);
        }
    }

    public Description asDescription() {
        return new JoinDescription(
            "Baseline set","",
            List.of(
                new JoinDescription("Actual   :","", Collections.singletonList(actual.asDescription())),
                new JoinDescription("Baseline :","", Collections.singletonList(baseline.asDescription()))
            )
        );
    }

    public String getBaselineAsString() {
        try (var input = getBaselineInputStream()) {
            return IOUtils.toString(input, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AssertionError(
                "Failed to read baseline as string\n%s".formatted(getActual().asDescription()),
                e
            );
        }
    }
}