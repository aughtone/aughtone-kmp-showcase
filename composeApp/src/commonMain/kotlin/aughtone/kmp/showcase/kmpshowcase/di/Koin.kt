package aughtone.kmp.showcase.kmpshowcase.di

import aughtone.kmp.showcase.kmpshowcase.data.repositoryModule
import aughtone.kmp.showcase.kmpshowcase.feature.journal.di.journalModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(repositoryModule)
        modules(journalModule)
    }
}
