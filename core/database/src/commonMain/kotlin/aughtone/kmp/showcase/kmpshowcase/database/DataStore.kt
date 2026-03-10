package aughtone.kmp.showcase.kmpshowcase.database

import androidx.datastore.core.DataStore
import aughtone.kmp.showcase.kmpshowcase.JournalEntry

expect fun createDataStore(): DataStore<List<JournalEntry>>

val DATASTORE_FILE_NAME = "journal_entries.json"
