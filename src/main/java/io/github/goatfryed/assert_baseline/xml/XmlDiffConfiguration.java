package io.github.goatfryed.assert_baseline.xml;

import org.w3c.dom.Node;
import org.xmlunit.diff.NodeFilters;

import java.util.function.Predicate;

public class XmlDiffConfiguration {

    public static Predicate<Node> standard() {
        return asPredicate(NodeFilters.Default);
    }

    public static Predicate<Node> acceptAll() {
        return asPredicate(NodeFilters.AcceptAll);
    }

    public static Predicate<Node> ignoringXPath(String xpath) {
        return new ContextAwareXPathNodeMatcher(xpath).negate();
    }


    private static Predicate<Node>  asPredicate(org.xmlunit.util.Predicate<Node> external) {
        return external::test;
    }

    private XmlDiffConfiguration() { }
}
