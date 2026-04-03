package aughtone.kmp.showcase.kmpshowcase.data

import aughtone.kmp.showcase.kmpshowcase.Mood
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondBadRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class JournalRepositoryImplTest {

    @Test
    fun testRefreshEntries() = runTest {
        val mockEngine = MockEngine { request ->
            if (request.url.encodedPath == "/journal-entries" && request.method == HttpMethod.Get) {
                respond(
                    content = """[{"id":"1","title":"Test","date":"2023-01-01","content":"Test Content","mood":"HAPPY"}]""",
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            } else {
                respondBadRequest()
            }
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json()
            }
            install(Resources)
        }

        val repository = JournalRepositoryImpl(client)
        
        repository.refreshEntries()

        val entries = repository.getEntries().first()
        assertEquals(1, entries.size)
        assertEquals("Test", entries[0].title)
        assertEquals(Mood.HAPPY, entries[0].mood)
        assertEquals("Test Content", entries[0].content)
    }

    @Test
    fun testAddEntry() = runTest {
        val mockEngine = MockEngine { request ->
            if (request.url.encodedPath == "/journal-entries" && request.method == HttpMethod.Post) {
                respond(
                    content = """{"id":"2","title":"New Title","date":"2023-01-02","content":"New Content","mood":"SAD"}""",
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            } else if (request.url.encodedPath == "/journal-entries" && request.method == HttpMethod.Get) {
                respond(
                    content = """[]""",
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            } else {
                respondBadRequest()
            }
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json()
            }
            install(Resources)
        }

        val repository = JournalRepositoryImpl(client)
        
        val result = repository.addEntry("New Title", "New Content", Mood.SAD)
        assertTrue(result.isSuccess)
        
        val newEntry = result.getOrNull()
        assertEquals("2", newEntry?.id)
        assertEquals("New Title", newEntry?.title)
        assertEquals("New Content", newEntry?.content)
        
        // Validate the cache update
        val entries = repository.getEntries().first()
        assertEquals(1, entries.size)
        assertEquals("2", entries[0].id)
        assertEquals("New Title", entries[0].title)
    }
}
