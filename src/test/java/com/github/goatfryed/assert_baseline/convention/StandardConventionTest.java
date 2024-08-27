package com.github.goatfryed.assert_baseline.convention;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class StandardConventionTest {

    @Test
    public void itSupportsMavenStandardLayout() {
        assertConventionBuildsPaths(
            "my-assumption.txt",
            "src/test/resources/specs/my-assumption.actual.txt",
            "src/test/resources/specs/my-assumption.txt"
        );
    }

    @Test
    public void itSupportsGradleTestFixtures() {
        var testFixturesPath = Path.of("src/testFixtures");
        assert testFixturesPath.toFile().mkdirs();
        try {
            assertConventionBuildsPaths(
                "my-assumption.txt",
                "src/testFixtures/resources/specs/my-assumption.actual.txt",
                "src/testFixtures/resources/specs/my-assumption.txt"
            );
        } finally {
            assert testFixturesPath.toFile().delete();
        }
    }

    @Test
    public void itSupportsMissingFileExtension() {
        assertConventionBuildsPaths(
            "my-assumption",
            "src/test/resources/specs/my-assumption.actual",
            "src/test/resources/specs/my-assumption"
        );
    }

    private void assertConventionBuildsPaths(
        String requestedBaseline,
        String actualPath,
        String baselinePath
    ) {
        var convention = new StandardConvention();

        var context = convention.createContext(requestedBaseline);

        assertThat(context)
            .extracting("baseline")
            .asInstanceOf(InstanceOfAssertFactories.FILE)
            .describedAs("baseline path")
            .satisfies(it -> assertThat(it.toString())
                .endsWith(Path.of(baselinePath).toString())
            );

        assertThat(context.getActual().toString())
            .describedAs("actual path")
            .endsWith(Path.of(actualPath).toString());
    }

}