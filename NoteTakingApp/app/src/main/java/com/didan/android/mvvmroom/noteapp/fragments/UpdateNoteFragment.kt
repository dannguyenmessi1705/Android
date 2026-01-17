package com.didan.android.mvvmroom.noteapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.didan.android.mvvmroom.noteapp.MainActivity
import com.didan.android.mvvmroom.noteapp.R
import com.didan.android.mvvmroom.noteapp.databinding.FragmentHomeBinding
import com.didan.android.mvvmroom.noteapp.databinding.FragmentUpdateNoteBinding
import com.didan.android.mvvmroom.noteapp.model.Note
import com.didan.android.mvvmroom.noteapp.viewmodel.NoteViewModel

class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {

    private var _binding: FragmentUpdateNoteBinding? =
        null // Biến binding để liên kết với giao diện (Có dấu _ ở đầu để tránh không thể truy cập trực tiếp)
    private val binding get() = _binding!! // Biến binding để truy cập các thành phần giao diện

    private lateinit var noteViewModel: NoteViewModel // Biến ViewModel để quản lý dữ liệu ghi chú
    private lateinit var currentNote: Note

    // Lấy đối số được truyền đến Fragment thông qua Safe Args (Argument đã được định nghĩa trong navigation graph)
    private val args: UpdateNoteFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Cho phép Fragment có menu tùy chỉnh
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateNoteBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as MainActivity).noteViewModel // Lấy ViewModel từ MainActivity
        currentNote = args.note!! // Lấy ghi chú hiện tại từ đối số
        binding.etNoteTitleUpdate.setText(currentNote.noteTitle)
        binding.etNoteBodyUpdate.setText(currentNote.noteBody)

        // Thiết lập sự kiện khi nhấn nút cập nhật
        binding.fabDone.setOnClickListener {
            val updatedTitle = binding.etNoteTitleUpdate.text.toString().trim()
            val updatedBody = binding.etNoteBodyUpdate.text.toString().trim()

            if (updatedTitle.isNotEmpty()) {
                val updatedNote = Note(
                    currentNote.id,
                    updatedTitle,
                    updatedBody
                )
                noteViewModel.updateNote(updatedNote) // Cập nhật ghi chú trong ViewModel

                // Di chuyển trở lại HomeFragment sau khi cập nhật
                it.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
            } else {
                Toast.makeText(
                    context,
                    "Note title cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun deleteNote() {
        // Hiển thị hộp thoại xác nhận xóa ghi chú
        AlertDialog.Builder(activity!!).apply {
            setTitle("Delete Note") // Tiêu đề của hộp thoại
            setMessage("Are you sure you want to delete this note?") // Thông điệp trong hộp thoại
            // Khi người dùng xác nhận xóa
            setPositiveButton("Delete") { _, _ ->
                noteViewModel.deleteNote(currentNote)
                // Quay lại HomeFragment sau khi xóa ghi chú
                view?.findNavController()?.navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }
            // Khi người dùng hủy bỏ xóa
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear() // Xóa menu hiện tại
        inflater.inflate(R.menu.menu_update_note, menu) // Inflate menu từ tệp XML
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                deleteNote() // Gọi hàm xóa ghi chú khi mục "Xóa" được chọn
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}