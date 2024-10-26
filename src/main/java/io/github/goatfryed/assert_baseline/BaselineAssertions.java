package io.github.goatfryed.assert_baseline;

import io.github.goatfryed.assert_baseline.json.JsonBaselineAssertion;
import io.github.goatfryed.assert_baseline.serializable.SerializableBaselineAssertion;
import io.github.goatfryed.assert_baseline.text.TextBaselineAssertion;
import io.github.goatfryed.assert_baseline.xml.XmlBaselineAssertion;

public class BaselineAssertions {

    public static JsonBaselineAssertion assertThatJson(String string) {
        return new JsonBaselineAssertion(new SerializableSubject(string));
    }

    public static TextBaselineAssertion assertThatText(String string) {
        return new TextBaselineAssertion(new SerializableSubject(string));
    }

    public static XmlBaselineAssertion assertThatXml(String string) {
        return new XmlBaselineAssertion(new SerializableSubject(string));
    }

    public static <ACTUAL> SerializableBaselineAssertion<ACTUAL> assertThatSerializable(ACTUAL actual) {
        return new SerializableBaselineAssertion<>(actual);
    }
}
