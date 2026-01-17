package com.didan.android.mvvmroom.noteapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.didan.android.mvvmroom.noteapp.MainActivity
import com.didan.android.mvvmroom.noteapp.R
import com.didan.android.mvvmroom.noteapp.adapter.NoteAdapter
import com.didan.android.mvvmroom.noteapp.databinding.FragmentHomeBinding
import com.didan.android.mvvmroom.noteapp.model.Note
import com.didan.android.mvvmroom.noteapp.viewmodel.NoteViewModel

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? =
        null // Biến binding để liên kết với giao diện (Có dấu _ ở đầu để tránh không thể truy cập trực tiếp)
    private val binding get() = _binding!! // Biến binding để truy cập các thành phần giao diện

    private lateinit var noteViewModel: NoteViewModel // Biến ViewModel để quản lý dữ liệu ghi chú
    private lateinit var noteAdapter: NoteAdapter // Biến adapter để hiển thị danh sách ghi chú

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Cho phép Fragment có menu tùy chỉnh
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as MainActivity).noteViewModel
        setupRecyclerView()
        // Khi bấm vào Floating Action Button thì chuyển đến NewNoteFragment
        binding.fabAddNote.setOnClickListener {
            // Chuyển hướng đến NewNoteFragment
            it.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter() // Khởi tạo NoteAdapter
        // Thiết lập RecyclerView với adapter
        binding.recyclerView.apply {
            // Sử dụng StaggeredGridLayoutManager để hiển thị các mục theo dạng lưới với 2 cột
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true) // Tối ưu hóa hiệu suất khi kích thước RecyclerView không thay đổi
            adapter = noteAdapter // Gán adapter cho RecyclerView
        }

        // Quan sát dữ liệu ghi chú từ ViewModel và cập nhật giao diện khi có thay đổi
        activity?.let { // Kiểm tra activity không null
            // Quan sát LiveData từ ViewModel
            noteViewModel.getAllNotes().observe(
                viewLifecycleOwner, // Sử dụng viewLifecycleOwner để đảm bảo quan sát trong vòng đời của Fragment
                { note ->
                    noteAdapter.differ.submitList(note) // Cập nhật danh sách ghi chú trong adapter
                    updateUI(note) // Cập nhật giao diện dựa trên danh sách ghi chú
                }
            )
        }
    }

    fun updateUI(note: List<Note>?) {
        if (note != null) {
            binding.cardView.visibility = View.GONE // Ẩn cardView khi có ghi chú
            binding.recyclerView.visibility = View.VISIBLE // Hiển thị RecyclerView khi có ghi chú
        } else {
            binding.cardView.visibility = View.VISIBLE // Hiển thị cardView khi không có ghi chú
            binding.recyclerView.visibility = View.GONE // Ẩn RecyclerView khi không có ghi chú
        }
    }

    // Tạo menu tùy chỉnh cho Fragment
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear() // Xóa menu hiện tại
        inflater.inflate(R.menu.home_menu, menu) // Nạp menu từ tài nguyên
        val search = menu.findItem(R.id.menu_search).actionView as SearchView // Lấy SearchView từ menu
        search.isSubmitButtonEnabled = false // Vô hiệu hóa nút gửi
        search.setOnQueryTextListener(this) // Đặt listener cho sự kiện thay đổi văn bản tìm kiếm
    }

    /**
     * onQueryTextSubmit: Phương thức này được gọi khi người dùng nhấn nút gửi (submit) trên SearchView.
     * Trong phương thức này, bạn có thể xử lý truy vấn tìm kiếm mà người dùng đã nhập.
     */
    override fun onQueryTextSubmit(query: String?): Boolean {
//        searchNotes(query)
        return false // Trả về false để cho phép SearchView xử lý sự kiện tiếp theo
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        // Gọi hàm tìm kiếm khi văn bản thay đổi
        if (newText != null) {
            searchNotes(newText)
        }
        return true // Trả về true để cho phép SearchView xử lý sự kiện tiếp theo
    }

    private fun searchNotes(query: String?) {
        val searchQuery = "%$query" // Thêm ký tự % để tìm kiếm tương tự LIKE trong SQL
        // Quan sát kết quả tìm kiếm từ ViewModel và cập nhật giao diện
        noteViewModel.searchNote(searchQuery).observe(
            this,
            { list ->
                noteAdapter.differ.submitList(list) // Cập nhật danh sách ghi chú trong adapter
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null // Giải phóng binding khi Fragment bị hủy để tránh rò rỉ bộ nhớ
    }
}