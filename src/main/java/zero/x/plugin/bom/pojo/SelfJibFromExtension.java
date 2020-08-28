package zero.x.plugin.bom.pojo;

import lombok.Getter;
import lombok.Setter;
import zero.x.plugin.bom.util.Util;

/**
 * @author zero-x
 * <p>
 * Properties that are supported by Jib's "from" extension.
 */
@Getter
@Setter
public class SelfJibFromExtension {

    // 1. "from::image"
    private String image = Util.ADOPT_JDK_14;

}
