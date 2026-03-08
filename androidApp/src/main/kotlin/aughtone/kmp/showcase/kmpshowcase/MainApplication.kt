package aughtone.kmp.showcase.kmpshowcase

import android.app.Application
import aughtone.kmp.showcase.kmpshowcase.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@MainApplication)
        }
    }
}
