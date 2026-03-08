package aughtone.kmp.showcase.kmpshowcase

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JournalEntry(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("date")
    val date: LocalDate,
    @SerialName("content")
    val content: String,
    @SerialName("mood")
    val mood: Mood
)

@Serializable
enum class Mood {
    @SerialName("HAPPY")
    HAPPY, 
    @SerialName("SAD")
    SAD, 
    @SerialName("CALM")
    CALM, 
    @SerialName("ENERGETIC")
    ENERGETIC, 
    @SerialName("ANXIOUS")
    ANXIOUS;
    
    override fun toString(): String = name.lowercase().replaceFirstChar { it.uppercase() }
}
