package com.didan.android.recyclerview.groceryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

/**
 * MyCustomAdapter là cầu nối giữa dữ liệu và RecyclerView để hiển thị danh sách các mục.
 * Nó kế thừa từ RecyclerView.Adapter và sử dụng MyViewHolder để quản lý các mục giao diện.
 * @param itemList Danh sách các mục dữ liệu để hiển thị trong RecyclerView.
 * inner class trong Kotlin cho phép truy cập trực tiếp vào các thành viên của lớp bên ngoài (giống như lớp tĩnh trong Java).
 */
class MyCustomAdapter(val itemList: ArrayList<ItemModel>) :
    RecyclerView.Adapter<MyCustomAdapter.MyViewHolder>() {

    /**
     * MyViewHolder đại diện cho một mục trong RecyclerView.
     * Nó kế thừa từ RecyclerView.ViewHolder và giữ tham chiếu đến các thành phần giao diện của mục.
     * @param itemView Giao diện của mục được truyền vào ViewHolder, đại diện cho mỗi mục trong danh sách.
     */
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView
        var itemDesc: TextView
        var itemImg: ImageView

        init {
            itemTitle = itemView.findViewById<TextView>(R.id.title_txt)
            itemDesc = itemView.findViewById<TextView>(R.id.desc_txt)
            itemImg = itemView.findViewById<ImageView>(R.id.imageView)

            // itemView đại diện cho một mục trong RecyclerView
            // Xư lý sự kiện click trên một mục
            itemView.setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    "You choose ${itemList[absoluteAdapterPosition].name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /**
     * Được gọi khi RecyclerView cần một ViewHolder mới để hiển thị một mục.
     * @param parent ViewGroup cha mà ViewHolder sẽ được gắn vào sau khi liên kết với dữ liệu.
     * @param viewType Loại của ViewHolder để tạo (nếu có nhiều loại mục) để phân biệt các kiểu giao diện khác nhau.
     * @return Trả về một thể hiện mới của MyViewHolder.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        // Tạo một View từ layout XML cho mục của RecyclerView
        val v =
            LayoutInflater.from(parent.context) // Lấy LayoutInflater từ context của ViewGroup cha
                .inflate(
                    R.layout.item_layout,
                    parent,
                    false
                ) // Nạp layout item_layout.xml và không gắn nó ngay lập tức vào ViewGroup cha

        return MyViewHolder(v) // Trả về một thể hiện mới của MyViewHolder
    }

    /**
     * Được gọi để liên kết dữ liệu với ViewHolder tại vị trí cụ thể trong danh sách.
     * @param holder ViewHolder cần được liên kết với dữ liệu.
     * @param position Vị trí của mục trong danh sách dữ liệu.
     */
    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.itemTitle.text = itemList[position].name
        holder.itemDesc.text = itemList[position].desc
        holder.itemImg.setImageResource(itemList[position].img)
    }

    /**
     * Trả về số lượng mục trong danh sách dữ liệu.
     */
    override fun getItemCount(): Int {
        return itemList.size
    }
}