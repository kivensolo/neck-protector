plugins {
    id("java")
//    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.intellij")
}

ext.set("plugin_version","1.1.212-231")

repositories {
    maven("https://maven.aliyun.com/repository/central")
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.aliyun.com/repository/google")
//    mavenCentral()
}

intellij {
    version.set("2023.1.7")  //设置运行插件的IntelliJ的版本
    type.set("IC") // Target IDE Platform
//    plugins.set(listOf(/* Plugin Dependencies */))
//    val pluginList = listOf("java", "android", "Kotlin","IntelliLang")
//    plugins.set(pluginList)
}

java.sourceSets.main {
    this.java.srcDir("src/main/java")
}

dependencies {
    implementation("org.jetbrains:annotations:24.0.0")
}

tasks{
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
        options.encoding = "UTF-8"
    }
//    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//        kotlinOptions.jvmTarget = "17"
//    }
    patchPluginXml{
        pluginId.set("com.zeke.wong.neck-protect")
        sinceBuild.set("212")
        untilBuild.set("231.*")
        version.set(ext.get("plugin_version") as String)
        changeNotes.set("""
          <ul>
            <li>v1.0 Init.</li>
            <li>v1.1 Automatically update Bing image every day.</li>
            <li>v1.1.1 Fix bugs in version 1.1.</li>
            <li>v1.1.202-211 Compiled with Java11 and Adjust IDEA compatibility range to 202-211.* .</li>
            <li>v1.1.212-231 Compiled with Java17 and Adjust IDEA compatibility range to 212-231.*</li>
          </ul>
          """)
    }

    jar {
        archiveFileName.set("neck_protect_${ext.get("plugin_version") as String}.jar")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }
    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}