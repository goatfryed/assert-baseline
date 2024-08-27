package com.github.goatfryed.assert_baseline.text;

import com.github.goatfryed.assert_baseline.BaselineAssertion;
import com.github.goatfryed.assert_baseline.BaselineUtils;
import com.github.goatfryed.assert_baseline.SerializableSubject;
import net.javacrumbs.jsonunit.core.Configuration;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractStringAssert;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class TextBaselineAssertion
    extends AbstractAssert<TextBaselineAssertion, String>
    implements BaselineAssertion<TextBaselineAssertion>
{
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
    public TextBaselineAssertion isEqualToBaseline(String baselinePath) {
        var context = BaselineUtils.getConvention().createContext(baselinePath);
        subject.writeTo(context.getActual());

        getStringAssert()
            .describedAs(context.asDescription())
            .isEqualTo(context.getBaselineAsString());

        return myself;
    }

    private AbstractStringAssert<?> getStringAssert() {
        return assertThat(subject.serialized());
    }
}
