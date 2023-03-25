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
    implementation("junit:junit:4.13.2")
    implementation(compose.desktop.currentOs)
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
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
