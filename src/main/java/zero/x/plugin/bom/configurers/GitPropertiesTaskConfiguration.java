package zero.x.plugin.bom.configurers;

import com.gorylenko.GitPropertiesPluginExtension;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import org.gradle.api.Project;
import zero.x.plugin.bom.exported.AutoConfigurePropertiesExtension;
import zero.x.plugin.bom.pojo.SelfGitPropertiesPluginParameters;
import zero.x.plugin.bom.util.Util;

/**
 * @author zero-x
 * programatic configuration of git-properties-gradle-plugin via the {@link SelfGitPropertiesPluginParameters}.
 */
public final class GitPropertiesTaskConfiguration {

    public static void configure(Project project) {

        SelfGitPropertiesPluginParameters gitPluginParameters = project.getExtensions()
                                                                       .getByType(AutoConfigurePropertiesExtension.class)
                                                                       .getGitParameters();

        GitPropertiesPluginExtension extension = project.getExtensions()
                                                        .getByType(GitPropertiesPluginExtension.class);

        Map<String, String> additionalGitProperties = gitPluginParameters.getAdditionalGitProperties();
        Map<String, Object> existingProperties = gitBuildVersionDefaulter(project);
        existingProperties.putAll(additionalGitProperties);

        // customProperty: gitProperty
        extension.setCustomProperties(existingProperties);

        // extProperty
        if (gitPluginParameters.getExtProperty() != null) {
            extension.setExtProperty(gitPluginParameters.getExtProperty());
        } else {
            extension.setExtProperty(Util.GIT_PROPERTIES_EXT_NAME);
        }

        // dotGitDirectory
        if (gitPluginParameters.getDotGitDirectory() != null) {
            extension.setDotGitDirectory(gitPluginParameters.getDotGitDirectory());
        } else {
            extension.setDotGitDirectory(project.getRootDir());
        }

        project.getLogger().info(settings(extension));
    }

    /**
     * "git.build.version" defaulter.
     */
    private static Map<String, Object> gitBuildVersionDefaulter(Project project) {
        // mutable on purpose
        Map<String, Object> map = new HashMap<>();

        String version;
        if (project.getParent() == null) {
            project.getLogger().info(
                " ==== project " + project.getName() + " has no parent. Will provide 'git.build.version' from itself"
            );
            version = (String) project.getVersion();
        } else {
            project.getLogger().info(
                " ==== project " + project.getName() + " has a parent. Will provide 'git.build.version' from it"
            );
            version = (String) project.getParent().getVersion();
        }

        map.put("git.build.version", version);
        return map;
    }

    private static String settings(GitPropertiesPluginExtension extension) {

        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(" ==== git-properties-plugin configured with : ");
        joiner.add("     gitProperty(ies) : " + extension.getCustomProperties());
        joiner.add("     extProperty      : " + extension.getExtProperty());
        joiner.add("     dotGitDirectory  : " + extension.getDotGitDirectory());

        return joiner.toString();

    }

}
