package io.github.goatfryed.assert_baseline.core.storage.location;

import io.github.goatfryed.assert_baseline.core.storage.StorageConfig;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Strings;

import java.nio.file.Path;
import java.util.function.Function;

/**
 * Transforms file name to create a sibling with infix as marker<br>
 * <code>path/to/my.baseline.file</code> -> <code>path/to/my.{infix}.file</code><br>
 * <code>path/to/my.expected.file</code> -> <code>path/to/my.{infix}.file</code><br>
 * <code>path/to/my.file</code> -> <code>path/to/my.{infix}.file</code><br>
 */
public class ActualAsSiblingPath implements Function<StorageConfig, Path> {

    private final String infix;

    public ActualAsSiblingPath(String infix) {
        this.infix = infix;
    }

    @Override
    public Path apply(StorageConfig storageConfig) {
        return Path.of(
            resolveBaselineSibling(storageConfig.getBaseline().getValuePath().toString())
        );
    }

    public String resolveBaselineSibling(String originalName) {

        if (StringUtils.countMatches(originalName, ".baseline.") == 1) {
            return originalName.replaceFirst("\\.baseline\\.", "." + infix + ".");
        }
        if (StringUtils.countMatches(originalName, ".expected.") == 1) {
            return originalName.replaceFirst("\\.expected\\.", "." + infix + ".");
        }

        var extension = FilenameUtils.getExtension(originalName);
        if (Strings.isNullOrEmpty(extension)) {
            return originalName + "." + infix;
        }
        return FilenameUtils.removeExtension(originalName)
            + "." + infix + "."
            + extension;
    }
}
