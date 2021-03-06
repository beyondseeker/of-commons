import JacocoUtils.createJacocoReportTask

plugins {
    Plugin_org_gradle_maven_publish
    Plugin_com_android_library
    Plugin_org_jetbrains_kotlin_android
    Plugin_kotlin_android_extensions
    Plugin_org_jetbrains_kotlin_kapt
    jacoco
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
        getByName("debug") {
            isTestCoverageEnabled = true
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

    androidTestImplementation(Deps.androidx_test__core_ktx)
    androidTestImplementation(Deps.androidx_test__rules)
    androidTestImplementation(Deps.androidx_test_espresso__espresso_core)
    androidTestImplementation(Deps.androidx_test_ext__junit)
    androidTestImplementation(Deps.com_google_truth__truth)
    androidTestImplementation(Deps.org_assertj__assertj_core)
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
            url = uri("$rootDir/${ReleaseUtils.repoDirName}")
        }
        publications {
            create<MavenPublication>("maven") {
                from(components["release"])
                artifact(tasks["androidSourcesJar"])
                groupId = "com.objectfanatics"
                artifactId = "commons-android"
                version = ReleaseUtils.version
            }
        }
    }
}

jacoco {
    toolVersion = JacocoUtils.toolVersion
}

afterEvaluate {
    // for 'jacocoTestXxxUnitTestReport' task (e.g. jacocoTestDebugUnitTestReport)
    android.libraryVariants.forEach { variant ->
        val variantName = variant.name
        val capitalizedVariantName = variantName.capitalize()
        createJacocoReportTask(
            "jacocoTest${capitalizedVariantName}UnitTestReport",
            variantName,
            "Generates code coverage report for the test${capitalizedVariantName}UnitTest task.",
            "test${capitalizedVariantName}UnitTest"
        )
    }

    // for 'jacocoTestReport' task
    task<JacocoReport>("jacocoTestReport") {
        android.libraryVariants.forEach { variant ->
            dependsOn("jacocoTest${variant.name.capitalize()}UnitTestReport")
        }
    }

    // for 'jacocoConnectedAndroidTestReport' task
    createJacocoReportTask(
        "jacocoConnectedAndroidTestReport",
        "debug",
        "Generates code coverage report for the connectedAndroidTest task.",
        "connectedAndroidTest"
    )

    // for 'jacocoMergeTestReport' task
    val testDebugUnitTest = "testDebugUnitTest"
    val connectedAndroidTest = "connectedAndroidTest"
    val description = "Generates code coverage report for the $testDebugUnitTest and $connectedAndroidTest tasks."
    createJacocoReportTask(
        "jacocoMergeTestReport",
        "debug",
        description,
        testDebugUnitTest,
        connectedAndroidTest
    )
}