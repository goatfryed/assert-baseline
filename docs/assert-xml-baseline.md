# XML
XML support is added through [xml-unit](https://github.com/xmlunit/xmlunit).

```java
import static com.github.goatfryed.assert_baseline.Assertions.assertThatXml;

assertThatXml(xmlString)
    .isEqualToBaseline("src/test/resources/specs/my.baseline.xml");
```

## Include in your project
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
        <groupId>org.xmlunit</groupId>
        <artifactId>xmlunit-assertj</artifactId>
        <version>2.10.0</version>
    </dependency>
</dependencies>
````

## Tips & Recipies
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