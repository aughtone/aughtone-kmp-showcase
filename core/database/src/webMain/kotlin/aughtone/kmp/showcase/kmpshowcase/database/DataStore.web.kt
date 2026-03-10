package aughtone.kmp.showcase.kmpshowcase.database

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import aughtone.kmp.showcase.kmpshowcase.JournalEntry
import okio.Path.Companion.toPath

actual fun createDataStore(): DataStore<List<JournalEntry>> {
    return DataStoreFactory.create(
        serializer = JournalEntrySerializer,
        produceFile = { DATASTORE_FILE_NAME.toPath() }
    )
}
