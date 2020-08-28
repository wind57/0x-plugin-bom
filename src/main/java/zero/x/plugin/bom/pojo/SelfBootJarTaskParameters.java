package zero.x.plugin.bom.pojo;

import java.util.LinkedHashMap;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zero-x
 * <p>
 * The place where this plugin adds parameters that will eventually be
 * mapped to settings from : https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/#packaging-executable
 * Supported features that can be enabled are the properties of this class.
 */
@Getter
@Setter
public class SelfBootJarTaskParameters {

    // 1. "archiveFileName" from the delegate plugin task
    private String archiveFileName;

    // 2. "mainClassName" from the delegate plugin
    private String mainClassName;

    // 3. manifest {
    //        attributes(
    //                'Build-Revision': SomeRevision
    //        )
    //    }
    private LinkedHashMap<String, String> manifestAttributes;

}
