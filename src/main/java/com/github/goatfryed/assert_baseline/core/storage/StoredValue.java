package com.github.goatfryed.assert_baseline.core.storage;

import org.assertj.core.description.Description;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * {@link StoredValue} is an interface for input and output streams to actual system output and baselines
 */
public interface StoredValue {

    Description asDescription();

    /**
     * Can be omitted when representing an actual value
     */
    InputStream getInputStream() throws IOException;

    /**
     * Can be omitted when representing a baseline value
     */
    OutputStream getOutputStream() throws IOException;
}
