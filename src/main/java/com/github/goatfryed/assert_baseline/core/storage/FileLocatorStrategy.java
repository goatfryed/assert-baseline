package com.github.goatfryed.assert_baseline.core.storage;

import com.github.goatfryed.assert_baseline.core.BaselineContextFactory;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.description.Description;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class FileLocatorStrategy implements Function<BaselineContextFactory, StoredValue> {

    private final String[] paths;

    public FileLocatorStrategy(String[] paths) {
        this.paths = paths;
    }

    @Override
    public StoredValue apply(BaselineContextFactory contextFactory) {
        var baselinePath = contextFactory.getBaselinePath();

        return locateBaselinePath(baselinePath)
            .<StoredValue>map(path -> new FileValue(path.toFile()))
            .orElseGet(() -> createMissingValue(baselinePath, () -> contextFactory.getActual().asDescription()));
    }

    private FaultyValue createMissingValue(Path baselinePath, Supplier<Description> actualDescription) {
        var description = "MISSING [requested: %s]".formatted(baselinePath);
        return new FaultyValue(
            description,
            () -> new AssertionError(
                "No baseline found. Consider saving %s as baseline.\nSearched %s in %s".formatted(
                    actualDescription.get(),
                    baselinePath,
                    StringUtils.join(paths, ", ")
                )
            )
        );
    }

    private Optional<Path> locateBaselinePath(Path requestedPath) {
        for (String testResourcePath : paths) {
            var path = Path.of(testResourcePath).resolve(requestedPath);

            if (path.toFile().exists()) {
                return Optional.of(path);
            }
        }
        return Optional.empty();
    }
}
