package com.devluque.dictionaryai

import android.app.Application
import com.devluque.core.diFrameworkCoreModule
import com.devluque.search.diSearchModule
import com.devluque.worddetail.diWordDetailModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                appModule,
                diSearchModule,
                diFrameworkCoreModule,
                diWordDetailModule
            )
        }
    }
}

val appModule = module {
    single(named("apiKey")) {
        BuildConfig.API_KEY
    }
}