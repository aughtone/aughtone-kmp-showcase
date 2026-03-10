package aughtone.kmp.showcase.kmpshowcase.database

import aughtone.kmp.showcase.kmpshowcase.JournalEntry
import kotlinx.browser.localStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

private class WebJournalDataStore : JournalDataStore {
    private val key = DATASTORE_FILE_NAME
    private val _data = MutableStateFlow(loadFromStorage())

    override val data: Flow<List<JournalEntry>> = _data.asStateFlow()

    override suspend fun updateData(transform: suspend (List<JournalEntry>) -> List<JournalEntry>) {
        val current = _data.value
        val updated = transform(current)
        _data.value = updated
        saveToStorage(updated)
    }

    private fun loadFromStorage(): List<JournalEntry> {
        return try {
            val stored = localStorage.getItem(key)
            if (stored != null) {
                Json.decodeFromString(stored)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun saveToStorage(entries: List<JournalEntry>) {
        try {
            localStorage.setItem(key, Json.encodeToString(entries))
        } catch (e: Exception) {
            // Ignore storage errors
        }
    }
}

actual fun createDataStore(): JournalDataStore = WebJournalDataStore()
