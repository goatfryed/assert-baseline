package com.github.goatfryed.assert_baseline.core.storage;

import java.nio.file.Path;
import java.util.Objects;

public class StorageConfig {
    
    private String requestedKey;
    private Path contextPath;
    /**
     * The path or key under which your baseline output is stored. Relative to {@link #contextPath}
     */
    private Path baselinePath;
    /**
     * The path or key under which your actual output shall be stored. Relative to {@link #contextPath}
     */
    private Path actualPath;

    public StorageConfig(String requestedKey) {
        this.requestedKey = requestedKey;
    }

    /**
     * See {@link #requestedKey}
     */
    public String getRequestedKey() {
        return Objects.requireNonNull(
            requestedKey,
            "requestedKey is not defined. Consider using assertThat[...].isEqualToBaseline(\"myData.format\")"
        );
    }

    /**
     * See {@link #requestedKey}
     */
    public StorageConfig setRequestedKey(String requestedKey) {
        this.requestedKey = requestedKey;
        return this;
    }

    /**
     * See {@link #contextPath}
     */
    public Path getContextPath() {
        return contextPath == null ? Path.of("") : contextPath;
    }

    /**
     * See {@link #contextPath}
     */
    public StorageConfig setContextPath(Path contextPath) {
        this.contextPath = contextPath;
        return this;
    }

    /**
     * See {@link #baselinePath}
     */
    public Path getBaselinePath() {
        return Objects.requireNonNull(
            baselinePath,
            "baseline path is not defined. Did you forget to apply a resolution strategy?"
        );
    }


    /**
     * @return {@link #getBaselinePath()} resolved in context path
     */
    public Path getFullBaselinePath() {
        return contextPath.resolve(baselinePath);
    }
    
    /**
     * See {@link #baselinePath}
     */
    public StorageConfig setBaselinePath(Path baselinePath) {
        this.baselinePath = baselinePath;
        return this;
    }
    
    /**
     * See {@link #actualPath}
     */
    public Path getActualPath() {
        return Objects.requireNonNull(
            actualPath,
            "actual path is not defined. Did you forget to apply a resolution strategy?"
        );
    }

    /**
     * @return {@link #getActualPath()} resolved in context path
     */
    public Path getFullActualPath() {
        return contextPath.resolve(actualPath);
    }

    /**
     * See {@link #actualPath}
     */
    public StorageConfig setActualPath(Path actualPath) {
        this.actualPath = actualPath;
        return this;
    }
}
