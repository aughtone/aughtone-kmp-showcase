package aughtone.kmp.showcase.kmpshowcase.database

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import aughtone.kmp.showcase.kmpshowcase.database.model.JournalEntryEntity
import kotlinx.coroutines.flow.Flow
import okio.FileSystem
import okio.Path.Companion.toPath
import java.io.File

private class JvmJournalDataStore(
    private val dataStore: DataStore<List<JournalEntryEntity>>
) : JournalDataStore {
    override val data: Flow<List<JournalEntryEntity>> = dataStore.data

    override suspend fun updateData(transform: suspend (List<JournalEntryEntity>) -> List<JournalEntryEntity>) {
        dataStore.updateData(transform)
    }
}

actual fun createDataStore(): JournalDataStore {
    val dataStore = DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = JournalEntrySerializer,
            producePath = {
                val home = System.getProperty("user.home")
                File(home, DATASTORE_FILE_NAME).absolutePath.toPath()
            }
        )
    )
    return JvmJournalDataStore(dataStore)
}
