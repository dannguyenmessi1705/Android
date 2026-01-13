package com.didan.android.mvvmroom.contactmanagerapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.didan.android.mvvmroom.contactmanagerapp.R
import com.didan.android.mvvmroom.contactmanagerapp.databinding.CardItemBinding
import com.didan.android.mvvmroom.contactmanagerapp.room.Contact

/**
 * Adapter cho RecyclerView để hiển thị danh sách liên hệ.
 * Sử dụng Data Binding để liên kết dữ liệu với giao diện người dùng.
 */
class MyRecyclerViewAdapter(
    private val contactsList: List<Contact>, // Danh sách các Contact để hiển thị
    private val clickListener: (Contact) -> Unit // Hàm xử lý sự kiện khi một mục được nhấp
) : RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {

    /**
     * ViewHolder đại diện cho mỗi mục trong RecyclerView.
     * Sử dụng Data Binding để liên kết các thành phần giao diện với dữ liệu.
     */
    class MyViewHolder(val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        // Liên kết dữ liệu Contact với các thành phần giao diện
        fun bind(contact: Contact, clickListener: (Contact) -> Unit) {
            binding.nameTextView.text = contact.contact_name // Hiển thị tên liên hệ
            binding.emailTextView.text = contact.contact_email // Hiển thị email liên hệ
            binding.listItemLayout.setOnClickListener { // Xử lý sự kiện nhấp vào mục
                clickListener(contact)
            }
        }
    }

    /**
     * Tạo ViewHolder mới khi cần thiết.
     * @param parent ViewGroup cha của ViewHolder.
     * @param viewType Loại của ViewHolder (nếu có nhiều loại).
     * @return MyViewHolder mới được tạo.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        // Tạo LayoutInflater từ context của parent
        val layoutInflater = LayoutInflater.from(parent.context)
        // Sử dụng DataBindingUtil để inflate layout với Data Binding
        val binding: CardItemBinding = DataBindingUtil
            .inflate(
                layoutInflater,
                R.layout.card_item,
                parent,
                false
            )
        return MyViewHolder(binding) // Trả về ViewHolder mới
    }

    /**
     * Liên kết dữ liệu với ViewHolder tại vị trí cụ thể.
     * @param holder ViewHolder cần liên kết dữ liệu.
     * @param position Vị trí của mục trong danh sách.
     */
    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.bind(contactsList[position], clickListener)
    }

    /**
     * Trả về số lượng mục trong danh sách.
     * @return Số lượng mục.
     */
    override fun getItemCount(): Int {
        return contactsList.size
    }
}