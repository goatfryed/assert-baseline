package com.github.goatfryed.assert_baseline.core;

import com.github.goatfryed.assert_baseline.BaselineAssertion;
import org.assertj.core.api.AbstractAssert;

import java.nio.file.Path;
import java.util.function.Function;

abstract public class AbstractBaselineAssertion<SELF extends AbstractBaselineAssertion<SELF>>
    extends AbstractAssert<SELF, String>
    implements BaselineAssertion<SELF> {

    private BaselineContextFactory contextFactory;

    protected AbstractBaselineAssertion(String string, Class<?> selfType) {
        super(string, selfType);
    }

    public SELF usingBaselineConfig(Function<BaselineContextFactory,BaselineContextFactory> config) {
        contextFactory = config.apply(getContextFactory());

        return myself;
    }

    public final SELF isEqualToBaseline(String baselinePath) {
        getContextFactory()
            .setBaselinePath(Path.of(baselinePath));

        return isEqualToBaseline();
    }

    public final SELF isEqualToBaseline() {
        var context = getContext();
        saveActual(context);
        verifyIsEqualToBaseline(context);

        return myself;
    }

    abstract protected void saveActual(BaselineContext context);

    abstract protected void verifyIsEqualToBaseline(BaselineContext context);

    private BaselineContext getContext() {
        return getContextFactory().build();
    }

    private BaselineContextFactory getContextFactory() {
        if (contextFactory == null) {
            contextFactory = BaselineConventionProvider.getConvention().getBaselineContextFactory();
        }
        return contextFactory;
    }
}
