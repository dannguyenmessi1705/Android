package com.didan.android.mvvmroom.contactmanagerapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Lớp dữ liệu đại diện cho một Contact trong cơ sở dữ liệu Room, mỗi class đại diện cho một bảng trong DB.
 */
@Entity(tableName = "contacts_table")
data class Contact(
    @PrimaryKey(autoGenerate = true) // Tự động tăng giá trị cho khóa chính
//    @ColumnInfo(name = "contact_id") // Tên cột trong bảng
    val contact_id : Int,

//    @ColumnInfo(name = "contact_name") // Tên cột trong bảng
    var contact_name : String,

//    @ColumnInfo(name = "contact_email") // Tên cột trong bảng
    var contact_email : String
)
