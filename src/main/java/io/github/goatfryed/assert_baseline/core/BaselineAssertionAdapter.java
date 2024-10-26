package io.github.goatfryed.assert_baseline.core;

/**
 * Adapter interface to perform the real serialization and assertion
 * <br><br>
 * This interface is used to separate the format dependent actions of baseline testing
 * from the test process itself.
 */
public interface BaselineAssertionAdapter {

    /**
     * Writes the serialized representation of the actual subject
     *
     * @param output  Out parameter
     * @param context
     */
    void writeActual(BaselineContext.ActualOutput output, BaselineContext context);

    /**
     * Perform the equality assertion against the provided baseline
     *
     * @param baseline the baseline input
     * @param context
     */
    void assertEquals(BaselineContext.BaselineInput baseline, BaselineContext context);

}
