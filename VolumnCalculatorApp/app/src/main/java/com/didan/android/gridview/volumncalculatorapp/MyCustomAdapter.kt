package com.didan.android.gridview.volumncalculatorapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

/**
 * Class MyCustomAdapter là một adapter tùy chỉnh kế thừa từ ArrayAdapter để hiển thị danh sách các đối tượng Shape trong một GridView hoặc ListView.
 * @param context Ngữ cảnh của ứng dụng hoặc Activity nơi adapter được sử dụng.
 * @param gridItems Danh sách các đối tượng Shape cần hiển thị.
 * Kế thừa ArrayAdapter để tận dụng các chức năng sẵn có của nó trong việc quản lý và hiển thị dữ liệu.
 * Tham số trong ArrayAdapter là context, resource ID (đặt là 0 vì chúng ta sẽ tùy chỉnh giao diện riêng) và danh sách các đối tượng Shape.
 */
class MyCustomAdapter(context: Context, private val gridItems: List<Shape>):
    ArrayAdapter<Shape>(context, 0, gridItems) {

    /**
     * ViewHolder là một lớp nội bộ dùng để giữ các tham chiếu đến các thành phần giao diện trong mỗi mục của danh sách.
     * Điều này giúp tối ưu hóa hiệu suất bằng cách tránh việc gọi findViewById nhiều lần. Giúp cải thiện hiệu suất của ListView hoặc GridView.
     */
    private class ViewHolder {
        lateinit var imageView: ImageView
        lateinit var textView: TextView
    }

    /**
     * Phương thức getView được ghi đè để tùy chỉnh cách hiển thị mỗi mục trong danh sách.
     * @param position Vị trí của mục hiện tại trong danh sách.
     * @param convertView View tái sử dụng nếu có, giúp tối ưu hóa hiệu suất.
     * @param parent ViewGroup cha chứa mục hiện tại.
     * @return Trả về View đã được tùy chỉnh để hiển thị mục tại vị trí cụ thể.
     */
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        // Tạm thời giữ convertView trong một biến itemView
        var itemView = convertView
        // Khai báo một biến holder để giữ ViewHolder
        var holder: ViewHolder

        // Kiểm tra nếu convertView là null, tức là không có View tái sử dụng nào có sẵn
        if (convertView == null) {
            // Inflate layout tùy chỉnh cho mục danh sách nếu không có View tái sử dụng
            itemView = LayoutInflater.from(context)
                .inflate(R.layout.grid_item_layout, parent, false) // Thay thế R.layout.grid_item_layout bằng layout thực tế của bạn

            // Tạo một ViewHolder mới để giữ các tham chiếu đến các thành phần giao diện
            holder = ViewHolder()
            holder.imageView = itemView.findViewById(R.id.imageView) // Thay thế R.id.imageView bằng ID thực tế của ImageView trong layout của bạn
            holder.textView = itemView.findViewById(R.id.textView) // Thay thế R.id.textView bằng ID thực tế của TextView trong layout của bạn

            // Gán ViewHolder vào tag của itemView để tái sử dụng sau này
            // Cũng giúp tránh việc tạo ViewHolder mới mỗi lần getView được gọi
            itemView.tag = holder
        } else {
            // Nếu có View tái sử dụng, lấy ViewHolder từ tag của itemView
            holder = itemView.tag as ViewHolder
        }

        // Bind dữ liệu từ đối tượng Shape vào các thành phần giao diện trong ViewHolder
        val currentItem = gridItems[position] // Lấy đối tượng Shape tại vị trí hiện tại
        holder.imageView.setImageResource(currentItem.shapeImg) // Đặt hình ảnh từ Shape
        holder.textView.text = currentItem.shapeName // Đặt tên từ Shape

        // Trả về itemView đã được tùy chỉnh
        return itemView!!
    }
}