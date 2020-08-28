package zero.x.plugin.bom.exported;

import com.google.cloud.tools.jib.gradle.JibPlugin;
import com.gorylenko.GenerateGitPropertiesTask;
import com.gorylenko.GitPropertiesPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.JavaPlugin;
import org.springframework.boot.gradle.plugin.SpringBootPlugin;
import zero.x.plugin.bom.util.Util;

/**
 * @author zero-x
 * <p>
 * this acts as a decorator for various plugins that are common accross multiple micro-services
 * and we want a common place to apply them.
 */
public class PluginEnhancer implements Plugin<Project> {

    @Override
    public void apply(Project project) {

        project.getLogger().info("0x-plugin-bom starting");

        project.getExtensions().create("pluginProperties", AutoConfigurePropertiesExtension.class);

        Task pluginPropertiesTask = project.getTasks().create("pluginPropertiesTask", AutoConfigurePropertiesTask.class);
        Task selfConfigureGitPropertiesTask = project.getTasks().create(
            "selfConfigureGitPropertiesTask",
            SelfGitPropertiesPluginTask.class
        );
        pluginPropertiesTask.setGroup("build");
        selfConfigureGitPropertiesTask.setGroup("build");

        project.getPluginManager().apply(GitPropertiesPlugin.class);
        project.getPluginManager().apply(SpringBootPlugin.class);
        project.getPluginManager().apply(JibPlugin.class);

        Util.printKnownExtensions(project);
        Util.printKnownTasks(project);

        ensureTaskRunsOnJavaClassesTask(project);

    }

    /**
     *
     */
    private static void ensureTaskRunsOnJavaClassesTask(Project project) {
        // if Java plugin is applied, execute this task automatically when "classes" task is executed
        // see https://guides.gradle.org/implementing-gradle-plugins/#reacting_to_plugins

        Task selfGitPropertiesTask = project.getTasks().getByPath(Util.SELF_GIT_PROPERTIES_CONFIGURATION_TASK);
        Task gitPropertiesTask = project.getTasks().getByName(Util.GIT_PROPERTIES_TASK_NAME);
        Task selfTask = project.getTasks().getByName(Util.SELF_TASK_NAME);

        project.getTasks().getByName(Util.GIT_PROPERTIES_TASK_NAME).dependsOn(selfGitPropertiesTask);
        project.getTasks().getByName(JavaPlugin.CLASSES_TASK_NAME).dependsOn(gitPropertiesTask);
        project.getTasks().getByName(JavaPlugin.CLASSES_TASK_NAME).dependsOn(selfTask);

        project.getPlugins().withType(JavaPlugin.class, javaPlugin -> {
            project.getGradle().projectsEvaluated(x -> {
                ((GenerateGitPropertiesTask) gitPropertiesTask).onJavaPluginAvailable();
            });
        });
    }


}
