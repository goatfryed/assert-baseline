package io.github.goatfryed.assert_baseline.serializable;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static io.github.goatfryed.assert_baseline.BaselineAssertions.assertThatSerializable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class SerializableBaselineAssertionTest {

    public final static String BASE_PATH = "serializable/object.%s.json";
    public final static String ALTERED_PATH = BASE_PATH.formatted("altered");
    public final static String ACTUAL_PATH = BASE_PATH.formatted("actual");
    public final static String BASELINE_PATH = BASE_PATH.formatted("baseline");
    public final static Path RESOURCE_ROOT = Path.of("src/test/resources");
    public final static File ACTUAL_FILE = RESOURCE_ROOT.resolve(ACTUAL_PATH).toFile();
    public final static File ALTERED_FILE = RESOURCE_ROOT.resolve(ALTERED_PATH).toFile();
    public final static File BASELINE_FILE = RESOURCE_ROOT.resolve(BASELINE_PATH).toFile();

    @Test
    void itPassesSameContent() {
        var foo = new Foo("chucky", new Foo.Bar("chuck","norris"));

        assertThatSerializable(foo)
            .isEqualToBaseline(BASELINE_PATH);
    }

    @Test
    void itFailsContentViolations() {
        var foo = new Foo("chucky", new Foo.Bar("chuck","the murder puppet"));

        assertThatCode(() ->
                assertThatSerializable(foo)
                .isEqualToBaseline(BASELINE_PATH)
            ).hasMessageContaining("expected: Foo[foo=chucky, fooBar=Bar[prop1=chuck, prop2=norris]]");
    }

    @Test
    void itPassesAllowedDifferences() {
        var foo = new Foo("chucky", new Foo.Bar("chuck","the murder puppet"));

        assertThatSerializable(foo)
            .usingSerializableComparator(assertion -> assertion
                .usingRecursiveComparison()
                .ignoringFields("foo", "fooBar.prop2")
            ).isEqualToBaseline(BASELINE_PATH);
    }

    @Test
    void itInteropsWithAssertJ() {
        var foo = new Foo("chucky", new Foo.Bar("chuck","norris"));

        assertThatSerializable(foo)
            .serializableSatisfies(assertion -> assertion
                .hasFieldOrProperty("fooBar")
            );
    }
}