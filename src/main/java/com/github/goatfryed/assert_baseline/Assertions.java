package com.github.goatfryed.assert_baseline;

import com.github.goatfryed.assert_baseline.json.JsonBaselineAssertion;
import com.github.goatfryed.assert_baseline.text.TextBaselineAssertion;
import com.github.goatfryed.assert_baseline.xml.XmlBaselineAssertion;

public class Assertions {

    public static JsonBaselineAssertion assertThatJson(String string) {
        return new JsonBaselineAssertion(new SerializableSubject(string));
    }

    public static XmlBaselineAssertion assertThatXml(String string) {
        return new XmlBaselineAssertion(new SerializableSubject(string));
    }

    public static TextBaselineAssertion assertThatText(String string) {
        return new TextBaselineAssertion(new SerializableSubject(string));
    }
}
