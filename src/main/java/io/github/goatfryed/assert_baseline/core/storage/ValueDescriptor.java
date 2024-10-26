package io.github.goatfryed.assert_baseline.core.storage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class ValueDescriptor {

    @Nullable
    private Path contextPath;
    @Nullable
    private Path valuePath;
    private final Set<String> tags = new HashSet<>();

    public ValueDescriptor() {}

    public ValueDescriptor(ValueDescriptor original) {
        contextPath = original.contextPath;
        valuePath = original.valuePath;
        tags.addAll(original.tags);
    }

    public Path getContextPath() {
        return contextPath == null ? Path.of("") : contextPath;
    }

    public ValueDescriptor setContextPath(@Nullable Path contextPath) {
        this.contextPath = contextPath;
        return this;
    }

    public @Nullable Path getValuePath() {
        return valuePath;
    }

    public ValueDescriptor setValuePath(@NotNull Path key) {
        this.valuePath = key;
        return this;
    }

    public Set<String> getTags() {
        return tags;
    }

    public ValueDescriptor addTag(String tag) {
        tags.add(tag);
        return this;
    }

    @Override
    public String toString() {
        return "ValueDescriptor{" +
            "contextPath=" + contextPath +
            ", valuePath=" + valuePath +
            ", tags=" + tags +
            '}';
    }
}
