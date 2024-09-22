package io.github.goatfryed.assert_baseline.core;

import java.util.function.UnaryOperator;

public interface Configurer<T> extends UnaryOperator<T> {

    default Configurer<T> and(Configurer<T> next) {
        return subject -> next.apply(apply(subject));
    };

    static <T> Configurer<T> noOp() {
        return it -> it;
    }
}
