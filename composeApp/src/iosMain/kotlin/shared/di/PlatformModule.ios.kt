package shared.di

import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults
import shared.preference.DataStoreManager

// Implementación actual del módulo de plataforma para iOS
actual fun platformModule(): Module = module {
    // Declarar ThemePreference como un singleton, usando NSUserDefaults.standard
    single { DataStoreManager(NSUserDefaults.standardUserDefaults()) }
}