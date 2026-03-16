package aughtone.kmp.showcase.kmpshowcase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import aughtone.kmp.showcase.kmpshowcase.design.theme.ShowcaseTheme
import aughtone.kmp.showcase.kmpshowcase.ui.navigation.ShowcaseNavigation

@Composable
fun App() {
    ShowcaseTheme {
        ShowcaseNavigation(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
    }
}
