package com.didan.android.mvvmroom.noteapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.didan.android.mvvmroom.noteapp.database.NoteDatabase
import com.didan.android.mvvmroom.noteapp.databinding.ActivityMainBinding
import com.didan.android.mvvmroom.noteapp.repository.NoteRepository
import com.didan.android.mvvmroom.noteapp.viewmodel.NoteViewModel
import com.didan.android.mvvmroom.noteapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpViewModel()
    }

    private fun setUpViewModel() {
        // Tạo instance của NoteRepository và NoteViewModel thông qua ViewModelFactory
        val noteRepository = NoteRepository(NoteDatabase(this))
        // Tạo ViewModelFactory để truyền NoteRepository vào NoteViewModel
        val viewModelFactory = ViewModelFactory(application, noteRepository)
        // Sử dụng ViewModelProvider để lấy NoteViewModel
        noteViewModel = ViewModelProvider(this, viewModelFactory)[NoteViewModel::class.java]
    }
}