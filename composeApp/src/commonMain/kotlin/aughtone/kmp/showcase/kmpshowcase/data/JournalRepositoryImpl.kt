package aughtone.kmp.showcase.kmpshowcase.data

import aughtone.kmp.showcase.kmpshowcase.JournalEntry
import aughtone.kmp.showcase.kmpshowcase.Mood
import aughtone.kmp.showcase.kmpshowcase.database.Database
import aughtone.kmp.showcase.kmpshowcase.domain.JournalRepository
import aughtone.kmp.showcase.kmpshowcase.endpoints.JournalEntryResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

class JournalRepositoryImpl(
    private val httpClient: HttpClient,
    private val database: Database
) : JournalRepository {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    init {
        fetchEntries()
    }

    private fun fetchEntries() {
        scope.launch {
            try {
                val remoteEntries: List<JournalEntry> = httpClient.get(JournalEntryResource()).body()
                database.saveEntries(remoteEntries)
            } catch (e: Exception) {
                println("Failed to fetch journal entries: ${e.message}")
            }
        }
    }
    
    override fun getEntries(): Flow<List<JournalEntry>> = database.getEntries()
        .map { entries -> 
            entries.sortedByDescending { it.date } 
        }

    override fun getEntry(id: String): Flow<JournalEntry?> = database.getEntry(id)

    override suspend fun addEntry(
        title: String,
        content: String,
        mood: Mood
    ): Result<JournalEntry> = runCatching {
        val now = Clock.System.now()
        val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        
        val newEntry = JournalEntry(
            id = "", 
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
        database.addEntry(createdEntry)
    }.onFailure { e ->
        println("Failed to create journal entry on server: ${e.message}")
        // If network fails, we still want to save it locally
        val now = Clock.System.now()
        val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        val offlineEntry = JournalEntry(
            id = "offline_${now.toEpochMilliseconds()}",
            title = title,
            date = today,
            content = content,
            mood = mood
        )
        database.addEntry(offlineEntry)
    }
}
