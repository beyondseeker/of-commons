plugins {
    Plugin_org_gradle_java_gradle_plugin
    Plugin_org_jetbrains_kotlin_jvm
    Plugin_org_gradle_maven_publish
}

dependencies {
    implementation(Deps.org_jetbrains_kotlin__kotlin_stdlib_jdk8)
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }
}

gradlePlugin {
    plugins {
        create("dynamicModulesPlugin") {
            id = "com.objectfanatics.gradle.plugin.dynamicmodulesplugin"
            displayName = "dynamicModulesPlugin"
            description = "This plugin registers the folders where \"build.gradle\" or \"build.gradle.kts\" exists except for rootDir and buildSrc dir as gradle projects."
            implementationClass = "com.objectfanatics.commons.com.objectfanatics.gradle.plugin.DynamicModulesPlugin"
        }
    }
}

afterEvaluate {
    publishing {
        repositories.maven {
            url = uri("$rootDir/${ReleaseUtils.repoDirName}")
        }
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])
                artifact(tasks["sourcesJar"])
                groupId = "com.objectfanatics"
                artifactId = "gradle-plugin-dynamicmodulesplugin"
                version = ReleaseUtils.version
            }
        }
    }
}