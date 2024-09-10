package io.github.goatfryed.assert_baseline.core.storage;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Function;

/**
 * The ConfigurableStorageConfigurer creates StorageConfig by applying a series of processors<br>
 * <ol>
 *     <li>
 *         Apply {@link #preProcessor}. The preProcessor might set defaults, normalize the requestedKey,
 *         set {@link StorageConfig#setContextPath(Path)} and perform similar actions.
 *     </li>
 *     <li>
 *         Resolve the baseline path. Usually, you'll use the requestedKey to infer the path or set a fixed one.
 *         The output of this resolver is usually used in the {@link #actualPathResolver} in the next step.
 *         If you want modifications that are not visible there, consider using a {@link #postProcessor}
 *     </li>
 *     <li>Resolve the actual path. This might use the resolved baseline path</li>
 *     <li>Apply {@link #postProcessor}. A post processor might modify the resolved paths. E.g. normalize paths</li>
 * </ol>
 */
public class ConfigurableStorageConfigurer implements StorageConfigurer {

    private Function<StorageConfig,StorageConfig> preProcessor = Function.identity();
    private Function<StorageConfig,StorageConfig> baselinePathResolver = Function.identity();
    private Function<StorageConfig,StorageConfig> actualPathResolver = Function.identity();
    private Function<StorageConfig,StorageConfig> postProcessor = Function.identity();


    public ConfigurableStorageConfigurer setBaselinePathResolver(Function<StorageConfig, StorageConfig> baselinePathResolver) {
        this.baselinePathResolver = Objects.requireNonNull(baselinePathResolver,"resolvers can't be null");
        return this;
    }

    public ConfigurableStorageConfigurer setActualPathResolver(Function<StorageConfig, StorageConfig> actualPathResolver) {
        this.actualPathResolver = Objects.requireNonNull(actualPathResolver, "resolvers can't be null");
        return this;
    }

    public ConfigurableStorageConfigurer setPreProcessor(Function<StorageConfig, StorageConfig> contextPathResolver) {
        this.preProcessor = Objects.requireNonNull(contextPathResolver, "preProcessor can't be null");
        return this;
    }

    public ConfigurableStorageConfigurer setPostProcessor(Function<StorageConfig, StorageConfig> contextPathResolver) {
        this.postProcessor = Objects.requireNonNull(contextPathResolver, "postProcessor can't be null");
        return this;
    }

    public ConfigurableStorageConfigurer appendPostProcessor(Function<StorageConfig, StorageConfig> postProcessor) {
        this.postProcessor = this.postProcessor.andThen(postProcessor);
        return this;
    }

    public ConfigurableStorageConfigurer appendPreProcessor(Function<StorageConfig, StorageConfig> preProcessor) {
        this.preProcessor = this.preProcessor.andThen(preProcessor);
        return this;
    }

    @Override
    public StorageConfig createConfig(String requestedKey) {

        /*mut*/ var config = new StorageConfig(requestedKey);
        config = preProcessor.apply(config);
        config = baselinePathResolver.apply(config);
        config = actualPathResolver.apply(config);
        config = postProcessor.apply(config);

        return config;
    }

    public ConfigurableStorageConfigurer copy() {
        var copy = new ConfigurableStorageConfigurer();

        copy.setPreProcessor(preProcessor);
        copy.setBaselinePathResolver(baselinePathResolver);
        copy.setActualPathResolver(actualPathResolver);
        copy.setPostProcessor(postProcessor);

        return copy;
    }
}
