package com.example.firstapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

class LocationUtils(val context: Context) {
    private val _fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context) // Lấy ra một đối tượng FusedLocationProviderClient để sử dụng trong việc lấy vị trí

    @Suppress("MissingPermission") // Bỏ qua lỗi MissingPermission vì đã kiểm tra quyền trước khi gọi hàm này
    fun requestLocationUpdate(locationViewModel: LocationViewModel) {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let { location ->
                    val locationUpdate = LocationDataInfo(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                    locationViewModel.updateLocation(locationUpdate)
                }
            }
        } // Tạo ra một LocationCallback để lắng nghe sự thay đổi vị trí và cập nhật vị trí mới vào ViewModel

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            1000
        )
            .build() // Tạo ra một LocationRequest với độ chính xác cao và thời gian cập nhật là 1000ms (1 giây)

        _fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        ) // Yêu cầu cập nhật vị trí từ FusedLocationProviderClient với LocationRequest và LocationCallback đã tạo
    }


    fun hasLocationPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    fun reverseGeocodeLocation(location: LocationDataInfo): String {
        val geocoder: Geocoder = Geocoder(
            context, // Sử dụng context để tạo ra một đối tượng Geocoder
            Locale.getDefault() // Sử dụng ngôn ngữ mặc định của thiết bị
        ) // Tạo ra một đối tượng Geocoder để chuyển đổi vị trí sang địa chỉ và ngược lại
        val coordinate = LatLng(
            location.latitude,
            location.longitude
        ) // Tạo ra một đối tượng LatLng từ vị trí đã có
        val address: MutableList<Address>? =
            // MutableList<Address> để lưu trữ danh sách địa chỉ trả về
            geocoder.getFromLocation( // Sử dụng phương thức getFromLocation để chuyển đổi vị trí sang địa chỉ
                coordinate.latitude, // Lấy ra vĩ độ từ vị trí
                coordinate.longitude, // Lấy ra kinh độ từ vị trí
                1 // Số lượng địa chỉ trả về, ở đây chỉ cần 1 địa chỉ
            ) // Sử dụng Geocoder để chuyển đổi vị trí sang địa chỉ

        return if (address?.isNotEmpty() == true) {
            address[0].getAddressLine(0)
        } else {
            "Address not found"
        }
    }
}

/*
ContextCompat.checkSelfPermission() trả về 0 nếu quyền được cấp và trả về -1 nếu quyền không được cấp
*/