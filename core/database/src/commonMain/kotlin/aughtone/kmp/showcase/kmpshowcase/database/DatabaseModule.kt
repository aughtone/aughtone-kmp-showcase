package aughtone.kmp.showcase.kmpshowcase.database

import org.koin.dsl.module

val databaseModule = module {
    single { createDataStore() }
    single<Database> { DatabaseImpl(get()) }
}
