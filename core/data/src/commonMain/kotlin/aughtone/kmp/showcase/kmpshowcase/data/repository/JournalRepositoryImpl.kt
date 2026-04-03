package aughtone.kmp.showcase.kmpshowcase.data.repository

import aughtone.kmp.showcase.kmpshowcase.JournalEntryDto
import aughtone.kmp.showcase.kmpshowcase.MoodDto
import aughtone.kmp.showcase.kmpshowcase.database.Database
import aughtone.kmp.showcase.kmpshowcase.database.model.JournalEntryEntity
import aughtone.kmp.showcase.kmpshowcase.database.model.MoodEntity
import aughtone.kmp.showcase.kmpshowcase.domain.model.JournalEntry
import aughtone.kmp.showcase.kmpshowcase.domain.model.Mood
import aughtone.kmp.showcase.kmpshowcase.domain.repository.JournalRepository
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
                val remoteEntries: List<JournalEntryDto> = httpClient.get(JournalEntryResource()).body()
                database.saveEntries(remoteEntries.map { it.toEntity() })
            } catch (e: Exception) {
                println("Failed to fetch journal entries: ${e.message}")
            }
        }
    }

    override fun getEntries(): Flow<List<JournalEntry>> = database.getEntries()
        .map { entries ->
            entries.sortedByDescending { it.date }
                .map { it.toDomain() }
        }

    override fun getEntry(id: String): Flow<JournalEntry?> = database.getEntry(id)
        .map { it?.toDomain() }

    override suspend fun addEntry(
        title: String,
        content: String,
        mood: Mood
    ): Result<JournalEntry> = runCatching {
        val now = Clock.System.now()
        val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date

        val newEntryDto = JournalEntryDto(
            id = "",
            title = title,
            date = today,
            content = content,
            mood = mood.toDto()
        )

        // Push to the server
        httpClient.post(JournalEntryResource()) {
            contentType(ContentType.Application.Json)
            setBody(newEntryDto)
        }.body<JournalEntryDto>().toDomain()
    }.onSuccess { createdEntry ->
        database.addEntry(createdEntry.toEntity())
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
        database.addEntry(offlineEntry.toEntity())
    }

    private fun JournalEntryDto.toEntity(): JournalEntryEntity = JournalEntryEntity(
        id = id,
        title = title,
        date = date,
        content = content,
        mood = mood.toEntity()
    )

    private fun MoodDto.toEntity(): MoodEntity = when (this) {
        MoodDto.HAPPY -> MoodEntity.HAPPY
        MoodDto.SAD -> MoodEntity.SAD
        MoodDto.CALM -> MoodEntity.CALM
        MoodDto.ENERGETIC -> MoodEntity.ENERGETIC
        MoodDto.ANXIOUS -> MoodEntity.ANXIOUS
    }

    private fun JournalEntryEntity.toDomain(): JournalEntry = JournalEntry(
        id = id,
        title = title,
        date = date,
        content = content,
        mood = mood.toDomain()
    )

    private fun MoodEntity.toDomain(): Mood = when (this) {
        MoodEntity.HAPPY -> Mood.HAPPY
        MoodEntity.SAD -> Mood.SAD
        MoodEntity.CALM -> Mood.CALM
        MoodEntity.ENERGETIC -> Mood.ENERGETIC
        MoodEntity.ANXIOUS -> Mood.ANXIOUS
    }

    private fun JournalEntry.toEntity(): JournalEntryEntity = JournalEntryEntity(
        id = id,
        title = title,
        date = date,
        content = content,
        mood = mood.toEntity()
    )

    private fun Mood.toEntity(): MoodEntity = when (this) {
        Mood.HAPPY -> MoodEntity.HAPPY
        Mood.SAD -> MoodEntity.SAD
        Mood.CALM -> MoodEntity.CALM
        Mood.ENERGETIC -> MoodEntity.ENERGETIC
        Mood.ANXIOUS -> MoodEntity.ANXIOUS
    }

    private fun JournalEntryDto.toDomain(): JournalEntry = JournalEntry(
        id = id,
        title = title,
        date = date,
        content = content,
        mood = mood.toDomain()
    )

    private fun MoodDto.toDomain(): Mood = when (this) {
        MoodDto.HAPPY -> Mood.HAPPY
        MoodDto.SAD -> Mood.SAD
        MoodDto.CALM -> Mood.CALM
        MoodDto.ENERGETIC -> Mood.ENERGETIC
        MoodDto.ANXIOUS -> Mood.ANXIOUS
    }

    private fun Mood.toDto(): MoodDto = when (this) {
        Mood.HAPPY -> MoodDto.HAPPY
        Mood.SAD -> MoodDto.SAD
        Mood.CALM -> MoodDto.CALM
        Mood.ENERGETIC -> MoodDto.ENERGETIC
        Mood.ANXIOUS -> MoodDto.ANXIOUS
    }
}
