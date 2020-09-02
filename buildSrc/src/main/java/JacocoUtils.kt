import org.gradle.api.Project
// import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.task
import org.gradle.testing.jacoco.tasks.JacocoReport

object JacocoUtils {
    const val toolVersion: String = "0.8.5"

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

            // Exclude the class files corresponding to the auto-generated source files.
            // TODO: 実際に目視確認したもののみを除外していく。(例えば R.class はまだ実際に目視確認してないので除外していない)
            val classDirectoriesTreeExcludes = setOf(
                // e.g. class   : androidx/databinding/library/baseAdapters/BR.class
                //      element : BR
                "androidx/**/*.class",

                // e.g. class   : <AndroidManifestPackage>/DataBinderMapperImpl.class
                //      element : DataBinderMapperImpl
                "**/DataBinderMapperImpl.class",

                // e.g. class   : <AndroidManifestPackage>/DataBinderMapperImpl$InnerBrLookup.class
                //      element : DataBinderMapperImpl.InnerBrLookup
                "**/DataBinderMapperImpl\$*.class",

                // e.g. class   : <AndroidManifestPackage>/BuildConfig.class
                //      element : BuildConfig
                "**/BuildConfig.class",

                // e.g. class   : <AndroidManifestPackage>/BR.class
                //      element : BR
                "**/BR.class",

                // e.g. class   : <AndroidManifestPackage>/DataBindingInfo.class
                //      element : DataBindingInfo
                "**/DataBindingInfo.class"

                //     "**/R.class",
                //     "**/R$*.class",
                //     "**/Manifest*.*",
                //     "android/**/*.*",
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
}