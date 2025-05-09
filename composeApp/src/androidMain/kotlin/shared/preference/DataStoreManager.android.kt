package shared.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

actual class DataStoreManager(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "quizly_datastore")
    private val tokenKey = stringPreferencesKey("userToken")

    actual suspend fun saveUserToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    actual fun getUserToken(): Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[tokenKey]
    }

    actual suspend fun deleteUserToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(tokenKey)
        }
    }
}