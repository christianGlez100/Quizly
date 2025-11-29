package shared

import androidx.compose.runtime.Composable

actual class PermissionsManager actual constructor(private val callback: PermissionCallback) :
    PermissionHandler {
    @Composable
    override fun askPermission(permission: PermissionType) {
        callback.onPermissionStatus(permission, PermissionStatus.GRANTED)
    }

    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean {
        return true
    }

    @Composable
    override fun launchSettings() {
        println("Attempted to launch settings on JVM, which is not applicable.")
    }

}

@Composable
actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager {
    return PermissionsManager(callback)
}