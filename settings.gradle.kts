rootProject.name = "of-commons"
rootDir
    .walkTopDown()
    .filter { it.parentFile.toRelativeString(rootDir) != "buildSrc" && it.parentFile != rootDir && it.isFile && (it.name == "build.gradle" || it.name == "build.gradle.kts") }
    .forEach { file: File ->
        val projectPath = ":${file.parentFile.toRelativeString(rootDir).replace(File.separatorChar, ':')}"
        include(projectPath)
    }