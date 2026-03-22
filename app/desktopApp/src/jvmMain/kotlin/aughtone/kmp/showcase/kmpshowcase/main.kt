package aughtone.kmp.showcase.kmpshowcase

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import aughtone.kmp.showcase.kmpshowcase.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "kmpshowcase",
        ) {
            App()
        }
    }
}
