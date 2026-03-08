package aughtone.kmp.showcase.kmpshowcase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import aughtone.kmp.showcase.kmpshowcase.ui.navigation.ShowcaseNavigation
import aughtone.kmp.showcase.kmpshowcase.ui.theme.ShowcaseTheme

@Composable
@Preview
fun App() {
    ShowcaseTheme {
        ShowcaseNavigation(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
    }
}