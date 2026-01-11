package com.didan.android.viewmodel.livedata

import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() {
    private var counter = 0

    fun getCounter(): Int {
        return counter
    }

    fun incrementCounter() {
        counter++
    }
}