package aughtone.kmp.showcase.kmpshowcase.database

import aughtone.kmp.showcase.kmpshowcase.JournalEntry
import kotlinx.coroutines.flow.Flow

interface Database {
    fun getEntries(): Flow<List<JournalEntry>>
    fun getEntry(id: String): Flow<JournalEntry?>
    suspend fun saveEntries(entries: List<JournalEntry>)
    suspend fun addEntry(entry: JournalEntry)
}