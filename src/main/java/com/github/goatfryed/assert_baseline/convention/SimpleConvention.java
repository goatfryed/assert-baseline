package com.github.goatfryed.assert_baseline.convention;

import com.github.goatfryed.assert_baseline.Convention;
import org.apache.commons.lang3.StringUtils;

public class SimpleConvention extends AbstractConvention {

    private static final Convention instance = new SimpleConvention();

    public static Convention getInstance() {
        return instance;
    }

    SimpleConvention() {}


    @Override
    public String resolveActualPath(String requestedBaselinePath) {
        var matches = StringUtils.countMatches(requestedBaselinePath, ".baseline.");
        if (matches != 1) {
            throw new IllegalArgumentException(
                "Failed to derive actual path. Expected baseline path to contain .baseline. exactly once."
            );
        }
        return requestedBaselinePath.replace(".baseline.", ".actual.");
    }

}
