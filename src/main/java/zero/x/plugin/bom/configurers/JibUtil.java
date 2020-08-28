package zero.x.plugin.bom.configurers;

import com.google.cloud.tools.jib.gradle.JibExtension;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.gradle.api.Project;
import zero.x.plugin.bom.pojo.SelfJibPluginParameters;

/**
 * @author zero-x
 */
final class JibUtil {

    private static final Set<String> DEFAULT_JVM_FLAGS = Set.of(
        "-Duser.timezone=UTC",
        "-XX:+UnlockExperimentalVMOptions",
        "-XX:InitialRAMPercentage=70",
        "-XX:InitialHeapSize=0",
        "-XX:MaxRAMPercentage=70",
        "-XX:+UseContainerSupport",
        "-XX:-AlwaysPreTouch",
        "-XX:+UseNUMA",
        "-XX:+UseBiasedLocking",
        "-XX:+DisableExplicitGC",
        "-XX:+ExplicitGCInvokesConcurrent",
        "-XX:+UseDynamicNumberOfGCThreads",
        "-XX:+UseCountedLoopSafepoints",
        "-XX:LoopStripMiningIter=500",
        "-XX:LoopStripMiningIterShortLoop=50"
    );

    static JibExtension configureJibFrom(
        JibExtension jibExtension, SelfJibPluginParameters selfJibParameters, Project project) {
        // 1. "from.image"
        jibExtension.getFrom().setImage(selfJibParameters.getFrom().getImage());
        project.getLogger().debug("==== jib.from.image = {}", jibExtension.getFrom().getImage());
        return jibExtension;
    }

    static JibExtension configureJibToTags(
        JibExtension jibExtension, SelfJibPluginParameters selfJibParameters, Project project) {
        // 3. "to.tags"
        // add default tags and provided ones
        Set<String> tags = new HashSet<>(defaultTags(project));
        if (selfJibParameters.getTo().getTags() != null) {
            tags.addAll(selfJibParameters.getTo().getTags());
        }

        jibExtension.getTo().setTags(tags);
        return jibExtension;
    }

    static JibExtension configureJibToImage(
        JibExtension jibExtension, SelfJibPluginParameters selfJibParameters, Project project) {

        if (selfJibParameters.getTo().getImage() != null) {
            String toImage = selfJibParameters.getTo().getImage();
            project.getLogger().info("==== 'jib.to.image' provided as : {}", toImage);
            jibExtension.getTo().setImage(toImage);
            return jibExtension;
        }

        throw new RuntimeException("jib.to.image can not be computed. read the docs on how it is supposed to be set-up");

    }

    static JibExtension configureJibContainer(
        JibExtension jibExtension, SelfJibPluginParameters selfJibParameters, Project project) {

        String mainClass = selfJibParameters.getContainer().getMainClass();

        if (mainClass == null || mainClass.isEmpty()) {
            throw new RuntimeException("mainClass is not provided in jib config, but must be");
        }

        // 1. 'mainClass'
        jibExtension.getContainer().setMainClass(mainClass);

        // 2. "ports"
        Set<String> ports = selfJibParameters.getContainer().getPorts();
        if (ports != null && !ports.isEmpty()) {
            jibExtension.getContainer().setPorts(new ArrayList<>(ports));
        }

        // 3. "jvmFlags"
        if (selfJibParameters.getContainer().isDefaultVMFlags()) {
            project.getLogger().info(" ==== default JVM arguments will be used, read the docs on what these are please");
            jibExtension.getContainer().setJvmFlags(new ArrayList<>(DEFAULT_JVM_FLAGS));
        } else {
            Set<String> jvmFlags = selfJibParameters.getContainer().getJvmFlags();
            if (jvmFlags != null && !jvmFlags.isEmpty()) {
                jibExtension.getContainer().setJvmFlags(new ArrayList<>(jvmFlags));
            }
        }
        return jibExtension;
    }

    private static Set<String> defaultTags(Project project) {

        if (project.getParent() != null) {
            return Set.of((String) project.getParent().getVersion());
        }

        return Set.of((String) project.getVersion());

    }
}
