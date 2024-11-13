plugins {
    id("java")
    id("org.jetbrains.intellij") version("1.11.0")
}

ext.set("plugin_version","1.1.4")

repositories {
    maven("https://maven.aliyun.com/repository/central")
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.aliyun.com/repository/google")
    mavenCentral()
}

intellij {
    version.set("2023.1.7")  //设置运行插件的IntelliJ的版本
    type.set("IC") // Target IDE Platform
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

    patchPluginXml{
        pluginId.set("com.zeke.wong.neck-protect")
        sinceBuild.set("221")
        untilBuild.set("231.9423.9")
        version.set(ext.get("plugin_version") as String)
        changeNotes.set("""
          <ul>
            <li>v1.0 Init.</li>
            <li>v1.1 Automatically update Bing image every day.</li>
            <li>v1.1.1 Fix bugs in version 1.1.</li>
            <li>v1.1.1_002 Compiled with Java11 and Adjust compatibility range to 202-211.* .</li>
            <li>v1.1.1_003 Adjust compatibility range to 2021.2(212.4746.92)-2022.2.*(222.*)</li>
            <li>v1.1.4 Compiled with Java17 and Adjust compatibility range to 2022.1(221.5080.210)-2023.1.7(231.9423.9)</li>
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