package com.github.goatfryed.assert_baseline.convention;

import com.github.goatfryed.assert_baseline.core.BaselineContext;
import com.github.goatfryed.assert_baseline.core.convention.ConventionSupport;
import com.github.goatfryed.assert_baseline.core.convention.presets.StandardConventionProvider;
import com.github.goatfryed.assert_baseline.core.storage.FileValue;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class StandardConventionTest {

    @Test
    public void itSupportsMavenStandardLayout() {
        assertConventionSelectsActualPaths(
            "my-assumption.txt",
            "src/test/resources/my-assumption.actual.txt"
        );
    }

    @Test
    public void itSupportsGradleTestFixtures() {
        var testFixturesPath = Path.of("src/testFixtures");
        assert testFixturesPath.toFile().mkdirs();
        try {
            assertConventionSelectsActualPaths(
                "my-assumption.txt",
                "src/testFixtures/resources/my-assumption.actual.txt"
            );
        } finally {
            assert testFixturesPath.toFile().delete();
        }
    }

    @Test
    public void itSupportsMissingFileExtension() {
        assertConventionSelectsActualPaths(
            "my-assumption",
            "src/test/resources/my-assumption.actual"
        );
    }

    private void assertConventionSelectsActualPaths(
        String requestedBaseline,
        String actualPath
    ) {
        var convention = new StandardConventionProvider().getConvention();
        var context = convention.createContext(requestedBaseline);

        assertThat(context)
            .extracting(BaselineContext::getActual)
            .asInstanceOf(InstanceOfAssertFactories.type(FileValue.class))
            .extracting(FileValue::asPath)
            .describedAs("actual path")
            .asInstanceOf(InstanceOfAssertFactories.PATH)
            .isEqualTo(Path.of(actualPath));
    }

}