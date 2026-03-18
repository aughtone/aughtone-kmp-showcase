package aughtone.kmp.showcase.kmpshowcase.data

import aughtone.kmp.showcase.kmpshowcase.JournalEntry
import aughtone.kmp.showcase.kmpshowcase.JournalRepository
import aughtone.kmp.showcase.kmpshowcase.Mood
import aughtone.kmp.showcase.kmpshowcase.endpoints.JournalEntryResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlin.time.Clock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class JournalRepositoryImpl(private val httpClient: HttpClient) : JournalRepository {
    private val _entries = MutableStateFlow<List<JournalEntry>>(emptyList())
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    init {
        scope.launch {
            refreshEntries()
        }
    }

    override suspend fun refreshEntries() {
        try {
            val remoteEntries: List<JournalEntry> = httpClient.get(JournalEntryResource()).body()
            _entries.value = remoteEntries
        } catch (e: Exception) {
            // Log or handle the fetch error as needed, keeping cached state in the meantime
            println("Failed to fetch journal entries: ${e.message}")
        }
    }
    
    override fun getEntries(): Flow<List<JournalEntry>> = _entries.asStateFlow()
        .map { entries -> 
            entries.sortedByDescending { it.date } 
        }

    override suspend fun addEntry(
        title: String,
        content: String,
        mood: Mood
    ): Result<JournalEntry> = runCatching {
        val now = Clock.System.now()
        val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        
        // Optimistically create the local entry
        val newEntry = JournalEntry(
            id = "", // Let the server decide if we want, or generate a temporary one
            title = title,
            date = today,
            content = content,
            mood = mood
        )

        // Push to the server
        httpClient.post(JournalEntryResource()) {
            contentType(ContentType.Application.Json)
            setBody(newEntry)
        }.body<JournalEntry>()
    }.onSuccess { createdEntry ->
        // Update the cache with the server's confirmed response (including the real ID)
        _entries.value += createdEntry
    }.onFailure { e ->
        println("Failed to create journal entry on server: ${e.message}")
    }
}
