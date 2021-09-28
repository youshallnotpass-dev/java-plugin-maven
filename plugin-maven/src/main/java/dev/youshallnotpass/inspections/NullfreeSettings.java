package dev.youshallnotpass.inspections;

import dev.youshallnotpass.inspections.nullfree.Nullfree;
import dev.youshallnotpass.plugin.Inspection;

import java.util.Arrays;
import java.util.List;

public final class NullfreeSettings implements InspectionSettings {
    private final CommonSettings common;
    private final List<Boolean> skipComparisons;

    public NullfreeSettings() {
        this(
            new CommonSettings(),
            Arrays.asList(false)
        );
    }

    public NullfreeSettings(
        final CommonSettings common,
        final List<Boolean> skipComparisons
    ) {
        this.common = common;
        this.skipComparisons = skipComparisons;
    }

    @Override
    public void setDisabled(final boolean disabled) {
        this.common.setDisabled(disabled);
    }

    @Override
    public void setExclude(final List<String> exclude) {
        this.common.setExclude(exclude);
    }

    @Override
    public void inheritExclude(final List<String> exclude) {
        this.common.inheritExclude(exclude);
    }

    @Override
    public void setThreshold(final int threshold) {
        this.common.setThreshold(threshold);
    }

    public void setSkipComparisons(final boolean skipComparisons) {
        this.skipComparisons.set(0, skipComparisons);
    }

    @Override
    public Inspection inspection() {
        final Inspection inspection;
        if (common.getDisabled()) {
            inspection = new Inspection.Fake();
        } else {
            inspection = new Nullfree(
                common.mask(),
                skipComparisons.get(0),
                common.getThreshold()
            );
        }
        return inspection;
    }
}
