package com.iwillfailyou;

import com.iwillfailyou.nullfree.NullfreeInspection;
import com.iwillfailyou.nullfree.sources.java.JavaSourceFileFactory;
import com.iwillfailyou.plugin.Inspection;
import com.iwillfailyou.plugin.IwfyException;
import com.iwillfailyou.plugin.IwfyPlugin;
import com.iwillfailyou.plugin.IwfyUrls;
import com.iwillfailyou.plugin.PublicInspection;
import com.iwillfailyou.staticfree.StaticfreeInspection;
import com.nikialeksey.goo.Goo;
import com.nikialeksey.goo.GooException;
import com.nikialeksey.goo.Origin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.cactoos.list.ListOf;

import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mojo(name = "iwillfailyou", threadSafe = true)
public class IwillfailyouMojo extends AbstractMojo {

    @Parameter(readonly = true, defaultValue = "${project.basedir}")
    private File baseDir;
    @Parameter(readonly = true, defaultValue = "false")
    private boolean offline;
    @Parameter(readonly = true)
    @Nullable
    private NullfreeSettings nullfree;
    @Parameter(readonly = true)
    @Nullable
    private StaticfreeSettings staticfree;

    @Override
    public void execute() throws MojoExecutionException {
        if (nullfree == null) {
            nullfree = new NullfreeSettings();
        }
        if (staticfree == null) {
            staticfree = new StaticfreeSettings();
        }

        final List<Inspection> inspections = new ListOf<>(
            new NullfreeInspection(
                new JavaSourceFileFactory(),
                nullfree.getSkipComparisions(),
                nullfree.getThreshold()
            ),
            new StaticfreeInspection(
                new com.iwillfailyou.staticfree.sources.java.JavaSourceFileFactory(),
                staticfree.getThreshold()
            )
        );
        try {
            final List<Inspection> wrapped;
            if (offline) {
                wrapped = inspections;
            } else {
                try {
                    final Origin origin = new Goo(
                        new File(baseDir, ".git")
                    ).origin();
                    wrapped = new ArrayList<>();
                    for (final Inspection inspection : inspections) {
                        wrapped.add(
                            new PublicInspection(
                                new IwfyUrls(
                                    origin,
                                    "https://www.iwillfailyou.com"
                                ),
                                inspection
                            )
                        );
                    }
                } catch (GooException e) {
                    throw new IwfyException(
                        "Could not get the origin for git repo. You can " +
                            "use offline version, if you have not git " +
                            "repo yet, just set the <offline>true</offline>",
                        e
                    );
                }
            }
            new IwfyPlugin(
                new MavenUi(getLog()),
                baseDir,
                wrapped
            ).run();
        } catch (final IwfyException e) {
            throw new MojoExecutionException(
                "Can not make the iwillfailyou analysis.",
                e
            );
        }
    }
}
