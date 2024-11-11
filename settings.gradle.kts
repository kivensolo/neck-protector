apply{
    from("config.gradle.kts")
}

rootProject.name = "neck-protect"

pluginManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/central")
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/google")
        maven("https://www.jetbrains.com/intellij-repository/releases")
        maven("https://www.jetbrains.com/intellij-repository/snapshots")
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}