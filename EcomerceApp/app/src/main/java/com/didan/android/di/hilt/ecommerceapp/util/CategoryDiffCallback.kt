package com.didan.android.di.hilt.ecommerceapp.util

import androidx.recyclerview.widget.DiffUtil
import com.didan.android.di.hilt.ecommerceapp.model.Category

/**
 * CategoryDiffCallback là một lớp dùng để so sánh sự khác biệt giữa các đối tượng Category trong danh sách.
 * Sử dụng để tối ưu hóa cập nhật RecyclerView khi dữ liệu thay đổi.
 * Đây là 1 phần cảu DiffUtil, nó giúp tối ưu cập nhật danh sách thay vì phải tải lại toàn bộ.
 */
class CategoryDiffCallback: DiffUtil.ItemCallback<Category>() {

    /**
     * Kiểm tra xem hai đối tượng Category có phải là cùng một thực thể hay không.
     * Nếu là cùng một danh mục xuất hiện ở cả danh sách cũ và mới.
     * DiffUtil sẽ không cần phải reload lại toàn bộ mục này, chỉ cập nhật nội dung nếu cần.
     */
    override fun areItemsTheSame(
        oldItem: Category,
        newItem: Category
    ): Boolean {
        return oldItem.name == newItem.name
    }

    /**
     * Kiểm tra xem nội dung của một mục Category có thay đổi hay không.
     * Nếu tên của danh mục vẫn giữ nguyên nhưng hình ảnh hoặc các thuộc tính khác thay đổi,
     * DiffUtil sẽ biết để cập nhật mục đó trong RecyclerView thay vì reload toàn bộ.
     */
    override fun areContentsTheSame(
        oldItem: Category,
        newItem: Category
    ): Boolean {
        return oldItem == newItem
    }

}