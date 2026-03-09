package aughtone.kmp.showcase.kmpshowcase

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.path
import io.ktor.server.resources.Resources
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.slf4j.event.Level
import schwarz.it.problem.details.Problem
import schwarz.it.problem.details.ProblemSerializer

val inMemoryJournalEntries = mutableListOf<JournalEntry>()

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(CallLogging) {
        level = Level.INFO
//        /journal-entries
        filter { call -> call.request.path().startsWith("/") }
    }
    install(Resources)
    install(ContentNegotiation) {
        json(Json {
            serializersModule = SerializersModule {
                contextual(Problem::class, ProblemSerializer())
            }
        })
    }

    configureRouting()
}
