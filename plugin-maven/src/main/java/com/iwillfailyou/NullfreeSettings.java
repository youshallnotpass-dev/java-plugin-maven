package com.iwillfailyou;

import com.iwillfailyou.inspection.sources.java.JavaSourceMask;
import com.iwillfailyou.inspections.nullfree.Nullfree;
import com.iwillfailyou.plugin.Inspection;

import java.util.Arrays;
import java.util.List;

public final class NullfreeSettings {
    private final List<Boolean> disabled;
    private final List<Integer> threshold;
    private final List<Boolean> skipComparisons;

    public NullfreeSettings() {
        this(
            Arrays.asList(false),
            Arrays.asList(0),
            Arrays.asList(false)
        );
    }

    public NullfreeSettings(
        final List<Boolean> disabled,
        final List<Integer> threshold,
        final List<Boolean> skipComparisons
    ) {
        this.disabled = disabled;
        this.threshold = threshold;
        this.skipComparisons = skipComparisons;
    }

    public void setDisabled(final boolean disabled) {
        this.disabled.set(0, disabled);
    }

    public void setSkipComparisons(final boolean skipComparisons) {
        this.skipComparisons.set(0, skipComparisons);
    }

    public int setThreshold(final int threshold) {
        return this.threshold.set(0, threshold);
    }

    public Inspection inspection() {
        final Inspection inspection;
        if (disabled.get(0)) {
            inspection = new Inspection.Fake();
        } else {
            inspection = new Nullfree(
                new JavaSourceMask(),
                skipComparisons.get(0),
                threshold.get(0)
            );
        }
        return inspection;
    }
}
