package io.github.goatfryed.assert_baseline.core;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface Options {
    enum StandardOptions implements Options {
        FORCE_BASELINE_UPDATE(
            new CliOption(
                "io.github.goatfryed.assert_baseline.forceBaselineUpdate",
                CliOption.OptionType.Boolean
            )
        );

        @Nullable
        private final CliOption cliOption;

        StandardOptions(@Nullable CliOption cliOption) {
            this.cliOption = cliOption;
        }

        public Optional<CliOption> cliOption() {
            return Optional.ofNullable(cliOption);
        }
    }
}
