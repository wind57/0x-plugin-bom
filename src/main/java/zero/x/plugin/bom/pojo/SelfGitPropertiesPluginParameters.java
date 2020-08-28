package zero.x.plugin.bom.pojo;

import java.io.File;
import java.util.LinkedHashMap;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zero-x
 * <p>
 * The place where this plugin adds parameters that will eventually be
 * mapped to settings from : https://github.com/n0mer/gradle-git-properties.
 * Supported features that can be enabled are the properties of this class.
 */
@Getter
@Setter
public class SelfGitPropertiesPluginParameters {

    // 1. this holds all the possible "gitProperty" from the delegate plugin
    private LinkedHashMap<String, String> additionalGitProperties = new LinkedHashMap<>();

    // 2. "extProperty" from the delegate plugin
    private String extProperty;

    // 3. "dotGitDirectory" from the delegate plugin
    private File dotGitDirectory;

}
