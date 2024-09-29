# Extend with another baseline format

You can extend [AbstractBaselineAssertion](../src/main/java/io/github/goatfryed/assert_baseline/core/AbstractBaselineAssertion.java)
to implement your own format.

Compare [JsonBaselineAssertion](../src/main/java/io/github/goatfryed/assert_baseline/json/JsonBaselineAssertion.java)

(!) This internal API is considered unstable.

## Extending from AbstractBaselineAssertion

### 1. implement write
Implement `AbstractBaselineAssertion::saveActual(BaselineContext)`.
Serialize your subject and write it to `BaselineContext::getActualOutputStream`

### 2. implement verification
Implement `verifyIsEqualToBaseline(BaselineContext context)`.
Usually, you'll want to read from `BaselineContext::getBaselineAsString(), deserialize the baseline,
and then compare the java beans.

### implement further conventional methods
