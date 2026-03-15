package aughtone.kmp.showcase.kmpshowcase.database

import aughtone.kmp.showcase.kmpshowcase.JournalEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseImpl(
    private val dataStore: JournalDataStore
) : Database {
    override fun getEntries(): Flow<List<JournalEntry>> = dataStore.data

    override fun getEntry(id: String): Flow<JournalEntry?> = dataStore.data.map { entries ->
        entries.find { it.id == id }
    }

    override suspend fun saveEntries(entries: List<JournalEntry>) {
        dataStore.updateData { entries }
    }

    override suspend fun addEntry(entry: JournalEntry) {
        dataStore.updateData { currentEntries ->
            currentEntries + entry
        }
    }
}