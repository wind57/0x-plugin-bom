package zero.x.plugin.bom.configurers;

import java.util.LinkedHashMap;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.springframework.boot.gradle.tasks.run.BootRun;
import zero.x.plugin.bom.exported.AutoConfigurePropertiesExtension;
import zero.x.plugin.bom.pojo.SelfBootRunTaskParameters;
import zero.x.plugin.bom.util.Util;

/**
 * @author zero-x
 * configure "bootRun" task from spring boot plugin via {@link SelfBootRunTaskParameters}
 */
public class SpringBootRunTaskConfiguration {

    public static void configure(Project project) {

        SelfBootRunTaskParameters bootRunProperties = project.getExtensions()
                                                             .getByType(AutoConfigurePropertiesExtension.class)
                                                             .getBootRunParameters();

        Task bootRunTask = project.getTasks()
                                  .getByName(Util.BOOT_RUN_TASK_NAME);
        BootRun bootRun = (BootRun) bootRunTask;

        // 1. "main".
        // Read spring-doc: if this property is not specified a class that has a "public static void main[String [] args]"
        // will be searched and set as "main"
        if (bootRunProperties.getMain() != null) {
            bootRun.setMain(bootRunProperties.getMain());
        }

        // 2. "systemProperties"
        LinkedHashMap<String, String> bootSystemProperties = bootRunProperties.getSystemProperties();
        LinkedHashMap<String, String> map = System.getProperties().entrySet().stream().collect(
            Collectors.toMap(
                e -> e.getKey().toString(),
                e -> e.getValue().toString(),
                (left, right) -> right, // take the latest one
                LinkedHashMap::new
            )
        );

        if (bootSystemProperties != null) {
            map.putAll(bootSystemProperties);
        }

        bootRun.setSystemProperties(map);

        // 3. optimizedLaunch
        // if optimizedLaunch == true => this is the default value anyway
        if (!bootRunProperties.isOptimizedLaunch()) {
            bootRun.setOptimizedLaunch(false);
        }

        project.getLogger().info(settings(bootRun));
    }


    private static String settings(BootRun task) {

        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(" ==== 'bootRun' configured with : ");
        joiner.add("     main             : " + task.getMain());
        joiner.add("     systemProperties : " + task.getSystemProperties());
        joiner.add("     optimizedLaunch  : " + task.isOptimizedLaunch());

        return joiner.toString();

    }
}
