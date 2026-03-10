package aughtone.kmp.showcase.kmpshowcase.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import aughtone.kmp.showcase.kmpshowcase.JournalEntry
import okio.Path.Companion.toPath
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual fun createDataStore(): DataStore<List<JournalEntry>> {
    val context: Context by object : KoinComponent {
        val context: Context by inject()
    }.inject()

    return DataStoreFactory.create(
        serializer = JournalEntrySerializer,
        produceFile = { context.filesDir.resolve(DATASTORE_FILE_NAME).absolutePath.toPath() }
    )
}
