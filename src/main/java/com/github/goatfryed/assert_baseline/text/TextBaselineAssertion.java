package com.github.goatfryed.assert_baseline.text;

import com.github.goatfryed.assert_baseline.BaselineAssertion;
import com.github.goatfryed.assert_baseline.Convention;
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

    /**
     * Allows for simple interop with <a href="https://github.com/lukas-krecan/JsonUnit?tab=readme-ov-file#assertj-integration">JsonUnit</a>
     * to
     */
    public TextBaselineAssertion textSatisfies(Consumer<AbstractStringAssert<?>> assertion) {
            assertion.accept(getStringAssert());
            return myself;
    }

    /**
     * Use the given custom comparison configuration for baseline comparison.<br>
     * The custom comparator is bound to assertion instance, meaning that if a new assertion instance is created,
     * the default comparison strategy will be used.<br>
     * We use <a href="https://github.com/lukas-krecan/JsonUnit?tab=readme-ov-file#assertj-integration">JsonUnit</a>
     * under the hood. This method gives direct access to modify the JsonUnit assertion via the configuration api
     */
    public TextBaselineAssertion usingStringComparator(
        Function<Configuration,Configuration> comparatorConfigurer
    ) {
        this.comparatorConfigurer = comparatorConfigurer;

        return myself;
    }

    @Override
    public TextBaselineAssertion isEqualToBaseline(String baselinePath) {
        var context = Convention.getInstance().createContext(baselinePath);
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
