package com.github.goatfryed.assert_baseline.convention;

import org.apache.commons.io.FilenameUtils;
import org.assertj.core.util.Strings;

import java.nio.file.Path;

public class StandardConvention extends AbstractConvention {

    private final Path basePath;

    public StandardConvention() {
        basePath = resolveBasePath();
    }

    @Override
    public String resolveActualPath(String requestedBaselinePath) {
        var baselinePath = doResolveBaselinePath(requestedBaselinePath).toString();
        var extension = FilenameUtils.getExtension(baselinePath);

        if (Strings.isNullOrEmpty(extension)) {
            return baselinePath + ".actual";
        }
        return FilenameUtils.removeExtension(baselinePath)
            + ".actual."
            + extension;
    }

    @Override
    public String resolveBaselinePath(String requestedBaselinePath) {
        return doResolveBaselinePath(requestedBaselinePath).toString();
    }

    private Path doResolveBaselinePath(String requestedBaselinePath) {
        return basePath.resolve(requestedBaselinePath).toAbsolutePath();
    }

    /**
     * resolve where to place test file<br>
     * Checks, whether the project uses gradle's testFixture source folder.
     * If not, assume Maven Standard Layout.<br>
     * Assume test data is stored in a sub folder called specs
     */
    private static Path resolveBasePath() {
        var testFixturesPath = Path.of("src/testFixtures");
        var testDataPath = testFixturesPath.toFile().exists() ?
            testFixturesPath : Path.of("src/test");

        return testDataPath.resolve("resources/specs");
    }


}
