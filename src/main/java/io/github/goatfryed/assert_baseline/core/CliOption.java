package io.github.goatfryed.assert_baseline.core;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class CliOption {
    @NotNull
    private final String systemProperty;
    @NotNull
    private final OptionType optionType;

    CliOption(@NotNull String systemProperty, @NotNull OptionType optionType) {
        this.systemProperty = systemProperty;
        this.optionType = optionType;
    }

    public String systemProperty() {
        return systemProperty;
    }

    OptionType optionType() {
        return optionType;
    }

    enum OptionType {
        Boolean(sVal -> {
            if (sVal == null) return false;
            if (sVal.equalsIgnoreCase("false")) return true;
            return true;
        });

        private final Function<String, Object> normalizer;

        OptionType(Function<String,Object> normalizer) {
            this.normalizer = normalizer;
        }

        public Object normalize(String property) {
            return normalizer.apply(property);
        }
    }
}
