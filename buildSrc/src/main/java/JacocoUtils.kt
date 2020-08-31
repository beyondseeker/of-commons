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

        // TODO: classDirectoriesTreeExcludes は、除外すべきものを実際に確認しながら対応することにする。
        val classDirectoriesTreeExcludes = setOf(
            // e.g. class name   : TransformationsUtils$map$$inlined$apply$lambda$1.class
            //      element      : TransformationsUtils.map..inlined.apply.lambda.new Observer() {...}
            "**/*\$\$inlined\$*.class"
            //     "**/R.class",
            //     "**/R$*.class",
            //     "**/Manifest*.*",
            //     "android/**/*.*",
            //     "androidx/**/*.*",
            //     "**/Lambda$*.class",
            //     "**/*\$Lambda$*.*",
            //     "**/Lambda.class",
            //     "**/*Lambda.class",
            //     "**/*Lambda*.class",
            //     "**/*Lambda*.*",
            //     "**/*Builder.*"
        )

        // classDirectories --------------------------------------------------------------------

        val javaClassDirectoriesTree = fileTree(
            mapOf(
                "dir" to "${buildDir}/intermediates/javac/${variantName}/classes/",
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

        val mainSourceDirectoryRelativePath = "src/main/java"
        val variantSourceDirectoryRelativePath = "src/${variantName}/java"
        sourceDirectories.setFrom(
            mainSourceDirectoryRelativePath,
            variantSourceDirectoryRelativePath
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