package com.didan.android.retrofit

import com.google.gson.annotations.SerializedName

// 1 - Data class để ánh xạ dữ liệu Album từ API
data class AlbumItem(
    @SerializedName("userId") // Annotation dùng để ánh xạ tên trường JSON với thuộc tính Kotlin (nếu tên khác nhau)
    val userId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
)