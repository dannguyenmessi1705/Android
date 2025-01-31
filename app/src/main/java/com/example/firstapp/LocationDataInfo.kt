package com.example.firstapp

data class LocationDataInfo(
    val latitude: Double,
    val longitude: Double
)

data class GeocodingResponse(
    val results: List<GeocodingResult>,
    val status: String
) // Dữ liệu trả về từ Geocoding API của Google

data class GeocodingResult(
    val formatted_address: String
) // Dữ liệu trả về từ Geocoding API của Google