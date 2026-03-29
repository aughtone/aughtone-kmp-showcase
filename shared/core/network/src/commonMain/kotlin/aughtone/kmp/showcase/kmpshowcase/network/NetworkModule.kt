package aughtone.kmp.showcase.kmpshowcase.network

import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule: Module = module {
    single<HttpClient> {
        httpClient()
    }
}
