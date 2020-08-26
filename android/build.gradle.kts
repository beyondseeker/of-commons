plugins {
    Plugin_org_gradle_maven_publish
    Plugin_com_android_library
    Plugin_org_jetbrains_kotlin_android
    Plugin_kotlin_android_extensions
    Plugin_org_jetbrains_kotlin_kapt
}

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        coreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Project__kotlin)

    coreLibraryDesugaring(Deps.com_android_tools__desugar_jdk_libs)

    implementation(Deps.androidx_activity__activity)
    implementation(Deps.androidx_activity__activity_ktx)
    implementation(Deps.androidx_appcompat__appcompat)
    implementation(Deps.androidx_arch_core__core_testing)
    implementation(Deps.androidx_constraintlayout__constraintlayout)
    implementation(Deps.androidx_core__core_ktx)
    implementation(Deps.androidx_fragment__fragment)
    implementation(Deps.androidx_lifecycle__lifecycle_livedata)
    implementation(Deps.com_google_android_material__material)
    implementation(Deps.org_jetbrains_kotlin__kotlin_reflect)
    implementation(Deps.org_jetbrains_kotlin__kotlin_stdlib_jdk8)

    testImplementation(Deps.androidx_arch_core__core_testing)
    testImplementation(Deps.io_mockk__mockk)
    testImplementation(Deps.junit__junit)

    androidTestImplementation(Deps.androidx_test__rules)
    androidTestImplementation(Deps.androidx_test_espresso__espresso_core)
    androidTestImplementation(Deps.androidx_test_ext__junit)
    androidTestImplementation(Deps.com_google_truth__truth)
    androidTestImplementation(Deps.org_assertj__assertj_core)

    // for androidx.test.core.app.launchActivity
    androidTestImplementation("androidx.test:core-ktx:1.3.0")
}

tasks {
    val androidSourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(android.sourceSets["main"].java.srcDirs)
    }
}

afterEvaluate {
    publishing {
        repositories.maven {
            url = uri("$rootDir/repository")
        }
        publications {
            create<MavenPublication>("maven") {
                from(components["release"])
                artifact(tasks["androidSourcesJar"])
                groupId = "com.objectfanatics"
                artifactId = "commons-android"
                version = "0.0.1-SNAPSHOT"
            }
        }
    }
}