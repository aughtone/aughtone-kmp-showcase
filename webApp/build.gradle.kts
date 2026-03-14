plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        webMain.dependencies {

        }
        commonMain.dependencies {
            implementation(projects.composeApp)
            implementation(libs.compose.ui)
        }
    }
}
