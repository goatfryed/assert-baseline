package io.github.goatfryed.assert_baseline;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

public record SerializableSubject(String actual) {

    public String serialized() {
        return actual;
    }

    public void writeTo(Supplier<OutputStream> outputStream) {
        try (var writer = new OutputStreamWriter(outputStream.get(), StandardCharsets.UTF_8)) {
            writer.write(actual);
        } catch (IOException e) {
            throw new AssertionError("couldn't write actual to file", e);
        }
    }
}
