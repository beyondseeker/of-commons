import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Creates a dependency on a project without adding it to a configuration.
 *
 * @param path the path of the project to be added as a dependency.
 * @param configuration the optional configuration of the project to be added as a dependency.
 * @return The dependency.
 */
fun DependencyHandler.project(
    path: String,
    configuration: String? = null
): ProjectDependency =
    uncheckedCast(
        project(
            if (configuration != null) mapOf("path" to path, "configuration" to configuration)
            else mapOf("path" to path)
        )
    )

@Suppress("unchecked_cast", "nothing_to_inline")
internal
inline fun <T> uncheckedCast(obj: Any?): T = obj as T