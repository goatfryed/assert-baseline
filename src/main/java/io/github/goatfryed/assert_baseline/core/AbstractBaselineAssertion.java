package io.github.goatfryed.assert_baseline.core;

import io.github.goatfryed.assert_baseline.BaselineAssertion;
import io.github.goatfryed.assert_baseline.core.convention.ConventionLocator;
import io.github.goatfryed.assert_baseline.core.storage.StorageFactory;
import org.assertj.core.api.AbstractAssert;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

abstract public class AbstractBaselineAssertion<SELF extends AbstractBaselineAssertion<SELF,ACTUAL>, ACTUAL>
    extends AbstractAssert<SELF, ACTUAL>
    implements BaselineAssertion<SELF> {

    private BaselineContextFactory contextFactory;

    protected AbstractBaselineAssertion(ACTUAL actual, Class<?> selfType) {
        super(actual, selfType);
    }

    public SELF using(Function<BaselineContextFactory,BaselineContextFactory> config) {
        contextFactory = config.apply(getContextFactory());

        return myself;
    }

    public final SELF isEqualToBaseline(String baseline) {

        getContextFactory()
            .build(baseline)
            .assertWithAdapter(getAssertionAdapter());

        return myself;
    }

    public final SELF usingStorage(Configurer<StorageFactory> configurer) {
        getContextFactory()
            .usingStorage(configurer);

        return myself;
    }

    @NotNull
    abstract protected BaselineAssertionAdapter getAssertionAdapter();

    private BaselineContextFactory getContextFactory() {
        if (contextFactory == null) {
            contextFactory = ConventionLocator.getConvention().getBaselineContextFactory();
        }
        return contextFactory;
    }
}
