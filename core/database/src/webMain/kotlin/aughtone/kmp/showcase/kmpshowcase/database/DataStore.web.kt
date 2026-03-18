package aughtone.kmp.showcase.kmpshowcase.database

import aughtone.kmp.showcase.kmpshowcase.JournalEntryDto
import kotlinx.browser.localStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

private class WebJournalDataStore : JournalDataStore {
    private val key = DATASTORE_FILE_NAME
    private val _data = MutableStateFlow(loadFromStorage())

    override val data: Flow<List<JournalEntryDto>> = _data.asStateFlow()

    override suspend fun updateData(transform: suspend (List<JournalEntryDto>) -> List<JournalEntryDto>) {
        val current = _data.value
        val updated = transform(current)
        _data.value = updated
        saveToStorage(updated)
    }

    private fun loadFromStorage(): List<JournalEntryDto> {
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

    private fun saveToStorage(entries: List<JournalEntryDto>) {
        try {
            localStorage.setItem(key, Json.encodeToString(entries))
        } catch (e: Exception) {
            // Ignore storage errors
        }
    }
}

actual fun createDataStore(): JournalDataStore = WebJournalDataStore()
