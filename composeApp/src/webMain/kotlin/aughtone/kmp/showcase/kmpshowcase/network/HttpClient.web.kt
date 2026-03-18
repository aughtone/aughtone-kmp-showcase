package aughtone.kmp.showcase.kmpshowcase.network

import aughtone.kmp.showcase.kmpshowcase.SERVER_PORT
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js

actual fun httpClient(): HttpClient = HttpClient(Js) {
    configureClient("http://localhost:${SERVER_PORT}")
}
