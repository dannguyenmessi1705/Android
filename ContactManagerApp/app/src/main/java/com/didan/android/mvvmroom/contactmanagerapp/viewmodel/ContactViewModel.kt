package com.didan.android.mvvmroom.contactmanagerapp.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didan.android.mvvmroom.contactmanagerapp.repository.ContactRepository
import com.didan.android.mvvmroom.contactmanagerapp.room.Contact
import kotlinx.coroutines.launch

/**
 * ViewModel: Lưu trữ và quản lý dữ liệu liên hệ cho giao diện người dùng.
 * Giữ dữ liệu tồn tại qua các thay đổi cấu hình như xoay màn hình.
 * Kế thừa từ ViewModel và triển khai Observable để hỗ trợ Data Binding.
 */
class ContactViewModel(private val repository: ContactRepository) : ViewModel(), Observable {
    // Lấy danh sách liên hệ từ repository dưới dạng LiveData.
    val contacts = repository.contacts

    // Tạo biến cờ để xác định hành động hiện tại (thêm mới hoặc cập nhật/xóa).
    private var isUpdateOrDelete = false

    // Tạo biến để lưu trữ liên hệ hiện tại đang được cập nhật hoặc xóa.
    private lateinit var contactToUpdateOrDelete: Contact

    // Data Binding với LiveData
    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    // Khởi tạo dữ liệu ban đầu
    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    /**
     * Chèn một liên hệ mới vào cơ sở dữ liệu.
     * Sử dụng viewModelScope để chạy trong coroutine, tránh chặn luồng chính
     */
    fun insert(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)
    }

    /**
     * Xóa một liên hệ khỏi cơ sở dữ liệu.
     * Sử dụng viewModelScope để chạy trong coroutine, tránh chặn luồng chính
     */
    fun delete(contact: Contact) = viewModelScope.launch {
        repository.delete(contact)

        // Sau khi xóa, đặt lại trạng thái về thêm mới
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    /**
     * Cập nhật một liên hệ trong cơ sở dữ liệu.
     * Sử dụng viewModelScope để chạy trong coroutine, tránh chặn luồng chính
     */
    fun update(contact: Contact) = viewModelScope.launch {
        repository.update(contact)

        // Sau khi cập nhật, đặt lại trạng thái về thêm mới
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    /**
     * Xóa tất cả liên hệ khỏi cơ sở dữ liệu.
     * Sử dụng viewModelScope để chạy trong coroutine, tránh chặn luồng chính
     */
    fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    /**
     * Xử lý sự kiện lưu hoặc cập nhật liên hệ dựa trên trạng thái hiện tại.
     * Nếu đang ở trạng thái cập nhật/xóa, sẽ cập nhật liên hệ hiện tại.
     * Nếu không, sẽ thêm một liên hệ mới.
     */
    fun saveOrUpdate() {
        if (isUpdateOrDelete) {
            // Cập nhật liên hệ hiện tại
            contactToUpdateOrDelete.contact_name = inputName.value!!
            contactToUpdateOrDelete.contact_email = inputEmail.value!!
            update(contactToUpdateOrDelete)
        } else {
            // Thêm liên hệ mới
            val name = inputName.value!!
            val email = inputEmail.value!!
            val newContact = Contact(0, name, email)
            insert(newContact)

            // Xóa dữ liệu nhập sau khi lưu
            inputName.value = null
            inputEmail.value = null
        }
    }

    /**
     * Xử lý sự kiện xóa tất cả hoặc xóa liên hệ hiện tại dựa trên trạng thái hiện tại.
     * Nếu đang ở trạng thái cập nhật/xóa, sẽ xóa liên hệ hiện tại.
     * Nếu không, sẽ xóa tất cả liên hệ.
     */
    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            // Xóa liên hệ hiện tại
            delete(contactToUpdateOrDelete)
        } else {
            // Xóa tất cả liên hệ
            clearAll()
        }
    }

    /**
     * Khởi tạo trạng thái cập nhật/xóa với liên hệ được chọn.
     * Thiết lập dữ liệu nhập và thay đổi văn bản nút tương ứng.
     */
    fun initUpdateAndDelete(contact: Contact) {
        inputName.value = contact.contact_name // Set tên liên hệ vào inputName
        inputEmail.value = contact.contact_email // Set email liên hệ vào inputEmail
        isUpdateOrDelete = true // Đặt cờ là đang ở trạng thái cập nhật/xóa
        contactToUpdateOrDelete = contact // Lưu liên hệ hiện tại để cập nhật/xóa
        saveOrUpdateButtonText.value = "Update" // Thay đổi nút thành "Update"
        clearAllOrDeleteButtonText.value = "Delete" // Thay đổi nút thành "Delete"
    }

    // Observable callBacks for Data Binding
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        // Không cần triển khai cho LiveData
    }

    // Observable callBacks for Data Binding
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        // Không cần triển khai cho LiveData
    }
}
