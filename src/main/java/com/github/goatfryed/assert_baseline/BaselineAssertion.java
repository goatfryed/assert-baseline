package com.github.goatfryed.assert_baseline;

public interface BaselineAssertion<SELF> {
    SELF isEqualToBaseline(String baselinePath);
}
