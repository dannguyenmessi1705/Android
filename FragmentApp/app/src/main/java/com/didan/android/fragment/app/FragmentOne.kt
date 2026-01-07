package com.didan.android.fragment.app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Fragment: Là một phần giao diện người dùng (UI) hoặc hành vi (behavior) có thể tái sử dụng trong
 * một hoạt động (Activity).
 * Fragment có vòng đời riêng và có thể được thêm, loại bỏ hoặc thay thế trong một hoạt động.
 */
class FragmentOne : Fragment() {

    /**
     * Hàm onAttach được gọi khi Fragment được gắn vào Activity.
     * Đây là nơi bạn có thể lấy tham chiếu đến Context của Activity chứa Fragment.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Bạn có thể thực hiện các thao tác khởi tạo liên quan đến Context ở đây
        Toast.makeText(
            context,
            "Fragment One onAttach called",
            Toast.LENGTH_SHORT
        ).show()
    }

    /**
     * Hàm onCreate được gọi để khởi tạo Fragment. Đây là nơi bạn có thể thực hiện các thiết lập ban đầu
     * cho Fragment, như khởi tạo biến, thiết lập cấu hình, v.v.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(
            context,
            "Fragment One onCreate called",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(
            context,
            "Fragment One onCreateView called",
            Toast.LENGTH_SHORT
        )
        // Nạp layout cho Fragment này
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    /**
     * Hàm onActivityCreated được gọi khi Activity chứa Fragment đã được tạo xong.
     * Đây là nơi bạn có thể thực hiện các thao tác liên quan đến Activity, như truy cập các thành phần UI
     * của Activity hoặc thiết lập các liên kết dữ liệu giữa Fragment và Activity.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    /**
     * Hàm onStart được gọi khi Fragment trở nên hiển thị với người dùng.
     * Đây là nơi bạn có thể thực hiện các thao tác liên quan đến giao diện người dùng,
     * như cập nhật dữ liệu hiển thị hoặc bắt đầu các hoạt động liên quan đến UI.
     */
    override fun onStart() {
        super.onStart()

        Toast.makeText(
            context,
            "Fragment One onStart called",
            Toast.LENGTH_SHORT
        ).show()
    }

    /**
     * Hàm onResume được gọi khi Fragment trở nên tương tác với người dùng.
     * Đây là nơi bạn có thể thực hiện các thao tác liên quan đến tương tác người dùng,
     * như bắt đầu các hoạt động lắng nghe sự kiện hoặc cập nhật giao diện người dùng.
     */
    override fun onResume() {
        super.onResume()
        Toast.makeText(
            context,
            "Fragment One onResume called",
            Toast.LENGTH_SHORT
        ).show()
    }

    /**
     * Hàm onPause được gọi khi Fragment không còn tương tác với người dùng.
     * Đây là nơi bạn có thể thực hiện các thao tác liên quan đến việc tạm dừng các hoạt động
     * liên quan đến tương tác người dùng, như dừng lắng nghe sự kiện hoặc lưu trạng thái giao diện người dùng.
     */
    override fun onPause() {
        super.onPause()

        Toast.makeText(
            context,
            "Fragment One onPause called",
            Toast.LENGTH_SHORT
        ).show()
    }

    /**
     * Hàm onStop được gọi khi Fragment không còn hiển thị với người dùng.
     * Đây là nơi bạn có thể thực hiện các thao tác liên quan đến việc dừng các hoạt động
     * liên quan đến giao diện người dùng, như dừng cập nhật dữ liệu hiển thị hoặc giải phóng tài nguyên liên quan đến UI.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        Toast.makeText(
            context,
            "Fragment One onDestroyView called",
            Toast.LENGTH_SHORT
        ).show()
    }

    /**
     * Hàm onDestroy được gọi khi Fragment bị hủy.
     * Đây là nơi bạn có thể thực hiện các thao tác dọn dẹp cuối cùng,
     * như giải phóng tài nguyên hoặc hủy các kết nối không còn cần thiết.
     */
    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(
            context,
            "Fragment One onDestroy called",
            Toast.LENGTH_SHORT
        ).show()
    }

    /**
     * Hàm onDetach được gọi khi Fragment bị tách ra khỏi Activity.
     * Đây là nơi bạn có thể thực hiện các thao tác dọn dẹp liên quan đến Context của Activity.
     */
    override fun onDetach() {
        super.onDetach()
        Toast.makeText(
            context,
            "Fragment One onDetach called",
            Toast.LENGTH_SHORT
        ).show()
    }
}