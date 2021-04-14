import kr.entree.spigradle.kotlin.paper

plugins {
    kotlin("jvm") version "1.4.10"
    id("kr.entree.spigradle") version "2.2.3"
}

group = "org.example"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://jitpack.io")
    //maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("com.github.spigradle.spigradle:kr.entree.spigradle.base.gradle.plugin:v2.2.3")
    compileOnly(paper("1.16.5"))
    implementation("com.github.NamuTree0345:firework:0.2.1")
    //compileOnly("com.comphenix.protocol:ProtocolLib:4.6.0")
}

spigot {
    authors = listOf("NamuTree0345")
    apiVersion = project.property("apiVersion").toString()
    //depends = listOf("ProtocolLib")
    commands {
        create("sethuman")
        create("setzombie")
        create("setsuperzombie")
        create("forcereleasemujuk")
        create("startparming")
    }
}

val shade = configurations.create("shade")
shade.extendsFrom(configurations.implementation.get())

tasks {

    javadoc {
        options.encoding = "UTF-8"
    }

    compileJava {
        options.encoding = "UTF-8"
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    create<Jar>("sourceJar") {
        archiveClassifier.set("source")
        from(sourceSets["main"].allSource)
    }

    jar {
        from (shade.map { if (it.isDirectory) it else zipTree(it) })
    }

    // From monun/tap-sample-plugin
    create<Copy>("copyToServer") {
        from(jar)
        val plugins = File(rootDir, ".server/plugins")
        if (File(shade.artifacts.files.asPath).exists()) {
            into(File(plugins, "update"))
        } else {
            into(plugins)
        }
    }
}