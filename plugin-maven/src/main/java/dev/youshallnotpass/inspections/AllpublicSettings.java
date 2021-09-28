package dev.youshallnotpass.inspections;

import dev.youshallnotpass.inspections.allpublic.Allpublic;
import dev.youshallnotpass.plugin.Inspection;

import java.util.List;

public final class AllpublicSettings implements InspectionSettings {
    private final CommonSettings common;

    public AllpublicSettings() {
        this(
            new CommonSettings()
        );
    }

    public AllpublicSettings(
        final CommonSettings common
    ) {
        this.common = common;
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

    @Override
    public Inspection inspection() {
        final Inspection inspection;
        if (common.getDisabled()) {
            inspection = new Inspection.Fake();
        } else {
            inspection = new Allpublic(
                common.mask(),
                common.getThreshold()
            );
        }
        return inspection;
    }
}
