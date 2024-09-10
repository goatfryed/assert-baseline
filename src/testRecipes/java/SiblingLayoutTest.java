import io.github.goatfryed.assert_baseline.core.convention.ConventionBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.goatfryed.assert_baseline.BaselineAssertions.assertThatText;
import static io.github.goatfryed.assert_baseline.core.storage.StorageConfigUtils.inContextPath;
import static support.Assertions.assertBaselineUsed;
import static support.Assertions.assertActualFile;
import static support.TestConventionProvider.testWithConvention;

class SiblingLayoutTest {

    @BeforeEach
    public void useConvention() {
        testWithConvention(
            ConventionBuilder
                .builderWithDefaults()
                .storing(inContextPath("src/testRecipes/resources"))
                .build()
        );
    }

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
    public void replacesTypeInfix() {
        assertBaselineUsed( text ->
            assertThatText(text)
                .isEqualToBaseline("my-data.baseline.txt"),
            "src/testRecipes/resources/my-data.baseline.txt"
        );
        assertActualFile("src/testRecipes/resources/my-data.actual.txt");
    }
}
