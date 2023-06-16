/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    id("multilib.kotlin-library-conventions")
    kotlin("plugin.serialization") version "1.8.10"
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.9.8")
}
