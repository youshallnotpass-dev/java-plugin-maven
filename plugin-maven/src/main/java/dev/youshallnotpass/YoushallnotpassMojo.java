package dev.youshallnotpass;

import dev.youshallnotpass.inspections.AllfinalSettings;
import dev.youshallnotpass.inspections.AllpublicSettings;
import dev.youshallnotpass.inspections.InheritancefreeSettings;
import dev.youshallnotpass.inspections.InspectionSettings;
import dev.youshallnotpass.inspections.NoMultipleReturnSettings;
import dev.youshallnotpass.inspections.NullfreeSettings;
import dev.youshallnotpass.inspections.SetterFreeSettings;
import dev.youshallnotpass.inspections.StaticfreeSettings;
import com.nikialeksey.goo.Goo;
import com.nikialeksey.goo.GooException;
import com.nikialeksey.goo.Origin;
import dev.youshallnotpass.plugin.Inspection;
import dev.youshallnotpass.plugin.PublicInspection;
import dev.youshallnotpass.plugin.YsnpException;
import dev.youshallnotpass.plugin.YsnpPlugin;
import dev.youshallnotpass.plugin.YsnpUrls;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.cactoos.func.SolidFunc;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.ListOf;

import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mojo(name = "youshallnotpass", threadSafe = true)
@SuppressWarnings("inheritancefree")
public final class YoushallnotpassMojo extends AbstractMojo {

    @Parameter(readonly = true, defaultValue = "${project.basedir}")
    @SuppressWarnings("allfinal")
    private File baseDir;
    @Parameter(readonly = true, defaultValue = "false")
    @SuppressWarnings("allfinal")
    private boolean offline;
    @Parameter(readonly = true, defaultValue = "[]")
    @SuppressWarnings("allfinal")
    private List<String> exclude;
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
    @Parameter(readonly = true)
    @Nullable
    @SuppressWarnings("allfinal")
    private SetterFreeSettings setterfree;
    @Parameter(readonly = true)
    @Nullable
    @SuppressWarnings("allfinal")
    private NoMultipleReturnSettings nomultiplereturn;
    @Parameter(readonly = true)
    @Nullable
    @SuppressWarnings("allfinal")
    private InheritancefreeSettings inheritancefree;

    @Override
    public void execute() throws MojoExecutionException {
        if (exclude == null) {
            exclude = new ArrayList<>();
        }
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
        if (setterfree == null) {
            setterfree = new SetterFreeSettings();
        }
        if (nomultiplereturn == null) {
            nomultiplereturn = new NoMultipleReturnSettings();
        }
        if (inheritancefree == null) {
            inheritancefree = new InheritancefreeSettings();
        }

        final List<InspectionSettings> inspectionSettings = new ListOf<>(
            nullfree,
            staticfree,
            allfinal,
            allpublic,
            setterfree,
            nomultiplereturn,
            inheritancefree
        );

        for (final InspectionSettings inspectionSetting : inspectionSettings) {
            inspectionSetting.inheritExclude(exclude);
        }
        final List<Inspection> inspections = new ListOf<>(
            new Mapped<>(
                new SolidFunc<>(InspectionSettings::inspection),
                inspectionSettings
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
                                new YsnpUrls(
                                    origin,
                                    "https://www.youshallnotpass.dev"
                                ),
                                inspection
                            )
                        );
                    }
                } catch (final GooException e) {
                    throw new YsnpException(
                        "Could not get the origin for git repo. You can " +
                            "use offline version, if you have not git " +
                            "repo yet, just set the <offline>true</offline>",
                        e
                    );
                }
            }
            new YsnpPlugin(
                new MavenUi(getLog()),
                baseDir,
                wrapped
            ).run();
        } catch (final YsnpException e) {
            throw new MojoExecutionException(
                "Can not make the youshallnotpass analysis.",
                e
            );
        }
    }
}
