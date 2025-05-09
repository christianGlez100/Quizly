package com.sesi.quizly

import android.app.Application
import com.sesi.quizly.di.appModule
import io.ktor.client.engine.android.Android
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import shared.di.platformModule


class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(appModule(Android.create()), platformModule())
        }
    }
}