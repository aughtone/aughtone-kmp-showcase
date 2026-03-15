package aughtone.kmp.showcase.kmpshowcase.di

import aughtone.kmp.showcase.kmpshowcase.data.JournalRepositoryImpl
import aughtone.kmp.showcase.kmpshowcase.domain.repository.JournalRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<JournalRepository> { JournalRepositoryImpl(get(), get()) }
}
