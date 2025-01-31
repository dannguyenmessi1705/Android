package com.example.firstapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun LocationSelectionScreen(
    location: LocationDataInfo,
    onLocationSelected: (LocationDataInfo) -> Unit
) {
    val userLocation = remember {
        mutableStateOf(LatLng(location.latitude, location.longitude))
    } // Lưu trữ vị trí người dùng dưới dạng LatLng

    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            userLocation.value,
            10f
        ) // Khởi tạo CameraPosition từ vị trí người dùng và zoom level 10
    } // rememberCameraPositionState để lưu trữ trạng thái của CameraPosition khi người dùng thay đổi vị trí trên bản đồ

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp), // Padding top 16dp
            cameraPositionState = cameraPositionState, // Sử dụng cameraPositionState đã khởi tạo
            onMapClick = { latLng ->
                userLocation.value = latLng
            } // Khi người dùng click vào bản đồ, cập nhật vị trí người dùng
        ) {
            Marker(
                state = MarkerState(
                    position = userLocation.value
                )
            ) // Hiển thị Marker tại vị trí người dùng
        } // Hiển thị bản đồ

        var newLocation: LocationDataInfo

        Button(onClick = {
            newLocation = LocationDataInfo(
                latitude = userLocation.value.latitude,
                longitude = userLocation.value.longitude
            )
            onLocationSelected(newLocation)
        }) {
            Text(text = "Set Location") // Button để chọn vị trí
        }
    }
}