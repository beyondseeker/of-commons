plugins {
    Plugin_org_gradle_java_library
    Plugin_org_gradle_maven_publish
    Plugin_org_jetbrains_kotlin_jvm
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

dependencies {
    implementation(Deps.org_jetbrains_kotlin__kotlin_stdlib_jdk8)
    testImplementation(Deps.junit__junit)
}

afterEvaluate {
    publishing {
        repositories.maven {
            url = uri("$rootDir/repository")
        }
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])
                artifact(tasks["sourcesJar"])
                groupId = "com.objectfanatics"
                artifactId = "commons-kotlin"
                version = "0.0.1-SNAPSHOT"
            }
        }
    }
}