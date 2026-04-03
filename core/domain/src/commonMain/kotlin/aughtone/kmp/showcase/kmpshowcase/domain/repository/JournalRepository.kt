package aughtone.kmp.showcase.kmpshowcase.domain.repository

import aughtone.kmp.showcase.kmpshowcase.domain.model.JournalEntry
import aughtone.kmp.showcase.kmpshowcase.domain.model.Mood
import kotlinx.coroutines.flow.Flow

interface JournalRepository {
    fun getEntries(): Flow<List<JournalEntry>>
    fun getEntry(id: String): Flow<JournalEntry?>
    suspend fun addEntry(title: String, content: String, mood: Mood): Result<JournalEntry>
}
