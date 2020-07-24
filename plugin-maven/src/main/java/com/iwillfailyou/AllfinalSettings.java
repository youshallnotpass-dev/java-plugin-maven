package com.iwillfailyou;

import java.util.Arrays;
import java.util.List;

public final class AllfinalSettings {
    private final List<Integer> threshold;
    private final List<Boolean> skipInterfaceMethodParams;

    public AllfinalSettings() {
        this(
            Arrays.asList(0),
            Arrays.asList(true)
        );
    }

    public AllfinalSettings(
        final List<Integer> threshold,
        final List<Boolean> skipInterfaceMethodParams
    ) {
        this.threshold = threshold;
        this.skipInterfaceMethodParams = skipInterfaceMethodParams;
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
}
