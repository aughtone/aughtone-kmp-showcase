package aughtone.kmp.showcase.kmpshowcase.di

import aughtone.kmp.showcase.kmpshowcase.JournalRepository
import aughtone.kmp.showcase.kmpshowcase.ui.list.ListViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val appModule = module {
    single { JournalRepository() }
    viewModel { ListViewModel(get()) }
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(appModule)
    }
}
