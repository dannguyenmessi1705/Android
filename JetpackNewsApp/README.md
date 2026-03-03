# JetpackNewsApp

Ứng dụng Android hiển thị danh sách bài viết (posts) từ API internet, được xây dựng bằng **Jetpack Compose** và kiến trúc **MVVM + Repository Pattern**.

---

## Mục lục

1. [Tổng quan dự án](#1-tổng-quan-dự-án)
2. [Công nghệ sử dụng](#2-công-nghệ-sử-dụng)
3. [Cấu trúc thư mục](#3-cấu-trúc-thư-mục)
4. [Kiến trúc MVVM](#4-kiến-trúc-mvvm)
5. [Luồng dữ liệu](#5-luồng-dữ-liệu)
6. [Giải thích từng file](#6-giải-thích-từng-file)
   - [AndroidManifest.xml](#androidmanifestxml)
   - [Tầng Network: RetrofitInstance, ApiService, Post](#tầng-network)
   - [Tầng Repository: PostRepository](#tầng-repository)
   - [Tầng ViewModel: PostViewModel](#tầng-viewmodel)
   - [Tầng UI: MainActivity, Screens](#tầng-ui)
   - [Tiện ích: GenRandomColor](#tiện-ích-genrandomcolor)
   - [Theme: Color, Type, Theme](#theme)
7. [Cấu hình Gradle](#7-cấu-hình-gradle)
8. [Cách chạy ứng dụng](#8-cách-chạy-ứng-dụng)
9. [Câu hỏi thường gặp (FAQ)](#9-câu-hỏi-thường-gặp)

---

## 1. Tổng quan dự án

**JetpackNewsApp** là ứng dụng đọc tin tức đơn giản dành cho người mới học Android. Ứng dụng:

- Kết nối tới API công khai [JSONPlaceholder](https://jsonplaceholder.typicode.com/posts)
- Tải về danh sách bài viết (id, title, body)
- Hiển thị từng bài viết dưới dạng thẻ (Card) có màu nền ngẫu nhiên
- Cho phép cuộn danh sách mượt mà

**Giao diện mẫu:**

```
┌─────────────────────────────┐
│       The News App          │
│  Get the latest Posts...    │
├─────────────────────────────┤
│ ┌─────────────────────────┐ │
│ │ Tiêu đề bài viết 1      │ │
│ │ Nội dung bài viết...    │ │
│ └─────────────────────────┘ │
│ ┌─────────────────────────┐ │
│ │ Tiêu đề bài viết 2      │ │
│ │ Nội dung bài viết...    │ │
│ └─────────────────────────┘ │
│           ...               │
└─────────────────────────────┘
```

---

## 2. Công nghệ sử dụng

| Công nghệ | Mục đích |
|-----------|----------|
| **Kotlin** | Ngôn ngữ lập trình chính |
| **Jetpack Compose** | Xây dựng giao diện hiện đại theo dạng khai báo (declarative UI) |
| **ViewModel** | Lưu trữ và quản lý dữ liệu theo vòng đời (lifecycle-aware) |
| **Retrofit 2** | Thư viện gọi HTTP request tới API |
| **Gson** | Chuyển đổi JSON sang Kotlin object và ngược lại |
| **Kotlin Coroutines** | Thực thi tác vụ bất đồng bộ (async) như gọi API |
| **Material Design 3** | Bộ thiết kế giao diện của Google |

---

## 3. Cấu trúc thư mục

```
JetpackNewsApp/
├── app/
│   ├── src/main/
│   │   ├── AndroidManifest.xml          ← Khai báo quyền, Activity
│   │   ├── java/com/didan/.../
│   │   │   ├── MainActivity.kt          ← Điểm khởi đầu của ứng dụng
│   │   │   │
│   │   │   ├── retrofit/                ← Tầng Network (gọi API)
│   │   │   │   ├── Post.kt              ← Data class: cấu trúc 1 bài viết
│   │   │   │   ├── ApiService.kt        ← Khai báo các endpoint API
│   │   │   │   └── RetrofitInstance.kt  ← Khởi tạo Retrofit (Singleton)
│   │   │   │
│   │   │   ├── repository/              ← Tầng Repository (trung gian)
│   │   │   │   └── PostRepository.kt    ← Lấy dữ liệu từ API
│   │   │   │
│   │   │   ├── viewmodel/               ← Tầng ViewModel (quản lý state)
│   │   │   │   └── PostViewModel.kt     ← Giữ danh sách posts cho UI
│   │   │   │
│   │   │   ├── screens/                 ← Tầng UI (giao diện)
│   │   │   │   ├── PostScreen.kt        ← Màn hình chính (container)
│   │   │   │   ├── PostList.kt          ← Danh sách cuộn (LazyColumn)
│   │   │   │   └── PostItem.kt          ← Một thẻ bài viết (Card)
│   │   │   │
│   │   │   ├── util/
│   │   │   │   └── GenRandomColor.kt    ← Tạo màu ngẫu nhiên
│   │   │   │
│   │   │   └── ui/theme/                ← Chủ đề giao diện
│   │   │       ├── Color.kt             ← Bảng màu
│   │   │       ├── Type.kt              ← Kiểu chữ (Typography)
│   │   │       └── Theme.kt             ← Chủ đề Material Design 3
│   │   │
│   │   └── res/
│   │       └── values/
│   │           ├── strings.xml          ← Chuỗi văn bản
│   │           ├── colors.xml           ← Màu sắc XML
│   │           └── themes.xml           ← Theme XML
│   │
│   └── build.gradle.kts                 ← Cấu hình build & dependencies
│
├── build.gradle.kts                     ← Cấu hình build gốc
└── settings.gradle.kts                  ← Cài đặt project
```

---

## 4. Kiến trúc MVVM

**MVVM** = **M**odel - **V**iew - **V**iew**M**odel

```
┌─────────────────────────────────────────────────────────┐
│                      TẦNG VIEW (UI)                     │
│         MainActivity → PostScreen → PostList            │
│                         → PostItem                      │
│  (Hiển thị dữ liệu, không xử lý logic nghiệp vụ)       │
└───────────────────────┬─────────────────────────────────┘
                        │  Quan sát (observe) state
                        ↓
┌─────────────────────────────────────────────────────────┐
│                   TẦNG VIEWMODEL                        │
│                    PostViewModel                        │
│  (Giữ state, gọi Repository, không biết về UI)         │
└───────────────────────┬─────────────────────────────────┘
                        │  Gọi hàm lấy dữ liệu
                        ↓
┌─────────────────────────────────────────────────────────┐
│                  TẦNG REPOSITORY                        │
│                   PostRepository                        │
│  (Trung gian giữa ViewModel và nguồn dữ liệu)          │
└───────────────────────┬─────────────────────────────────┘
                        │  Gọi API
                        ↓
┌─────────────────────────────────────────────────────────┐
│                   TẦNG NETWORK                          │
│         ApiService ← RetrofitInstance                   │
│                    Post (Model)                         │
│  (Gọi HTTP, parse JSON thành Kotlin object)             │
└─────────────────────────────────────────────────────────┘
```

**Lợi ích của MVVM:**
- **Tách biệt trách nhiệm**: Mỗi tầng chỉ làm một việc
- **Dễ kiểm thử**: Có thể test ViewModel và Repository độc lập với UI
- **Dễ bảo trì**: Thay đổi UI không ảnh hưởng logic, thay đổi API không ảnh hưởng UI

---

## 5. Luồng dữ liệu

```
                    App khởi động
                         │
                         ▼
              MainActivity được tạo
                         │
                         ▼
          PostViewModel được khởi tạo (by viewModels())
                         │
                         ▼
         [init block] viewModelScope.launch { ... }
                         │  ← Coroutine bắt đầu (thread phụ)
                         ▼
          PostRepository.getPosts() được gọi
                         │
                         ▼
     RetrofitInstance.api.getPosts() → HTTP GET request
                         │
                         ▼
     https://jsonplaceholder.typicode.com/posts
                         │
                         ▼
          Server trả về JSON:
          [{"id":1,"title":"...","body":"..."},...]
                         │
                         ▼
          Gson tự động parse JSON → List<Post>
                         │
                         ▼
          posts = fetchedPosts (cập nhật state)
                         │  ← Compose tự động re-render
                         ▼
          PostScreen đọc viewModel.posts
                         │
                         ▼
          PostList hiển thị LazyColumn
                         │
                         ▼
          PostItem vẽ từng Card bài viết
```

---

## 6. Giải thích từng file

---

### AndroidManifest.xml

**Vị trí:** `app/src/main/AndroidManifest.xml`

File quan trọng nhất trong Android - khai báo mọi thứ về ứng dụng với hệ điều hành.

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <!--
        Khai báo quyền truy cập Internet.
        Nếu thiếu dòng này, app sẽ CRASH khi gọi API vì
        Android không cho phép app truy cập mạng mà không được cấp phép.
    -->
    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"        ← Icon ứng dụng
        android:label="@string/app_name"           ← Tên ứng dụng
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"                 ← Hỗ trợ ngôn ngữ từ phải sang trái (RTL)
        android:theme="@style/Theme.JetpackNewsApp">

        <!--
            Khai báo Activity duy nhất trong app.
            android:exported="true" = Activity có thể được khởi động từ bên ngoài app.
        -->
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:label="@string/app_name"
            android:theme="@style/Theme.JetpackNewsApp">

            <!-- intent-filter: Đây là Activity được khởi chạy đầu tiên khi mở app -->
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

---

### Tầng Network

#### `Post.kt` - Data Model

**Vị trí:** `retrofit/Post.kt`

```kotlin
package com.didan.jetpack.compose.jetpacknewsapp.retrofit

// data class: Kotlin tự động tạo equals(), hashCode(), toString(), copy() cho class này.
// Đây là "khuôn" để Gson dùng khi parse JSON từ API về.
data class Post(
    val id: Int,       // Số nguyên: ID của bài viết (1, 2, 3, ...)
    val title: String, // Chuỗi: Tiêu đề bài viết
    val body: String   // Chuỗi: Nội dung bài viết
)
```

**JSON từ API trông như thế này:**
```json
{
    "userId": 1,
    "id": 1,
    "title": "sunt aut facere repellat provident occaecati...",
    "body": "quia et suscipit\nsuscipit recusandae..."
}
```

Gson sẽ tự động map `id` → `id`, `title` → `title`, `body` → `body`.
Field `userId` không có trong data class nên bị bỏ qua.

---

#### `ApiService.kt` - Khai báo Endpoint

**Vị trí:** `retrofit/ApiService.kt`

```kotlin
package com.didan.jetpack.compose.jetpacknewsapp.retrofit

import retrofit2.http.GET

// interface: Đây là "bản thiết kế" (blueprint) cho Retrofit.
// Retrofit sẽ tự động tạo ra class thực thi interface này - lập trình viên
// chỉ cần khai báo, không cần tự viết code gọi HTTP.
interface ApiService {

    // @GET("posts"): Đây là HTTP GET request tới đường dẫn "posts"
    // URL đầy đủ = BASE_URL + "posts"
    //            = "https://jsonplaceholder.typicode.com/" + "posts"
    //            = "https://jsonplaceholder.typicode.com/posts"
    //
    // suspend: Hàm này phải chạy trong Coroutine (không chặn thread chính).
    // Nếu không có suspend, gọi hàm này trên main thread sẽ crash với
    // lỗi NetworkOnMainThreadException.
    @GET("posts")
    suspend fun getPosts(): List<Post>
    //                      ^^^^^^^^^^
    //                      Retrofit + Gson tự động parse JSON array
    //                      thành List<Post>
}
```

---

#### `RetrofitInstance.kt` - Khởi tạo Retrofit

**Vị trí:** `retrofit/RetrofitInstance.kt`

```kotlin
package com.didan.jetpack.compose.jetpacknewsapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// object: Singleton trong Kotlin - chỉ có 1 instance duy nhất trong toàn app.
// Đảm bảo chỉ tạo 1 kết nối Retrofit, tiết kiệm tài nguyên.
object RetrofitInstance {

    // const val: Hằng số compile-time (biết giá trị từ lúc biên dịch).
    // Đây là địa chỉ gốc của API - mọi request đều bắt đầu từ đây.
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    // by lazy: Chỉ khởi tạo khi được dùng lần đầu, thread-safe.
    // Lần đầu truy cập RetrofitInstance.api → tạo Retrofit instance.
    // Các lần sau → trả về instance đã tạo sẵn (không tạo lại).
    val api: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)                          // Địa chỉ API gốc
            .addConverterFactory(GsonConverterFactory.create()) // Dùng Gson để parse JSON
            .build()                                    // Tạo Retrofit instance

        // Retrofit tự tạo ra class thực thi interface ApiService.
        // Lập trình viên không cần tự viết code gọi HTTP.
        retrofit.create(ApiService::class.java)
    }
}
```

**Tại sao dùng `by lazy`?**
- Retrofit khởi tạo tốn thời gian → chỉ tạo khi thực sự cần
- Thread-safe: nhiều thread cùng truy cập cũng không bị lỗi

---

### Tầng Repository

#### `PostRepository.kt`

**Vị trí:** `repository/PostRepository.kt`

```kotlin
package com.didan.jetpack.compose.jetpacknewsapp.repository

import com.didan.jetpack.compose.jetpacknewsapp.retrofit.Post
import com.didan.jetpack.compose.jetpacknewsapp.retrofit.RetrofitInstance

// Repository: "Kho dữ liệu" - lớp trung gian giữa ViewModel và nguồn dữ liệu.
// ViewModel không cần biết dữ liệu từ API hay từ database local.
// Lợi ích: Muốn đổi nguồn dữ liệu (API → Room DB) chỉ cần sửa file này.
class PostRepository {

    // Lấy instance của ApiService từ Retrofit singleton.
    // RetrofitInstance.api trả về đối tượng đã cài sẵn URL, Gson, v.v.
    private val apiService = RetrofitInstance.api

    // suspend: Hàm bất đồng bộ, phải gọi từ Coroutine.
    // Gọi API qua apiService rồi trả về kết quả.
    suspend fun getPosts(): List<Post> {
        return apiService.getPosts()
        // Dòng này thực chất gửi HTTP GET tới:
        // https://jsonplaceholder.typicode.com/posts
        // Chờ response → Gson parse JSON → trả về List<Post>
    }
}
```

**Tại sao cần Repository nếu ViewModel có thể gọi thẳng API?**

```
❌ Không dùng Repository (code xấu):
   ViewModel → ApiService (gọi trực tiếp)
   Muốn thêm cache local → phải sửa ViewModel

✅ Dùng Repository (code tốt):
   ViewModel → Repository → ApiService hoặc LocalDatabase
   Muốn thêm cache local → chỉ cần sửa Repository
```

---

### Tầng ViewModel

#### `PostViewModel.kt`

**Vị trí:** `viewmodel/PostViewModel.kt`

```kotlin
package com.didan.jetpack.compose.jetpacknewsapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didan.jetpack.compose.jetpacknewsapp.repository.PostRepository
import com.didan.jetpack.compose.jetpacknewsapp.retrofit.Post
import kotlinx.coroutines.launch

// ViewModel: Lưu trữ và quản lý dữ liệu UI.
// Quan trọng: ViewModel KHÔNG bị xóa khi xoay màn hình (configuration change).
// Nếu đặt data trong Activity/Composable, xoay màn hình sẽ mất data và gọi lại API!
class PostViewModel : ViewModel() {

    // Tạo instance của Repository để lấy dữ liệu.
    private val repository = PostRepository()

    // mutableStateOf: Tạo state theo dõi được bởi Jetpack Compose.
    // Khi giá trị thay đổi, mọi Composable đang "đọc" posts sẽ tự động vẽ lại.
    //
    // by: Delegate property - tự động tạo getter/setter.
    // getValue/setValue import ở trên giúp delegate hoạt động.
    //
    // private set: Chỉ có thể thay đổi từ trong ViewModel.
    // Bên ngoài (UI) chỉ được đọc, không được ghi trực tiếp.
    var posts by mutableStateOf<List<Post>>(emptyList())
        private set

    // init: Chạy ngay khi ViewModel được tạo lần đầu.
    init {
        // viewModelScope: CoroutineScope gắn với vòng đời ViewModel.
        // Khi ViewModel bị hủy, tất cả coroutine trong scope này cũng bị hủy.
        // → Không lo memory leak!
        viewModelScope.launch {
            // launch tạo Coroutine chạy trên thread phụ (không chặn UI).
            // Ứng dụng vẫn mượt trong khi đợi API response.

            // Gọi suspend function - đợi cho tới khi có kết quả.
            val fetchedPosts = repository.getPosts()

            // Cập nhật state → Compose tự động vẽ lại danh sách.
            posts = fetchedPosts
        }
    }
}
```

**So sánh có và không có ViewModel:**

```
❌ Không dùng ViewModel:
   Xoay màn hình → Activity bị tạo lại → posts bị xóa → gọi API lại
   Lãng phí băng thông và làm UX kém

✅ Dùng ViewModel:
   Xoay màn hình → Activity tạo lại nhưng ViewModel còn sống
   → posts vẫn còn → không cần gọi API lại
```

---

### Tầng UI

#### `MainActivity.kt`

**Vị trí:** `MainActivity.kt`

```kotlin
package com.didan.jetpack.compose.jetpacknewsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.didan.jetpack.compose.jetpacknewsapp.screens.PostScreen
import com.didan.jetpack.compose.jetpacknewsapp.ui.theme.JetpackNewsAppTheme
import com.didan.jetpack.compose.jetpacknewsapp.viewmodel.PostViewModel

// ComponentActivity: Lớp cơ bản cho Activity dùng Jetpack Compose.
class MainActivity : ComponentActivity() {

    // by viewModels(): Delegate của AndroidX.
    // Tự động tạo PostViewModel và gắn vào vòng đời của Activity.
    // ViewModel sẽ tồn tại qua configuration changes (xoay màn hình).
    // Lần đầu gọi: tạo mới PostViewModel.
    // Lần sau (sau xoay màn hình): trả về instance đã tồn tại.
    private val myViewModel: PostViewModel by viewModels()

    // @SuppressLint: Tắt cảnh báo lint "UnusedMaterial3ScaffoldPaddingParameter"
    // vì chúng ta truyền innerPadding vào Column bên trong Scaffold.
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Phải gọi super trước

        // enableEdgeToEdge(): Cho phép UI vẽ tới tận mép màn hình
        // (bên dưới status bar và navigation bar) để trông đẹp hơn.
        enableEdgeToEdge()

        // setContent: Thay thế XML layout truyền thống.
        // Toàn bộ UI được định nghĩa bằng Composable functions bên trong.
        setContent {
            // JetpackNewsAppTheme: Bọc toàn bộ UI trong theme Material Design.
            // Cung cấp màu sắc, kiểu chữ nhất quán cho toàn app.
            JetpackNewsAppTheme {

                // Scaffold: Khung Material Design cung cấp cấu trúc màn hình.
                // Hỗ trợ: TopBar, BottomBar, FloatingActionButton, Snackbar, ...
                // App này chỉ dùng body (content).
                Scaffold(
                    modifier = Modifier.fillMaxSize() // Scaffold chiếm toàn màn hình
                ) { innerPadding ->
                    // innerPadding: Padding từ Scaffold (tránh đè lên status bar/nav bar)

                    // Column: Sắp xếp các thành phần theo chiều dọc (từ trên xuống).
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        HeaderComposable()          // Tiêu đề app
                        PostScreen(myViewModel)     // Màn hình danh sách bài viết
                    }
                }
            }
        }
    }
}

// @Composable: Annotation đánh dấu đây là Composable function.
// Composable không return View mà "mô tả" giao diện.
// Compose tự quyết định khi nào vẽ lại (recompose).
@Composable
fun HeaderComposable() {
    // Column: Sắp xếp Text theo chiều dọc, căn giữa theo chiều ngang.
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, // Căn giữa ngang
        modifier = Modifier
            .fillMaxWidth() // Chiều rộng 100% màn hình
    ) {
        // Text lớn: Tiêu đề chính của app
        Text(
            text = "The News App",
            fontSize = 32.sp,             // 32 scale-independent pixels
            fontWeight = FontWeight.Bold  // Chữ đậm
        )

        // Text nhỏ: Phụ đề mô tả app
        Text(
            text = "Get the latest Posts & News",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal // Chữ thường
        )
    }
}
```

---

#### `PostScreen.kt` - Màn hình Container

**Vị trí:** `screens/PostScreen.kt`

```kotlin
package com.didan.jetpack.compose.jetpacknewsapp.screens

import androidx.compose.runtime.Composable
import com.didan.jetpack.compose.jetpacknewsapp.viewmodel.PostViewModel

// PostScreen: "Container" Composable - nhận ViewModel, lấy data, truyền cho PostList.
// Trách nhiệm: Cầu nối giữa ViewModel và UI components.
// Nguyên tắc: Chỉ Composable ở tầng này nên nhận ViewModel,
//             các Composable con chỉ nhận data thuần (List<Post>).
@Composable
fun PostScreen(viewModel: PostViewModel) {
    // Đọc posts từ ViewModel.
    // viewModel.posts là mutableStateOf nên Compose sẽ tự theo dõi:
    // Khi posts thay đổi → PostScreen tự động vẽ lại.
    val posts = viewModel.posts

    // Truyền data xuống PostList để vẽ danh sách.
    PostList(posts)
}
```

---

#### `PostList.kt` - Danh sách cuộn

**Vị trí:** `screens/PostList.kt`

```kotlin
package com.didan.jetpack.compose.jetpacknewsapp.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.didan.jetpack.compose.jetpacknewsapp.retrofit.Post

@Composable
fun PostList(posts: List<Post>) {
    // LazyColumn: Giống RecyclerView trong XML-based Android.
    // "Lazy" = Chỉ vẽ các item đang hiển thị trên màn hình.
    // Nếu có 100 bài viết mà màn hình chỉ hiển thị được 5,
    // LazyColumn chỉ vẽ 5-7 item (thêm vài item buffer).
    // → Hiệu suất tốt hơn Column thông thường khi danh sách dài.
    LazyColumn {
        // items(): Extension function của LazyColumn.
        // Lặp qua từng phần tử trong list, tạo Composable cho mỗi phần tử.
        // "it" trong lambda là phần tử Post hiện tại.
        items(posts) {
            PostItem(it) // Vẽ từng bài viết dưới dạng Card
        }
    }
}
```

**So sánh `Column` và `LazyColumn`:**

```
Column:
- Vẽ TẤT CẢ items ngay lập tức (kể cả không nhìn thấy)
- Dùng khi số item ít và cố định (< 20 items)

LazyColumn:
- Chỉ vẽ items đang hiển thị
- Dùng khi số item lớn hoặc không biết trước số lượng
- Tương đương RecyclerView với ViewHolder pattern
```

---

#### `PostItem.kt` - Một thẻ bài viết

**Vị trí:** `screens/PostItem.kt`

```kotlin
package com.didan.jetpack.compose.jetpacknewsapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.didan.jetpack.compose.jetpacknewsapp.retrofit.Post
import com.didan.jetpack.compose.jetpacknewsapp.util.GenRandomColor

// PostItem: Hiển thị 1 bài viết dưới dạng thẻ (Card) Material Design.
@Composable
fun PostItem(post: Post) {
    // Tạo màu nền ngẫu nhiên cho mỗi Card.
    // Mỗi lần Compose vẽ lại PostItem, màu có thể thay đổi.
    val backgroundColor = GenRandomColor.getRandomColor()

    // Card: Component Material Design 3.
    // Tự động thêm shadow (elevation), bo góc, màu nền.
    Card(
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor, // Màu nền Card (ngẫu nhiên)
            contentColor = Color.Black         // Màu text và icon bên trong Card
        ),
        elevation = CardDefaults.cardElevation(8.dp), // Độ nổi bóng đổ (8dp)
        modifier = Modifier
            .padding(12.dp)    // Khoảng cách 12dp xung quanh Card
            .fillMaxWidth()    // Card chiều rộng 100% (trừ padding)
    ) {
        // Column bên trong Card: Sắp xếp title và body theo chiều dọc.
        Column(modifier = Modifier.padding(16.dp)) { // Padding bên trong Card

            // Tiêu đề bài viết (to, đậm hơn)
            Text(
                text = post.title,
                style = MaterialTheme.typography.labelLarge
                // labelLarge: kiểu chữ cho nhãn lớn theo Material Design
            )

            // Nội dung bài viết (nhỏ hơn)
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodySmall
                // bodySmall: kiểu chữ cho nội dung nhỏ theo Material Design
            )
        }
    }
}
```

---

### Tiện ích: GenRandomColor

#### `GenRandomColor.kt`

**Vị trí:** `util/GenRandomColor.kt`

```kotlin
package com.didan.jetpack.compose.jetpacknewsapp.util

import androidx.compose.ui.graphics.Color

// object: Singleton - chỉ có 1 instance, không cần khởi tạo.
// Dùng cho các utility class (class chứa các hàm tiện ích).
// Gọi bằng: GenRandomColor.getRandomColor() - không cần new GenRandomColor()
object GenRandomColor {

    // Hàm tạo màu ngẫu nhiên từ thành phần RGB.
    // Mỗi thành phần có giá trị 0-255 (256 giá trị):
    //   0   = không có màu đó
    //   255 = tối đa màu đó
    fun getRandomColor(): Color {
        val red   = (0..255).random() // Thành phần đỏ ngẫu nhiên: 0 đến 255
        val green = (0..255).random() // Thành phần xanh lá ngẫu nhiên
        val blue  = (0..255).random() // Thành phần xanh dương ngẫu nhiên

        // Tạo Color từ 3 giá trị RGB.
        // Ví dụ: Color(255, 0, 0) = màu đỏ thuần
        //        Color(0, 255, 0) = màu xanh lá thuần
        //        Color(128, 128, 128) = màu xám
        return Color(red, green, blue)
    }
}
```

---

### Theme

#### `Color.kt` - Bảng màu

**Vị trí:** `ui/theme/Color.kt`

```kotlin
package com.didan.jetpack.compose.jetpacknewsapp.ui.theme

import androidx.compose.ui.graphics.Color

// Màu cho chế độ tối (Dark Mode) - hậu tố "80" (sáng hơn)
val Purple80     = Color(0xFFD0BCFF) // Tím nhạt
val PurpleGrey80 = Color(0xFFCCC2DC) // Tím xám nhạt
val Pink80       = Color(0xFFEFB8C8) // Hồng nhạt

// Màu cho chế độ sáng (Light Mode) - hậu tố "40" (tối hơn)
val Purple40     = Color(0xFF6650a4) // Tím đậm
val PurpleGrey40 = Color(0xFF625b71) // Tím xám đậm
val Pink40       = Color(0xFF7D5260) // Hồng đậm

// Cách đọc mã màu hex: 0xFFRRGGBB
// FF = Alpha (độ trong suốt): FF = hoàn toàn mờ đục (không trong suốt)
// RR = Red (đỏ), GG = Green (xanh lá), BB = Blue (xanh dương)
```

---

#### `Type.kt` - Kiểu chữ

**Vị trí:** `ui/theme/Type.kt`

```kotlin
package com.didan.jetpack.compose.jetpacknewsapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Typography: Bộ kiểu chữ cho toàn ứng dụng theo Material Design 3.
// Material Design 3 định nghĩa sẵn các kiểu: bodyLarge, bodyMedium, bodySmall,
// labelLarge, labelMedium, headlineLarge, titleLarge, v.v.
val Typography = Typography(
    // Định nghĩa style cho bodyLarge (nội dung lớn).
    bodyLarge = TextStyle(
        fontFamily   = FontFamily.Default,  // Font mặc định của hệ thống
        fontWeight   = FontWeight.Normal,   // Độ đậm bình thường
        fontSize     = 16.sp,              // Kích thước 16sp (scale-independent pixels)
        lineHeight   = 24.sp,              // Chiều cao dòng 24sp
        letterSpacing = 0.5.sp             // Khoảng cách giữa các chữ cái
    )
)
// Lưu ý: "sp" tự động co/giãn theo cài đặt cỡ chữ của người dùng,
// còn "dp" thì không. Dùng "sp" cho text, "dp" cho kích thước layout.
```

---

#### `Theme.kt` - Chủ đề chính

**Vị trí:** `ui/theme/Theme.kt`

```kotlin
package com.didan.jetpack.compose.jetpacknewsapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Bộ màu cho chế độ TỐI (Dark Mode)
private val DarkColorScheme = darkColorScheme(
    primary   = Purple80,     // Màu chính
    secondary = PurpleGrey80, // Màu phụ
    tertiary  = Pink80        // Màu thứ ba
)

// Bộ màu cho chế độ SÁNG (Light Mode)
private val LightColorScheme = lightColorScheme(
    primary   = Purple40,
    secondary = PurpleGrey40,
    tertiary  = Pink40
)

// Composable Function bọc toàn bộ UI của app.
// darkTheme: Tự động phát hiện chế độ tối của hệ thống.
// dynamicColor: Dùng màu từ hình nền điện thoại (Android 12+ / API 31+).
@Composable
fun JetpackNewsAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Dynamic Color: Android 12+ lấy màu từ wallpaper người dùng.
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        // Android < 12: Dùng màu tùy chỉnh theo chế độ tối/sáng.
        darkTheme -> DarkColorScheme
        else      -> LightColorScheme
    }

    // MaterialTheme: Cung cấp colorScheme và typography cho toàn bộ
    // Composable con thông qua CompositionLocal (đọc bằng MaterialTheme.colors, v.v.)
    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content // Nội dung UI được bọc bên trong theme
    )
}
```

---

## 7. Cấu hình Gradle

### `app/build.gradle.kts` (App-level)

```kotlin
plugins {
    alias(libs.plugins.android.application)  // Plugin build Android app
    alias(libs.plugins.kotlin.compose)        // Plugin hỗ trợ Jetpack Compose
}

android {
    namespace     = "com.didan.jetpack.compose.jetpacknewsapp"
    compileSdk    = 36  // Phiên bản Android SDK dùng để compile

    defaultConfig {
        applicationId = "com.didan.jetpack.compose.jetpacknewsapp" // ID duy nhất trên Play Store
        minSdk        = 24  // Phiên bản Android tối thiểu: Android 7.0 (Nougat)
        targetSdk     = 36  // Phiên bản Android mục tiêu tối ưu hóa
        versionCode   = 1   // Số phiên bản (dùng cho Play Store update)
        versionName   = "1.0" // Tên phiên bản hiển thị cho người dùng
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        // Dùng Java 11 features trong code
    }
    kotlinOptions {
        jvmTarget = "11" // Compile Kotlin target Java 11 bytecode
    }
    buildFeatures {
        compose = true // Bật Jetpack Compose
    }
}

dependencies {
    // AndroidX Core: Các API Kotlin Extensions tiện ích
    implementation(libs.androidx.core.ktx)

    // Lifecycle Runtime: Hỗ trợ lifecycle-aware components
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Activity Compose: setContent{} cho Activity
    implementation(libs.androidx.activity.compose)

    // Compose BOM: Quản lý version của tất cả thư viện Compose
    // Chỉ cần khai báo version BOM, các thư viện Compose khác tự lấy version phù hợp
    implementation(platform(libs.androidx.compose.bom))

    // Compose UI core
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview) // Preview trong Android Studio

    // Material Design 3
    implementation(libs.androidx.material3)

    // ViewModel cho Compose: by viewModels(), viewModel()
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // LiveData (không dùng trực tiếp trong app này nhưng thường đi kèm)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // Retrofit: HTTP client để gọi REST API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Gson: Thư viện parse JSON
    implementation("com.google.code.gson:gson:2.10.1")

    // Gson Converter: Kết nối Retrofit với Gson để tự động parse JSON
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}
```

---

## 8. Cách chạy ứng dụng

### Yêu cầu hệ thống

- **Android Studio** Iguana (2023.2.1) hoặc mới hơn
- **JDK 11** trở lên
- **Android SDK** API 36
- **Thiết bị/Giả lập** Android API 24+ (Android 7.0+)

### Các bước chạy

1. **Clone/mở project**
   ```
   Mở Android Studio → File → Open → Chọn thư mục JetpackNewsApp
   ```

2. **Đồng bộ Gradle**
   ```
   Android Studio sẽ tự hỏi "Sync Now" → Nhấn đồng ý
   Hoặc: File → Sync Project with Gradle Files
   ```

3. **Tạo thiết bị ảo (nếu chưa có)**
   ```
   Tools → Device Manager → Create Device
   Chọn: Pixel 6 → API 34 → Finish
   ```

4. **Chạy ứng dụng**
   ```
   Nhấn nút ▶ Run (Shift+F10)
   Chọn thiết bị → OK
   ```

5. **Kiểm tra kết quả**
   - App khởi động
   - Hiển thị "The News App" ở đầu
   - Danh sách bài viết xuất hiện (sau vài giây tải từ API)
   - Các Card có màu ngẫu nhiên

### Kết nối Internet

App cần kết nối Internet để tải dữ liệu. Đảm bảo:
- Thiết bị thật: Bật Wifi/Mobile Data
- Giả lập: Máy tính đang kết nối Internet (giả lập dùng mạng của máy tính)

---

## 9. Câu hỏi thường gặp

### Q: Tại sao app hiển thị danh sách trống khi mới mở?
**A:** Bình thường - app đang tải dữ liệu từ API. Khi API trả về kết quả, danh sách sẽ hiện ra. Để cải thiện UX, có thể thêm loading indicator.

### Q: Màu của Card thay đổi khi cuộn lên xuống - có phải bug không?
**A:** Đây là do `GenRandomColor.getRandomColor()` được gọi mỗi khi Compose vẽ lại `PostItem`. Khi cuộn, Compose hủy item cũ và tạo lại item mới → màu mới. Để fix: lưu màu vào `remember {}`.

### Q: Tại sao cần `suspend` trước `getPosts()`?
**A:** Gọi API là tác vụ chậm (có thể mất vài giây). Nếu chạy trên main thread (UI thread), app sẽ bị đơ trong lúc chờ. `suspend` báo hiệu hàm này cần chạy trong Coroutine (thread phụ), main thread vẫn tự do để vẽ UI.

### Q: JSONPlaceholder là gì?
**A:** [JSONPlaceholder](https://jsonplaceholder.typicode.com/) là API giả (fake REST API) miễn phí dành cho học tập và thử nghiệm. Nó cung cấp dữ liệu mẫu về users, posts, comments, todos, v.v.

### Q: `by viewModels()` khác `viewModel()` trong Compose như thế nào?
**A:** Cả hai đều tạo/lấy ViewModel theo lifecycle:
- `by viewModels()` - dùng trong **Activity/Fragment** (Kotlin property delegate)
- `viewModel()` - dùng trong **Composable function** (Compose-aware)

---

## Tóm tắt kiến trúc

```
┌─────────────────────────────────────────────────────────────┐
│  MainActivity (Activity)                                    │
│  ├── PostViewModel (by viewModels)                          │
│  └── setContent {                                           │
│       JetpackNewsAppTheme {                                 │
│         Scaffold {                                          │
│           Column {                                          │
│             HeaderComposable()    ← Tiêu đề app            │
│             PostScreen(viewModel) ← Nhận ViewModel          │
│               └── PostList(posts) ← Nhận List<Post>         │
│                     └── PostItem(post) × N ← 1 Card/bài    │
│           }                                                 │
│         }                                                   │
│       }                                                     │
│     }                                                       │
│                                                             │
│  PostViewModel                                              │
│  ├── posts: List<Post>  (mutableStateOf)                    │
│  └── init → viewModelScope.launch → PostRepository          │
│                                                             │
│  PostRepository                                             │
│  └── getPosts() → RetrofitInstance.api.getPosts()           │
│                                                             │
│  RetrofitInstance (Singleton)                               │
│  └── api: ApiService (by lazy)                              │
│        └── @GET("posts") → https://jsonplaceholder...       │
└─────────────────────────────────────────────────────────────┘
```

---

*Dự án này được xây dựng để học tập Android Development với Jetpack Compose và MVVM Architecture.*
