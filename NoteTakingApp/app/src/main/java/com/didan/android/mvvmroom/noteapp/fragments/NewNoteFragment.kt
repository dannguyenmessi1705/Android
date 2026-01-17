package com.didan.android.mvvmroom.noteapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.didan.android.mvvmroom.noteapp.MainActivity
import com.didan.android.mvvmroom.noteapp.R
import com.didan.android.mvvmroom.noteapp.adapter.NoteAdapter
import com.didan.android.mvvmroom.noteapp.databinding.FragmentHomeBinding
import com.didan.android.mvvmroom.noteapp.databinding.FragmentNewNoteBinding
import com.didan.android.mvvmroom.noteapp.model.Note
import com.didan.android.mvvmroom.noteapp.viewmodel.NoteViewModel

class NewNoteFragment : Fragment(R.layout.fragment_new_note) {

    private var _binding: FragmentNewNoteBinding? =
        null // Biến binding để liên kết với giao diện (Có dấu _ ở đầu để tránh không thể truy cập trực tiếp)
    private val binding get() = _binding!! // Biến binding để truy cập các thành phần giao diện

    private lateinit var noteViewModel: NoteViewModel // Biến ViewModel để quản lý dữ liệu ghi chú
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Cho phép Fragment có menu tùy chỉnh
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewNoteBinding.inflate(
            inflater,
            container,
            false
        )
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as MainActivity).noteViewModel // Lấy ViewModel từ MainActivity
        mView = view // Lưu tham chiếu đến View của Fragment
    }

    private fun saveNote(view: View) {
        val noteTitle = binding.etNoteTitle.text.toString().trim()
        val noteBody = binding.etNoteBody.text.toString().trim()

        if (noteTitle.isNotEmpty()) {
            val note = Note(
                0,
                noteTitle,
                noteBody
            )
            noteViewModel.insertNote(note)

            Toast.makeText(
                mView.context,
                "Note Saved Successfully",
                Toast.LENGTH_SHORT
            ).show()

            // Quay lại HomeFragment sau khi lưu ghi chú
            view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
        } else {
            Toast.makeText(
                mView.context,
                "Please enter a title",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_new_note, menu) // Inflate menu từ tệp XML
        super.onCreateOptionsMenu(menu, inflater)

    }

    /**
     * Xử lý sự kiện khi một mục trong menu được chọn
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                saveNote(mView) // Gọi hàm lưu ghi chú khi mục "Lưu" được chọn
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null // Giải phóng binding khi Fragment bị hủy để tránh rò rỉ bộ nhớ
    }
}