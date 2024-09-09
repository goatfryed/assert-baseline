package com.github.goatfryed.assert_baseline.json;

import com.github.goatfryed.assert_baseline.core.AbstractBaselineAssertion;
import com.github.goatfryed.assert_baseline.core.BaselineContext;
import com.github.goatfryed.assert_baseline.SerializableSubject;
import net.javacrumbs.jsonunit.assertj.JsonAssert;
import net.javacrumbs.jsonunit.core.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class JsonBaselineAssertion extends AbstractBaselineAssertion<JsonBaselineAssertion> {
    private final SerializableSubject subject;
    private Function<Configuration, Configuration> comparatorConfigurer = Function.identity();

    public JsonBaselineAssertion(SerializableSubject subject) {
        super(subject.actual(), JsonBaselineAssertion.class);
        this.subject = subject;
    }

    /**
     * Allows for simple interop with <a href="https://github.com/lukas-krecan/JsonUnit?tab=readme-ov-file#assertj-integration">JsonUnit</a>
     * to
     */
    public JsonBaselineAssertion jsonSatisfies(Consumer<JsonAssert> assertion) {
            assertion.accept(getJsonAssert());
            return myself;
    }

    /**
     * Use the given custom comparison configuration for baseline comparison.<br>
     * The custom comparator is bound to assertion instance, meaning that if a new assertion instance is created,
     * the default comparison strategy will be used.<br>
     * We use <a href="https://github.com/lukas-krecan/JsonUnit?tab=readme-ov-file#assertj-integration">JsonUnit</a>
     * under the hood. This method gives direct access to modify the JsonUnit assertion via the configuration api
     */
    public JsonBaselineAssertion usingJsonComparator(
        Function<Configuration,Configuration> comparatorConfigurer
    ) {
        this.comparatorConfigurer = comparatorConfigurer;

        return myself;
    }

    @Override
    protected void saveActual(BaselineContext context) {
        subject.writeTo(context::getActualOutputStream);
    }

    @Override
    protected void verifyIsEqualToBaseline(BaselineContext context) {
        getJsonAssert()
            .describedAs(context.asDescription())
            .isEqualTo(context.getBaselineAsString());
    }

    private JsonAssert.ConfigurableJsonAssert getJsonAssert() {
        return assertThatJson(subject.serialized())
            .withConfiguration(comparatorConfigurer);
    }
}
