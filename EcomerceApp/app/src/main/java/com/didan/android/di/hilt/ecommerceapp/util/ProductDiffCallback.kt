package com.didan.android.di.hilt.ecommerceapp.util

import androidx.recyclerview.widget.DiffUtil
import com.didan.android.di.hilt.ecommerceapp.model.Product

/**
 * Lớp này dùng để so sánh sự khác biệt giữa hai danh sách sản phẩm (Product) nhằm tối ưu hóa việc cập nhật giao diện người dùng.
 * Thông qua việc xác định xem các mục có giống nhau hay không và nội dung của chúng có thay đổi hay không,
 * giúp giảm thiểu việc tải lại toàn bộ danh sách khi có sự thay đổi nhỏ.
 */
class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {

    /**
     * Kiểm tra xem hai đối tượng Product có phải là cùng một thực thể hay không.
     * Nếu là cùng một sản phẩm xuất hiện ở cả danh sách cũ và mới.
     * DiffUtil sẽ không cần phải reload lại toàn bộ mục này, chỉ cập nhật nội dung nếu cần.
     */
    override fun areItemsTheSame(
        oldItem: Product,
        newItem: Product
    ): Boolean {
        return oldItem.title == newItem.title
    }

    /**
     * Kiểm tra xem nội dung của một mục Product có thay đổi hay không.
     * Nếu tên của sản phẩm vẫn giữ nguyên nhưng hình ảnh hoặc các thuộc tính khác thay đổi,
     * DiffUtil sẽ biết để cập nhật mục đó trong RecyclerView thay vì reload toàn bộ.
     */
    override fun areContentsTheSame(
        oldItem: Product,
        newItem: Product
    ): Boolean {
        return oldItem == newItem
    }
}