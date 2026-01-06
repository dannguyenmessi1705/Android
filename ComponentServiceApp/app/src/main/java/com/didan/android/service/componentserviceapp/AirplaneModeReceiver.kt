package com.didan.android.service.componentserviceapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * AirplaneModeReceiver là một lớp kế thừa từ BroadcastReceiver để lắng nghe các sự kiện liên quan đến chế độ máy bay (Airplane Mode) trên thiết bị Android.
 * Khi chế độ máy bay được bật hoặc tắt, phương thức onReceive sẽ được gọi để xử lý sự kiện này.
 * BroadcastReceiver là một thành phần quan trọng trong hệ thống Android, cho phép ứng dụng nhận và phản hồi các thông điệp phát sóng (broadcast messages) từ hệ thống hoặc các ứng dụng khác.
 */
class AirplaneModeReceiver : BroadcastReceiver() {

    /**
     * Phương thức onReceive được gọi khi BroadcastReceiver nhận được một Intent phát sóng.
     * Trong phương thức này, bạn có thể xử lý sự kiện liên quan đến chế độ máy bay.
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        // Kiểm tra xem Intent có hành động liên quan đến bật tắt chế độ máy bay hay không
        if (intent?.action != null
            && intent.action.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            // Lấy trạng thái hiện tại của chế độ máy bay từ Intent, mặc định là false nếu không có thông tin
            val isAirplaneModeOn: Boolean = intent.getBooleanExtra("state", false)

            // Xử lý sự kiện bật/tắt chế độ máy bay
            if (isAirplaneModeOn) {
                Toast.makeText(
                    context,
                    "Airplane Mode is ON",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "Airplane Mode is OFF",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}