package aughtone.kmp.showcase.kmpshowcase.database

import aughtone.kmp.showcase.kmpshowcase.JournalEntryDto
import kotlinx.coroutines.flow.Flow

interface JournalDataStore {
    val data: Flow<List<JournalEntryDto>>
    suspend fun updateData(transform: suspend (List<JournalEntryDto>) -> List<JournalEntryDto>)
}

expect fun createDataStore(): JournalDataStore

const val DATASTORE_FILE_NAME = "journal_entries.json"
