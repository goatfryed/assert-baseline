# Extend with another baseline format

You can extend [AbstractBaselineAssertion](../src/main/java/io/github/goatfryed/assert_baseline/core/AbstractBaselineAssertion.java)
to implement your own format.

Compare [JsonBaselineAssertion](../src/main/java/io/github/goatfryed/assert_baseline/json/JsonBaselineAssertion.java)

(!) This internal API is considered unstable.

## Extending from AbstractBaselineAssertion
You can extend from [AbstractBaselineAssertion](../src/main/java/io/github/goatfryed/assert_baseline/core/AbstractBaselineAssertion.java)

### implement assertion logic
First implement `AbstractBaselineAssertion::getAdapter(BaselineContext)`.

Compare [JsonBaselineAssertion](../src/main/java/io/github/goatfryed/assert_baseline/json/JsonBaselineAssertion.java)
for an example.

The adapter is used to decouple operations from the test strategy used.
Effectively, atm we just implement the adapter on the assertion itself and don't use more complex context.
This is likely to change in the future, when more complex strategies are required.

### implement further conventional methods
Usually, you want to implement at least
- `usingYourFormatComparator(...)`
- `yourFormatSatisfies(...)`