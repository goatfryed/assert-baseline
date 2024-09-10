package io.github.goatfryed.assert_baseline.core.storage;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Strings;

import java.nio.file.Path;
import java.util.function.UnaryOperator;

public class StorageConfigUtils {

    public static UnaryOperator<ConfigurableStorageConfigurer> inContextPath(String path) {
        return configurer -> configurer.appendPreProcessor(
            config -> config.setContextPath(config.getContextPath().resolve(Path.of(path)))
        );
    }

    public static UnaryOperator<ConfigurableStorageConfigurer> baselineAs(String baselinePath) {
        return configurer -> configurer.setBaselinePathResolver(
            config -> config.setBaselinePath(Path.of(baselinePath))
        );
    }

    public static UnaryOperator<ConfigurableStorageConfigurer> baselineAsRequested() {
        return configurer -> configurer.setBaselinePathResolver(
            config -> config.setBaselinePath(Path.of(config.getRequestedKey()))
        );
    }

    public static UnaryOperator<ConfigurableStorageConfigurer> baselineInPath(Path path) {
        return configurer -> configurer.appendPostProcessor(
            config -> config.setBaselinePath(path.resolve(config.getActualPath()))
        );
    }

    public static UnaryOperator<ConfigurableStorageConfigurer> actualAs(String actualPath) {
        return configurer -> configurer.setActualPathResolver(
            config -> config.setActualPath(Path.of(actualPath))
        );
    }

    public static UnaryOperator<ConfigurableStorageConfigurer> actualInPath(Path path) {
        return configurer -> configurer.appendPostProcessor(
            config -> config.setActualPath(path.resolve(config.getActualPath()))
        );
    }

    public static UnaryOperator<ConfigurableStorageConfigurer> actualNamedSameAsBaseline() {
        return configurer -> configurer.setActualPathResolver(
            config -> config.setActualPath(config.getBaselinePath())
        );
    }

    public static UnaryOperator<ConfigurableStorageConfigurer> actualAsSibling(String infix) {
        return configurer -> configurer.setActualPathResolver(
            config -> config.setActualPath(resolveActualAsSiblingWithInfix(config.getBaselinePath(), infix))
        );
    }

    public static Path resolveActualAsSiblingWithInfix(Path path, String siblingInfix) {
        return path.resolveSibling(resolveActualAsSiblingNameWithInfix(path.getFileName().toString(), siblingInfix));
    }

    public static String resolveActualAsSiblingNameWithInfix(String filename, String siblingInfix) {

        if (StringUtils.countMatches(filename, ".baseline.") == 1) {
            return filename.replaceFirst("\\.baseline\\.", "." + siblingInfix + ".");
        }
        if (StringUtils.countMatches(filename, ".expected.") == 1) {
            return filename.replaceFirst("\\.expected\\.", "." + siblingInfix + ".");
        }

        var extension = FilenameUtils.getExtension(filename);
        if (Strings.isNullOrEmpty(extension)) {
            return filename + "." + siblingInfix;
        }
        return FilenameUtils.removeExtension(filename)
            + "." + siblingInfix + "."
            + extension;
    }

    public static UnaryOperator<ConfigurableStorageConfigurer> locateBaselinesAndInferContextPath(
        String[] searchRoots,
        Path fallbackRoot
    ) {
        var postProcessor = new InferContextPathFromBaselineLocationPostProcessor(
            searchRoots,
            fallbackRoot
        );
        return configurer -> configurer.appendPostProcessor(postProcessor);
    }
}
