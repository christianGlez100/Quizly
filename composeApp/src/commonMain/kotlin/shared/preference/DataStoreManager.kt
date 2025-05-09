package shared.preference

import kotlinx.coroutines.flow.Flow
import org.koin.core.module.Module

expect class DataStoreManager {
    suspend fun saveUserToken(token: String)
    fun getUserToken(): Flow<String?>
    suspend fun deleteUserToken()
}