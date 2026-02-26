package com.didan.jetpack.compose.jetpackbottomnavigation.naviagtion

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Class này sẽ chứa các thành phần liên quan đến Bottom Navigation Bar, như các item, icon, label, và logic điều hướng.
 */
@Composable
fun BottomNavigationBar(navController: NavController) {
    // 1. Items của Bottom Navigation Bar: Bạn có thể định nghĩa một danh sách các item mà bạn muốn hiển thị trong Bottom Navigation Bar. Mỗi item sẽ có một icon, một label, và một route để điều hướng đến màn hình tương ứng. Ví dụ, bạn có thể định nghĩa một danh sách các item như Home, Profile, Settings,... với các icon và label tương ứng.
    val navItems = listOf(
        NavItem.Home,
        NavItem.Profile,
        NavItem.Settings
    )

    // Đồng bộ giữa Navigationbar và Màn hình hiện tại: Bạn có thể sử dụng biến trạng thái selectedItem để đồng bộ giữa NavigationBar và màn hình hiện tại. Khi người dùng chọn một item mới trong Bottom Navigation Bar, bạn có thể cập nhật biến trạng thái selectedItem với chỉ số của item được chọn, và sau đó sử dụng biến này để điều hướng đến màn hình tương ứng với item đó. Điều này giúp đảm bảo rằng khi người dùng chọn một item mới, họ sẽ được điều hướng đến màn hình tương ứng với item đó, và giao diện của Bottom Navigation Bar sẽ được cập nhật để phản ánh item mới được chọn, giúp cải thiện trải nghiệm người dùng bằng cách cung cấp một giao diện trực quan và dễ hiểu cho các item trong Bottom Navigation Bar.

    // 3. Theo dõi trạng thái của back stack của NavController: Bạn có thể sử dụng navController.currentBackStackEntryAsState() để theo dõi trạng thái của back stack của NavController. Điều này cho phép bạn biết được màn hình hiện tại đang hiển thị dựa trên back stack của NavController, giúp bạn đồng bộ giữa NavigationBar và màn hình hiện tại. Khi người dùng điều hướng giữa các màn hình khác nhau trong ứng dụng của bạn, navBackStackEntry sẽ được cập nhật để phản ánh màn hình hiện tại, và bạn có thể sử dụng thông tin này để cập nhật giao diện của Bottom Navigation Bar, chẳng hạn như thay đổi màu sắc của icon hoặc label của item được chọn để phản ánh màn hình hiện tại, giúp cải thiện trải nghiệm người dùng bằng cách cung cấp một giao diện trực quan và dễ hiểu cho các item trong Bottom Navigation Bar.
    val navBackStackEntry by navController.currentBackStackEntryAsState() // Sử dụng navController.currentBackStackEntryAsState() để theo dõi trạng thái của back stack của NavController. Điều này cho phép bạn biết được màn hình hiện tại đang hiển thị dựa trên back stack của NavController, giúp bạn đồng bộ giữa NavigationBar và màn hình hiện tại. Khi người dùng điều hướng giữa các màn hình khác nhau trong ứng dụng của bạn, navBackStackEntry sẽ được cập nhật để phản ánh màn hình hiện tại, và bạn có thể sử dụng thông tin này để cập nhật giao diện của Bottom Navigation Bar, chẳng hạn như thay đổi màu sắc của icon hoặc label của item được chọn để phản ánh màn hình hiện tại, giúp cải thiện trải nghiệm người dùng bằng cách cung cấp một giao diện trực quan và dễ hiểu cho các item trong Bottom Navigation Bar.

    // 4. Route hiện tại: Bạn có thể lấy route hiện tại từ navBackStackEntry để xác định
    val currentRoute =
        navBackStackEntry?.destination?.route // Sử dụng navBackStackEntry để lấy route hiện tại của màn hình đang hiển thị. Điều này cho phép bạn xác định route hiện tại và so sánh nó với đường dẫn (path) của các item trong danh sách navItems để xác định item nào đang được chọn trong Bottom Navigation Bar. Khi bạn so sánh currentRoute với đường dẫn của các item trong navItems, bạn có thể cập nhật biến trạng thái selectedItem để phản ánh item nào đang được chọn dựa trên route hiện tại, giúp đồng bộ giữa NavigationBar và màn hình hiện tại, và cải thiện trải nghiệm người dùng bằng cách cung cấp một giao diện trực quan và dễ hiểu cho các item trong Bottom Navigation Bar.

    // 5. Tạo biến trạng thái để theo dõi item hiện tại được chọn: Bạn có thể sử dụng một biến trạng thái để lưu trữ item hiện tại được chọn trong Bottom Navigation Bar. Biến này sẽ được cập nhật mỗi khi người dùng chọn một item mới, và bạn có thể sử dụng nó để thay đổi giao diện của Bottom Navigation Bar, chẳng hạn như thay đổi màu sắc của icon hoặc label của item được chọn.
    val selectedItem = navItems.indexOfFirst {
        it.path == currentRoute
    } // Sử dụng navItems.indexOfFirst để tìm chỉ số của item trong danh sách navItems mà có đường dẫn (path) trùng với route hiện tại (currentRoute). Điều này cho phép bạn xác định item nào đang được chọn trong Bottom Navigation Bar dựa trên route hiện tại của màn hình đang hiển thị. Khi bạn tìm thấy chỉ số của item được chọn, bạn có thể sử dụng nó để cập nhật giao diện của Bottom Navigation Bar, chẳng hạn như thay đổi màu sắc của icon hoặc label của item được chọn để phản ánh màn hình hiện tại, giúp đồng bộ giữa NavigationBar và màn hình hiện tại, và cải thiện trải nghiệm người dùng bằng cách cung cấp một giao diện trực quan và dễ hiểu cho các item trong Bottom Navigation Bar.

    // Sử dụng rememberSaveable để lưu trữ trạng thái của selectedItem, giúp đảm bảo rằng khi người dùng điều hướng giữa các màn hình khác nhau trong ứng dụng của bạn, trạng thái của selectedItem sẽ được duy trì và không bị mất khi người dùng quay trở lại màn hình trước đó. Điều này cải thiện trải nghiệm người dùng bằng cách giữ cho trạng thái của ứng dụng được duy trì một cách nhất quán khi người dùng điều hướng giữa các màn hình khác nhau trong ứng dụng của bạn.
    var selectedNavItem by rememberSaveable {
        mutableIntStateOf(if (selectedItem >= 0) selectedItem else 0) // Nếu selectedItem có giá trị hợp lệ (>= 0), sử dụng giá trị đó, ngược lại, đặt selectedNavItem thành 0 để đảm bảo rằng luôn có một item được chọn trong Bottom Navigation Bar. Điều này giúp cải thiện trải nghiệm người dùng bằng cách đảm bảo rằng luôn có một item được chọn trong Bottom Navigation Bar, ngay cả khi route hiện tại không khớp với bất kỳ item nào trong danh sách navItems, giúp đồng bộ giữa NavigationBar và màn hình hiện tại, và cung cấp một giao diện trực quan và dễ hiểu cho các item trong Bottom Navigation Bar. Điều này cũng giúp tránh việc không có item nào được chọn trong Bottom Navigation Bar, điều này có thể gây nhầm lẫn cho người dùng và làm giảm trải nghiệm người dùng khi họ không biết được item nào đang được chọn trong Bottom Navigation Bar, giúp cải thiện trải nghiệm người dùng bằng cách đảm bảo rằng luôn có một item được chọn trong Bottom Navigation Bar, ngay cả khi route hiện tại không khớp với bất kỳ item nào trong danh sách navItems, giúp đồng bộ giữa NavigationBar và màn hình hiện tại, và cung cấp một giao diện trực quan và dễ hiểu cho các item trong Bottom Navigation Bar.
    }

    // 6. Navigation Bar
    NavigationBar() {
        // forEachIndexed: Là một hàm trong Kotlin được sử dụng để lặp qua một tập hợp (collection) và cung cấp cả chỉ số (index) và giá trị (item) của mỗi phần tử trong tập hợp đó. Khi bạn sử dụng forEachIndexed, bạn có thể thực hiện các thao tác trên từng phần tử của tập hợp dựa trên chỉ số của nó, điều này rất hữu ích khi bạn cần biết vị trí của phần tử trong tập hợp để thực hiện các hành động cụ thể, chẳng hạn như cập nhật giao diện người dùng hoặc xử lý sự kiện.
        navItems.forEachIndexed { index, item ->
            // 4. NavigationBarItem: Đây là một thành phần trong Jetpack Compose được sử dụng để tạo ra các item trong Bottom Navigation Bar. Mỗi NavigationBarItem sẽ đại diện cho một item trong danh sách navItems, với các thuộc tính như selected, onClick, icon,... Bạn có thể sử dụng NavigationBarItem để hiển thị các item trong Bottom Navigation Bar và xử lý sự kiện khi người dùng chọn một item.
            NavigationBarItem(
                selected = selectedItem == index, // Kiểm tra xem item hiện tại có phải là item được chọn hay không bằng cách so sánh chỉ số của item với biến trạng thái selectedItem. Nếu chúng bằng nhau, điều đó có nghĩa là item này đang được chọn và sẽ được hiển thị với giao diện đặc biệt để người dùng nhận biết.
                onClick = {
                    selectedNavItem =
                        index // Cập nhật biến trạng thái selectedItem với chỉ số của item được chọn khi người dùng nhấp vào một item trong Bottom Navigation Bar. Điều này sẽ kích hoạt việc cập nhật giao diện của Bottom Navigation Bar để phản ánh item mới được chọn, chẳng hạn như thay đổi màu sắc của icon hoặc label của item đó.


                    // Xử lý điều hướng tới ProfileScreen, truyền các tham số id và showDetails khi người dùng chọn tab Profile trong Bottom Navigation Bar.
                    val route = if (item.path == NavRoute.Profile.path) {
                        NavRoute.Profile.path.plus("/77/true") // Thay thế "123" và "true" bằng giá trị thực tế của id và showDetails mà bạn muốn truyền khi) người dùng chọn tab Profile trong Bottom Navigation Bar. Điều này sẽ tạo ra một route đầy đủ với các tham số id và showDetails, cho phép bạn điều hướng đến màn hình Profile với các tham số này khi người dùng chọn tab Profile trong Bottom Navigation Bar.
                    } else {
                        item.path // Nếu item không phải là tab Profile, sử dụng đường dẫn (path) của item để điều hướng đến màn hình tương ứng với item đó khi người dùng chọn tab đó trong Bottom Navigation Bar.
                    }


                    navController.navigate(route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState =
                                    true // Khi người dùng chọn một item mới trong Bottom Navigation Bar, bạn có thể sử dụng popUpTo để điều hướng trở lại route bắt đầu của NavGraph. Điều này giúp đảm bảo rằng khi người dùng chọn một item mới, họ sẽ được điều hướng trở lại route bắt đầu của NavGraph, thay vì bị đẩy vào một stack mới của các màn hình, giúp cải thiện trải nghiệm người dùng và tránh việc tạo ra một stack quá sâu của các màn hình khi người dùng liên tục chọn các item trong Bottom Navigation Bar. saveState = true: Khi bạn sử dụng popUpTo để điều hướng trở lại route bắt đầu của NavGraph, bạn có thể đặt saveState = true để lưu trạng thái của màn hình hiện tại trước khi điều hướng. Điều này có nghĩa là khi người dùng chọn một item mới trong Bottom Navigation Bar và được điều hướng trở lại route bắt đầu của NavGraph, trạng thái của màn hình hiện tại sẽ được lưu lại, giúp người dùng có thể tiếp tục từ nơi họ đã dừng lại khi họ quay trở lại màn hình đó sau này. Điều này cải thiện trải nghiệm người dùng bằng cách giữ cho trạng thái của ứng dụng được duy trì một cách nhất quán khi người dùng điều hướng giữa các màn hình khác nhau trong ứng dụng của bạn.
                            }
                            launchSingleTop =
                                true // Khi bạn sử dụng navController.navigate để điều hướng đến một màn hình mới, bạn có thể đặt launchSingleTop = true để đảm bảo rằng nếu màn hình đó đã tồn tại trong stack của NavController, nó sẽ không được tạo lại mà sẽ được đưa lên đầu stack. Điều này giúp tránh việc tạo ra nhiều instance của cùng một màn hình khi người dùng liên tục chọn cùng một item trong Bottom Navigation Bar, giúp cải thiện hiệu suất và trải nghiệm người dùng bằng cách giữ cho stack của NavController gọn gàng và tránh việc tạo ra nhiều instance không cần thiết của cùng một màn hình khi người dùng tương tác với Bottom Navigation Bar.
                            restoreState =
                                true // Khi bạn sử dụng navController.navigate để điều hướng đến một màn hình mới, bạn có thể đặt restoreState = true để đảm bảo rằng nếu màn hình đó đã tồn tại trong stack của NavController và đã bị loại bỏ khỏi stack (ví dụ, khi người dùng nhấn nút back), trạng thái của màn hình đó sẽ được khôi phục khi người dùng điều hướng
                        } // Kiểm tra xem có route bắt đầu nào được định nghĩa trong NavGraph hay không bằng cách sử dụng navController.graph.startDestinationRoute. Nếu có, điều đó có nghĩa là có một route bắt đầu đã được định nghĩa và bạn có thể sử dụng popUpTo để điều hướng trở lại route đó khi người dùng chọn một item mới trong Bottom Navigation Bar. Điều này giúp đảm bảo rằng khi người dùng chọn một item mới, họ sẽ được điều hướng trở lại route bắt đầu của NavGraph, thay vì bị đẩy vào một stack mới của các màn hình, giúp cải thiện trải nghiệm người dùng và tránh việc tạo ra một stack quá sâu của các màn hình khi người dùng liên tục chọn các item trong Bottom Navigation Bar.
                    } // Sử dụng navController để điều hướng đến màn hình tương ứng với item được chọn bằng cách sử dụng đường dẫn (path) của item. Khi người dùng nhấp vào một item trong Bottom Navigation Bar, navController sẽ điều hướng đến màn hình tương ứng với item đó, cho phép người dùng chuyển đổi giữa các màn hình khác nhau trong ứng dụng của bạn một cách dễ dàng và trực quan.
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                }, // Sử dụng icon của item để hiển thị biểu tượng trong Bottom Navigation Bar. Khi người dùng nhìn thấy biểu tượng này, họ sẽ biết được chức năng của item đó trong ứng dụng của bạn, giúp cải thiện trải nghiệm người dùng bằng cách cung cấp một giao diện trực quan và dễ hiểu cho các item trong Bottom Navigation Bar.
                label = {
                    Text(text = item.title)
                } // Sử dụng label của item để hiển thị nhãn (label) trong Bottom Navigation Bar. Khi người dùng nhìn thấy nhãn này, họ sẽ biết được chức năng của item đó trong ứng dụng của bạn, giúp cải thiện trải nghiệm người dùng bằng cách cung cấp một giao diện trực quan và dễ hiểu cho các item trong Bottom Navigation Bar.
            )
        }
    }
}