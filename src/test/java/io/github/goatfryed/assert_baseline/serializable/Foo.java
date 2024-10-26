package io.github.goatfryed.assert_baseline.serializable;

import java.io.Serializable;

public record Foo(String foo, Bar fooBar) implements Serializable {

    public record Bar(String prop1, String prop2) implements Serializable { }
}
