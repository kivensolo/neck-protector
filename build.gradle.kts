plugins {
    id("java")
    id("org.jetbrains.intellij")
}

ext.set("plugin_version","1.1.202-211")

repositories {
    maven("https://maven.aliyun.com/repository/central")
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.aliyun.com/repository/google")
//    mavenCentral()
}

intellij {
    version.set("2021.1.3")  //设置运行插件的IntelliJ的版本
    type.set("IC") // Target IDE Platform
//    plugins.set(listOf(/* Plugin Dependencies */))
}

java.sourceSets.main {
    this.java.srcDir("src/main/java")
}

dependencies {
    implementation("org.jetbrains:annotations:24.0.0")
}

//dependencies {
//    implementation("org.jetbrains:annotations:24.0.0")
//    //    testImplementation("junit:junit:4.12")
//}

tasks{
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
        options.encoding = "UTF-8"
    }

    patchPluginXml{
        pluginId.set("com.zeke.wong.neck-protect")
        sinceBuild.set("202")
        untilBuild.set("211.*")
        version.set(ext.get("plugin_version") as String)
        changeNotes.set("""
          <ul>
            <li>v1.0 Init.</li>
            <li>v1.1 Automatically update Bing image every day.</li>
            <li>v1.1.1 Fix bugs in version 1.1.</li>
            <li>v1.1.2 Compiled with Java11 and Adjust Intellij Platform 202-211.* .</li>
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