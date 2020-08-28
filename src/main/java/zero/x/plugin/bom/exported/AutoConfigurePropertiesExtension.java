package zero.x.plugin.bom.exported;

import javax.inject.Inject;
import org.gradle.api.Action;
import org.gradle.api.model.ObjectFactory;
import zero.x.plugin.bom.pojo.SelfBootJarTaskParameters;
import zero.x.plugin.bom.pojo.SelfBootRunTaskParameters;
import zero.x.plugin.bom.pojo.SelfGitPropertiesPluginParameters;
import zero.x.plugin.bom.pojo.SelfJibPluginParameters;

/**
 * @author zero-x
 */
public class AutoConfigurePropertiesExtension {

    private final SelfGitPropertiesPluginParameters gitParameters;
    private final SelfBootRunTaskParameters bootRunParameters;
    private final SelfBootJarTaskParameters bootJarParameters;
    private final SelfJibPluginParameters jibPluginParameters;

    @Inject
    public AutoConfigurePropertiesExtension(ObjectFactory objectFactory) {
        gitParameters = objectFactory.newInstance(SelfGitPropertiesPluginParameters.class);
        bootRunParameters = objectFactory.newInstance(SelfBootRunTaskParameters.class);
        bootJarParameters = objectFactory.newInstance(SelfBootJarTaskParameters.class);
        jibPluginParameters = objectFactory.newInstance(SelfJibPluginParameters.class);
    }

    public void git(Action<? super SelfGitPropertiesPluginParameters> action) {
        action.execute(gitParameters);
    }

    public void run(Action<? super SelfBootRunTaskParameters> action) {
        action.execute(bootRunParameters);
    }

    public void jar(Action<? super SelfBootJarTaskParameters> action) {
        action.execute(bootJarParameters);
    }

    public void jib(Action<? super SelfJibPluginParameters> action) {
        action.execute(jibPluginParameters);
    }

    public SelfGitPropertiesPluginParameters getGitParameters() {
        return gitParameters;
    }

    public SelfBootRunTaskParameters getBootRunParameters() {
        return bootRunParameters;
    }

    public SelfBootJarTaskParameters getBootJarParameters() {
        return bootJarParameters;
    }

    public SelfJibPluginParameters getJibParameters() {
        return jibPluginParameters;
    }
}
