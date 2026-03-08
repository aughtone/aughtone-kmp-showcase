package aughtone.kmp.showcase.kmpshowcase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class JournalRepository {
    private val _entries = MutableStateFlow<List<JournalEntry>>(emptyList())
    
    fun getEntries(): Flow<List<JournalEntry>> = _entries.asStateFlow()
        .map { entries -> 
            entries.sortedByDescending { it.date } 
        }

    fun addEntry(title: String, content: String, mood: Mood) {
        val now = Clock.System.now()
        val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        val newEntry = JournalEntry(
            id = now.toEpochMilliseconds().toString(),
            title = title,
            date = today,
            content = content,
            mood = mood
        )
        _entries.value = _entries.value + newEntry
    }
}
