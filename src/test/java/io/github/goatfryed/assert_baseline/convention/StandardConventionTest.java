package io.github.goatfryed.assert_baseline.convention;

import io.github.goatfryed.assert_baseline.core.BaselineContext;
import io.github.goatfryed.assert_baseline.core.convention.presets.StandardConventionProvider;
import io.github.goatfryed.assert_baseline.core.storage.StoredValue;
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
            .asInstanceOf(InstanceOfAssertFactories.type(StoredValue.class))
            .extracting(StoredValue::getDriverDescriptor)
            .asInstanceOf(InstanceOfAssertFactories.PATH)
            .describedAs("actual file")
            .satisfies(path ->
                assertThat(path)
                    .isEqualTo(Path.of(actualPath))
            );
    }

}