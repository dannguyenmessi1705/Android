package com.example.firstapp

import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {
    @GET("maps/api/geocode/json")
    suspend fun getAddressFromCoordinates(
        @Query("latlng") latlng: String, // Tham số truyền vào là latlng (?latlng=...)
        @Query("key") apiKey: String // Tham số truyền vào là key (?key=...)
    ): GeocodingResponse // Phương thức GET để lấy dữ liệu từ Geocoding API của Google
}