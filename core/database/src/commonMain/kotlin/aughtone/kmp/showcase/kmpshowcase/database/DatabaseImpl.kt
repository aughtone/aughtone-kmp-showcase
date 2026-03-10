package aughtone.kmp.showcase.kmpshowcase.database

import androidx.datastore.core.DataStore
import aughtone.kmp.showcase.kmpshowcase.JournalEntry
import kotlinx.coroutines.flow.Flow

class DatabaseImpl(
    private val dataStore: DataStore<List<JournalEntry>>
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