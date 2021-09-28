package dev.youshallnotpass;

import dev.youshallnotpass.plugin.Ui;
import org.apache.maven.plugin.logging.Log;

public final class MavenUi implements Ui {

    private final Log log;

    public MavenUi(final Log log) {
        this.log = log;
    }

    @Override
    public void println(final String text) {
        log.warn(text);
    }
}
