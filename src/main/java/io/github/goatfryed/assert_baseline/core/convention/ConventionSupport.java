package io.github.goatfryed.assert_baseline.core.convention;

import io.github.goatfryed.assert_baseline.core.storage.ConfigurableStorageConfigurer;
import io.github.goatfryed.assert_baseline.core.storage.StorageConfig;
import io.github.goatfryed.assert_baseline.core.storage.StorageConfigUtils;

import java.nio.file.Path;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class ConventionSupport {

    public static final String STANDARD_ACTUAL_INFIX = "actual";

    private static final PathWithActivation[] STANDARD_BASELINE_RESOURCE_ROOTS = {
        new PathWithActivation("var/specs", "var/specs"),
        new PathWithActivation("src/testFixtures/resources", "src/testFixtures"),
        new PathWithActivation("src/test/resources", "src"),
    };

    public static UnaryOperator<ConfigurableStorageConfigurer> actualAsSibling() {
        return StorageConfigUtils.actualAsSibling(STANDARD_ACTUAL_INFIX);
    }

    public static UnaryOperator<ConfigurableStorageConfigurer> locateBaselinesAndInferContextPath() {
        var searchRoots = standardResourceRoots();
        var fallbackRoot = guessFallbackResourceRoot();
        return StorageConfigUtils.locateBaselinesAndInferContextPath(searchRoots, fallbackRoot);
    }

    public static String[] standardResourceRoots() {
        return Stream.of(STANDARD_BASELINE_RESOURCE_ROOTS)
            .map(PathWithActivation::path)
            .toArray(String[]::new);
    }

    public static Path guessFallbackResourceRoot() {
        for (var current: STANDARD_BASELINE_RESOURCE_ROOTS) {
            if (Path.of(current.ifPath).toFile().exists()) {
                return Path.of(current.path);
            }
        }
        throw new RuntimeException("Can't guess default baseline resource directory. Follow standard project layout or bring your own convention");
    }

    public static void validateDifferentPaths(StorageConfig config) {
        var fullBaselinePath = config.getFullBaselinePath();
        if (fullBaselinePath.equals(config.getFullActualPath())) {
            throw new IllegalStateException(
                "Actual and baseline both named " + fullBaselinePath
                + "\nActual both should have different names or be placed in different paths."
                + "\nIf the storage strategy handles this and this is intentional, setup your own convention."
            );
        }
    }

    record PathWithActivation(String path, String ifPath) {}
}
