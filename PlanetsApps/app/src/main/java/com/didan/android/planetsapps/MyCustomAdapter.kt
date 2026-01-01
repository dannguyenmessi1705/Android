package com.didan.android.planetsapps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MyCustomAdapter(val context: Context, val planets: List<Planet>): BaseAdapter() {
    override fun getCount(): Int {
        // Return the number of items in the data set
        return planets.size
    }

    override fun getItem(position: Int): Any {
        // Return the data item at the specified position
        return planets[position]
    }

    override fun getItemId(position: Int): Long {
        // Return the row ID of the item at the specified position
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        // Return the view for the item at the specified position
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater // Khởi tạo LayoutInflater dùng để nạp layout XML

        val view: View // Biến lưu trữ View của item

        // Kiểm tra xem convertView có null hay không, convertView là View tái sử dụng nếu có
        if (convertView == null) {
            view = inflater.inflate(
                R.layout.item_list_layout, // Nạp layout XML cho item, lấy ra layout từ file item_list_layout.xml
                parent, // ViewGroup cha của item
                false // Không gắn trực tiếp vào ViewGroup cha
            ) // Nạp layout XML cho item
        } else {
            view = convertView // Sử dụng lại View tái sử dụng nếu có
        }

        // Lấy dữ liệu của item tại vị trí position
        val item = getItem(position) as Planet

        // Khởi tạo và gán dữ liệu vào các thành phần trong layout của item
        val titleTextView = view.findViewById<TextView>(R.id.planet_name)
        val moonCountTextView = view.findViewById<TextView>(R.id.moon_count_text)
        val moonImage = view.findViewById<ImageView>(R.id.imageView)

        titleTextView.text = item.title // Gán tên hành tinh
        moonCountTextView.text = item.moonCount // Gán số lượng mặt trăng
        moonImage.setImageResource(item.imagePlanet) // Gán hình ảnh hành tinh

        // Xử lý sự kiện click cho item (nếu cần)
        view.setOnClickListener {
            Toast.makeText(
                context,
                "You clicked on ${planets[position].title}",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Trả về View của item đã được cấu hình
        return view
    }
}