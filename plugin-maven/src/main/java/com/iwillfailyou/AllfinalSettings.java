package com.iwillfailyou;

import com.iwillfailyou.inspection.sources.java.JavaSourceMask;
import com.iwillfailyou.inspections.allfinal.Allfinal;
import com.iwillfailyou.plugin.Inspection;

import java.util.Arrays;
import java.util.List;

public final class AllfinalSettings {
    private final List<Boolean> disabled;
    private final List<Integer> threshold;
    private final List<Boolean> skipInterfaceMethodParams;
    private final List<Boolean> skipLambdaParams;
    private final List<Boolean> skipCatchParams;

    public AllfinalSettings() {
        this(
            Arrays.asList(false),
            Arrays.asList(0),
            Arrays.asList(true),
            Arrays.asList(false),
            Arrays.asList(false)
        );
    }

    public AllfinalSettings(
        final List<Boolean> disabled,
        final List<Integer> threshold,
        final List<Boolean> skipInterfaceMethodParams,
        final List<Boolean> skipLambdaParams,
        final List<Boolean> skipCatchParams
    ) {
        this.disabled = disabled;
        this.threshold = threshold;
        this.skipInterfaceMethodParams = skipInterfaceMethodParams;
        this.skipLambdaParams = skipLambdaParams;
        this.skipCatchParams = skipCatchParams;
    }

    public void setDisabled(final boolean disabled) {
        this.disabled.set(0, disabled);
    }

    public void setThreshold(final int threshold) {
        this.threshold.set(0, threshold);
    }

    public void setSkipInterfaceMethodParams(final boolean skipInterfaceMethodParams) {
        this.skipInterfaceMethodParams.set(0, skipInterfaceMethodParams);
    }

    public void setSkipLambdaParams(final boolean skipLambdaParams) {
        this.skipLambdaParams.set(0, skipLambdaParams);
    }

    public void setSkipCatchParams(final boolean skipCatchParams) {
        this.skipCatchParams.set(0, skipCatchParams);
    }

    public Inspection inspection() {
        final Inspection inspection;
        if (disabled.get(0)) {
            inspection = new Inspection.Fake();
        } else {
            inspection = new Allfinal(
                new JavaSourceMask(),
                threshold.get(0),
                skipInterfaceMethodParams.get(0),
                skipLambdaParams.get(0),
                skipCatchParams.get(0)
            );
        }
        return inspection;
    }
}
