package aughtone.kmp.showcase.kmpshowcase.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import aughtone.kmp.showcase.kmpshowcase.database.model.JournalEntryEntity
import kotlinx.coroutines.flow.Flow
import okio.FileSystem
import okio.Path.Companion.toPath
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private class AndroidJournalDataStore(
    private val dataStore: DataStore<List<JournalEntryEntity>>
) : JournalDataStore {
    override val data: Flow<List<JournalEntryEntity>> = dataStore.data

    override suspend fun updateData(transform: suspend (List<JournalEntryEntity>) -> List<JournalEntryEntity>) {
        dataStore.updateData(transform)
    }
}

actual fun createDataStore(): JournalDataStore {
    val context: Context by object : KoinComponent {}.inject()

    val dataStore = DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = JournalEntrySerializer,
            producePath = { context.filesDir.resolve(DATASTORE_FILE_NAME).absolutePath.toPath() }
        )
    )
    return AndroidJournalDataStore(dataStore)
}
