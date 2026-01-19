package com.didan.android.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumService {

    // 2 - Định nghĩa phương thức GET để lấy danh sách Album từ API
    // Nên đinh nghĩa các endpoint API cụ thể của Url
    @GET("/albums") // {host}/albums là endpoint để lấy danh sách Album
    suspend fun getAlbums(): Response<Albums> // Sử dụng suspend để hỗ trợ coroutine, trả về Response chứa danh sách Album

    // Lấy danh sách Album của một user cụ thể dựa trên userId (truyền tham số truy vấn)
    // {host}/albums?userId=1
    @GET("/albums")
    suspend fun getSpecificAlbums(@Query("userId") userId: Int): Response<Albums> // Sử dụng suspend để hỗ trợ coroutine, trả về Response chứa danh sách Album
}