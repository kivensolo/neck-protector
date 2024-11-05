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
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.jetbrains.kotlin.jvm" -> {
                    useVersion("${settings.extra["kotlinVersion"]}")
                }
                "org.jetbrains.kotlin" -> {
                    useVersion("${settings.extra["kotlinVersion"]}")
                }
                "org.jetbrains.intellij" -> {
                    useVersion("${settings.extra["ideaPluginVersion"]}")
                }
            }
        }
    }
}