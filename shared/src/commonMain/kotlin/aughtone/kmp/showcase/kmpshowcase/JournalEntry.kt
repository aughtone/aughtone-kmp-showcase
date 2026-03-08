package aughtone.kmp.showcase.kmpshowcase

import kotlinx.datetime.LocalDate

data class JournalEntry(
    val id: String,
    val title: String,
    val date: LocalDate,
    val content: String,
    val mood: Mood
)

enum class Mood {
    HAPPY, SAD, CALM, ENERGETIC, ANXIOUS;
    
    override fun toString(): String = name.lowercase().replaceFirstChar { it.uppercase() }
}
