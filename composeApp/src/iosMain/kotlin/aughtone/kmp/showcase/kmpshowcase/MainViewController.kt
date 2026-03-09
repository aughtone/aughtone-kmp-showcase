package aughtone.kmp.showcase.kmpshowcase

import androidx.compose.ui.window.ComposeUIViewController
import aughtone.kmp.showcase.kmpshowcase.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }
