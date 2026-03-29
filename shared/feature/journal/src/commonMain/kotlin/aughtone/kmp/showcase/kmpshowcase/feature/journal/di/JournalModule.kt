package aughtone.kmp.showcase.kmpshowcase.feature.journal.di

import aughtone.kmp.showcase.kmpshowcase.feature.journal.details.DetailsViewModel
import aughtone.kmp.showcase.kmpshowcase.feature.journal.list.ListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val journalModule = module {
    viewModel { ListViewModel(get()) }
    viewModel { DetailsViewModel(get(), get()) }
}
