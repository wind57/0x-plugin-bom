package zero.x.plugin.bom.exported;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import zero.x.plugin.bom.configurers.JibBuildDockerTaskConfiguration;
import zero.x.plugin.bom.configurers.SpringBootJarTaskConfiguration;
import zero.x.plugin.bom.configurers.SpringBootRunTaskConfiguration;

/**
 * @author zero-x
 * <p>
 * A task that kicks in on "classes" java-plugin add-on.
 * It auto-configures a few other plugins.
 */
public class AutoConfigurePropertiesTask extends DefaultTask {

    @TaskAction
    void execute() {

        getLogger().info("kicking off properties task");
        SpringBootRunTaskConfiguration.configure(getProject());
        SpringBootJarTaskConfiguration.configure(getProject());
        JibBuildDockerTaskConfiguration.configure(getProject());
    }

}
