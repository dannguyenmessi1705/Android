package com.didan.android.di.hilt.ecommerceapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.didan.android.di.hilt.ecommerceapp.R
import com.didan.android.di.hilt.ecommerceapp.model.Category
import com.didan.android.di.hilt.ecommerceapp.model.Product
import com.didan.android.di.hilt.ecommerceapp.room.CartDao
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// Trung tâm hoạt động quản lý dữ liệu của ứng dụng (Firestore, Room, SharedPreferences, v.v.)
// @Inject constructor cho phép Hilt tự động cung cấp các dependencies cần thiết, ở đây là FirebaseFirestore (đã được định nghĩa trong FirebaseModule).
class Repository @Inject constructor(
    private val firestore: FirebaseFirestore, // Firebase Firestore để truy cập dữ liệu từ đám mây.
    private val cartDao: CartDao // Thêm CartDao để quản lý dữ liệu giỏ hàng trong Room database.
) {

    // Firebase: Lấy danh mục sản phẩm từ Firestore.
    fun fetchCategories(): MutableLiveData<List<Category>> {
        var categoriesList =
            MutableLiveData<List<Category>>() // LiveData để quan sát danh sách danh mục.

        // Khi lấy dữ liệu categories từ Firestore, ứng dụng sẽ kiểm tra liệu tên danh mục có khớp với các khóa trong bản đồ catImages không, sau đó sẽ lấy hình ảnh tương ứng.
        val catImages = mapOf(
            "Electronics" to R.drawable.electronics,
            "Jewelry" to R.drawable.jewelery,
            "mensclothing" to R.drawable.mensclothing,
            "womenclothing" to R.drawable.womenclothing
        )

        // Lấy dữ liệu từ Firestore.
        firestore.collection("categories") // Truy cập collection "categories".
            .get() // Lấy tất cả tài liệu trong collection một cách bất đồng bộ.
            .addOnSuccessListener { docs -> // Xử lý khi lấy dữ liệu thành công.
                val category = docs.map { doc ->
                    // Lấy hình ảnh theo id danh mục, nếu không tìm thấy thì sử dụng hình nền mặc định.
                    val imageRes = catImages[doc.id] ?: R.drawable.ic_launcher_background
                    Category(name = doc.id, catImg = imageRes) // trả về đối tượng Category.
                }

                categoriesList.postValue(category) // Cập nhật LiveData với danh sách danh mục, nó sẽ thông báo cho các observer.
                Log.v("DANY1705", "Categories fetched: $category") // Ghi log để kiểm tra dữ liệu.
            }

        return categoriesList
    }

    // Firebase: Lấy sản phẩm từ Firestore
    fun fetchProducts(categoryName: String): MutableLiveData<List<Product>> {
        var productList =
            MutableLiveData<List<Product>>() // LiveData để quan sát danh sách sản phẩm.

        // Lấy dữ liệu từ Firestore.
        firestore.collection("categories") // Truy cập collection "categories".)
            .document(categoryName) // Truy cập document tương ứng với tên danh mục.
            .collection("products") // Truy cập collection "products" bên trong document danh mục.
            .get() // Lấy tất cả tài liệu trong collection một cách bất đồng bộ.
            .addOnSuccessListener { docs -> // Xử lý khi lấy dữ liệu thành công
                val products = docs.map { doc ->
                    // Chuyển đổi tài liệu Firestore thành đối tượng Product.
                    Product(
                        id = doc.id,
                        title = doc.getString("title") ?: "",
                        price = doc.getDouble("price") ?: 0.0,
                        imageUrl = doc.getString("imageUrl") ?: ""
                    )
                }
                productList.postValue(products) // Cập nhật LiveData với danh sách sản phẩm, nó sẽ thông báo cho các observer.
                Log.v(
                    "DANY1705",
                    "Products fetched for $categoryName: $products"
                ) // Ghi log để kiểm tra dữ liệu.
            }
            .addOnFailureListener { ex ->
                Log.v(
                    "DANY1705",
                    "Error fetching products for $categoryName: ${ex.message}"
                ) // Ghi log nếu có lỗi xảy ra.
            }
        return productList
    }

    // Room: Thêm sản phẩm vào giỏ hàng
    suspend fun addProductToCart(product: Product) {
        cartDao.addToCart(product) // Sử dụng CartDao để thêm sản phẩm vào giỏ hàng trong Room database.
    }

    // Room: Lấy tất cả sản phẩm trong giỏ hàng
    suspend fun getAllCartItems(): List<Product> {
        return cartDao.getCartItems() // Sử dụng CartDao để lấy tất cả sản phẩm trong giỏ hàng từ Room database.
    }

    // Room: Xóa sản phẩm khỏi giỏ hàng
    suspend fun removeProductFromCart(productId: String) {
        cartDao.removeFromCart(productId) // Sử dụng CartDao để xóa sản phẩm
    }

    // Room: Xóa tất cả sản phẩm khỏi giỏ hàng
    suspend fun clearCart() {
        cartDao.clearCart() // Sử dụng CartDao để xóa tất cả sản phẩm khỏi giỏ hàng trong Room database.
    }

    // Upload Product lên Firestore
    fun savePurchaseInFirestore(product: Product) {
        firestore.collection("purchases") // Truy cập collection "purchases".)
            .add(product) // Thêm sản phẩm vào collection.
            .addOnSuccessListener { documentReference ->
                Log.v(
                    "DANY1705",
                    "Purchase saved with ID: ${documentReference.id}"
                ) // Ghi log khi lưu thành công.
                // Xóa sản phẩm khỏi giỏ hàng sau khi lưu thành công, sử dụng Coroutine để thực hiện việc xóa này trên luồng IO.
                CoroutineScope(Dispatchers.IO).launch {
                    clearCart()
                }
            }
            .addOnFailureListener { e ->
                Log.v(
                    "DANY1705",
                    "Error saving purchase: ${e.message}"
                ) // Ghi log khi có lỗi xảy ra.
            }
    }
}