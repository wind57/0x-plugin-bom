package zero.x.plugin.bom.configurers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtraPropertiesExtension;
import org.springframework.boot.gradle.tasks.bundling.BootJar;
import zero.x.plugin.bom.exported.AutoConfigurePropertiesExtension;
import zero.x.plugin.bom.pojo.SelfBootJarTaskParameters;
import zero.x.plugin.bom.util.Util;

/**
 * @author zero-x
 * configure "bootJar" task from {@link SelfBootJarTaskParameters}
 */
public final class SpringBootJarTaskConfiguration {

    public static void configure(Project project) {

        SelfBootJarTaskParameters bootJarParameters =
            project.getExtensions()
                   .getByType(AutoConfigurePropertiesExtension.class)
                   .getBootJarParameters();

        BootJar bootJar = (BootJar) project.getTasks().getByName(Util.BOOT_JAR_TASK_NAME);

        // 1. mainClassName
        if (bootJarParameters.getMainClassName() != null) {
            bootJar.setMainClassName(bootJarParameters.getMainClassName());
        }

        // 2. archiveName - it's deprecated, but there is not replacement in the API yet :|
        if (bootJarParameters.getArchiveFileName() != null) {
            bootJar.setArchiveName(bootJarParameters.getArchiveFileName());
        } else {
            bootJar.setArchiveName("app.jar");
        }

        // manifest attributes
        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>)
            project.getExtensions()
                   .getByType(ExtraPropertiesExtension.class)
                   .getProperties()
                   .get(Util.GIT_PROPERTIES_EXT_NAME);
        Map<String, String> interpolated = new HashMap<>();
        interpolated.put("Build-Version", map.get("git.commit.id.abbrev"));
        if (bootJarParameters.getManifestAttributes() != null) {
            bootJarParameters.getManifestAttributes()
                             .forEach((key, value) -> {
                                 interpolated.put(key, Optional.ofNullable(map.get(value)).orElse(value));
                             });

        }

        bootJar.getManifest().attributes(interpolated);
        project.getLogger().info(settings(bootJar));

    }

    private static String settings(BootJar task) {

        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(" ==== 'bootJar' configured with : ");
        joiner.add("     mainClassName       : " + task.getMainClassName());
        joiner.add("     archiveName         : " + task.getArchiveName());
        joiner.add("     manifestAttributes  : " + task.getManifest().getAttributes().entrySet().toString());

        return joiner.toString();

    }

}
