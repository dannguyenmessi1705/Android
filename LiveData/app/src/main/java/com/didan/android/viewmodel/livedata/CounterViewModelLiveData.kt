package com.didan.android.viewmodel.livedata

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CounterViewModelLiveData : ViewModel() {

    // Sử dụng MutableLiveData để giữ giá trị counter. trong layout cần bind đến thuộc tính này (Observable) để tự động cập nhật giao diện khi giá trị thay đổi
    var counter = MutableLiveData<Int>()

    // Khởi tạo giá trị ban đầu cho counter
    init {
        counter.value = 0
    }

    // Hàm tăng giá trị counter
    fun incrementCounter(view: View) {
        counter.value = (counter.value ?: 0) + 1
    }
}