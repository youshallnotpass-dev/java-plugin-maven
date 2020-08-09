package com.iwillfailyou.inspections;

import com.iwillfailyou.inspection.sources.ExcludeSourceMask;
import com.iwillfailyou.inspection.sources.PathSourceMask;
import com.iwillfailyou.inspection.sources.SourceMask;
import com.iwillfailyou.inspection.sources.java.JavaSourceMask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class CommonSettings {
    private final AtomicBoolean disabled;
    private final List<String> exclude;
    private final AtomicBoolean excludeOverridden;
    private final AtomicInteger threshold;

    public CommonSettings() {
        this(
            new AtomicBoolean(false),
            new ArrayList<>(),
            new AtomicBoolean(false),
            new AtomicInteger(0)
        );
    }

    public CommonSettings(
        final AtomicBoolean disabled,
        final List<String> exclude,
        final AtomicBoolean excludeOverridden,
        final AtomicInteger threshold
    ) {
        this.disabled = disabled;
        this.exclude = exclude;
        this.excludeOverridden = excludeOverridden;
        this.threshold = threshold;
    }

    public boolean getDisabled() {
        return this.disabled.get();
    }

    public void setDisabled(final boolean disabled) {
        this.disabled.set(disabled);
    }

    public int getThreshold() {
        return this.threshold.get();
    }

    public void setThreshold(final int threshold) {
        this.threshold.set(threshold);
    }

    public SourceMask mask() {
        return new ExcludeSourceMask(
            new PathSourceMask(exclude.toArray(new String[0])),
            new JavaSourceMask()
        );
    }

    public void setExclude(final List<String> exclude) {
        this.exclude.clear();
        this.exclude.addAll(exclude);
        this.excludeOverridden.set(true);
    }

    public void inheritExclude(final List<String> exclude) {
        if (!excludeOverridden.get()) {
            this.exclude.clear();
            this.exclude.addAll(exclude);
        }
    }
}
