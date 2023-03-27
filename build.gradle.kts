plugins {
    kotlin("jvm") version "1.8.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
    id("org.jetbrains.compose") version "1.3.1"
}

group = "hu.kszi2"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("io.ktor:ktor-client-core:2.2.4")
    implementation("io.ktor:ktor-client-cio:2.2.4")
    implementation("ch.qos.logback:logback-classic:1.4.6")
    implementation(compose.desktop.currentOs)
    testImplementation("junit:junit:4.13.2")
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}

tasks.jar {
    manifest.attributes["Main-Class"] = "hu.kszi2.moscht.MainKt"
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    val notifRelease = project("native-notif").tasks["linkRelease"].outputs.files.filter {
        it.extension in arrayOf("dll", "so", "dylib")
    }.files
    manifest.attributes["Moscht-Notification"] = notifRelease.take(1)[0].name

    dependsOn(project("native-notif").tasks["linkRelease"])

    from(dependencies + notifRelease)
}
