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

intellij {
    version.set("2023.2")
    type.set("IC")
    plugins.set(listOf("com.intellij.java"))
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
        version.set(mPluginVersion)
        changeNotes.set("""
          <ul>
            <li>v1.0 Init.</li>
            <li>v1.1 Automatically update Bing image every day.</li>
            <li>v1.1.1 Fix bugs in version 1.1.</li>
            <li>v1.1.2 Adjust Intellij Platform 202-211.* .</li>
            <li>v1.1.3 Adjust Intellij Platform 212-231.*</li>
            <li>v1.1.4 Adjust Intellij Platform to 2024.3(243.21565.193)</li>
          </ul>
          """)
    }

    jar {
        archiveFileName.set("neck-protect.jar")
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