# Convention & Configuration
We assume that you follow *some* conventions that define where your test files are stored.
You can either use our recommended conventions or define your own.

## Recommended convention
By default, the following conventions are assumed

1. baseline paths *may* contain `.baseline`, e.g. `my-response.baseline.json`
2. baselines are stored in
    - `src/test/resources`
    - `src/testFixtures/resources`
    - `var/specs`
3. The actual output is saved in the same directory with `.actual.` in the name
4. The argument of `.isEqualToBaseline(testKey)` defines the relative path in the resource root.
5. you should add `path/to/your/test/resources/**/*.actual.*` to your .gitignore

## Custom convention
If you want to customize the baseline test behavior, you can do so using [SPI](https://docs.oracle.com/javase/tutorial/sound/SPI-intro.html).

Register your desired Convention in `META-INF/services/com.github.goatfryed.assert_baseline.Convention`.
See [this](./src/testSpi/resources/META-INF/services/com.github.goatfryed.assert_baseline.Convention) example.

Note: Configuration of different resource roots is easy. More complex setups can be achieved,
but we do not guarantee the stability of the internal api at the moment.

If you want to define your own convention, implement [com.github.goatfryed.assert_baseline.Convention](./src/main/java/com/github/goatfryed/assert_baseline/Convention.java).

Usually, you want to do so by extending
[com.github.goatfryed.assert_baseline.convention.AbstractConvention](./src/main/java/com/github/goatfryed/assert_baseline/convention/AbstractConvention.java)

```java
public class MyBaselineConvention extends AbstractConvention {
    
    @Override
    public String resolveActualPath(String requestedBaselinePath) {
        return "src/test/resources/actual/" + requestedBaselinePath;
    }
    
    @Override
    public String resolveActualPath(String requestedBaselinePath) {
        return "src/test/resources/expected/" + requestedBaselinePath;
    }
}
```
This would allow you to call `assertThatJson(event).isEqualToBaseline(event.json)`
and have the actual file stored as `src/test/resources/actual/event.json` and compared with
`src/test/resources/expected/event.json`
