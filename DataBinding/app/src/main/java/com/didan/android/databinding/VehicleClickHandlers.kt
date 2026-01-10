package com.didan.android.databinding

import android.content.Context
import android.view.View
import android.widget.Toast

class VehicleClickHandlers(var context: Context) {

    /**
     * Hàm xử lý sự kiện click cho nút button sử dụng Data Binding (thêm thuộc tính android:onClick(@{data::displayToastMessage}" trong tag Button của layout)
     * @param view View được click (ở đây là Button trong layout)
     */
    fun displayToastMessage(view: View): Unit {
        Toast.makeText(
            context,
            "You Clicked the Button!",
            Toast.LENGTH_SHORT
        ).show()
    }
}