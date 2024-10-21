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
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class BaselineContext {

    @NotNull
    private final StoredValue actual;
    @NotNull
    private final StoredValue baseline;
    @NotNull final Map<Options, Object> options;

    public BaselineContext(
        @NotNull StoredValue baseline,
        @NotNull StoredValue actual,
        @NotNull Map<Options, Object> options
    ) {
        this.actual = actual;
        this.baseline = baseline;
        this.options = options;

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
        // not using .getOrDefault, because explicit null should be treated as false as well
        boolean forceBaselineUpdate = Optional.ofNullable(
                (Boolean) options.get(Options.StandardOptions.FORCE_BASELINE_UPDATE)
            ).orElse(false);

        if (forceBaselineUpdate) {
            adapter.writeActual(new ActualOutput(getBaseline()), this);
            System.err.println("WARNING: Forced baseline creation or update of %s. Skipping check.".formatted(getBaseline().asDescription()));
            return;
        }

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