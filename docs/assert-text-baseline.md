# Text Assertions
String assertions require no additional dependency and use simply AssertJ.

```java
import static io.github.goatfryed.assert_baseline.Assertions.assertThatText;

assertThatText(yourString)
    .isEqualToBaseline("src/test/resources/specs/my.baseline.txt");
```

## Include in your project
For gradle
```groovy
    implementation("io.github.goatfryed:assert-baseline:1.0.0-alpha1-SNAPSHOT") {}
```

For maven
````xml
<dependencies>
    <dependency>
        <groupId>io.github.goatfryed</groupId>
        <artifactId>assert-baseline</artifactId>
        <version>{version}</version>
    </dependency>
</dependencies>
````

## Tips & Recipes
`usingTextComparator` is simply an alias for `usingComparator` of AssertJ on String subjects
