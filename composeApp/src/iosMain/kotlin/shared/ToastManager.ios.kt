package shared

import androidx.compose.runtime.Composable
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication

@Composable
actual fun showToast(message: String, duration: ToastDuration) {
    val alert = UIAlertController.alertControllerWithTitle(
        title = "Message",
        message = message,
        preferredStyle = UIAlertControllerStyleAlert
    )
    alert.addAction(UIAlertAction.actionWithTitle("OK", UIAlertActionStyleDefault, null))
    UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(alert, true, null)
}