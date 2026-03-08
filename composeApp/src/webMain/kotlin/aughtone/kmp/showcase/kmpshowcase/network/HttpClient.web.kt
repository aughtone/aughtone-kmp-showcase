package aughtone.kmp.showcase.kmpshowcase.network

import aughtone.kmp.showcase.kmpshowcase.SERVER_PORT
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

actual fun httpClient(): HttpClient  = HttpClient(CIO) {
    configureClient("http://localhost:${SERVER_PORT}")
}
