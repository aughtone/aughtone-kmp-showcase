package aughtone.kmp.showcase.kmpshowcase.database

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import aughtone.kmp.showcase.kmpshowcase.JournalEntry
import okio.Path.Companion.toPath
import java.io.File

actual fun createDataStore(): DataStore<List<JournalEntry>> {
    return DataStoreFactory.create(
        serializer = JournalEntrySerializer,
        produceFile = {
            val home = System.getProperty("user.home")
            File(home, DATASTORE_FILE_NAME).absolutePath.toPath()
        }
    )
}
