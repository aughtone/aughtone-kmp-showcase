package aughtone.kmp.showcase.kmpshowcase

import aughtone.kmp.showcase.kmpshowcase.endpoints.JournalEntryResource
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import schwarz.it.problem.details.Problem

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }

        // --- JournalEntryResource Routes ---

        // Get all entries
        get<JournalEntryResource> { _ ->
            call.respond(inMemoryJournalEntries.sortedByDescending { it.date })
        }

        // Create a new entry
        post<JournalEntryResource, JournalEntry> { _, newEntry ->
            val now = Clock.System.now()
            val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
            
            val entryToSave = newEntry.copy(
                id = newEntry.id.ifEmpty { now.toEpochMilliseconds().toString() },
                date = today
            )
            
            inMemoryJournalEntries.add(entryToSave)
            call.respond(HttpStatusCode.Created, entryToSave)
        }

        // Get a specific entry
        get<JournalEntryResource.Id> { resource ->
            val entry = inMemoryJournalEntries.find { it.id == resource.id }
            if (entry != null) {
                call.respond(entry)
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = Problem(
                        type = "https://example.com/problems/not-found",
                        title = "Entry Not Found",
                        status = HttpStatusCode.NotFound.value,
                        detail = "The journal entry with ID '${resource.id}' was not found.",
                        instance = call.request.uri
                    )
                )
            }
        }

        // Update a specific entry
        put<JournalEntryResource.Id, JournalEntry> { resource, updateData ->
            val index = inMemoryJournalEntries.indexOfFirst { it.id == resource.id }
            
            if (index != -1) {
                // Ensure the ID isn't changed during update
                val updatedEntry = updateData.copy(id = resource.id)
                inMemoryJournalEntries[index] = updatedEntry
                call.respond(HttpStatusCode.OK, updatedEntry)
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = Problem(
                        type = "https://example.com/problems/not-found",
                        title = "Entry Not Found",
                        status = HttpStatusCode.NotFound.value,
                        detail = "Cannot update because the journal entry with ID '${resource.id}' was not found.",
                        instance = call.request.uri
                    )
                )
            }
        }

        // Delete a specific entry
        delete<JournalEntryResource.Id> { resource ->
            val removed = inMemoryJournalEntries.removeIf { it.id == resource.id }
            if (removed) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = Problem(
                        type = "https://example.com/problems/not-found",
                        title = "Entry Not Found",
                        status = HttpStatusCode.NotFound.value,
                        detail = "Cannot delete because the journal entry with ID '${resource.id}' was not found.",
                        instance = call.request.uri
                    )
                )
            }
        }
    }
}
