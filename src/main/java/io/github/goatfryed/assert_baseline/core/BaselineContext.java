package io.github.goatfryed.assert_baseline.core;

import io.github.goatfryed.assert_baseline.core.storage.StoredValue;
import org.apache.commons.io.IOUtils;
import org.assertj.core.description.Description;
import org.assertj.core.description.JoinDescription;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BaselineContext {

    private final StoredValue actual;
    private final StoredValue baseline;
    private String requestedKey;

    public BaselineContext(StoredValue baseline, StoredValue actual) {
        this(null, baseline, actual);
    }

    public BaselineContext(
        String requestedKey,
        StoredValue baseline,
        StoredValue actual
    ) {
        this.requestedKey = requestedKey;
        this.actual = actual;
        this.baseline = baseline;

        actual  .setName("actual   :");
        baseline.setName("baseline :");
    }

    public String getRequestedKey() {
        return requestedKey;
    }

    public BaselineContext setRequestedKey(String requestedKey) {
        this.requestedKey = requestedKey;
        return this;
    }

    public StoredValue getActual() {
        return actual;
    }

    public OutputStream getActualOutputStream() {
        return actual.getOutputStream();
    }

    public StoredValue getBaseline() {
        return baseline;
    }

    public InputStream getBaselineInputStream() {
        try {
            return baseline.getInputStream();
        } catch (AssertionError e) {
            if (e.getCause() instanceof FileNotFoundException) {
                throw new AssertionError(
                    "No baseline found. Consider saving %s as baseline.".formatted(
                        actual.asDescription()
                    ), e
                );
            }
            throw e;
        }
    }

    public Description asDescription() {
        return new JoinDescription(
            "Baseline set","",
            Arrays.asList(
                baseline.asDescription(),
                actual.asDescription()
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