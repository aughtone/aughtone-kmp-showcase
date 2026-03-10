package aughtone.kmp.showcase.kmpshowcase.database

import aughtone.kmp.showcase.kmpshowcase.JournalEntry
import kotlinx.coroutines.flow.Flow

interface JournalDataStore {
    val data: Flow<List<JournalEntry>>
    suspend fun updateData(transform: suspend (List<JournalEntry>) -> List<JournalEntry>)
}

expect fun createDataStore(): JournalDataStore

const val DATASTORE_FILE_NAME = "journal_entries.json"
