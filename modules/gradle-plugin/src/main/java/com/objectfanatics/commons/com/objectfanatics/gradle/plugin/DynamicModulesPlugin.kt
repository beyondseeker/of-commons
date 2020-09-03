package com.objectfanatics.commons.com.objectfanatics.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

/**
 * This plugin registers the folders where "build.gradle" or "build.gradle.kts" exists except for rootDir and buildSrc dir as gradle projects.
 *
 * For example, if the file "/modules/android/build.gradle.kts" exists, the project with the path ":modules:android" will be registered.
 *
 * The following is an example of settings.gradle.kts:
 * ------------------------------------------------------------------------------------------
 * buildscript {
 *     repositories {
 *         maven { url = uri("https://beyondseeker.github.io/of-commons/mvn-repo") } // for of-commons
 *         google()
 *         jcenter()
 *     }
 *     dependencies {
 *         classpath("com.objectfanatics:gradle-plugin-dynamicmodulesplugin:0.0.1")
 *     }
 * }
 * apply(mapOf("plugin" to "com.objectfanatics.gradle.plugin.dynamicmodulesplugin"))
 * ------------------------------------------------------------------------------------------
 *
 * The following is an example of settings.gradle:
 * ------------------------------------------------------------------------------------------
 * buildscript {
 *     repositories {
 *         maven url = "https://beyondseeker.github.io/of-commons/mvn-repo" // for of-commons
 *         google()
 *         jcenter()
 *     }
 *     dependencies {
 *         classpath 'com.objectfanatics:gradle-plugin-dynamicmodulesplugin:0.0.1'
 *     }
 * }
 * apply plugin: 'com.objectfanatics.gradle.plugin.dynamicmodulesplugin'
 * ------------------------------------------------------------------------------------------
 */
class DynamicModulesPlugin : Plugin<Settings> {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun apply(settings: Settings) {
        // TODO: 以前の実装をコメントアウトしたもの。階層構造にしたり project path 名を変えたりするケースのサンプルとしてコメントとして残しています。
        //       モジュール構造の決定版が出るまではコメントを残しておきましょう。
        //       たぶん、現状の、"build.gradle" もしくは "build.gradle.kts" ファイルの存在するフォルダを project のフォルダとして認識するという
        //       部分はそのままで、それを任意の project path 名に変換するための lambda を渡せるようにすれば、万能になる気がする。
        //       今作成してもニーズが無いので作ったものの正しさを証明できないので、必要性が生じたら対応しましょう。それまでは放置ということで。
        // /modules 直下のすべてのフォルダを  project として登録し、デフォルトから ":modules" を除いたパスを設定するプラグインです。
        // 例えば、/modules/android というフォルダがあった場合、そのフォルダが project として設定され、パスは ":android" になります。
        //
        // File("modules").listFiles()?.forEach { file ->
        //     val name = file.name
        //     val path = ":$name"
        //     settings.include(path)
        //     settings.project(path).projectDir = File(settings.rootDir, "/modules/$name")
        // }
        with(settings) {
            rootDir
                .walkTopDown()
                .filter {
                    isSubprojectBuildFile(it).also { isSubprojectBuildFile ->
                        logSubprojectBuildFileCandidate(isSubprojectBuildFile, it)
                    }
                }
                .forEach { file ->
                    val projectPath = toProjectPath(file)
                    include(projectPath)
                    log.info("[subproject path] $projectPath")
                }
        }
    }

    private fun Settings.toProjectPath(file: File) =
        ":${file.parentFile.toRelativeString(rootDir).replace(File.separatorChar, ':')}"

    private fun Settings.isSubprojectBuildFile(file: File) =
        file.parentFile.toRelativeString(rootDir) != "buildSrc" && file.parentFile != rootDir && file.isFile && (file.name == "build.gradle" || file.name == "build.gradle.kts")

    private fun logSubprojectBuildFileCandidate(isSubprojectBuildFile: Boolean, it: File) {
        log.debug(
            when (isSubprojectBuildFile) {
                true -> "[    subproject build file] ${it.absolutePath}"
                else -> "[not subproject build file] ${it.absolutePath}"
            }
        )
    }
}