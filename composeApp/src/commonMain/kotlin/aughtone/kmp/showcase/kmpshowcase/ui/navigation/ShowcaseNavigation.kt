package aughtone.kmp.showcase.kmpshowcase.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import aughtone.kmp.showcase.kmpshowcase.feature.journal.DetailsRoute
import aughtone.kmp.showcase.kmpshowcase.feature.journal.ListRoute
import aughtone.kmp.showcase.kmpshowcase.feature.journal.list.ListScreen
import aughtone.kmp.showcase.kmpshowcase.feature.journal.details.DetailsScreen


@Composable
fun ShowcaseNavigation(
    modifier: Modifier = Modifier
) {
    val backStack = remember { mutableStateListOf<Any>(ListRoute) }
    NavDisplay(
        backStack = backStack,
        modifier = modifier
            .fillMaxSize(),
        entryProvider = entryProvider {
            entry<ListRoute> {
                ListScreen(
                    onEntryClick = { id -> backStack.add(DetailsRoute(id)) }
                )
            }
            entry<DetailsRoute> { route ->
                DetailsScreen(
                    id = route.id,
                    onBack = { backStack.removeLast() }
                )
            }
        }
    )
}
