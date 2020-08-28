package zero.x.plugin.bom.pojo;

import javax.inject.Inject;
import lombok.Getter;
import org.gradle.api.Action;
import org.gradle.api.model.ObjectFactory;

/**
 * @author zero-x
 */
@Getter
public class SelfJibPluginParameters {

    private final SelfJibFromExtension from;
    private final SelfJibToExtension to;
    private final SelfJibContainerParameters container;

    @Inject
    public SelfJibPluginParameters(ObjectFactory objectFactory) {
        from = objectFactory.newInstance(SelfJibFromExtension.class);
        to = objectFactory.newInstance(SelfJibToExtension.class);
        container = objectFactory.newInstance(SelfJibContainerParameters.class);
    }

    public void from(Action<? super SelfJibFromExtension> action) {
        action.execute(from);
    }

    public void to(Action<? super SelfJibToExtension> action) {
        action.execute(to);
    }

    public void container(Action<? super SelfJibContainerParameters> action) {
        action.execute(container);
    }
}
