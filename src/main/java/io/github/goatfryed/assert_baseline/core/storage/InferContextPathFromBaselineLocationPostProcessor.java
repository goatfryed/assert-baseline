package io.github.goatfryed.assert_baseline.core.storage;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class InferContextPathFromBaselineLocationPostProcessor implements UnaryOperator<StorageConfig> {

    private final String[] paths;
    private final Path defaultPath;

    public InferContextPathFromBaselineLocationPostProcessor(String[] paths, Path defaultPath) {
        this.paths = paths;
        this.defaultPath = defaultPath;
    }

    @Override
    public StorageConfig apply(StorageConfig config) {
        var baselinePath = config.getBaselinePath();
        var prevContextPath = config.getContextPath();
        var fullBaselinePath = prevContextPath.resolve(baselinePath);

        findRootWithPath(fullBaselinePath)
            .ifPresentOrElse(
                root -> config.setContextPath(root.resolve(prevContextPath)),
                () -> config.setContextPath(defaultPath.resolve(prevContextPath))
            );

        return config;
    }

    private Optional<Path> findRootWithPath(Path requestedPath) {
        for (String testResourcePath : paths) {
            var rootPath = Path.of(testResourcePath);
            var path = rootPath.resolve(requestedPath);

            if (path.toFile().exists()) {
                return Optional.of(rootPath);
            }
        }
        return Optional.empty();
    }
}
