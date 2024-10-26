package io.github.goatfryed.assert_baseline.xml;

import io.github.goatfryed.assert_baseline.core.AbstractBaselineAssertion;
import io.github.goatfryed.assert_baseline.core.BaselineAssertionAdapter;
import io.github.goatfryed.assert_baseline.core.BaselineContext;
import io.github.goatfryed.assert_baseline.SerializableSubject;
import org.jetbrains.annotations.NotNull;
import org.xmlunit.assertj.CompareAssert;
import org.xmlunit.assertj.XmlAssert;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.xmlunit.assertj.XmlAssert.assertThat;

public class XmlBaselineAssertion extends AbstractBaselineAssertion<XmlBaselineAssertion,String> {

    private final SerializableSubject subject;
    private Function<CompareAssert, CompareAssert> comparatorConfigurer = Function.identity();

    public XmlBaselineAssertion(SerializableSubject subject) {
        super(subject.actual(), XmlBaselineAssertion.class);
        this.subject = subject;
    }

    /**
     * Allows for simple interop with <a href="https://github.com/lukas-krecan/JsonUnit?tab=readme-ov-file#assertj-integration">JsonUnit</a>
     * to
     */
    public XmlBaselineAssertion xmlSatisfies(Consumer<XmlAssert> assertion) {
            assertion.accept(getXmlAssert());
            return myself;
    }

    /**
     * Use the given custom comparison configuration for baseline comparison.<br>
     * The custom comparator is bound to assertion instance, meaning that if a new assertion instance is created,
     * the default comparison strategy will be used.<br>
     * We use <a href="https://github.com/lukas-krecan/JsonUnit?tab=readme-ov-file#assertj-integration">JsonUnit</a>
     * under the hood. This method gives direct access to modify the JsonUnit assertion via the configuration api
     */
    public XmlBaselineAssertion usingXmlComparator(
        Function<CompareAssert,CompareAssert> comparatorConfigurer
    ) {
        this.comparatorConfigurer = comparatorConfigurer;

        return myself;
    }

    @Override
    protected @NotNull BaselineAssertionAdapter getAssertionAdapter() {
        return new Adapter();
    }

    private XmlAssert getXmlAssert() {
        return assertThat(subject.serialized());
    }

    private class Adapter implements BaselineAssertionAdapter {

        @Override
        public void writeActual(BaselineContext.ActualOutput actualOutput, BaselineContext context) {
            subject.writeTo(actualOutput::outputStream);
        }

        @Override
        public void assertEquals(BaselineContext.BaselineInput baselineInput, BaselineContext context) {
            comparatorConfigurer.apply(
                    getXmlAssert().and(baselineInput.readContentAsString())
                )
                .describedAs(context.asDescription())
                .areSimilar();
        }
    }
}
