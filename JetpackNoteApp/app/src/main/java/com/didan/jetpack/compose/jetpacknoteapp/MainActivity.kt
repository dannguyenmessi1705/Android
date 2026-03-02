package com.didan.jetpack.compose.jetpacknoteapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModelProvider
import com.didan.jetpack.compose.jetpacknoteapp.repository.NoteRepository
import com.didan.jetpack.compose.jetpacknoteapp.roomdb.Note
import com.didan.jetpack.compose.jetpacknoteapp.roomdb.NotesDB
import com.didan.jetpack.compose.jetpacknoteapp.screens.DisplayDialog
import com.didan.jetpack.compose.jetpacknoteapp.screens.DisplayNotesList
import com.didan.jetpack.compose.jetpacknoteapp.ui.theme.JetpackNoteAppTheme
import com.didan.jetpack.compose.jetpacknoteapp.viewmodel.NoteViewModel
import com.didan.jetpack.compose.jetpacknoteapp.viewmodel.NoteViewModelFactory

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Room DB
        val database = NotesDB.getInstance(applicationContext)

        // Repository
        val repository = NoteRepository(database.notesDAO)

        // ViewModel Factory
        val viewModelFactory = NoteViewModelFactory(repository)

        // ViewModel
        val noteViewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[NoteViewModel::class.java]

        // Tạo một ghi chú mới và thêm nó vào cơ sở dữ liệu thông qua NoteViewModel. Màu sắc của ghi chú được định nghĩa bằng một chuỗi hex và được chuyển đổi sang Int bằng phương thức toColorInt() để lưu trữ trong cơ sở dữ liệu.
//        val note1 = Note(
//            0,
//            "Ghi chú 1",
//            "Đây là nội dung của ghi chú 1",
//            "#f59597".toColorInt() // Màu hồng nhạt được chuyển đổi từ chuỗi hex sang Int bằng phương thức toColorInt()
//        )
//
//        noteViewModel.insert(note1)

        setContent {
            JetpackNoteAppTheme {
                Scaffold(floatingActionButton = { MyFAB(viewModel = noteViewModel) }) {
                    // Hiển thị tất cả ghi chú bằng cách sử dụng DisplayNotesList và truyền dữ liệu từ NoteViewModel
                    val notes by noteViewModel.allNotes.observeAsState(emptyList()) // Quan sát LiveData từ NoteViewModel để lấy danh sách ghi chú, sử dụng observeAsState để chuyển đổi LiveData thành State trong Compose để có thể quan sát. Nếu không có dữ liệu, trả về một danh sách rỗng.

                    DisplayNotesList(notes = notes)
                }
            }
        }

    }
}

@Composable
fun MyFAB(viewModel: NoteViewModel) {
    // Kiểm soát việc hiển thị dialog thông qua một biến trạng thái showDialog, được khởi tạo là false. Khi người dùng nhấn vào FloatingActionButton, biến này sẽ được đặt thành true để hiển thị dialog.
    var showDialog by remember {
        mutableStateOf(false)
    }

    DisplayDialog(
        viewModel = viewModel,
        showDialog = showDialog
    ) {
        showDialog =
            false // Khi dialog được đóng, biến showDialog sẽ được đặt lại thành false để ẩn dialog. (func: onDismiss)
    }

    FloatingActionButton(
        onClick = {
            showDialog =
                true // Khi người dùng nhấn vào FloatingActionButton, biến showDialog sẽ được đặt thành true để hiển thị dialog.
        },
        containerColor = Color.Blue,
        contentColor = Color.White
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Note"
        )
    }
}