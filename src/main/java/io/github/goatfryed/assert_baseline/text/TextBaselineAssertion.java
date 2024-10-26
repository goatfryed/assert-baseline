package io.github.goatfryed.assert_baseline.text;

import io.github.goatfryed.assert_baseline.core.AbstractBaselineAssertion;
import io.github.goatfryed.assert_baseline.core.BaselineAssertionAdapter;
import io.github.goatfryed.assert_baseline.core.BaselineContext;
import io.github.goatfryed.assert_baseline.SerializableSubject;
import org.assertj.core.api.AbstractStringAssert;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class TextBaselineAssertion extends AbstractBaselineAssertion<TextBaselineAssertion> implements BaselineAssertionAdapter {

    private final SerializableSubject subject;

    public TextBaselineAssertion(SerializableSubject subject) {
        super(subject.actual(), TextBaselineAssertion.class);
        this.subject = subject;
    }

    public TextBaselineAssertion textSatisfies(Consumer<AbstractStringAssert<?>> assertion) {
            assertion.accept(getStringAssert());
            return myself;
    }

    @Override
    protected @NotNull BaselineAssertionAdapter getAssertionAdapter() {
        return this;
    }

    @Override
    public void writeActual(BaselineContext.ActualOutput actualOutput, BaselineContext context) {
        subject.writeTo(actualOutput::outputStream);
    }

    @Override
    public void assertEquals(BaselineContext.BaselineInput baselineInput, BaselineContext context) {
        getStringAssert()
            .describedAs(context.asDescription())
            .isEqualTo(baselineInput.readContentAsString());
    }

    private AbstractStringAssert<?> getStringAssert() {
        return assertThat(subject.serialized());
    }
}
