package support;

import io.github.goatfryed.assert_baseline.core.BaselineContextFactory;
import io.github.goatfryed.assert_baseline.core.convention.BaselineConvention;
import io.github.goatfryed.assert_baseline.core.convention.BaselineConventionProvider;

public class TestConventionProvider implements BaselineConventionProvider {

    private static BaselineConvention delegate;

    public static void testWithConvention(BaselineConvention delegate) {
        TestConventionProvider.delegate = delegate;
    }


    @Override
    public BaselineConvention getConvention() {
        return new TestConvention();
    }

    public static class TestConvention implements BaselineConvention {

        @Override
        public BaselineContextFactory getBaselineContextFactory() {
            return delegate.getBaselineContextFactory();
        }

    }
}
