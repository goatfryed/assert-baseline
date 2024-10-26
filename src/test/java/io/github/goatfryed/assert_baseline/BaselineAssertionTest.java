package io.github.goatfryed.assert_baseline;

import io.github.goatfryed.assert_baseline.core.AbstractBaselineAssertion;
import io.github.goatfryed.assert_baseline.core.BaselineAssertionAdapter;
import io.github.goatfryed.assert_baseline.core.BaselineContext;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static io.github.goatfryed.assert_baseline.BaselineConfigurations.*;
import static org.assertj.core.api.Assertions.assertThatCode;

public class BaselineAssertionTest {

    @Test
    public void itValidatesThatActualAndBaselineAreDifferent() {
        assertThatCode(() ->
            new DummyBaselineAssertion()
                .usingStorage(
                    commonRootPath("var/test/specs")
                        .and(baselinePath("my-baseline.txt"))
                        .and(actualPath("my-baseline.txt"))
                ).isEqualToBaseline("whatever")
        ).hasMessageContaining("Both should have different names");
    }

    private static class DummyBaselineAssertion extends AbstractBaselineAssertion<DummyBaselineAssertion,String> {

        protected DummyBaselineAssertion() {
            super("ignored", DummyBaselineAssertion.class);
        }

        @Override
        protected @NotNull BaselineAssertionAdapter getAssertionAdapter() {
            return new BaselineAssertionAdapter() {

                @Override
                public void writeActual(BaselineContext.ActualOutput output, BaselineContext context) {}

                @Override
                public void assertEquals(BaselineContext.BaselineInput baseline, BaselineContext context) {}
            };
        }
    }
}
