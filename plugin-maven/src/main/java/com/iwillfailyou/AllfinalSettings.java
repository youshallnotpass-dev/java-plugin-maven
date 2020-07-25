package com.iwillfailyou;

import java.util.Arrays;
import java.util.List;

public final class AllfinalSettings {
    private final List<Integer> threshold;
    private final List<Boolean> skipInterfaceMethodParams;
    private final List<Boolean> skipLambdaParams;

    public AllfinalSettings() {
        this(
            Arrays.asList(0),
            Arrays.asList(true),
            Arrays.asList(false)
        );
    }

    public AllfinalSettings(
        final List<Integer> threshold,
        final List<Boolean> skipInterfaceMethodParams,
        final List<Boolean> skipLambdaParams
    ) {
        this.threshold = threshold;
        this.skipInterfaceMethodParams = skipInterfaceMethodParams;
        this.skipLambdaParams = skipLambdaParams;
    }

    public int getThreshold() {
        return threshold.get(0);
    }

    public void setThreshold(final int threshold) {
        this.threshold.set(0, threshold);
    }

    public boolean getSkipInterfaceMethodParams() {
        return skipInterfaceMethodParams.get(0);
    }

    public void setSkipInterfaceMethodParams(final boolean skipInterfaceMethodParams) {
        this.skipInterfaceMethodParams.set(0, skipInterfaceMethodParams);
    }

    public boolean getSkipLambdaParams() {
        return skipLambdaParams.get(0);
    }

    public void setSkipLambdaParams(final boolean skipLambdaParams) {
        this.skipLambdaParams.set(0, skipLambdaParams);
    }
}
