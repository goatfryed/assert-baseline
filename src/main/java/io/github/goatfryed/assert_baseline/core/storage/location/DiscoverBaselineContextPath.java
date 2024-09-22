package io.github.goatfryed.assert_baseline.core.storage.location;

import io.github.goatfryed.assert_baseline.core.storage.StorageConfig;
import io.github.goatfryed.assert_baseline.core.storage.ValueDescriptor;
import io.github.goatfryed.assert_baseline.core.storage.driver.StorageDriver;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class DiscoverBaselineContextPath implements BiFunction<ValueDescriptor, StorageConfig, Path> {

    public final static Path UNKNOWN_PATH = Path.of("__unknown__");

    private final Path[] searchPaths;

    public DiscoverBaselineContextPath(String[] searchPaths) {
        this.searchPaths = Stream.of(searchPaths)
            .map(Path::of)
            .toArray(Path[]::new);
    }

    @Override
    public Path apply(ValueDescriptor value, StorageConfig config) {
        return findContextPathWithValue(config.getBaselineDriver(), value)
            .orElse(UNKNOWN_PATH);
    }

    private Optional<Path> findContextPathWithValue(StorageDriver driver, ValueDescriptor original) {
        var test = new ValueDescriptor(original);

        for (var testContextPath : searchPaths) {
            test.setContextPath(testContextPath);
            if (driver.hasValue(test)) {
                return Optional.of(testContextPath);
            }
        }
        return Optional.empty();
    }
}
