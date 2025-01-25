Các Annotation trong Android
===========================

# 1. Composable

- `@Composable`: Đánh dấu một hàm là một Composable function. Composable function là một hàm tạo ra
  UI trong Compose.
- `@Preview`: Đánh dấu một hàm là một Composable function được sử dụng để hiển thị trước khi chạy
  ứng dụng.
- `@PreviewParameter`: Đánh dấu một tham số của hàm `@Preview` để truyền dữ liệu vào Composable
  function.
- `@Preview(name = "Tên preview")`: Đặt tên cho preview.
- `@Preview(showBackground = true)`: Hiển thị background màu trắng cho preview.
- `@Preview(showBackground = false)`: Không hiển thị background cho preview.
- `@Preview(device = Devices.PIXEL_4)`: Hiển thị preview trên thiết bị Pixel 4.
- `@Preview(widthDp = 360, heightDp = 640)`: Đặt kích thước cho preview.
- `@PreviewGroup`: Nhóm các preview lại với nhau.
- `@PreviewGroup(name = "Tên nhóm")`: Đặt tên cho nhóm preview.
- `@PreviewGroup(showBackground = true)`: Hiển thị background màu trắng cho nhóm preview.
- `@PreviewGroup(showBackground = false)`: Không hiển thị background cho nhóm preview.
- `@PreviewGroup(device = Devices.PIXEL_4)`: Hiển thị nhóm preview trên thiết bị Pixel 4.
- `@PreviewGroup(widthDp = 360, heightDp = 640)`: Đặt kích thước cho nhóm preview.

# 2. Sử dụng parameter trong Composable

Với các function Composable, chúng ta có thể sử dụng parameter để truyền dữ liệu vào Composable
function.

Nếu `parameter` của Composable function thay đổi, Composable sẽ được rebuild.

Khi truyền `function` vào `parameter`, chúng ta thực hiện gọi nó ngay trong argument của Composable
hoặc gọi sau "{ }".

```kotlin
@Composable
fun Greeting(
    name: String,
    funParam1: () -> Unit,
    funParam2: (String) -> Unit
) { // Unit là kiểu dữ liệu của function không trả về giá trị
    Button(text = "Hello, $name!", onClick = funParam, onClick = { funParam2("Android") })
}

@Composable
fun App() {
    Greeting(
        name = "Android",
        funParam = { /* Do something */ },
        funParam2 = { name -> /* Do something */ })
}

// Hoặc
@Composable
fun App() {
    Greeting(
        name = "Android",
        funParam = { /* Do something */ }) { name -> /* Do something */ Thực thi funParam2
    }
}

// Hoặc
@Composable
fun App() {
    Greeting(name = "Android", funParam2 = { name -> /* Do something */ }) {
        /* Do something */ Thực thi funParam1
    }
}
```
