package zero.x.plugin.bom.pojo;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zero-x
 * <p>
 * Properties that are supported by Jib's "container" extension.
 */
@Getter
@Setter
public class SelfJibContainerParameters {

    private String mainClass;
    private Set<String> ports;
    private Set<String> jvmFlags;
    private boolean defaultVMFlags = true;

}
