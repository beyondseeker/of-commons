import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

/**
 * 複数個所で利用されるバージョン情報
 */
private object Versions {
    const val activity = "1.2.0-alpha06"
    const val kotlin = "1.3.72"
    const val arch = "2.1.0"
}

/**
 * DependencyHandler.add(CLASSPATH_CONFIGURATION, dependencyNotation) の dependencyNotation として
 * 扱われる文字列です。
 *
 * 変数の命名手順：
 *   - <groupId>:<artifactId>:<version> のうち、:<version> を削除する。
 *   - colon を 2連続の underscore に変換し、それ以外の編巣名に利用不可能な文字を underscore に変換する。
 *
 * 利用例：
 *   classpath(Classpaths.com_android_tools_build__gradle)
 */
object Classpaths {
    const val com_android_tools_build__gradle = "com.android.tools.build:gradle:4.0.1"
    const val org_jetbrains_kotlin__kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

/**
 * dependencyNotation として扱われる文字列です。
 *
 * 変数の命名手順：
 *   - <groupId>:<artifactId>:<version> のうち、:<version> を削除する。
 *   - colon を 2連続の underscore に変換し、それ以外の変数名に利用不可能な文字を underscore に変換する。
 *
 * 利用例：
 *   coreLibraryDesugaring(Deps.com_android_tools__desugar_jdk_libs)
 *   implementation(Deps.androidx_appcompat__appcompat)
 *   testImplementation(Deps.junit__junit)
 *   androidTestImplementation(Deps.androidx_test_espresso__espresso_core)
 */
object Deps {
    const val androidx_activity__activity = "androidx.activity:activity:${Versions.activity}"
    const val androidx_activity__activity_ktx = "androidx.activity:activity-ktx:${Versions.activity}"
    const val androidx_appcompat__appcompat = "androidx.appcompat:appcompat:1.1.0"
    const val androidx_arch_core__core_testing = "androidx.arch.core:core-testing:${Versions.arch}"
    const val androidx_constraintlayout__constraintlayout = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val androidx_core__core_ktx = "androidx.core:core-ktx:1.3.0"
    const val androidx_fragment__fragment = "androidx.fragment:fragment:1.3.0-alpha06" // 1.3.0-alpha06 より前のバージョンだと、registerForActivityResult を利用した際に 'java.lang.IllegalArgumentException: Can only use lower 16 bits for requestCode' というエラーで落ちるので注意！
    const val androidx_lifecycle__lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata:2.3.0-alpha05"
    const val androidx_test__core_ktx = "androidx.test:core-ktx:1.3.0"
    const val androidx_test__rules = "androidx.test:rules:1.2.0"
    const val androidx_test_espresso__espresso_core = "androidx.test.espresso:espresso-core:3.2.0"
    const val androidx_test_ext__junit = "androidx.test.ext:junit:1.1.1"
    const val com_android_tools__desugar_jdk_libs = "com.android.tools:desugar_jdk_libs:1.0.10"
    const val com_google_android_material__material = "com.google.android.material:material:1.3.0-alpha01"
    const val com_google_truth__truth = "com.google.truth:truth:1.0.1"
    const val io_mockk__mockk = "io.mockk:mockk:1.10.0"
    const val junit__junit = "junit:junit:4.12"
    const val org_assertj__assertj_core = "org.assertj:assertj-core:3.12.2"
    const val org_jetbrains_kotlin__kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    const val org_jetbrains_kotlin__kotlin_stdlib_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
}

/**
 * DependencyHandler.project(path: String, configuration: String?) で path のみ指定し configuration を
 * 指定しない場合の dependencyNotation として扱われる [ProjectDependency] です。
 *
 * 変数の命名手順：
 *   - project(":aaa:bbb") のような形式をベースとする。
 *   - 先頭を大文字にする。(Pが大文字になる)
 *   - colon を 2連続の underscore に変換する。
 *   - 括弧とダブルクウォーテーションを削除する。
 *   - 変数名に利用不可能な文字を underscore に変換する。
 *
 * 利用例：
 *   implementation(Project__kotlin)
 */
val DependencyHandler.Project__kotlin: ProjectDependency get() = project(":modules:kotlin")

// Plugin ids
//
// plugin の呼び出し方は id("com.android.library"), `java-library`, kotlin("android") のような形でバラつきがあります。
// また、id の取扱個所が分散していると、許されない組み合わせなどのチェックが困難になります。そのため、ここで plugin in を
// 一元管理しています。
//
// 命名規則
//   - 本来の呼び出し方をベースとする。
//   - id("") を削除する
//   - バッククウォーテーションを削除する。
//   - kotlin の property として利用できない文字をアンダースコアに変換する。
//   - 接頭辞に Plugin_ を付加する。
//
// 備考
//   - 全て id 方式をベースとしています。kotlin("android") や `java-library` のような呼び出し方は、命名規則を適用して変換した際に
//     文字列が衝突する可能性があるためです。
//     ※それでも同じ開発元が _ と . だけ異なる別プラグインを出すようなケースでは衝突しますが、さすがにそれは無いと信じましょう。
//   - `com-android-library` のようにすればハイフンが使えますが、それでもドットは使えず、IDEの補完も効かないので、
//     アンダースコア方式を採用しています。
val PluginDependenciesSpec.Plugin_com_android_library: PluginDependencySpec get() = id("com.android.library")
val PluginDependenciesSpec.Plugin_kotlin_android_extensions: PluginDependencySpec get() = id("kotlin-android-extensions")
val PluginDependenciesSpec.Plugin_org_gradle_java_gradle_plugin: PluginDependencySpec get() = id("org.gradle.java-gradle-plugin")
val PluginDependenciesSpec.Plugin_org_gradle_java_library: PluginDependencySpec get() = id("org.gradle.java-library")
val PluginDependenciesSpec.Plugin_org_gradle_maven: PluginDependencySpec get() = id("org.gradle.maven")
val PluginDependenciesSpec.Plugin_org_gradle_maven_publish: PluginDependencySpec get() = id("org.gradle.maven-publish")
val PluginDependenciesSpec.Plugin_org_jetbrains_kotlin_android: PluginDependencySpec get() = id("org.jetbrains.kotlin.android")
val PluginDependenciesSpec.Plugin_org_jetbrains_kotlin_jvm: PluginDependencySpec get() = id("org.jetbrains.kotlin.jvm")
val PluginDependenciesSpec.Plugin_org_jetbrains_kotlin_kapt: PluginDependencySpec get() = id("org.jetbrains.kotlin.kapt")