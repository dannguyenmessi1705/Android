package com.didan.jetpack.compose.jetpackecommerceapp.repository

import android.util.Log
import com.didan.jetpack.compose.jetpackecommerceapp.model.Category
import com.didan.jetpack.compose.jetpackecommerceapp.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    // Flow: Là một luồng dữ liệu bất đồng bộ. Trong Kotlin, nó chỉ được bắt đầu khi được thu thập (luồng live update)
    // callbackFlow: Là một builder cho Flow, dùng để chuyển đổi hàm callback API sang Flow, với các hàm quan trọng mà nó cung cấp:
    // - trySend: Gửi dữ liệu đến luồng (non-suspend)
    // - send: Gửi dữ liệu đến luồng (suspend)
    // - close: Đóng luồng
    // - awaitClose: Đợi đến khi luồng bị đóng (Bắt buộc)
    fun getCategoriesFlow(): Flow<List<Category>> = callbackFlow {
        val listenerRegistration = firestore.collection("categories")
            .addSnapshotListener { snapshots, error ->
                // Nếu có lỗi xảy ra, gửi lỗi đến luồng và dừng
                if (error != null) {
                    println("Error fetching categories: ${error.message}")
                    return@addSnapshotListener
                }

                // Nếu không có lỗi, chuyển đổi dữ liệu và gửi đến luồng
                if (snapshots != null) {
                    val categories = snapshots.toObjects(Category::class.java)
                    trySend(categories)
                }
            }

        // Đóng luồng khi coroutine bị hủy
        awaitClose {
            listenerRegistration.remove()
        }
    }

    suspend fun getProductsByCategory(categoryId: String): List<Product> {
        return try {
            val result = firestore.collection("products")
                .whereEqualTo("categoryId", categoryId)
                .get()
                .await()

            result.toObjects(Product::class.java).also {
                Log.v("TAGY", "Mapped products: $it")
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getProductById(productId: String): Product? {
        return try {
            val result = firestore.collection("products")
                .document(productId)
                .get()
                .await()
            result.toObject(Product::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getAllProductsInFirestore(): List<Product> {
        return try {
            val allProducts = firestore.collection("products")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Product::class.java) }
            allProducts
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun searchProducts(query: String): List<Product> {
        return try {
            val allProducts = firestore.collection("products")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Product::class.java) }

            allProducts.filter {
                it.name.lowercase().contains(query)
            }
        } catch (e: Exception) {
            Log.e("TAGY", "Error searching products: ${e.message}")
            emptyList()
        }
    }
}