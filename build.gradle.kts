buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Classpaths.com_android_tools_build__gradle)
        classpath(Classpaths.org_jetbrains_kotlin__kotlin_gradle_plugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

task("clean", Delete::class) {
    delete = setOf(rootProject.buildDir)
}