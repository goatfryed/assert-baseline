package io.github.goatfryed.assert_baseline.core;

import io.github.goatfryed.assert_baseline.BaselineAssertion;
import io.github.goatfryed.assert_baseline.core.convention.ConventionLocator;
import org.assertj.core.api.AbstractAssert;

import java.util.function.Function;

abstract public class AbstractBaselineAssertion<SELF extends AbstractBaselineAssertion<SELF>>
    extends AbstractAssert<SELF, String>
    implements BaselineAssertion<SELF> {

    private BaselineContextFactory contextFactory;

    protected AbstractBaselineAssertion(String string, Class<?> selfType) {
        super(string, selfType);
    }

    public SELF using(Function<BaselineContextFactory,BaselineContextFactory> config) {
        contextFactory = config.apply(getContextFactory());

        return myself;
    }

    public final SELF isEqualToBaseline(String baseline) {
        var context = getContextFactory().build(baseline);

        saveActual(context);
        verifyIsEqualToBaseline(context);

        return myself;
    }

    abstract protected void saveActual(BaselineContext context);

    abstract protected void verifyIsEqualToBaseline(BaselineContext context);

    private BaselineContextFactory getContextFactory() {
        if (contextFactory == null) {
            contextFactory = ConventionLocator.getConvention().getBaselineContextFactory();
        }
        return contextFactory;
    }
}
