package com.iwillfailyou.inspections;

import com.iwillfailyou.plugin.Inspection;

import java.util.List;

public interface InspectionSettings {
    void setDisabled(boolean disabled);
    void setExclude(List<String> exclude);
    void inheritExclude(List<String> exclude);
    void setThreshold(int threshold);
    Inspection inspection();
}
