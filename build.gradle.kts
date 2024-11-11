apply {
    from("config.gradle.kts")
}

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    id("org.jetbrains.intellij") version "1.11.0"
}

val ideVersion: String by extra
val baseVersion: String by extra
val javaVersion: String by extra
val since: String by extra
val until: String? by extra
val mPluginName: String by extra
val mPluginId: String by extra
val mPluginVersion: String by extra


repositories {
    maven("https://maven.aliyun.com/repository/central")
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.aliyun.com/repository/google")
//    mavenCentral()
}

kotlin {
    jvmToolchain(8)
}

intellij {
//    version.set("2023.2")  //设置运行插件的IntelliJ的版本
//    type.set("IC") // Target IDE Platform
        localPath.set("D:\\Program Files\\JetBrains\\IntelliJ IDEA Community Edition 2023.2")
//    plugins.set(listOf(/* Plugin Dependencies */))
//    val pluginList = listOf("java", "android", "Kotlin","IntelliLang")
//    plugins.set(pluginList)
}

java {
    sourceSets.main {
        this.java.srcDir("src/main/java")
    }

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}

dependencies {
    implementation("org.jetbrains:annotations:24.0.0")
    implementation(kotlin("stdlib-jdk8"))
}

tasks{
    // Avoid execution failed for task ‘:buildSearchableOptions’
    buildSearchableOptions{
        enabled=false
    }

    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        options.encoding = "UTF-8"

        //Add -Xlint:deprecation to the compiler args for more details
//        options.compilerArgs.add("-Xlint:deprecation")
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        kotlinOptions {
            jvmTarget = javaVersion
        }
    }

    patchPluginXml{
        pluginId.set(mPluginId)
        sinceBuild.set(since)
        if(until.isNullOrEmpty()){
            untilBuild.set(provider {  null })
        } else {
            untilBuild.set(until)
        }
        version.set(ideVersion)
        changeNotes.set("""
          <ul>
            <li>v1.0 Init.</li>
            <li>v1.1 Automatically update Bing image every day.</li>
            <li>v1.1.1 Fix bugs in version 1.1.</li>
            <li>v1.1.202-211 Compiled with Java11 and Adjust IDEA compatibility range to 202-211.* .</li>
            <li>v1.1.212-231 Compiled with Java17 and Adjust IDEA compatibility range to 212-231.*</li>
            <li>v1.1.232-241 Adjust IDEA compatibility range to 232-241.*</li>
          </ul>
          """)
    }

    jar {
        archiveFileName.set("neck_protect_${mPluginVersion}.jar")
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