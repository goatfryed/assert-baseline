package com.github.goatfryed.assert_baseline;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class Convention {

    private static final Convention instance = new Convention();

    public static Convention getInstance() {
        return instance;
    }

    private Convention() {}


    public BaselineContext createContext(String baselinePath) {
        return new BaselineContext(
            new File(deriveActualName(baselinePath)),
            new File(baselinePath)
        );
    }

    public String deriveActualName(String baselinePath) {
        var matches = StringUtils.countMatches(baselinePath, ".baseline.");
        if (matches != 1) {
            throw new IllegalArgumentException(
                "Failed to derive actual path. Expected baseline path to contain .baseline. exactly once."
            );
        }
        return baselinePath.replace(".baseline.", ".actual.");
    }

}
