package shared.di


import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import shared.preference.DataStoreManager

// expect fun para obtener el módulo específico de la plataforma
actual fun platformModule(): Module = module {
    single { DataStoreManager(androidContext()) }
}
