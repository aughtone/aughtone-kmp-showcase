package aughtone.kmp.showcase.kmpshowcase.database

import aughtone.kmp.showcase.kmpshowcase.JournalEntryDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseImpl(
    private val dataStore: JournalDataStore
) : Database {
    override fun getEntries(): Flow<List<JournalEntryDto>> = dataStore.data

    override fun getEntry(id: String): Flow<JournalEntryDto?> = dataStore.data.map { entries ->
        entries.find { it.id == id }
    }

    override suspend fun saveEntries(entries: List<JournalEntryDto>) {
        dataStore.updateData { entries }
    }

    override suspend fun addEntry(entry: JournalEntryDto) {
        dataStore.updateData { currentEntries ->
            currentEntries + entry
        }
    }
}