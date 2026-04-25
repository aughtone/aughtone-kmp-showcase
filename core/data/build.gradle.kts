import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)
}

kotlin {
    android {
        namespace = "aughtone.kmp.showcase.kmpshowcase.core.data"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    iosArm64()
    iosSimulatorArm64()

    jvm()

    js(IR) { browser() }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.libs.network)
            implementation(projects.core.database)
            implementation(projects.core.api)
            
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.resources)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.ktor.client.mock)
        }
    }
}
