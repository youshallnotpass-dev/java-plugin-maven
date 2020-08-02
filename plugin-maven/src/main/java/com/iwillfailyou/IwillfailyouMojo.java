package com.iwillfailyou;

import com.iwillfailyou.plugin.Inspection;
import com.iwillfailyou.plugin.IwfyException;
import com.iwillfailyou.plugin.IwfyPlugin;
import com.iwillfailyou.plugin.IwfyUrls;
import com.iwillfailyou.plugin.PublicInspection;
import com.nikialeksey.goo.Goo;
import com.nikialeksey.goo.GooException;
import com.nikialeksey.goo.Origin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.cactoos.list.ListOf;

import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mojo(name = "iwillfailyou", threadSafe = true)
public final class IwillfailyouMojo extends AbstractMojo {

    @Parameter(readonly = true, defaultValue = "${project.basedir}")
    @SuppressWarnings("allfinal")
    private File baseDir;
    @Parameter(readonly = true, defaultValue = "false")
    @SuppressWarnings("allfinal")
    private boolean offline;
    @Parameter(readonly = true)
    @Nullable
    @SuppressWarnings("allfinal")
    private NullfreeSettings nullfree;
    @Parameter(readonly = true)
    @Nullable
    @SuppressWarnings("allfinal")
    private StaticfreeSettings staticfree;
    @Parameter(readonly = true)
    @Nullable
    @SuppressWarnings("allfinal")
    private AllfinalSettings allfinal;
    @Parameter(readonly = true)
    @Nullable
    @SuppressWarnings("allfinal")
    private AllpublicSettings allpublic;

    @Override
    public void execute() throws MojoExecutionException {
        if (nullfree == null) {
            nullfree = new NullfreeSettings();
        }
        if (staticfree == null) {
            staticfree = new StaticfreeSettings();
        }
        if (allfinal == null) {
            allfinal = new AllfinalSettings();
        }
        if (allpublic == null) {
            allpublic = new AllpublicSettings();
        }

        final List<Inspection> inspections = new ListOf<>(
            nullfree.inspection(),
            staticfree.inspection(),
            allfinal.inspection(),
            allpublic.inspection()
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
                } catch (final GooException e) {
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
