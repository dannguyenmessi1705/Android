package com.didan.android.cardview.sportsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

/**
 * SportAdapter là cầu nối giữa dữ liệu và RecyclerView để hiển thị danh sách các mục.
 * Nó kế thừa từ RecyclerView.Adapter và sử dụng MyViewHolder để quản lý các mục giao diện.
 * @param sportList Danh sách các mục dữ liệu để hiển thị trong RecyclerView.
 * inner class trong Kotlin cho phép truy cập trực tiếp vào các thành viên của lớp bên ngoài (giống như lớp tĩnh trong Java).
 */
class SportAdapter(val sportList: ArrayList<SportModel>) :
    RecyclerView.Adapter<SportAdapter.MyViewHolder>() {

    /**
     * MyViewHolder đại diện cho một mục trong RecyclerView.
     * Nó kế thừa từ RecyclerView.ViewHolder và giữ tham chiếu đến các thành phần giao diện của mục.
     * @param itemView Giao diện của mục được truyền vào ViewHolder, đại diện cho mỗi mục trong danh sách.
     */
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sportImg: ImageView
        var sportName: TextView

        init {
            sportName = itemView.findViewById<TextView>(R.id.textView)
            sportImg = itemView.findViewById<ImageView>(R.id.imageViewCard)

            // Thiết lập sự kiện click cho mỗi mục trong RecyclerView
            itemView.setOnClickListener{
                Toast.makeText(
                    itemView.context,
                    "You choose ${sportList[absoluteAdapterPosition].txt}",
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
        // Tạo một View từ layout XML cho mục danh sách
        val v = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.card_item_layout,
                parent,
                false
            )

        // Trả về một thể hiện mới của MyViewHolder với View đã tạo
        return MyViewHolder(v)
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
        holder.sportImg.setImageResource(sportList[position].img)
        holder.sportName.setText(sportList[position].txt)
    }

    /**
     * Trả về tổng số mục trong danh sách dữ liệu.
     * @return Số lượng mục trong sportList.
     */
    override fun getItemCount(): Int {
        return sportList.size
    }
}