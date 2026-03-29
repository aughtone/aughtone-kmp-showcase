package aughtone.kmp.showcase.kmpshowcase.database.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class JournalEntryEntity(
    val id: String,
    val title: String,
    val date: LocalDate,
    val content: String,
    val mood: MoodEntity
)

@Serializable
enum class MoodEntity {
    HAPPY,
    SAD,
    CALM,
    ENERGETIC,
    ANXIOUS
}
