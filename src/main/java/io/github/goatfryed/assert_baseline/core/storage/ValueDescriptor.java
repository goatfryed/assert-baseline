package io.github.goatfryed.assert_baseline.core.storage;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class ValueDescriptor {

    private Path contextPath;
    private Path valuePath;
    private final Set<String> tags = new HashSet<>();

    public ValueDescriptor setContextPath(Path contextPath) {
        this.contextPath = contextPath;
        return this;
    }

    public ValueDescriptor() {}

    public ValueDescriptor(ValueDescriptor original) {
        contextPath = original.contextPath;
        valuePath = original.valuePath;
        tags.addAll(original.tags);
    }

    public Path getContextPath() {
        return contextPath == null ? Path.of("") : contextPath;
    }

    public Path getValuePath() {
        return valuePath;
    }

    public ValueDescriptor setValuePath(Path key) {
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
}
