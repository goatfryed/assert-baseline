package support;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class Assertions {

    public static void assertActualFile(String path) {
        assertThat(Path.of(path))
            .isNotEmptyFile()
            .exists();
    }

    public static void assertBaselineUsed(Consumer<String> assertion, String baselinePath) {
        var dummyData = "my data";
        var baselineFile = new File(baselinePath);
        try {
            FileUtils.writeStringToFile(baselineFile, dummyData, StandardCharsets.UTF_8);
            assertion.accept(dummyData);
        } catch (IOException e) {
            throw new AssertionError("file system error", e);
        } catch (Throwable e){
            assert baselineFile.delete();
            throw e;
        }
        assert baselineFile.delete();
    }
}
