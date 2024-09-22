package io.github.goatfryed.assert_baseline.core.storage.driver;

import io.github.goatfryed.assert_baseline.core.storage.*;
import org.assertj.core.description.Description;
import org.assertj.core.description.TextDescription;

import java.io.*;
import java.nio.file.Path;

public class FileSystemDriver implements StorageDriver {

    @Override
    public Description asDescription(ValueDescriptor descriptor) {
        return new TextDescription(getFile(descriptor).getAbsolutePath());
    }

    @Override
    public Object getDriverDescriptor(ValueDescriptor descriptor) {
        return getPath(descriptor);
    }

    @Override
    public boolean hasValue(ValueDescriptor descriptor) {
        return getFile(descriptor).exists();
    }

    @Override
    public StoredValue resolve(ValueDescriptor descriptor) {
        return new StoredValue(this, descriptor);
    }

    @Override
    public InputStream getInputStream(ValueDescriptor descriptor) throws FileNotFoundException {
        return new FileInputStream(getFile(descriptor));
    }

    @Override
    public OutputStream getOutputStream(ValueDescriptor descriptor) throws FileNotFoundException {
        var file = getFile(descriptor);
        //noinspection ResultOfMethodCallIgnored
        file.getParentFile().mkdirs();
        return new FileOutputStream(file);
    }

    public Path getPath(ValueDescriptor descriptor) {
        return descriptor.getContextPath()
            .resolve(descriptor.getValuePath());
    }

    public File getFile(ValueDescriptor descriptor) {
        return getPath(descriptor).toFile();
    }
}
