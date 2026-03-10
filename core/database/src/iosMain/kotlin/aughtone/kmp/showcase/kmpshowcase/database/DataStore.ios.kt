package aughtone.kmp.showcase.kmpshowcase.database

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import aughtone.kmp.showcase.kmpshowcase.JournalEntry
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual fun createDataStore(): DataStore<List<JournalEntry>> {
    return DataStoreFactory.create(
        serializer = JournalEntrySerializer,
        produceFile = {
            val directory = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null
            )
            (directory?.path + "/$DATASTORE_FILE_NAME").toPath()
        }
    )
}
