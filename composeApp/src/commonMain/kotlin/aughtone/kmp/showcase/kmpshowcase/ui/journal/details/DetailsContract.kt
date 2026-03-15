package aughtone.kmp.showcase.kmpshowcase.ui.journal.details

import aughtone.kmp.showcase.kmpshowcase.Mood

data class DetailsUiState(
    val id: String = "",
    val title: String = "",
    val date: String = "",
    val content: String = "",
    val mood: Mood = Mood.CALM,
    val isLoading: Boolean = false,
    val error: String? = null
)