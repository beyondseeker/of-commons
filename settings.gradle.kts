rootProject.name = "of-commons"

// TODO: 『にわとりたまご』状態になってしまうため、ここで com.objectfanatics:gradle-plugin-dynamicmodulesplugin は利用しないようにしてください。
//        DynamicModulesPlugin とコードを共有したいが現状だといい方法が思いついていないので、思いついたらやりましょう。
rootDir
    .walkTopDown()
    .filter { it.parentFile.toRelativeString(rootDir) != "buildSrc" && it.parentFile != rootDir && it.isFile && (it.name == "build.gradle" || it.name == "build.gradle.kts") }
    .forEach { file: File ->
        val projectPath = ":${file.parentFile.toRelativeString(rootDir).replace(File.separatorChar, ':')}"
        include(projectPath)
    }