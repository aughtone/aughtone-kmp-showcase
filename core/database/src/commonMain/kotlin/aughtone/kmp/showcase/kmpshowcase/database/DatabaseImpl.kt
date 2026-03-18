package aughtone.kmp.showcase.kmpshowcase.database

import aughtone.kmp.showcase.kmpshowcase.database.model.JournalEntryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseImpl(
    private val dataStore: JournalDataStore
) : Database {
    override fun getEntries(): Flow<List<JournalEntryEntity>> = dataStore.data

    override fun getEntry(id: String): Flow<JournalEntryEntity?> = dataStore.data.map { entries ->
        entries.find { it.id == id }
    }

    override suspend fun saveEntries(entries: List<JournalEntryEntity>) {
        dataStore.updateData { entries }
    }

    override suspend fun addEntry(entry: JournalEntryEntity) {
        dataStore.updateData { currentEntries ->
            currentEntries + entry
        }
    }
}
