package com.didan.android.mvvmroom.contactmanagerapp.repository

import com.didan.android.mvvmroom.contactmanagerapp.room.Contact
import com.didan.android.mvvmroom.contactmanagerapp.room.ContactDAO

// Repository: Hoạt động như một cầu nối giữa ViewModel và Data Source (DAO).
class ContactRepository(private val contactDAO: ContactDAO) {

    // Lấy tất cả các Contact từ cơ sở dữ liệu dưới dạng LiveData.
    val contacts = contactDAO.getAllContactsInDB()

    suspend fun insert(contact: Contact): Long {
        return contactDAO.insertContact(contact)
    }

    suspend fun update(contact: Contact) {
        return contactDAO.updateContact(contact)
    }

    suspend fun delete(contact: Contact) {
        return contactDAO.deleteContact(contact)
    }

    suspend fun deleteAll() {
        return contactDAO.deleteAll()
    }

}