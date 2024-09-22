package io.github.goatfryed.assert_baseline.core.convention;

import io.github.goatfryed.assert_baseline.core.storage.location.*;

import java.nio.file.Path;
import java.util.stream.Stream;

public class Conventions {

    public static final String STANDARD_ACTUAL_INFIX = "actual";

    private static final PathWithActivation[] STANDARD_BASELINE_RESOURCE_ROOTS = {
        new PathWithActivation("var/specs", "var/specs"),
        new PathWithActivation("src/testFixtures/resources", "src/testFixtures"),
        new PathWithActivation("src/test/resources", "src"),
    };

    public static ConfigurableLocationStrategy recommendBaselineLocationStrategy() {
        return new ConfigurableLocationStrategy(
            new RequestedKeyAsPath(),
            new DiscoverBaselineContextPath(standardResourceRoots())
        );
    }

    public static ConfigurableLocationStrategy recommendActualLocationStrategy() {
        return new ConfigurableLocationStrategy(
            new ActualAsSiblingPath(STANDARD_ACTUAL_INFIX),
            new ActualMatchesBaselineContext(guessFallbackResourceRoot().toString())
        );
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

    record PathWithActivation(String path, String ifPath) {}
}
