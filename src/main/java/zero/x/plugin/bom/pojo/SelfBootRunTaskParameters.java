package zero.x.plugin.bom.pojo;

import java.util.LinkedHashMap;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zero-x
 * <p>
 * The place where this plugin adds parameters that will eventually be
 * mapped to settings from : https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/#running-your-application
 * Supported features that can be enabled are the properties of this class.
 */
@Getter
@Setter
public class SelfBootRunTaskParameters {

    // 1. "main" from the delegate plugin
    private String main;

    // 2. "optimizedLaunch" from the delegate plugin
    private boolean optimizedLaunch;

    // 3. "systemProperties" from the delegate plugin
    private LinkedHashMap<String, String> systemProperties;

}
