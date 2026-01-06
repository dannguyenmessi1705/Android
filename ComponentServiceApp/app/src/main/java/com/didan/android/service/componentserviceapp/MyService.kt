package com.didan.android.service.componentserviceapp

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings

/**
 * Class MyService kế thừa từ Service để tạo một dịch vụ nền trong ứng dụng Android.
 * Dịch vụ này có thể thực hiện các tác vụ dài hạn mà không cần tương tác với người dùng (không có giao diện người dùng).
 */
class MyService : Service() {

    lateinit var mediaPlayer: MediaPlayer

    /**
     * Phương thức onBind được gọi khi một thành phần khác muốn liên kết với dịch vụ này.
     * Trả về null nếu việc liên kết không được phép hoặc không cần thiết (trong trường hợp này, dịch vụ không hỗ trợ liên kết).
     */
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    /**
     * Phương thức onStartCommand được gọi khi dịch vụ được khởi động bằng cách gọi startService().
     * Trong phương thức này, bạn có thể thực hiện các tác vụ cần thiết khi dịch vụ bắt đầu.
     * Trả về giá trị từ phương thức cha để xác định cách hệ thống xử lý dịch vụ nếu nó bị giết.
     * intent: Intent? - Intent được sử dụng để khởi động dịch vụ.
     * flags: Int - Các cờ bổ sung về cách dịch vụ được khởi động.
     * startId: Int - Một định danh duy nhất cho yêu cầu khởi động này.
     * Trả về: Int - Giá trị từ phương thức cha xác định cách hệ thống xử lý dịch vụ:
     ** START_STICKY (nếu dịch vụ bị giết, nó sẽ được khởi động lại với intent null),
     ** START_NOT_STICKY (nếu dịch vụ bị giết, nó sẽ không được khởi động lại),
     ** START_REDELIVER_INTENT (nếu dịch vụ bị giết, nó sẽ được khởi động lại với intent ban đầu).
     */
    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        // Tạo một MediaPlayer để phát âm thanh chuông mặc định của hệ thống.
        mediaPlayer = MediaPlayer.create(
            this,
            Settings.System.DEFAULT_RINGTONE_URI
        )

        // Bắt đầu phát âm thanh lặp lại.
        mediaPlayer.isLooping

        mediaPlayer.start()

        return START_STICKY
    }

    /**
     * Phương thức onDestroy được gọi khi dịch vụ bị hủy.
     * Trong phương thức này, bạn nên giải phóng các tài nguyên mà dịch vụ đã sử dụng.
     * Ở đây, chúng ta dừng MediaPlayer để ngừng phát âm thanh khi dịch vụ bị hủy.
     */
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
    }
}