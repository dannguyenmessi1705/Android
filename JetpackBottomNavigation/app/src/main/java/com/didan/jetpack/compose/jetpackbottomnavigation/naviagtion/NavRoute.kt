package com.didan.jetpack.compose.jetpackbottomnavigation.naviagtion

/**
 * Đây là một sealed class đại diện cho các route trong ứng dụng của bạn. Bạn có thể định nghĩa các route cụ thể như Home, Profile, Settings,... bằng cách tạo các object kế thừa từ NavRoute. Mỗi route sẽ có một đường dẫn (path) riêng để điều hướng đến màn hình tương ứng. Ví dụ, bạn có thể định nghĩa một object Home trong NavRoute như sau:
 * Sealed Class: Là một loại class đặc biệt dùng để định nghĩa hệ thống phân cấp lớp bị hạn chế (closed hierarchy), nơi mà giá trị có thể là một trong những loại đã được xác định trước. Điều này giúp đảm bảo rằng tất cả các trường hợp có thể xảy ra đã được xử lý, thường được sử dụng trong khi làm việc với các kiểu dữ liệu phức tạp hoặc khi muốn giới hạn số lượng các lớp con có thể tồn tại.
 */
sealed class NavRoute(val path: String) {

    // Định nghĩa các route cụ thể như Home, Profile, Settings,... bằng cách tạo các object kế thừa từ NavRoute. Mỗi route sẽ có một đường dẫn (path) riêng để điều hướng đến màn hình tương ứng. Ví dụ, bạn có thể định nghĩa một object Home trong NavRoute như sau:
    // object: Là một từ khóa trong Kotlin được sử dụng để định nghĩa một đối tượng duy nhất (singleton) hoặc một lớp ẩn danh. Khi bạn sử dụng object, bạn đang tạo ra một instance duy nhất của lớp đó, và bạn có thể truy cập nó trực tiếp thông qua tên của object mà không cần phải khởi tạo thêm bất kỳ instance nào khác. Trong trường hợp này, object Home là một instance duy nhất đại diện cho route Home trong ứng dụng của bạn.
    object Home : NavRoute("home")

    object Profile : NavRoute("profile") {
        val id = "id"
        val showDetails = "showDetails"
    }

    object Settings : NavRoute("settings")
}