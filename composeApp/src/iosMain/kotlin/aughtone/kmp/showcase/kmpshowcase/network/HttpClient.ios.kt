package aughtone.kmp.showcase.kmpshowcase.network

import aughtone.kmp.showcase.kmpshowcase.SERVER_PORT
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual fun httpClient(): HttpClient = HttpClient(Darwin) {
    engine {
        configureRequest {
            setAllowsCellularAccess(true)
        }
    }
    configureClient("http://localhost:${SERVER_PORT}")
}
