package aughtone.kmp.showcase.kmpshowcase.di

import aughtone.kmp.showcase.kmpshowcase.domain.JournalRepository
import aughtone.kmp.showcase.kmpshowcase.data.JournalRepositoryImpl
import aughtone.kmp.showcase.kmpshowcase.ui.list.ListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val listModule: Module = module {
    single<JournalRepository> { JournalRepositoryImpl(get(), get()) }
    viewModel { ListViewModel(get()) }
}
