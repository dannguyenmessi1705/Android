package com.didan.android.mvvmroom.contactmanagerapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Data Access Object (DAO) cho bảng contacts_table trong cơ sở dữ liệu Room.
 * Chứa các phương thức để thực hiện các thao tác CRUD (Create, Read, Update, Delete) trên bảng này.
 */
@Dao // Đánh dấu interface này là một DAO cho Room
interface ContactDAO {

    /**
     * Chèn một Contact mới vào cơ sở dữ liệu.
     * @param contact Đối tượng Contact cần chèn.
     * suspend: Phương thức này là hàm tạm dừng để hỗ trợ coroutine (Ngăn chặn luồng chính).
     */
    @Insert
    suspend fun insertContact(contact: Contact): Long

    /**
     * Cập nhật thông tin của một Contact đã tồn tại trong cơ sở dữ liệu.
     * @param contact Đối tượng Contact cần cập nhật.
     * suspend: Phương thức này là hàm tạm dừng để hỗ trợ coroutine (Ngăn chặn luồng chính).
     */
    @Update
    suspend fun updateContact(contact: Contact)

    /**
     * Xóa một Contact khỏi cơ sở dữ liệu.
     * @param contact Đối tượng Contact cần xóa.
     * suspend: Phương thức này là hàm tạm dừng để hỗ trợ coroutine (Ngăn chặn luồng chính).
     */
    @Delete
    suspend fun deleteContact(contact: Contact)

    /**
     * Xóa tất cả các Contact khỏi cơ sở dữ liệu.
     * suspend: Phương thức này là hàm tạm dừng để hỗ trợ coroutine (Ngăn chặn luồng chính).
     */
    @Query("DELETE FROM contacts_table")
    suspend fun deleteAll()

    /**
     * Lấy tất cả các Contact từ cơ sở dữ liệu dưới dạng LiveData.
     * @return LiveData chứa danh sách tất cả các Contact.
     * LiveData: Cho phép quan sát dữ liệu và tự động cập nhật giao diện khi dữ liệu thay đổi.
     * Không sử dụng suspend vì LiveData tự xử lý việc quan sát dữ liệu bất đồng bộ.
     */
    @Query("SELECT * FROM contacts_table")
    fun getAllContactsInDB(): LiveData<List<Contact>>
}