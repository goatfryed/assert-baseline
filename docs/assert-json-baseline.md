# JSON Assertions
JSON support is added through [json-unit](https://github.com/lukas-krecan/JsonUnit).

```java
import static com.github.goatfryed.assert_baseline.Assertions.assertThatJson;

assertThatJson(jsonString)
    .isEqualToBaseline("src/test/resources/specs/my.baseline.json");
```

## Include in your project
For gradle
```groovy
    implementation("com.github.goatfryed:assert-baseline:1.0.0-alpha1-SNAPSHOT") {
        capabilities {
           requireCapability("com.github.goatfryed:assert-baseline-json")
        }
    }
```

For maven
````xml
<dependencies>
    <dependency>
        <groupId>com.github.goatfryed</groupId>
        <artifactId>assert-baseline</artifactId>
        <version>{version}</version>
    </dependency>
    <dependency>
        <groupId>net.javacrumbs.json-unit</groupId>
        <artifactId>json-unit-assertj</artifactId>
        <version>3.4.1</version>
    </dependency>
</dependencies>
````

## Tips & Recipes
### Ignore auto-generated, volatile fields
```java
assertThatJson(jsonString)
    .usingJsonComparator(diff -> diff
        .whenIgnoringPaths(
            "$.event.uuid",
            "$.event.timestamp"
        )
    ).isEqualToBaseline(pathToBaseline);
```
