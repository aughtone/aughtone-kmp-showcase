package aughtone.kmp.showcase.kmpshowcase.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js

actual fun httpClient(): HttpClient = HttpClient(Js) {
    configureClient("http://localhost:8080")
}
