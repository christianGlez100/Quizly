package shared.preference

import kotlinx.coroutines.flow.Flow

actual class DataStoreManager {
    actual suspend fun saveUserToken(token: String) {
    }

    actual fun getUserToken(): Flow<String?> {
        TODO("Not yet implemented")
    }

    actual suspend fun deleteUserToken() {
    }
}