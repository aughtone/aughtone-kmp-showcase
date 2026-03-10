package aughtone.kmp.showcase.kmpshowcase.database

import aughtone.kmp.showcase.kmpshowcase.JournalEntry
import kotlinx.coroutines.flow.Flow

class DatabaseImpl(
    private val dataStore: JournalDataStore
) : Database {
    override fun getEntries(): Flow<List<JournalEntry>> = dataStore.data

    override suspend fun saveEntries(entries: List<JournalEntry>) {
        dataStore.updateData { entries }
    }

    override suspend fun addEntry(entry: JournalEntry) {
        dataStore.updateData { currentEntries ->
            currentEntries + entry
        }
    }
}