package io.github.goatfryed.assert_baseline.core;

import io.github.goatfryed.assert_baseline.core.storage.StoredValue;
import org.apache.commons.io.IOUtils;
import org.assertj.core.description.Description;
import org.assertj.core.description.JoinDescription;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.Supplier;

public class BaselineContext {

    @NotNull
    private final StoredValue actual;
    @NotNull
    private final StoredValue baseline;

    public BaselineContext(
        @NotNull StoredValue baseline,
        @NotNull StoredValue actual
    ) {
        this.actual = actual;
        this.baseline = baseline;

        actual.setName("actual   :");
        baseline.setName("baseline :");
    }

    public @NotNull StoredValue getActual() {
        return actual;
    }


    public @NotNull StoredValue getBaseline() {
        return baseline;
    }

    public @NotNull Description asDescription() {
        return new JoinDescription(
            "Baseline set", "",
            Arrays.asList(
                baseline.asDescription(),
                actual.asDescription()
            )
        );
    }

    public void assertWithAdapter(BaselineAssertionAdapter adapter) {
        var actualOutput = new ActualOutput(getActual());
        adapter.writeActual(actualOutput, this);

        var baselineInput = new BaselineInput(
            getBaseline(),
            () -> "Consider saving %s as baseline.".formatted(
                actual.asDescription()
            )
        );
        adapter.assertEquals(baselineInput, this);
    }

    public static class BaselineInput {

        private final StoredValue baseline;
        private final Supplier<String> notFoundSuggestionSupplier;

        public BaselineInput(
            StoredValue baseline,
            Supplier<String> notFoundSuggestionSupplier
        ) {
            this.baseline = baseline;
            this.notFoundSuggestionSupplier = notFoundSuggestionSupplier;
        }

        public @NotNull InputStream getInputStream() {
            try {
                return baseline.getInputStream();
            } catch (FileNotFoundException e) {
                throw new AssertionError(
                    "No baseline found. %s".formatted(
                        notFoundSuggestionSupplier.get()
                    ), e
                );
            } catch (IOException e) {
                throw new AssertionError(
                    "Failed to create input stream for %s.".formatted(
                        baseline.asDescription()
                    ), e
                );
            }
        }

        public String readContentAsString() {
            try (var input = getInputStream()) {
                return IOUtils.toString(input, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new AssertionError(
                    "Failed to read baseline as string\n%s".formatted(baseline.asDescription()),
                    e
                );
            }
        }
    }

    public static class ActualOutput {

        private final StoredValue actual;

        public ActualOutput(StoredValue actual) {
            this.actual = actual;
        }

        public @NotNull OutputStream outputStream() {
            try {
                return actual.getOutputStream();
            } catch (IOException e) {
                throw new AssertionError("failed to create output stream for " + actual.asDescription(), e);
            }
        }
    }
}