package aughtone.kmp.showcase.kmpshowcase.domain

import aughtone.kmp.showcase.kmpshowcase.JournalEntry
import aughtone.kmp.showcase.kmpshowcase.Mood
import kotlinx.coroutines.flow.Flow

interface JournalRepository {
    fun getEntries(): Flow<List<JournalEntry>>
    suspend fun addEntry(title: String, content: String, mood: Mood): Result<JournalEntry>
}