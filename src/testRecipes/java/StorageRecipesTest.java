import io.github.goatfryed.assert_baseline.core.convention.ConventionBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.goatfryed.assert_baseline.BaselineAssertions.assertThatText;
import static io.github.goatfryed.assert_baseline.BaselineConfigurations.*;
import static support.Assertions.assertBaselineUsed;
import static support.Assertions.assertActualFile;
import static support.TestConventionProvider.testWithConvention;

class StorageRecipesTest {

    @Test
    public void basic() {
        assertBaselineUsed( text ->
            assertThatText(text)
                .isEqualToBaseline("my-data.txt"),
            "src/testRecipes/resources/my-data.txt"
        );
        assertActualFile("src/testRecipes/resources/my-data.actual.txt");
    }

    @Test
    public void replacesBaselineInfix() {
        assertBaselineUsed( text ->
            assertThatText(text)
                .isEqualToBaseline("my-data.baseline.txt"),
            "src/testRecipes/resources/my-data.baseline.txt"
        );
        assertActualFile("src/testRecipes/resources/my-data.actual.txt");
    }

    @Test
    public void overwriteBaselinePath() {
        assertBaselineUsed( text ->
                assertThatText(text)
                    .usingStorage(
                        commonRootPath("var/test/specs")
                            .and(actualPath("my-actual.txt"))
                            .and(baselinePath("my-baseline.txt"))
                    ).isEqualToBaseline("whatever"),
            "var/test/specs/my-baseline.txt"
        );
        assertActualFile("var/test/specs/my-actual.txt");

    }

    @BeforeEach
    public void useConvention() {
        testWithConvention(
            ConventionBuilder
                .builderWithDefaults()
                .usingStorage(commonRootPath("src/testRecipes/resources"))
                .build()
        );
    }
}
