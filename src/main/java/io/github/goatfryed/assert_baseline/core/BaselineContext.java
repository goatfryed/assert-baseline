package io.github.goatfryed.assert_baseline.core;

import io.github.goatfryed.assert_baseline.core.storage.StoredValue;
import org.apache.commons.io.IOUtils;
import org.assertj.core.description.Description;
import org.assertj.core.description.JoinDescription;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BaselineContext {

    @NotNull
    private final StoredValue actual;
    @NotNull
    private final StoredValue baseline;
    @Nullable
    private String requestedKey;

    public BaselineContext(
        @Nullable String requestedKey,
        @NotNull StoredValue baseline,
        @NotNull StoredValue actual
    ) {
        this.requestedKey = requestedKey;
        this.actual = actual;
        this.baseline = baseline;

        actual  .setName("actual   :");
        baseline.setName("baseline :");
    }

    public @Nullable String getRequestedKey() {
        return requestedKey;
    }

    public BaselineContext setRequestedKey(@Nullable String requestedKey) {
        this.requestedKey = requestedKey;
        return this;
    }

    public @NotNull StoredValue getActual() {
        return actual;
    }

    public @NotNull OutputStream getActualOutputStream() {
        return actual.getOutputStream();
    }

    public @NotNull StoredValue getBaseline() {
        return baseline;
    }

    public @NotNull InputStream getBaselineInputStream() {
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

    public @NotNull Description asDescription() {
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