package aughtone.kmp.showcase.kmpshowcase.feature.journal.list

import aughtone.kmp.showcase.kmpshowcase.domain.model.Mood

data class ListUiState(
    val entries: List<Entry> = emptyList()
) {
    data class Entry(
        val id: String,
        val title: String,
        val date: String,
        val content: String,
        val mood: Mood
    )
}

sealed interface ListUiEvent {
    data class AddEntry(val title: String, val content: String, val mood: Mood) : ListUiEvent
    data object Refresh : ListUiEvent
}
