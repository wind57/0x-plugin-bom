package zero.x.plugin.bom.configurers;

import com.google.cloud.tools.jib.gradle.BuildDockerTask;
import com.google.cloud.tools.jib.gradle.JibExtension;
import com.google.cloud.tools.jib.gradle.JibPlugin;
import java.util.StringJoiner;
import org.gradle.api.Project;
import zero.x.plugin.bom.exported.AutoConfigurePropertiesExtension;
import zero.x.plugin.bom.pojo.SelfJibPluginParameters;

/**
 * @author zero-x
 * <p>
 * configure {@link com.google.cloud.tools.jib.gradle.BuildDockerTask} from {@link SelfJibPluginParameters}
 */
public final class JibBuildDockerTaskConfiguration {

    public static void configure(Project project) {

        SelfJibPluginParameters selfJibParameters = project.getExtensions()
                                                           .getByType(AutoConfigurePropertiesExtension.class)
                                                           .getJibParameters();

        JibExtension jibExtension = project.getExtensions()
                                           .getByType(JibExtension.class);

        JibUtil.configureJibFrom(jibExtension, selfJibParameters, project);
        JibUtil.configureJibToTags(jibExtension, selfJibParameters, project);
        JibUtil.configureJibToImage(jibExtension, selfJibParameters, project);
        JibUtil.configureJibContainer(jibExtension, selfJibParameters, project);

        BuildDockerTask buildDockerTask = (BuildDockerTask)
            project.getTasks().getByName(JibPlugin.BUILD_DOCKER_TASK_NAME);

        buildDockerTask.setJibExtension(jibExtension);

        project.getLogger().info(settings(jibExtension));

    }

    private static String settings(JibExtension jibExtension) {

        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(" ==== 'JIB' configured with : ");
        joiner.add("     from.image          : " + jibExtension.getFrom().getImage());
        joiner.add("     to.image            : " + jibExtension.getTo().getImage());
        joiner.add("     to.tags             : " + jibExtension.getTo().getTags());
        joiner.add("     container.mainClass : " + jibExtension.getContainer().getMainClass());
        joiner.add("     container.ports     : " + jibExtension.getContainer().getPorts());
        joiner.add("     container.jmvFlags  : " + jibExtension.getContainer().getJvmFlags());

        return joiner.toString();

    }


}
