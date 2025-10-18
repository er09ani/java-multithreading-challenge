plugins {
    id("java")
    id("io.freefair.aspectj") version "8.4"
}

group = "er09ani"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.hamcrest:hamcrest-core:1.3")

    implementation("org.aspectj:aspectjrt:1.9.21")

    implementation("org.slf4j:slf4j-api:2.0.17")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.22.0")
    implementation("org.apache.logging.log4j:log4j-core:2.22.0")
}

tasks.test {
    useJUnit()

    // Use all available CPU cores for tests
    maxParallelForks = Runtime.getRuntime().availableProcessors()

    // Minimal but effective JVM tuning
    jvmArgs = listOf("-Xmx4g", "-XX:+UseG1GC")

    testLogging {
        events("passed", "failed")
        showStandardStreams = true
    }
}

// Faster incremental compilation
tasks.withType<JavaCompile> {
    options.isIncremental = true
}
