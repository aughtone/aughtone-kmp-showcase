package aughtone.kmp.showcase.kmpshowcase

import aughtone.kmp.showcase.kmpshowcase.endpoints.JournalEntryResource
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import schwarz.it.problem.details.Problem
import schwarz.it.problem.details.ProblemSerializer
import kotlin.test.*

class JournalEntryResourceTest {

    private val jsonConfig = Json {
        serializersModule = SerializersModule {
            contextual(Problem::class, ProblemSerializer())
        }
    }

    @BeforeTest
    fun setup() {
        inMemoryJournalEntries.clear()
    }

    @Test
    fun testGetEmptyJournalEntries() = testApplication {
        application { module() }

        val client = createClient {
            install(ContentNegotiation) {
                json(jsonConfig)
            }
        }

        val response = client.get("/journal-entries")

        assertEquals(HttpStatusCode.OK, response.status)
        val entries: List<JournalEntry> = response.body()
        assertTrue(entries.isEmpty())
    }

    @Test
    fun testCreateJournalEntry() = testApplication {
        application { module() }

        val client = createClient {
            install(ContentNegotiation) {
                json(jsonConfig)
            }
        }

        val newEntry = JournalEntry(
            id = "test-id",
            title = "Test Entry",
            date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            content = "This is a test entry",
            mood = Mood.HAPPY
        )

        val response = client.post("/journal-entries") {
            contentType(ContentType.Application.Json)
            setBody(newEntry)
        }

        assertEquals(HttpStatusCode.Created, response.status)
        
        val createdEntry: JournalEntry = response.body()
        assertEquals("Test Entry", createdEntry.title)
        assertEquals("This is a test entry", createdEntry.content)
        assertEquals(Mood.HAPPY, createdEntry.mood)
        
        assertEquals(1, inMemoryJournalEntries.size)
    }

    @Test
    fun testGetSpecificJournalEntry() = testApplication {
        application { module() }

        val testEntry = JournalEntry(
            id = "test-id-123",
            title = "Test Entry",
            date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            content = "This is a test entry",
            mood = Mood.CALM
        )
        inMemoryJournalEntries.add(testEntry)

        val client = createClient {
            install(ContentNegotiation) {
                json(jsonConfig)
            }
        }

        val response = client.get("/journal-entries/test-id-123")

        assertEquals(HttpStatusCode.OK, response.status)
        val fetchedEntry: JournalEntry = response.body()
        assertEquals("test-id-123", fetchedEntry.id)
        assertEquals("Test Entry", fetchedEntry.title)
    }

    @Test
    fun testGetNonExistentJournalEntryReturnsProblem() = testApplication {
        application { module() }

        val client = createClient {
            install(ContentNegotiation) {
                json(jsonConfig)
            }
        }

        val response = client.get("/journal-entries/non-existent-id")

        assertEquals(HttpStatusCode.NotFound, response.status)
        
        val problemText = response.bodyAsText()
        assertTrue(problemText.contains("https://example.com/problems/not-found"))
        assertTrue(problemText.contains("The journal entry with ID 'non-existent-id' was not found."))
    }

    @Test
    fun testUpdateJournalEntry() = testApplication {
        application { module() }

        val testEntry = JournalEntry(
            id = "test-id-update",
            title = "Old Title",
            date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            content = "Old content",
            mood = Mood.SAD
        )
        inMemoryJournalEntries.add(testEntry)

        val client = createClient {
            install(ContentNegotiation) {
                json(jsonConfig)
            }
        }

        val updatedEntry = testEntry.copy(title = "New Title", mood = Mood.HAPPY)

        val response = client.put("/journal-entries/test-id-update") {
            contentType(ContentType.Application.Json)
            setBody(updatedEntry)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val fetchedEntry: JournalEntry = response.body()
        assertEquals("New Title", fetchedEntry.title)
        assertEquals(Mood.HAPPY, fetchedEntry.mood)
        
        assertEquals("New Title", inMemoryJournalEntries.first().title)
    }

    @Test
    fun testDeleteJournalEntry() = testApplication {
        application { module() }

        val testEntry = JournalEntry(
            id = "test-id-delete",
            title = "To be deleted",
            date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            content = "Delete this",
            mood = Mood.ANXIOUS
        )
        inMemoryJournalEntries.add(testEntry)

        val client = createClient {
            install(ContentNegotiation) {
                json(jsonConfig)
            }
        }

        val response = client.delete("/journal-entries/test-id-delete")

        assertEquals(HttpStatusCode.NoContent, response.status)
        assertTrue(inMemoryJournalEntries.isEmpty())
    }
}
