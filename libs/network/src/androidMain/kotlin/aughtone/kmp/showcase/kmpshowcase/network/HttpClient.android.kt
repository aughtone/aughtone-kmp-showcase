package aughtone.kmp.showcase.kmpshowcase.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

actual fun httpClient(): HttpClient = HttpClient(CIO) {
    // Note: Use a default or configurable base URL
    configureClient("http://10.0.2.2:8080")
}
