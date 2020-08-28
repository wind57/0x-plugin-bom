package zero.x.plugin.bom.exported;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import zero.x.plugin.bom.configurers.GitPropertiesTaskConfiguration;
import zero.x.plugin.bom.pojo.SelfGitPropertiesPluginParameters;

/**
 * @author zero-x
 * a task that configures git-properties-plugin via the {@link SelfGitPropertiesPluginParameters}
 * <p>
 * This tasks needs to run first, before any other, as it sets some properties in the :
 * <p>
 * ext {
 * <p>
 * }
 * <p>
 * part of gradle; that is later read by spring-boot.
 */
public class SelfGitPropertiesPluginTask extends DefaultTask {

    @TaskAction
    void execute() {
        getLogger().info("kicking off self-git-properties task");
        GitPropertiesTaskConfiguration.configure(getProject());
    }

}
