package com.github.goatfryed.assert_baseline.core;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Strings;

import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Stream;

public class StandardConventionSupport {

    public static final String STANDARD_ACTUAL_INFIX = "actual";

    private static final PathWithActiviation[] STANDARD_BASELINE_RESOURCE_ROOTS = {
        new PathWithActiviation("var/specs", "var/specs"),
        new PathWithActiviation("src/testFixtures/resources", "src/testFixtures"),
        new PathWithActiviation("src/test/resources", "src"),
    };

    public static String[] standardBaselineResourceRoots() {
        return Stream.of(STANDARD_BASELINE_RESOURCE_ROOTS)
            .map(PathWithActiviation::path)
            .toArray(String[]::new);
    }

    public static Path guessFallbackBaselineResourceRoot() {
        for (var current: STANDARD_BASELINE_RESOURCE_ROOTS) {
            if (Path.of(current.ifPath).toFile().exists()) {
                return Path.of(current.path);
            }
        }
        throw new RuntimeException("Can't guess default baseline resource directory. Follow standard project layout or bring your own convention");
    }

    public static Function<BaselineContextFactory, Path> siblingPath(String siblingInfix) {
        return ctx -> resolveSiblingWithInfix(ctx.getBaselinePath(), siblingInfix);
    }

    public static Path resolveSiblingWithInfix(Path path, String siblingInfix) {
        return path.resolveSibling(resolveSiblingNameWithInfix(path.getFileName().toString(), siblingInfix));
    }

    public static String resolveSiblingNameWithInfix(String filename, String siblingInfix) {

        if (StringUtils.countMatches(filename, ".baseline.") == 1) {
            return filename.replaceFirst("\\.baseline\\.", "." + siblingInfix + ".");
        }
        if (StringUtils.countMatches(filename, ".baseline.") == 1) {
            return filename.replaceFirst("\\.baseline\\.", "." + siblingInfix + ".");
        }

        var extension = FilenameUtils.getExtension(filename);
        if (Strings.isNullOrEmpty(extension)) {
            return filename + "." + siblingInfix;
        }
        return FilenameUtils.removeExtension(filename)
            + "." + siblingInfix + "."
            + extension;
    }

    record PathWithActiviation(String path, String ifPath) {}
}
