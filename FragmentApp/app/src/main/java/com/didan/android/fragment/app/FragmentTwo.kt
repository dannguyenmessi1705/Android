package com.didan.android.fragment.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Fragment: Là một phần giao diện người dùng (UI) hoặc hành vi (behavior) có thể tái sử dụng trong
 * một hoạt động (Activity).
 * Fragment có vòng đời riêng và có thể được thêm, loại bỏ hoặc thay thế trong một hoạt động.
 */
class FragmentTwo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Nạp layout cho Fragment này
        return inflater.inflate(R.layout.fragment_two, container, false)
    }
}