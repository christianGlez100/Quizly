package shared.preference

import kotlinx.coroutines.flow.Flow

class PreferenceManager(private val dataStoreManager: DataStoreManager) {

   suspend fun saveUserToken(token: String) {
        dataStoreManager.saveUserToken(token)
    }

    fun getUserToken(): Flow<String?> {
        return dataStoreManager.getUserToken()
    }

    suspend fun deleteUserToken() {
        dataStoreManager.deleteUserToken()
    }

}