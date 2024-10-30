plugins {
    id("java")
//    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.intellij")
}
repositories {
    maven("https://maven.aliyun.com/repository/central")
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.aliyun.com/repository/google")
    mavenCentral()
}

intellij {
    version.set("2020.2.3")  //设置运行插件的IntelliJ的版本
    type.set("IC") // Target IDE Platform
    plugins.set(listOf(/* Plugin Dependencies */))
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


//group "com.zeke.wong"
//version '1.1'

//sourceCompatibility = 1.8
//targetCompatibility = 1.8

//dependencies {
//    implementation("org.jetbrains:annotations:24.0.0")
//    //    testImplementation("junit:junit:4.12")
//}

//tasks.withType(JavaCompile) {
//    options.encoding = "UTF-8"
//}
//
tasks.patchPluginXml{
    pluginId.set("com.zeke.wong.neck-protect")
    sinceBuild.set("202")
    untilBuild.set("241.*")
    version.set("1.1.2")
    changeNotes.set("""
      <ul>
        <li>v1.0 Init.</li>
        <li>v1.1 Automatically update Bing image every day.</li>
        <li>v1.1.1 Fix bugs in version 1.1.</li>
        <li>v1.1.2 Compatible with IDEA versions after 212.5457.46 (2021.2.3)</li>
      </ul>
      """)
}

//
//tasks {
//    // Set the JVM compatibility versions
//    withType<JavaCompile> {
//        sourceCompatibility = "17"
//        targetCompatibility = "17"
//    }
//    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//        kotlinOptions.jvmTarget = "17"
//    }
//
//    patchPluginXml {
//        sinceBuild.set("222")
//        untilBuild.set("232.*")
//    }
//
//    signPlugin {
//        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
//        privateKey.set(System.getenv("PRIVATE_KEY"))
//        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
//    }
//
//    publishPlugin {
//        token.set(System.getenv("PUBLISH_TOKEN"))
//    }
//}