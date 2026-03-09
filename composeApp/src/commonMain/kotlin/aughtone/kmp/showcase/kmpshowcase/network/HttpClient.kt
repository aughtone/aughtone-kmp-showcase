package aughtone.kmp.showcase.kmpshowcase.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


expect fun httpClient(): HttpClient

fun HttpClientConfig<*>.configureClient(baseUrl:String= "http://localhost:8080") {
    expectSuccess = true
    defaultRequest {
        url(baseUrl)
        accept(ContentType.Application.Json)
        contentType(ContentType.Application.Json)
    }

    install(Logging) {
        level = LogLevel.NONE
    }
    install(Resources)
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            // isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = true
            explicitNulls = false
        })
    }
}
