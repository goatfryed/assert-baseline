# AssertBaseline
Compare objects with well-defined rules for serialization based on there serialized representation

## What is baseline testing?
Baseline testing, also known as approval testing is an approach where you store
a serializable representation of your output and compare it with an approved version.

You can combine baseline with both test-driven approaches or implementation first.
A typical development flow with baseline testing might be
1. write implementation
2. write a baseline test that executes your implementation
3. the test fails, because no baseline as been defined
4. verify that the actual output satisfies your requirements
5. save the actual as baseline. The output is now automatically verified
6. If requirements change, you can update the baseline

## Usage
AssertBaseline integrates with [AssertJ](https://github.com/assertj/assertj).

We wrap various well-established format-specific assertion libraries.
They are optional dependencies and must be required explicitly.
For gradle, capabilities can be used.

```java
import static com.github.goatfryed.assert_baseline.Assertions.assertThatJson;

assertThatJson(jsonString)
    .jsonSatisfies(jsonAssert -> jsonAssert.satisfiesSomething())
    .usingJsonComparator(diffConfig -> diffConfig.whenIgnoringPaths("..."))
    .isEqualToBaseline("src/test/resources/specs/my.baseline.json");
```
`formatAssertion.isEqualToBaseline(pathToBaseline)` executes the baseline assertion

`formatAssertion.usingFormatComparator(Function.identity())` provides ways to control the comparison
in a format specific way exposing options of the reused libraries.

`formatAssertion.formatSatisfies(...)` is a convenience method for custom assertions on the wrapped library.
This should make it easier to mix both assertions in one chain.


## JSON
Json support is added through [json-unit](https://github.com/lukas-krecan/JsonUnit).

```java
import static com.github.goatfryed.assert_baseline.Assertions.assertThatJson;

assertThatJson(jsonString)
    .isEqualToBaseline("src/test/resources/specs/my.baseline.json");
```

### Include in your project
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
        <version>1.0.0-alpha1-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>net.javacrumbs.json-unit</groupId>
        <artifactId>json-unit-assertj</artifactId>
        <version>3.4.1</version>
    </dependency>
</dependencies>
````

### Tips & Recipies
Ignore auto-generated, volatile fields
```java
assertThatJson(jsonString)
    .usingJsonComparator(diff -> diff
        .whenIgnoringPaths(
            "$.event.uuid",
            "$.event.timestamp"
        )
    ).isEqualToBaseline(pathToBaseline);
```

## XML
XML support is added through [xml-unit](https://github.com/xmlunit/xmlunit).

```java
import static com.github.goatfryed.assert_baseline.Assertions.assertThatXml;

assertThatXml(xmlString)
    .isEqualToBaseline("src/test/resources/specs/my.baseline.xml");
```

### Include in your project
For gradle
```groovy
    implementation("com.github.goatfryed:assert-baseline:1.0.0-alpha1-SNAPSHOT") {
        capabilities {
           requireCapability("com.github.goatfryed:assert-baseline-xml")
        }
    }
```

For maven
````xml
<dependencies>
    <dependency>
        <groupId>com.github.goatfryed</groupId>
        <artifactId>assert-baseline</artifactId>
        <version>1.0.0-alpha1-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>net.javacrumbs.json-unit</groupId>
        <artifactId>json-unit-assertj</artifactId>
        <version>3.4.1</version>
    </dependency>
</dependencies>
````

### Tips & Recipies
Ignore auto-generated, volatile fields
```java
import static com.github.goatfryed.assert_baseline.xml.XmlDiffConfiguration.ignoringXPath;

assertThatXml(xmlContent)
    .usingXmlComparator(diff -> diff
        .withNodeFilter(
            ignoringXPath("/root/event/uuid")
            .and(ignoringXPath("/root/event/timestamp"))
            ::test
        )
    ).isEqualToBaseline(pathToBaseline);
```

## Convention & Configuration
We assume that you follow *some* conventions about where your test files are stored.
You can either use our minimal conventions or set up your own.

### Default convention
By default, the following conventions are assumed

1. baseline paths *must* contain `.baseline`, e.g. `my-response.baseline.json`
2. The actual serialized version is saved in the same directory with `.actual.` in the name
3. you should add `path/to/your/test/resources/**/*.actual.*` to your .gitignore

### Custom convention
If you want to customize the baseline test behavior, you can do so using [SPI](https://docs.oracle.com/javase/tutorial/sound/SPI-intro.html).

Register your desired Convention in `META-INF/services/com.github.goatfryed.assert_baseline.Convention`.
See [this](./src/testSpi/resources/META-INF/services/com.github.goatfryed.assert_baseline.Convention) example.

#### Recommended Convention
Our recommended naming convention is

1. baseline test data is placed in `src/test/resources/specs`. All baseline paths are relative to this directory
   - if you use testFixtures, baseline test data is placed in `src/testFixtures/resources/specs` instead
2. baseline test data can be named in any way
3. actual data adds `.actual` before the file extension. E.g. `my-event.json` is saved as `my-event.actual.json`
4. you should add `path/to/your/test/resources/**/*.actual.*` to your .gitignore

If you want to opt into our recommendation, create `META-INF/services/com.github.goatfryed.assert_baseline.Convention`
with the following content
```text
com.github.goatfryed.assert_baseline.convention.StandardConvention
```


#### Bring your own convention
If you want to define your own convention, implement [com.github.goatfryed.assert_baseline.Convention](./src/main/java/com/github/goatfryed/assert_baseline/Convention.java).

Usually, you want to do so by extending
[com.github.goatfryed.assert_baseline.convention.AbstractConvention](./src/main/java/com/github/goatfryed/assert_baseline/convention/AbstractConvention.java)

```java
public class MyBaselineConvention extends AbstractConvention {
    
    @Override
    public void String resolveActualPath(String requestedBaselinePath) {
        return "src/test/resources/actual/" + requestedBaselinePath;
    }
    
    @Override
    public void String resolveActualPath(String requestedBaselinePath) {
        return "src/test/resources/expected/" + requestedBaselinePath;
    }
}
```
This would allow you to call `assertThatJson(event).isEqualToBaseline(event.json)`
and have the actual file stored as `src/test/resources/actual/event.json` and compared with
`src/test/resources/expected/event.json`

## Related
### Related Projects
- json-unit: compare json serializable data structures
- xml-unit: compare xml serializable data structures
- [approvaltests](https://github.com/approvals/approvaltests.java): another baseline testing project, serializable format agnostic

### Related Concepts
- snapshot testing: Snapshot testing is one sub-variant of baseline testing in the context of UI testing

## Roadmap
- [ ] support parameters other than string
- [ ] more formats?