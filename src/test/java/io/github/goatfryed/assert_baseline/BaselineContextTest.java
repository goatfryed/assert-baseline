package io.github.goatfryed.assert_baseline;

import io.github.goatfryed.assert_baseline.core.BaselineAssertionAdapter;
import io.github.goatfryed.assert_baseline.core.BaselineContext;
import io.github.goatfryed.assert_baseline.core.convention.presets.StandardConventionProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.fail;


class BaselineContextTest {

    @Test
    public void itRecommendsCopyActionOnMissingBaseline() {
        var defaultConvention = new StandardConventionProvider().getConvention();
        var context = defaultConvention.createContext("baseline.txt");

        assertThatCode(() -> {
            context.assertWithAdapter(new BaselineAssertionAdapter() {
                @Override
                public void writeActual(BaselineContext.ActualOutput output, BaselineContext context) {}

                @Override
                public void assertEquals(BaselineContext.BaselineInput baseline, BaselineContext context) {
                    baseline.getInputStream();
                    fail("expected the above line to fail with FileNotFound");
                }
            });
        })
            .hasMessageContaining("No baseline found")
            .hasMessageMatching("(.|\\n)*Consider saving .* as .*(.|\\n)*");
    }

}