package com.github.goatfryed.assert_baseline;

import com.github.goatfryed.assert_baseline.json.JsonBaselineAssertion;

public class Assertions {

    public static JsonBaselineAssertion assertThatJson(String string) {
        return new JsonBaselineAssertion(new SerializableSubject(string));
    }
}
