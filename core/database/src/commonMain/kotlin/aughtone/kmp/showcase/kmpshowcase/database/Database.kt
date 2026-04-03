package aughtone.kmp.showcase.kmpshowcase.database

import aughtone.kmp.showcase.kmpshowcase.database.model.JournalEntryEntity
import kotlinx.coroutines.flow.Flow

interface Database {
    fun getEntries(): Flow<List<JournalEntryEntity>>
    fun getEntry(id: String): Flow<JournalEntryEntity?>
    suspend fun saveEntries(entries: List<JournalEntryEntity>)
    suspend fun addEntry(entry: JournalEntryEntity)
}
