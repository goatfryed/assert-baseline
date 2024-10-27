# AssertBaseline
[![Build Status](https://github.com/goatfryed/assert-baseline/actions/workflows/verify.yml/badge.svg)](https://github.com/goatfryed/assert-baseline/actions/workflows/verify.yml)

Compare and approve system outputs with well-defined serialization rules based on their serialized representation.

## What is AssertBaseline?
AssertBaseline extends [AssertJ](https://github.com/assertj/assertj) to integrate format specific assertions with
common aspect of baseline approval testing, primarily the storage aspect (load baselines, save outputs).

### What is baseline approval testing?
In baseline testing you define a baseline based on some system output and verify that
your system satisfies this output as it evolves. If the evolution of your system changes
the output intentionally, you verify the new output and approve it as your updated baseline.

### Baseline approval testing for serializable outputs
In the context of serializable outputs (log messages, json messages, xml requests, ...)
baseline testing can often ease and improve your test setup. You can define tests in terms
of the actual serialized representation instead and omit a translation layer from format to models
in your actual test code.

### Workflow example
You can combine baseline with both test-driven approaches or implementation first.
A typical development flow with baseline testing might be
1. write your implementation
2. write a baseline test that executes your implementation
3. the test fails, because no baseline as been defined
4. verify that the actual output satisfies your requirements
5. save the actual as baseline. The output is now automatically verified
6. If requirements change, you can update the baseline

## Getting started
This example explains common aspects and assumes you're testing json.
For more details and other formats, see below.

All formats are optional dependencies.

#### Include in your project
For Gradle, capabilities can be used
```groovy
implementation("io.github.goatfryed:assert-baseline:{version}") {
  capabilities {
     requireCapability("io.github.goatfryed:assert-baseline-{format}")
  }
}
```

For maven
````xml
<dependencies>
    <dependency>
        <groupId>io.github.goatfryed</groupId>
        <artifactId>assert-baseline</artifactId>
        <version>{version}</version>
    </dependency>
    <!-- check the optional dependencies of your format -->
</dependencies>
````

See [releases](https://github.com/goatfryed/assert-baseline/releases) for the latest release version.

#### Writing assertions
```java
import static io.github.goatfryed.assert_baseline.Assertions.assertThatJson;

assertThatJson(jsonString)
    .jsonSatisfies(jsonAssert -> jsonAssert.satisfiesSomething())
    .usingJsonComparator(diffConfig -> diffConfig.whenIgnoringPaths("..."))
    .isEqualToBaseline("specs/my.baseline.json");
```
`formatAssertion.isEqualToBaseline(pathToBaseline)` executes the baseline assertion.
The parameter pathToBaseline is relative to the resource context. See more on conventions below

`formatAssertion.using{Format}Comparator(...)` provides ways to control the comparison
in a format specific way exposing options of the reused libraries.

`formatAssertion.formatSatisfies(...)` is a convenience method for custom assertions on the wrapped library.
This should make it easier to mix both assertions in one chain.

## Formats
We wrap various well-established format-specific assertion libraries and delegate
the comparison to these libraries.

The following formats are supported

- [assert text](./docs/assert-text-baseline.md)
- [assert json](./docs/assert-json-baseline.md)
- [assert xml](./docs/assert-xml-baseline.md)
- [extend your own format](./docs/extend-assert-baseline.md)

## Storage locations & conventions
By default, the following conventions are assumed

1. baseline paths *may* contain `.baseline`, e.g. `my-response.baseline.json`
2. The actual output is saved in the same directory with `.actual.` in the name
3. baselines are stored in
   - `src/test/resources`
   - `src/testFixtures/resources`
   - `var/specs`
4. The argument of `.isEqualToBaseline(testKey)` defines the relative path in the resource root.
5. you should add `path/to/your/test/resources/**/*.actual.*` to your .gitignore

See [conventions & configuration](./docs/convention-and-configuration.md) how this can be changed.

## Tips & Tricks
### Force overwrite
Use system property `-Dio.github.goatfryed.assert_baseline.forceBaselineUpdate` to force baseline overwrites.
Use with care! This will skip all assertions and overwrite your baselines.
We recommend to git commit before and git diff after to verify your new baselines. This can ease test updates after larger updates.


## Goals & Non-Goals
See our [project scope](./docs/project-scope.md) to for more

## Related
### Related Projects
- [json-unit](https://github.com/lukas-krecan/JsonUnit): compare json serializable data structures
- [xml-unit](https://www.xmlunit.org/): compare xml serializable data structures
- [approvaltests](https://github.com/approvals/approvaltests.java): another baseline testing project, serializable format agnostic

### Related Concepts
- snapshot testing: Snapshot testing is one sub-variant of baseline testing in the context of UI testing

### Roadmap
Check the issues for enhancement to see plans or the future.

The following format support is considered
- csv
- yaml
- toml
- structured logging ...?
- suggestions welcome

Let us know what formats you are interested in.
Enhancement request welcome. [Want to contribute? awesome!](./CONTRIBUTING.md)

