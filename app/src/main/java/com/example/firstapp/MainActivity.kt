package com.example.firstapp

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.firstapp.ui.theme.FirstAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val locationViewModel: LocationViewModel = viewModel()
            FirstAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
//                        RecipeApp(navController = navController)
//                        AppScreen()
//                        RecipeScreen()
//                        LocationApp(locationViewModel)
                        NavigationShoppingLocation()
                    }

                }
            }
        }
    }
}

@Composable
fun AppScreen() {
    val navController =
        rememberNavController() // Tạo một navController để điều hướng giữa các man hình
    NavHost(
        navController = navController, // Truyền navController vào NavHost để điều hướng
        startDestination = "firstScreen" // Màn hình mặc định khi chạy ứng dụng sẽ là firstScreen
    ) {
        composable(
            route = "firstScreen" // Định nghĩa route cho màn hình tên là firstScreen
        ) {
            FirstScreen(navigationToSecondScreen = { name ->
                navController.navigate("secondScreen/$name") // Điều hướng tới route secondScreen với tham số name
            })
        }
        composable(
            route = "secondScreen/{name}" // Định nghĩa route cho màn hình tên là secondScreen với tham số name
        ) {
            val name = it.arguments?.getString("name")
                ?: "no name" // Lấy tham số name từ route hoặc mặc định là "no name"
            SecondScreen(
                name,
                navigateToFirstScreen = {
                    navController.navigate("firstScreen")
                }
            )
        }
    }
}

@Composable
fun LocationDisplay(
    locationUtils: LocationUtils,
    locationViewModel: LocationViewModel,
    context: Context
) {
    val location = locationViewModel.location.value // Lấy ra state vị trí từ ViewModel

    val address = location?.let {
        locationUtils.reverseGeocodeLocation(it) // Chuyển vị trí sang địa chỉ
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permission ->
            if (permission[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                &&
                permission[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                // Quyền đã được cấp, thực hiện tiêp theo
                locationUtils.requestLocationUpdate(locationViewModel)
            } else {
                // Quyền chưa được cấp, hiển thị thông báo yêu cầu cấp quyền
                val retionalRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity, // Ép kiểu context về MainActivity khi đang thực hiện trong Ứng dụng
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                        || ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity, // Ép kiểu context về MainActivity khi đang thực hiện trong Ứng dụng
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                // retionalRequired sẽ trả về true nếu người dùng từ chối cấp quyền và chọn "Không hiển thị lần nữa"

                if (retionalRequired) {
                    // Nếu người dùng từ chối cấp quyền và chọn "Không hiển thị lần nữa"
                    Toast.makeText(
                        context,
                        "Location Permission is required for this feature to work",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    // Nếu người dùng từ chối cấp quyền nhưng chưa chọn "Không hiển thị lần nữa"
                    Toast.makeText(
                        context,
                        "Location Permission is required. Please enable it in the app settings",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (location != null) {
            Text(text = "Latitude: ${location.latitude}")
            Text(text = "Longitude: ${location.longitude}")
            Text(text = "Address: $address")
        } else {
            Text(text = "Location not available")
        }
        Button(onClick = {
            if (locationUtils.hasLocationPermission(context)) {
                // Quyền đã được cấp, thực hiện tiếp theo
                locationUtils.requestLocationUpdate(locationViewModel)
            } else {
                // Quyền chưa được cấp, yêu cầu cấp quyền
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) // Hàm yêu cầu người dùng cấp quyền truy cập vị trí
            }
        }) {
            Text(text = "Get Location")
        }
    }
}

@Composable
fun LocationApp(viewModel: LocationViewModel) {
    val context = LocalContext.current
    val localUtils = LocationUtils(context)
    LocationDisplay(locationUtils = localUtils, viewModel, context = context)
}

// Navigation Shopping App vs Location
@Composable
fun NavigationShoppingLocation() {
    val navController = rememberNavController()
    val locationViewModel: LocationViewModel = viewModel()
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)

    NavHost(
        navController = navController,
        startDestination = "shoppinglistscreen"
    ) {
        composable("shoppinglistscreen") {
            ShoppingListApp(
                locationUtils = locationUtils,
                locationViewModel = locationViewModel,
                navController = navController,
                context = context,
                address = locationViewModel.address.value.firstOrNull()?.formatted_address
                    ?: "No Address"
            )
        }
        dialog("locationscreen") { backstack ->
            locationViewModel.location.value?.let { it1 ->
                LocationSelectionScreen(
                    location = it1,
                    onLocationSelected = { locationData ->
                        locationViewModel.fetchAddress("${locationData.latitude},${locationData.longitude}")
                        navController.popBackStack() // Thực hiện popBackStack (đóng dialog)
                    }
                )
            }
        }
    }
}