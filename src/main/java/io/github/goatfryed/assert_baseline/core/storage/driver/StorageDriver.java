package io.github.goatfryed.assert_baseline.core.storage.driver;


import io.github.goatfryed.assert_baseline.core.storage.StoredValue;
import io.github.goatfryed.assert_baseline.core.storage.ValueDescriptor;
import org.assertj.core.description.Description;
import org.assertj.core.description.TextDescription;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StorageDriver {
    boolean hasValue(ValueDescriptor descriptor);
    StoredValue resolve(ValueDescriptor descriptor);
    InputStream getInputStream(ValueDescriptor descriptor) throws IOException;
    OutputStream getOutputStream(ValueDescriptor descriptor) throws IOException;
    Description asDescription(ValueDescriptor descriptor);

    Object getDriverDescriptor(ValueDescriptor descriptor);
}
