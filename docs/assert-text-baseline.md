# Text Assertions
String assertions require no additional dependency and use simply assertJ.

```java
import static com.github.goatfryed.assert_baseline.Assertions.assertThatJson;

assertThatText(yourString)
    .isEqualToBaseline("src/test/resources/specs/my.baseline.txt");
```

## Include in your project
For gradle
```groovy
    implementation("com.github.goatfryed:assert-baseline:1.0.0-alpha1-SNAPSHOT") {}
```

For maven
````xml
<dependencies>
    <dependency>
        <groupId>com.github.goatfryed</groupId>
        <artifactId>assert-baseline</artifactId>
        <version>{version}</version>
    </dependency>
</dependencies>
````

## Tips & Recipies
`usingTextComparator` is simply an alias for `usingComparator` of assertJ on String subjects