package com.didan.android.databinding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

/**
 * Lớp Vehicle sử dụng cho Data Binding với khả năng thông báo thay đổi dữ liệu (Two-Way Data Binding)
 */
class TwoBindingVehicle : BaseObservable() {
    var modelYear: String = ""

    @Bindable // Annotation để đánh dấu thuộc tính này có thể được binding hai chiều
    // Để annotation hoạt động, thêm id("kotlin-kapt") vào build.gradle.kts và chạy lại project
    // Thư viện sẽ tạo mã để xử lý việc thông báo thay đổi dữ liệu
    // Nó sẽ tự động tạo ra một lớp BR với các hằng số tương ứng với tên thuộc tính được đánh dấu bằng @Bindable
    var name: String = ""
        // Ghi đè setter để thông báo khi thuộc tính 'name' thay đổi
        set(value) {
            field = value // Cập nhật giá trị thực của thuộc tính
            // Gọi phương thức notifyPropertyChanged với tham số là hằng số tương ứng trong lớp BR
            // để thông báo rằng thuộc tính 'name' đã thay đổi
            notifyPropertyChanged(BR.name)
        }
}
