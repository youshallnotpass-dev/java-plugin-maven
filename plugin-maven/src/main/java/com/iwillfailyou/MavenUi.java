package com.iwillfailyou;

import com.iwillfailyou.plugin.Ui;
import org.apache.maven.plugin.logging.Log;

public class MavenUi implements Ui {

    private final Log log;

    public MavenUi(final Log log) {
        this.log = log;
    }

    @Override
    public void println(final String text) {
        log.warn(text);
    }
}
