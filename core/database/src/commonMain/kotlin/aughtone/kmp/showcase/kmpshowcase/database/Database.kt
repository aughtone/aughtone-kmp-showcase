package aughtone.kmp.showcase.kmpshowcase.database

import aughtone.kmp.showcase.kmpshowcase.JournalEntryDto
import kotlinx.coroutines.flow.Flow

interface Database {
    fun getEntries(): Flow<List<JournalEntryDto>>
    fun getEntry(id: String): Flow<JournalEntryDto?>
    suspend fun saveEntries(entries: List<JournalEntryDto>)
    suspend fun addEntry(entry: JournalEntryDto)
}