package io.github.goatfryed.assert_baseline;

import io.github.goatfryed.assert_baseline.core.AbstractBaselineAssertion;
import io.github.goatfryed.assert_baseline.core.BaselineContext;
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

    private static class DummyBaselineAssertion extends AbstractBaselineAssertion<DummyBaselineAssertion> {

        protected DummyBaselineAssertion() {
            super("ignored", DummyBaselineAssertion.class);
        }

        @Override
        protected void saveActual(BaselineContext context) {}

        @Override
        protected void verifyIsEqualToBaseline(BaselineContext context) {}
    }
}
