package aughtone.kmp.showcase.kmpshowcase.feature.journal.list

import aughtone.kmp.showcase.kmpshowcase.Mood

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
