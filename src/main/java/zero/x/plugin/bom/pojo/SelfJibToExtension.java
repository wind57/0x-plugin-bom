package zero.x.plugin.bom.pojo;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zero-x
 * <p>
 * Properties that are supported by Jib's "to" extension.
 */
@Getter
@Setter
public class SelfJibToExtension {

    private String image;
    private Set<String> tags;

}
