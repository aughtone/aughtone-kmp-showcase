package aughtone.kmp.showcase.kmpshowcase.database

import aughtone.kmp.showcase.kmpshowcase.database.model.JournalEntryEntity
import kotlinx.coroutines.flow.Flow

interface JournalDataStore {
    val data: Flow<List<JournalEntryEntity>>
    suspend fun updateData(transform: suspend (List<JournalEntryEntity>) -> List<JournalEntryEntity>)
}

expect fun createDataStore(): JournalDataStore

const val DATASTORE_FILE_NAME = "journal_entries.json"
