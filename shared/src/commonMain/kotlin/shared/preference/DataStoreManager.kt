package shared.preference

import kotlinx.coroutines.flow.Flow

expect class DataStoreManager {
    suspend fun saveUserToken(token: String)
    fun getUserToken(): Flow<String?>
    suspend fun deleteUserToken()
}