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

## General
The following conventions project conventions apply

- baseline paths *must* contain `.baseline`, e.g. `my-response.baseline.json`
- The actual serialized version is saved in the same directory with `.actual.` in the name
- you should add `path/to/your/test/resources/**/*.actual*` to your .gitignore

The general usage is as follows

```java
import static com.github.goatfryed.assert_baseline.Assertions.assertThatJson;

assertThatJson(jsonString)
    .jsonSatisfies(jsonAssert -> jsonAssert.satisfiesSomething())
    .usingJsonComparator(diffConfig -> diffConfig.whenIgnoringPaths("..."))
    .isEqualToBaseline("src/test/resources/my.baseline.json");
```
`formatAssertion.isEqualToBaseline(pathToBaseline)` executes the baseline assertion

`formatAssertion.usingFormatComparator(Function.identity())` provides ways to control the comparison
in a format specific way exposing options of the reused libraries.

`formatAssertion.formatSatisfies(...)` is a convenience method for custom assertions of the wrapped library. 


## JSON
Json support is added through [json-unit](https://github.com/lukas-krecan/JsonUnit).

```java
import static com.github.goatfryed.assert_baseline.Assertions.assertThatJson;

assertThatJson(jsonString)
    .isEqualToBaseline("src/test/resources/my.baseline.json");
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
    .isEqualToBaseline("src/test/resources/my.baseline.xml");
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


## Related
### Related Projects
- json-unit: compare json serializable data structures
- xml-unit: compare xml serializable data structures
- [approvaltests](https://github.com/approvals/approvaltests.java): another baseline testing project, serializable format agnostic

### Related Concepts
- snapshot testing: Snapshot testing is one sub-variant of baseline testing in the context of UI testing

## Roadmap
- [ ] configure the used conventions
- [ ] support parameters other than string
- [ ] more formats?