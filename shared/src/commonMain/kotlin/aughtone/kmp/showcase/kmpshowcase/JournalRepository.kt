package aughtone.kmp.showcase.kmpshowcase

import kotlinx.coroutines.flow.Flow

interface JournalRepository {
    fun getEntries(): Flow<List<JournalEntry>>
    suspend fun refreshEntries()
    suspend fun addEntry(title: String, content: String, mood: Mood): Result<JournalEntry>
}
