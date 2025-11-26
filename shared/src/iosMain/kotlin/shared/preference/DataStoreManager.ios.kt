package shared.preference

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import platform.Foundation.NSUserDefaults

actual class DataStoreManager(private val userDefaults: NSUserDefaults) {

    private val settings: Settings = NSUserDefaultsSettings(userDefaults)
    private val tokenKey = "userToken"

    actual suspend fun saveUserToken(token: String) {
        settings.putString(tokenKey, token)
    }

    actual fun getUserToken(): Flow<String?> = flow {
        emit(settings.getStringOrNull(tokenKey))
    }

    actual suspend fun deleteUserToken() {
        settings.remove(tokenKey)
    }
}