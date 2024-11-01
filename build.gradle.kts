plugins {
    id("java")
//    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.intellij")
}
repositories {
    maven("https://maven.aliyun.com/repository/central")
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.aliyun.com/repository/google")
//    mavenCentral()
}

intellij {
    version.set("2022.2.1")  //设置运行插件的IntelliJ的版本
    type.set("IC") // Target IDE Platform
//    localPath.set("G:\\GradleCache\\caches\\modules-2\\files-2.1\\com.jetbrains.intellij.idea\\ideaIC\\2023.1.7\\33fea784f8f3d29e870e5387877a2a01f48fffae\\ideaIC-2023.1.7")
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
//    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//        kotlinOptions.jvmTarget = "11"
//    }

    patchPluginXml{
        pluginId.set("com.zeke.wong.neck-protect")
        sinceBuild.set("202")
        untilBuild.set("222.*")
        version.set("1.1.202-222")
        changeNotes.set("""
          <ul>
            <li>v1.0 Init.</li>
            <li>v1.1 Automatically update Bing image every day.</li>
            <li>v1.1.1 Fix bugs in version 1.1.</li>
            <li>v1.1.2 Compatible with IDEA versions after 212.5457.46 (2021.2.3)</li>
          </ul>
          """)
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