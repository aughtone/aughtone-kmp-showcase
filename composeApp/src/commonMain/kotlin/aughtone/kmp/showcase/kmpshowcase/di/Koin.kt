package aughtone.kmp.showcase.kmpshowcase.di

import aughtone.kmp.showcase.kmpshowcase.database.databaseModule
import aughtone.kmp.showcase.kmpshowcase.feature.journal.di.journalModule
import aughtone.kmp.showcase.kmpshowcase.network.networkModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(networkModule)
        modules(databaseModule)
        modules(repositoryModule)
        modules(journalModule)
    }
}
