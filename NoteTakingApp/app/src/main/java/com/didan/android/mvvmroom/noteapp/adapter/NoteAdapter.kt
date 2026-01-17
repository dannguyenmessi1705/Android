package com.didan.android.mvvmroom.noteapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.didan.android.mvvmroom.noteapp.databinding.NoteLayoutBinding
import com.didan.android.mvvmroom.noteapp.fragments.HomeFragmentDirections
import com.didan.android.mvvmroom.noteapp.model.Note
import java.util.Random

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(val binding: NoteLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * DiffUtil Callback là một lớp tiện ích trong Android được sử dụng để tối ưu hóa việc cập nhật dữ liệu trong RecyclerView.
     * Nó giúp xác định sự khác biệt giữa hai danh sách dữ liệu và chỉ cập nhật những phần thay đổi, thay vì làm mới toàn bộ danh sách.
     * Điều này giúp cải thiện hiệu suất và trải nghiệm người dùng khi làm việc với các danh sách lớn.
     * object là từ khóa trong Kotlin để khai báo một singleton, tức là một lớp chỉ có một thể hiện duy nhất trong suốt vòng đời của ứng dụng.
     */
    private val differCallback = object : DiffUtil.ItemCallback<Note>() {

        /**
         * areItemsTheSame: Phương thức này được sử dụng để kiểm tra xem hai mục (item) có phải là cùng một mục hay không.
         * Thông thường, bạn sẽ so sánh các ID hoặc các thuộc tính duy nhất của mục để xác định điều này.
         */
        override fun areItemsTheSame(
            oldItem: Note,
            newItem: Note
        ): Boolean {
            return oldItem.id == newItem.id
                    && oldItem.noteTitle == newItem.noteTitle
                    && oldItem.noteBody == newItem.noteBody
        }

        /**
         * areContentsTheSame: Phương thức này được sử dụng để kiểm tra xem nội dung của hai mục có giống nhau hay không.
         * Nếu hai mục là cùng một mục (theo areItemsTheSame), thì phương thức này sẽ kiểm tra xem các thuộc tính khác của mục có thay đổi hay không.
         */
        override fun areContentsTheSame(
            oldItem: Note,
            newItem: Note
        ): Boolean {
            return oldItem == newItem
        }
    }

    // AsyncListDiffer là một lớp tiện ích trong Android được sử dụng để quản lý danh sách dữ liệu trong RecyclerView một cách hiệu quả.
    val differ = AsyncListDiffer(
        this,
        differCallback
    ) // Khởi tạo AsyncListDiffer với adapter và callback đã định nghĩa

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        return NoteViewHolder(
            NoteLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {
        val currentNote = differ.currentList[position] // Lấy ghi chú hiện tại từ danh sách
        holder.binding.tvNoteTitle.text = currentNote.noteTitle
        holder.binding.tvNoteBody.text = currentNote.noteBody

        val rnd = Random()
        val color = Color.argb(
            255,
            rnd.nextInt(256),
            rnd.nextInt(256),
            rnd.nextInt(256)
        )
        holder.binding.ibColor.setBackgroundColor(color)

        // Thiết lập sự kiện click cho mỗi mục trong RecyclerView
        holder.itemView.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(currentNote) // Tạo hướng điều hướng với ghi chú hiện tại (đã tạo argument trong nav_graph.xml)
            it.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}