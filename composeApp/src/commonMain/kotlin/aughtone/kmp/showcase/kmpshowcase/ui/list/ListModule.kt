package aughtone.kmp.showcase.kmpshowcase.ui.list

import aughtone.kmp.showcase.kmpshowcase.JournalRepository
import aughtone.kmp.showcase.kmpshowcase.data.JournalRepositoryImpl
import aughtone.kmp.showcase.kmpshowcase.network.httpClient
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val listModule: Module = module {
    single<JournalRepository> { JournalRepositoryImpl(get()) }
    viewModel { ListViewModel(get()) }
}
