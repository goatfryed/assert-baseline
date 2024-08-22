package com.github.goatfryed.assert_baseline.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathNodes;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ContextAwareXPathNodeMatcher implements Predicate<Node> {

    private final ConcurrentHashMap<Document, Set<Node>> cache = new ConcurrentHashMap<>();
    private final XPathExpression expression ;

    public ContextAwareXPathNodeMatcher(String xpath) {
        try {
            expression = XPathFactory.newInstance().newXPath().compile(xpath);
        } catch (XPathExpressionException e) {
            throw new AssertionError("invalid xpath", e);
        }
    }

    @Override
    public boolean test(Node node) {
        var nodes = cache.computeIfAbsent(
            node.getOwnerDocument(),
            k -> {
                try {
                    return StreamSupport.stream(
                        expression.evaluateExpression(k, XPathNodes.class).spliterator(),
                        false
                    ).collect(Collectors.toSet());
                } catch (XPathExpressionException e) {
                    throw new AssertionError("failed to select nodes matching xpath", e);
                }
            });
        return nodes.contains(node);
    }
}
