import org.gradle.api.Project
// import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.task
import org.gradle.testing.jacoco.tasks.JacocoReport

fun Project.createJacocoReportTask(reportTaskName: String, variantName: String, description: String, vararg testTaskNames: String) {
    task<JacocoReport>(reportTaskName) {
        testTaskNames.forEach { testTaskName ->
            dependsOn(testTaskName)

            // テストタスク完了時に必ずレポートタスクを実行させたい場合はアンコメントしてください。
            // report is always generated after tests run
            // tasks[testTaskName].finalizedBy(tasks[reportTaskName])
        }

        group = "verification"
        this.description = description

        // The following are the default settings for 'reports'
        // reports {
        //     csv.isEnabled = false
        //     html.isEnabled = true
        //     xml.isEnabled = false
        // }

        // TODO: classDirectoriesTreeExcludes をどうするのがベストなのかは要検討。
        val classDirectoriesTreeExcludes = emptySet<String>()
        // val excludes = setOf(
        //     "**/R.class",
        //     "**/R$*.class",
        //     "**/Manifest*.*",
        //     "android/**/*.*",
        //     "androidx/**/*.*",
        //     "**/Lambda$*.class",
        //     "**/*\$Lambda$*.*",
        //     "**/*\$inlined$*.*", // e.g. TransformationsUtils$map$$inlined$apply$lambda$1.class
        //     "**/Lambda.class",
        //     "**/*Lambda.class",
        //     "**/*Lambda*.class",
        //     "**/*Lambda*.*",
        //     "**/*Builder.*"
        // )

        // classDirectories --------------------------------------------------------------------

        val javaClassDirectoriesTree = fileTree(
            mapOf(
                "dir" to "${buildDir}/intermediates/javac/${variantName}/compile${variantName.capitalize()}JavaWithJavac/classes",
                "excludes" to classDirectoriesTreeExcludes
            )
        )

        val kotlinClassDirectoriesTree = fileTree(
            mapOf(
                "dir" to "${buildDir}/tmp/kotlin-classes/${variantName}",
                "excludes" to classDirectoriesTreeExcludes
            )
        )

        classDirectories.setFrom(files(javaClassDirectoriesTree, kotlinClassDirectoriesTree))

        // sourceDirectories -------------------------------------------------------------------

        val mainSourceDirectoriesTree =
            fileTree(mapOf<String?, String>("dir" to "${project.projectDir}/src/main/java"))

        val variantSourceDirectoriesTree =
            fileTree(mapOf<String?, String>("dir" to "${project.projectDir}/src/${variantName}/java"))

        sourceDirectories.setFrom(
            files(
                mainSourceDirectoriesTree,
                variantSourceDirectoriesTree
            )
        )

        // executionData -----------------------------------------------------------------------

        executionData.setFrom(
            fileTree(
                mapOf(
                    "dir" to project.projectDir,
                    "includes" to listOf(
                        // unit test execution data
                        "**/*.exec",
                        // instrumented test execution data
                        "**/*.ec"
                    )
                )
            )
        )
    }
}