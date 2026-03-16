package aughtone.kmp.showcase.kmpshowcase.data

import aughtone.kmp.showcase.kmpshowcase.database.databaseModule
import aughtone.kmp.showcase.kmpshowcase.domain.repository.JournalRepository
import aughtone.kmp.showcase.kmpshowcase.network.networkModule
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    includes(networkModule, databaseModule)

    single<JournalRepository> { JournalRepositoryImpl(get(), get()) }
}
