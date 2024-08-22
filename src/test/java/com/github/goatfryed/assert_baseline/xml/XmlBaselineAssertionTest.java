package com.github.goatfryed.assert_baseline.xml;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static com.github.goatfryed.assert_baseline.Assertions.assertThatXml;
import static com.github.goatfryed.assert_baseline.xml.XmlDiffConfiguration.ignoringXPath;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

class XmlBaselineAssertionTest {

    public final static String BASE_PATH = "src/test/resources/xml/event.%s.xml";
    public final static String ALTERED_PATH = BASE_PATH.formatted("altered");
    public final static String ACTUAL_PATH = BASE_PATH.formatted("actual");
    public final static String BASELINE_PATH = BASE_PATH.formatted("baseline");
    public final static File ACTUAL_FILE = new File(ACTUAL_PATH);
    public final static File ALTERED_FILE = new File(ALTERED_PATH);
    public final static File BASELINE_FILE = new File(BASELINE_PATH);

    @BeforeEach
    public void cleanup() {
        FileUtils.deleteQuietly(ACTUAL_FILE);
    }

    @Test
    public void itPassesSameContent() throws Exception {
        var xmlContent = FileUtils.readFileToString(BASELINE_FILE, StandardCharsets.UTF_8);

        assertThatXml(xmlContent)
            .isEqualToBaseline(BASELINE_PATH);
    }

    @Test
    public void itWritesActualToDisk() throws Exception {
        var xmlContent = FileUtils.readFileToString(BASELINE_FILE, StandardCharsets.UTF_8);

        assertFalse(ACTUAL_FILE.exists(), "actual file was present before testing. Is test cleanup not working?");
        assertThatXml(xmlContent)
            .isEqualToBaseline(BASELINE_PATH);
        assertTrue(ACTUAL_FILE.exists(), "Actual file not written to %s".formatted(ACTUAL_FILE.getAbsolutePath()));
    }

    @Test
    public void itFailsViolations() throws Exception {
        var xmlContent = FileUtils.readFileToString(ALTERED_FILE, StandardCharsets.UTF_8);

        assertThatCode(
            () -> assertThatXml(xmlContent)
                .isEqualToBaseline(BASELINE_PATH)
        ).isInstanceOf(AssertionError.class)
            .hasMessageContaining("Expected :<<id>5d341667-083c-4ff0-b844-24193e0c96d4</id>>")
            .hasMessageContaining("Actual   :<<id>3a6f211d-938f-4e7e-af43-6b1807f8d4b0</id>>");
    }

    @Test
    public void itPassesAllowedDifferences() throws Exception {
        var xmlContent = FileUtils.readFileToString(ALTERED_FILE, StandardCharsets.UTF_8);

        assertThatXml(xmlContent)
            .usingXmlComparator(diff -> diff
                .withNodeFilter(
                    ignoringXPath("/root/event/id")
                    .and(ignoringXPath("/root/event/timestamp"))
                    ::test
                )
            ).isEqualToBaseline(BASELINE_PATH);
    }

    @Test
    public void itInteropsWithXmlUnit() throws Exception {
        var xmlContent = FileUtils.readFileToString(BASELINE_FILE, StandardCharsets.UTF_8);

        assertThatXml(xmlContent)
            .xmlSatisfies(xml -> {
                xml.hasXPath("/root/event/id");
                xml.doesNotHaveXPath("/root/message/id");
                xml.nodesByXPath("/root/event/witnesses/witness/text()")
                    .hasSize(3)
                    .extractingText()
                    .element(0)
                    .isEqualTo("Momo");
            });
    }
}