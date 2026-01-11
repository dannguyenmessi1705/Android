package com.didan.android.viewmodel.livedata

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.didan.android.viewmodel.livedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var counterViewModel: CounterViewModel
    lateinit var counterViewModelLiveData: CounterViewModelLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Liên kết dữ liệu với layout
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        // Thiết lập lifecycleOwner cho binding để Data Binding có thể quan sát LiveData
        binding.lifecycleOwner = this
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Khởi tạo ViewModel
        // Sử dụng ViewModelProvider để lấy instance của CounterViewModel
        counterViewModel = ViewModelProvider(this)
            .get(CounterViewModel::class.java)

        // Cập nhật TextView với giá trị ban đầu của counter
        binding.modelViewText.text = counterViewModel.getCounter().toString()

        // Thiết lập sự kiện click cho nút tăng counter
        binding.viewModelBtn.setOnClickListener {
            // Tăng giá trị counter trong ViewModel
            counterViewModel.incrementCounter()
            // Cập nhật TextView với giá trị mới của counter
            binding.modelViewText.text = counterViewModel.getCounter().toString()
        }

        // Khởi tạo ViewModel LiveData
        counterViewModelLiveData = ViewModelProvider(this)
            .get(CounterViewModelLiveData::class.java)

        // Gán ViewModel LiveData cho binding
        binding.liveData = counterViewModelLiveData

    }
}