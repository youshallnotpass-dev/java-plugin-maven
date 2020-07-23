package com.iwillfailyou;

import java.util.Arrays;
import java.util.List;

public final class AllfinalSettings {
    private final List<Integer> threshold;

    public AllfinalSettings() {
        this(
            Arrays.asList(0)
        );
    }

    public AllfinalSettings(
        final List<Integer> threshold
    ) {
        this.threshold = threshold;
    }

    public int getThreshold() {
        return threshold.get(0);
    }

    public int setThreshold(final int threshold) {
        return this.threshold.set(0, threshold);
    }
}
