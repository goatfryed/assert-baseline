package io.github.goatfryed.assert_baseline.text;

import io.github.goatfryed.assert_baseline.core.AbstractBaselineAssertion;
import io.github.goatfryed.assert_baseline.core.BaselineContext;
import io.github.goatfryed.assert_baseline.SerializableSubject;
import net.javacrumbs.jsonunit.core.Configuration;
import org.assertj.core.api.AbstractStringAssert;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class TextBaselineAssertion extends AbstractBaselineAssertion<TextBaselineAssertion> {
    private final SerializableSubject subject;
    private Function<Configuration, Configuration> comparatorConfigurer = Function.identity();

    public TextBaselineAssertion(SerializableSubject subject) {
        super(subject.actual(), TextBaselineAssertion.class);
        this.subject = subject;
    }

    public TextBaselineAssertion textSatisfies(Consumer<AbstractStringAssert<?>> assertion) {
            assertion.accept(getStringAssert());
            return myself;
    }

    @Override
    protected void saveActual(BaselineContext context) {
        subject.writeTo(context::getActualOutputStream);
    }

    @Override
    protected void verifyIsEqualToBaseline(BaselineContext context) {
        getStringAssert()
            .describedAs(context.asDescription())
            .isEqualTo(context.getBaselineAsString());
    }

    private AbstractStringAssert<?> getStringAssert() {
        return assertThat(subject.serialized());
    }
}
