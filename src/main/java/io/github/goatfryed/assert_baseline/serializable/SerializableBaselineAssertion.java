package io.github.goatfryed.assert_baseline.serializable;

import io.github.goatfryed.assert_baseline.core.AbstractBaselineAssertion;
import io.github.goatfryed.assert_baseline.core.BaselineAssertionAdapter;
import io.github.goatfryed.assert_baseline.core.BaselineContext;
import org.assertj.core.api.Assert;
import org.assertj.core.api.ObjectAssert;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class SerializableBaselineAssertion<ACTUAL> extends AbstractBaselineAssertion<SerializableBaselineAssertion<ACTUAL>,ACTUAL> {

    private Function<ObjectAssert<ACTUAL>, Assert<?,?>> assertConfig = assertion -> assertion;

    public SerializableBaselineAssertion(ACTUAL actual) {
        super(actual, SerializableBaselineAssertion.class);
    }

    /**
     * This is the same as just calling<br>
     * <code>
     *     serializableAssertion.satisfies(it -> assertThat(it)...)
     * </code><br>
     * It's only implemented as a convenience method in line with our general pattern
     */
    public SerializableBaselineAssertion<ACTUAL> serializableSatisfies(Consumer<ObjectAssert<ACTUAL>> assertion) {
        assertion.accept(assertThat(actual));
        return myself;
    }

    /**
     * This yields control of the {@link Assert} to the user.<br>
     * Note that whatever assertion is returned gets called with <code>isEqualTo(baselineObject)</code>.
     */
    public SerializableBaselineAssertion<ACTUAL> usingSerializableComparator(Function<ObjectAssert<ACTUAL>, Assert<?,?>> assertConfig) {
        this.assertConfig = assertConfig;
        return myself;
    }

    @Override
    protected @NotNull BaselineAssertionAdapter getAssertionAdapter() {
        return new Adapter();
    }

    class Adapter implements BaselineAssertionAdapter {
        @Override
        public void writeActual(BaselineContext.ActualOutput output, BaselineContext context) {
            try (var oos = new ObjectOutputStream(output.outputStream())) {
                oos.writeObject(actual);
            } catch (IOException e) {
                throw new AssertionError("failed to serialize actual", e);
            }
        }

        @Override
        public void assertEquals(BaselineContext.BaselineInput baseline, BaselineContext context) {
            try (var ois = new ObjectInputStream(baseline.getInputStream())) {
                var baselineObject = ois.readObject();
                assertConfig.apply(assertThat(actual))
                    .isEqualTo(baselineObject);
            } catch (IOException e) {
                throw new AssertionError("failed to read baseline", e);
            } catch (ClassNotFoundException e) {
                throw new AssertionError("baseline class does not exist. This indicates that you changed class definition and need to recreate the baseline.", e);
            }
        }
    }

}
