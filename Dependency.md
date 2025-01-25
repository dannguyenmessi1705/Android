DEPENDENCIES TRONG ANDROID VỚI JETPACK COMPOSE

# 1. Dependencies là gì?

Dependencies là các thư viện, module, hoặc các tài nguyên khác mà ứng dụng cần để hoạt động. Trong
Android, chúng ta thường sử dụng dependencies để thêm các thư viện hỗ trợ, các module giúp việc phát
triển ứng dụng dễ dàng hơn.

# 2. Các dependencies phổ biến trong Android với Jetpack Compose

Dưới đây là một số dependencies phổ biến mà chúng ta thường sử dụng trong Android với Jetpack
Compose:

## 2.1. androidx.lifecycle:lifecycle-viewmodel-compose

`androidx.lifecycle:lifecycle-viewmodel-compose` là một dependencies giúp chúng ta sử dụng ViewModel
trong Compose. ViewModel giúp chúng ta lưu trữ và quản lý dữ liệu giữa các configuration changes.
Giống như sử dụng `useContext` trong React.

## 2.2. com.squareup.retrofit2:retrofit

`com.squareup.retrofit2:retrofit` là một dependencies giúp chúng ta thực hiện các HTTP requests một
cách dễ dàng. Retrofit giúp chúng ta tạo các API service để giao tiếp với server.

## 2.3. com.squareup.retrofit2:converter-gson

`com.squareup.retrofit2:converter-gson` là một dependencies giúp chúng ta chuyển đổi JSON sang
Java/Kotlin objects và ngược lại. Gson là một thư viện giúp chúng ta parse JSON dễ dàng.

## 2.4. io.coil-kt:coil-compose

`io.coil-kt:coil-compose` là một dependencies giúp chúng ta load ảnh từ Internet một cách dễ dàng.
Coil là một thư viện giúp chúng ta load ảnh hiệu quả và dễ dàng.