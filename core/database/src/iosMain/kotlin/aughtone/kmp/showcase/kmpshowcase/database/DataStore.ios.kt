package aughtone.kmp.showcase.kmpshowcase.database

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import aughtone.kmp.showcase.kmpshowcase.JournalEntryDto
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.flow.Flow
import okio.FileSystem
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

private class IosJournalDataStore(
    private val dataStore: DataStore<List<JournalEntryDto>>
) : JournalDataStore {
    override val data: Flow<List<JournalEntryDto>> = dataStore.data

    override suspend fun updateData(transform: suspend (List<JournalEntryDto>) -> List<JournalEntryDto>) {
        dataStore.updateData(transform)
    }
}

@OptIn(ExperimentalForeignApi::class)
actual fun createDataStore(): JournalDataStore {
    val dataStore = DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = JournalEntrySerializer,
            producePath = {
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
    )
    return IosJournalDataStore(dataStore)
}
