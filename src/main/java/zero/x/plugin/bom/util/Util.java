package zero.x.plugin.bom.util;

import org.gradle.api.Project;

/**
 * @author zero-x
 */
public final class Util {

    public static final String ADOPT_JDK_14 = "adoptopenjdk/openjdk14:latest";

    public static final String SELF_GIT_PROPERTIES_CONFIGURATION_TASK = "selfConfigureGitPropertiesTask";

    public static final String SELF_TASK_NAME = "pluginPropertiesTask";

    public static final String GIT_PROPERTIES_EXT_NAME = "exposedGitProperties";

    // see https://github.com/n0mer/gradle-git-properties
    public static final String GIT_PROPERTIES_TASK_NAME = "generateGitProperties";

    public static final String BOOT_RUN_TASK_NAME = "bootRun";

    public static final String BOOT_JAR_TASK_NAME = "bootJar";

    /**
     * a small debug utility; run with "gradle build --info". This can be used
     * to fail fast in case we upgrade one of the dependent plugins and extension name changes
     */
    public static void printKnownExtensions(Project project) {
        project.getExtensions().getExtensionsSchema().forEach(x -> {
            project.getLogger().info(" ==== extension : " + x.getName());
        });
    }

    /**
     * a small debug utility; run with "gradle build --info". This can be used
     * to fail fast in case we upgrade one of the dependent plugins and task name changes
     */
    public static void printKnownTasks(Project project) {
        project.getTasks().getCollectionSchema().getElements().forEach(x -> {
            project.getLogger().info(" ==== task : " + x.getName());
        });
    }
}
