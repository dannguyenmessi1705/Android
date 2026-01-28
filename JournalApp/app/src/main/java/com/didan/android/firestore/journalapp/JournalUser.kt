package com.didan.android.firestore.journalapp

import android.app.Application

/**
 * Class JournalUser kế thừa từ Application để khởi tạo các thành phần toàn cục của ứng dụng.
 * Nó sẽ chạy trước khi bất kỳ Activity nào được tạo.
 */
class JournalUser : Application() {
    var username: String? = null
    var userId: String? = null

    /**
     * Đối tượng companion để giữ một instance duy nhất của JournalUser, theo kiểu Singleton, tránh việc tạo nhiều instance không cần thiết.
     * Phương thức get sẽ tạo một instance mới nếu chưa có.
     */
    companion object {
        var instance: JournalUser? = null
            get() {
                if (field == null) {
                    // Tạo một instance mới của JournalUser nếu chưa có
                    field = JournalUser()
                }
                return field
            }

            private set
    }
}