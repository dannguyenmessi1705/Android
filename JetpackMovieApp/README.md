# JetpackMovieApp

Ứng dụng Android hiển thị danh sách phim phổ biến, được xây dựng bằng **Jetpack Compose**, **MVVM Architecture**, **Retrofit** (gọi API) và **Room** (lưu trữ offline).

---

## Mục lục

1. [Tổng quan kiến trúc](#1-tổng-quan-kiến-trúc)
2. [Luồng hoạt động](#2-luồng-hoạt-động)
3. [Cấu trúc thư mục](#3-cấu-trúc-thư-mục)
4. [Các thư viện sử dụng](#4-các-thư-viện-sử-dụng)
5. [Giải thích từng lớp](#5-giải-thích-từng-lớp)
   - [5.1 Lớp Retrofit (Network)](#51-lớp-retrofit-network)
   - [5.2 Lớp Room (Database)](#52-lớp-room-database)
   - [5.3 Lớp Repository](#53-lớp-repository)
   - [5.4 Lớp ViewModel](#54-lớp-viewmodel)
   - [5.5 Lớp UI (Screens)](#55-lớp-ui-screens)
   - [5.6 MainActivity](#56-mainactivity)
6. [Giải thích chi tiết từng file](#6-giải-thích-chi-tiết-từng-file)
7. [Câu hỏi thường gặp cho người mới](#7-câu-hỏi-thường-gặp-cho-người-mới)

---

## 1. Tổng quan kiến trúc

Ứng dụng này sử dụng kiến trúc **MVVM (Model - View - ViewModel)**:

```
┌─────────────────────────────────────────────────────┐
│                     UI Layer                        │
│   MainActivity → MovieScreen → MovieList → MovieItem│
└───────────────────────┬─────────────────────────────┘
                        │ quan sát state
┌───────────────────────▼─────────────────────────────┐
│                  ViewModel Layer                    │
│                  MovieViewModel                     │
└───────────────────────┬─────────────────────────────┘
                        │ gọi hàm
┌───────────────────────▼─────────────────────────────┐
│                 Repository Layer                    │
│                 MovieRepository                     │
└──────────┬────────────────────────┬─────────────────┘
           │                        │
┌──────────▼──────────┐  ┌──────────▼──────────────┐
│   Network (Retrofit)│  │   Database (Room)        │
│   TMDB API          │  │   movies_db (SQLite)     │
└─────────────────────┘  └─────────────────────────-┘
```

**Ý nghĩa từng lớp:**
- **UI Layer**: Hiển thị dữ liệu lên màn hình, không chứa logic nghiệp vụ
- **ViewModel Layer**: Giữ và quản lý trạng thái UI, điều phối luồng dữ liệu
- **Repository Layer**: Nguồn dữ liệu duy nhất (single source of truth), quyết định lấy data từ API hay DB
- **Network/Database**: Nơi thực sự lấy và lưu dữ liệu

---

## 2. Luồng hoạt động

```
App khởi động
     │
     ▼
MovieViewModel.init chạy
     │
     ▼
Gọi API lấy phim phổ biến ──── Thành công ──► Lưu vào Room DB
     │                                              │
     │                                              ▼
     │                                    Hiển thị danh sách phim
     │
     └── Thất bại (không có mạng) ──► Đọc từ Room DB
                                              │
                                              ▼
                                    Hiển thị phim đã lưu trước
```

**Chiến lược offline-first:**
- Khi có mạng: lấy dữ liệu mới từ API → lưu vào DB → hiển thị
- Khi mất mạng: đọc dữ liệu cũ từ DB → hiển thị (không crash)

---

## 3. Cấu trúc thư mục

```
app/src/main/java/com/didan/jetpack/compose/jetpackmovieapp/
│
├── MainActivity.kt              ← Điểm khởi đầu của ứng dụng
│
├── retrofit/                    ← Tầng gọi API
│   ├── Movie.kt                 ← Model dữ liệu phim từ API
│   ├── MovieResponse.kt         ← Model response từ TMDB API
│   ├── ApiService.kt            ← Định nghĩa các endpoint API
│   └── RetrofitInstance.kt      ← Singleton khởi tạo Retrofit
│
├── room/                        ← Tầng lưu trữ cục bộ (SQLite)
│   ├── Movie.kt                 ← Entity (bảng) trong database
│   ├── MovieDao.kt              ← Interface thao tác với database
│   └── MoviesDb.kt              ← Singleton khởi tạo Room Database
│
├── repository/
│   └── MovieRepository.kt       ← Trung gian giữa ViewModel và data sources
│
├── viewmodel/
│   ├── MovieViewModel.kt        ← Quản lý state và logic UI
│   └── MovieViewModelFactory.kt ← Factory để tạo ViewModel với dependency
│
└── screens/                     ← Các màn hình UI (Jetpack Compose)
    ├── MovieScreen.kt           ← Màn hình chính
    ├── MovieList.kt             ← Component danh sách phim
    └── MovieItem.kt             ← Component một item phim
```

---

## 4. Các thư viện sử dụng

| Thư viện | Mục đích |
|----------|----------|
| **Jetpack Compose** | Xây dựng UI theo kiểu declarative (khai báo) |
| **Retrofit2** | Gọi HTTP API REST |
| **Gson** | Chuyển đổi JSON ↔ Kotlin object |
| **Room** | Database SQLite với type-safety |
| **ViewModel + Coroutines** | Quản lý state và tác vụ bất đồng bộ |
| **Coil** | Tải và cache ảnh từ URL |
| **KSP** | Code generation cho Room (thay thế kapt) |

---

## 5. Giải thích từng lớp

### 5.1 Lớp Retrofit (Network)

#### `retrofit/Movie.kt` — Model dữ liệu phim

```kotlin
data class Movie(
    val id: Int,                            // ID duy nhất của phim
    val title: String,                      // Tên phim
    val overview: String,                   // Mô tả nội dung phim
    @SerializedName("poster_path")          // ← Map JSON "poster_path" → Kotlin "posterPath"
    val posterPath: String?,                // Đường dẫn ảnh poster (có thể null)
    @SerializedName("release_date")         // ← Map JSON "release_date" → Kotlin "releaseDate"
    val releaseDate: String                 // Ngày phát hành
)
```

> **Tại sao cần `@SerializedName`?**
> API trả về JSON với tên field kiểu `snake_case` (ví dụ: `poster_path`), nhưng Kotlin dùng `camelCase` (ví dụ: `posterPath`). Annotation này nói với Gson: "khi đọc JSON, hãy map field `poster_path` sang property `posterPath`".
>
> **Nếu thiếu `@SerializedName`**, Gson sẽ tìm key `posterPath` trong JSON nhưng không tìm thấy → giá trị bị `null` → có thể gây crash khi insert vào Room.

---

#### `retrofit/MovieResponse.kt` — Wrapper cho response API

```kotlin
data class MovieResponse(
    val page: Int,                          // Trang hiện tại (phân trang)
    val results: List<Movie>,              // Danh sách phim (đây là thứ ta cần)
    @SerializedName("total_pages")
    val totalPages: Int,                   // Tổng số trang
    @SerializedName("total_results")
    val totalResults: Int                  // Tổng số phim
)
```

> **Tại sao cần class này?**
> TMDB API không trả về thẳng mảng phim. Nó trả về một object bọc ngoài:
> ```json
> {
>   "page": 1,
>   "results": [ { "id": 1, "title": "..." }, ... ],
>   "total_pages": 500,
>   "total_results": 10000
> }
> ```
> Ta chỉ cần `.results` để lấy danh sách phim.

---

#### `retrofit/ApiService.kt` — Định nghĩa endpoint

```kotlin
interface ApiService {

    @GET("movie/popular")                          // HTTP GET tới "movie/popular"
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String           // Thêm ?api_key=xxx vào URL
    ): MovieResponse                               // Trả về MovieResponse sau khi parse JSON
}
```

> **`suspend fun` là gì?**
> Là hàm có thể "tạm dừng" mà không block luồng chính (main thread). Retrofit tự động chạy network call trong background thread khi dùng `suspend`.
>
> **URL hoàn chỉnh khi gọi:**
> `https://api.themoviedb.org/3/movie/popular?api_key=YOUR_KEY`

---

#### `retrofit/RetrofitInstance.kt` — Singleton Retrofit

```kotlin
object RetrofitInstance {                          // "object" = Singleton (chỉ tạo 1 lần)
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    val api: ApiService by lazy {                  // lazy = chỉ khởi tạo khi lần đầu sử dụng
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)                     // URL gốc
            .addConverterFactory(GsonConverterFactory.create()) // Dùng Gson để parse JSON
            .build()
        retrofit.create(ApiService::class.java)    // Tạo implementation của ApiService
    }
}
```

> **`object` vs `class`?**
> `object` trong Kotlin là cách tạo Singleton. Chỉ có đúng 1 instance tồn tại trong toàn bộ app. Phù hợp cho Retrofit vì ta không cần tạo nhiều client.
>
> **`by lazy`?**
> Giá trị chỉ được tính toán lần đầu tiên được truy cập. Những lần sau trả về kết quả đã cached. Giúp tối ưu hiệu năng khởi động.

---

### 5.2 Lớp Room (Database)

#### `room/Movie.kt` — Entity (Bảng trong DB)

```kotlin
@Entity(tableName = "movies_table")    // Khai báo đây là bảng tên "movies_table"
data class Movie(
    @PrimaryKey                        // Khóa chính, phải unique
    val id: Int,
    val title: String,
    val overview: String,
    @ColumnInfo("poster_path")         // Tên cột trong SQLite là "poster_path"
    val posterPath: String?,           // Nullable → cột cho phép NULL
    @ColumnInfo("release_date")
    val releaseDate: String            // Non-nullable → cột NOT NULL
)
```

> **Lưu ý:** Đây là class riêng biệt với `retrofit/Movie.kt`, dù có cùng tên. Chúng tồn tại ở 2 package khác nhau:
> - `retrofit.Movie` = model cho API response
> - `room.Movie` = entity cho database
>
> **Tại sao lại tách ra?** Vì API model và DB model có thể khác nhau (ví dụ DB có thêm cột `created_at`, API không có). Tách ra giúp code linh hoạt hơn.

---

#### `room/MovieDao.kt` — Data Access Object

```kotlin
@Dao                                               // Đánh dấu đây là DAO
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Nếu đã tồn tại (trùng id) → ghi đè
    suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movieList: List<Movie>) // Insert nhiều phim cùng lúc

    @Query("SELECT * FROM movies_table")             // Câu SQL để lấy tất cả phim
    suspend fun getAllMoviesInDb(): List<Movie>
}
```

> **DAO là gì?**
> DAO (Data Access Object) là interface định nghĩa các thao tác với database. Room tự động tạo code implementation dựa trên các annotation (`@Insert`, `@Query`, ...) — ta không cần viết SQL thủ công.
>
> **`OnConflictStrategy.REPLACE` vs mặc định?**
> - Mặc định (`ABORT`): Nếu insert phim đã tồn tại (trùng `id`) → **throw exception** → crash
> - `REPLACE`: Nếu trùng `id` → **xóa bản ghi cũ, insert bản ghi mới** → an toàn khi refresh data

---

#### `room/MoviesDb.kt` — Database Singleton

```kotlin
@Database(
    entities = [Movie::class],         // Danh sách các bảng trong DB
    version = 1,                       // Phiên bản DB (tăng khi thay đổi schema)
    exportSchema = false               // Không export schema ra file JSON
)
abstract class MoviesDb : RoomDatabase() {

    abstract val movieDao: MovieDao    // Room tự tạo implementation cho DAO này

    companion object {                 // Tương đương static trong Java
        @Volatile                      // Đảm bảo mọi thread đều thấy giá trị mới nhất
        private var INSTANCE: MoviesDb? = null

        fun getInstance(context: Context): MoviesDb {
            synchronized(this) {       // Chỉ cho phép 1 thread vào block này tại một thời điểm
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,  // Dùng applicationContext để tránh memory leak
                        MoviesDb::class.java,
                        "movies_db"                  // Tên file database
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
```

> **Tại sao phải Singleton?**
> Nếu tạo nhiều instance Database cùng lúc → nhiều connection tới cùng 1 file SQLite → có thể gây conflict, corrupt data.
>
> **`@Volatile` + `synchronized` là gì?**
> Đây là pattern "Double-Checked Locking" để đảm bảo thread-safe trong môi trường đa luồng (multi-thread). Tránh trường hợp 2 thread cùng tạo instance cùng lúc.

---

### 5.3 Lớp Repository

#### `repository/MovieRepository.kt`

```kotlin
class MovieRepository(context: Context) {

    // ─── Khởi tạo database và DAO ───────────────────────────────────────────
    private val db = MoviesDb.getInstance(context)
    private val movieDao: MovieDao = db.movieDao

    // ─── Lấy phim từ API ────────────────────────────────────────────────────
    suspend fun getPopularMoviesFromApi(apiKey: String): List<Movie> {
        return RetrofitInstance.api.getPopularMovies(apiKey).results
        //                                                   ^^^^^^^^
        //                  Chỉ lấy danh sách phim, bỏ qua page/total_pages
    }

    // ─── Lấy phim từ Room DB ────────────────────────────────────────────────
    suspend fun getMoviesFromDb(): List<Movie> {
        val movie: List<room.Movie> = movieDao.getAllMoviesInDb()
        // Chuyển đổi từ room.Movie → retrofit.Movie (vì UI chỉ hiểu retrofit.Movie)
        return movie.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate
            )
        }
    }

    // ─── Lưu danh sách phim vào Room DB ─────────────────────────────────────
    suspend fun insertMoviesToDb(movieList: List<Movie>) {
        // Chuyển đổi từ retrofit.Movie → room.Movie trước khi lưu
        val moviesToInsert = movieList.map {
            room.Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate
            )
        }
        movieDao.insertMovies(moviesToInsert)
    }

    // ─── Lưu 1 phim vào Room DB ─────────────────────────────────────────────
    suspend fun insertMovieToDb(movie: Movie) {
        val movieToInsert = room.Movie(/* ... */)
        movieDao.insertMovie(movieToInsert)
    }
}
```

> **Tại sao cần Repository?**
> Repository đóng vai trò **"người quản lý dữ liệu"**. ViewModel không cần biết dữ liệu đến từ đâu (API hay DB). Nó chỉ cần gọi hàm từ Repository.
>
> Lợi ích:
> - Dễ thay đổi nguồn dữ liệu (đổi API khác, đổi DB khác) mà không ảnh hưởng ViewModel
> - Dễ test (có thể mock Repository)
>
> **Tại sao phải convert `room.Movie` ↔ `retrofit.Movie`?**
> Vì 2 class này tồn tại ở 2 package khác nhau với mục đích khác nhau. UI layer chỉ biết `retrofit.Movie`, nên ta phải convert khi lấy từ DB.

---

### 5.4 Lớp ViewModel

#### `viewmodel/MovieViewModel.kt`

```kotlin
class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    // ─── State variables (trạng thái UI) ────────────────────────────────────
    // "by mutableStateOf" = Compose sẽ tự recompose khi giá trị thay đổi
    var movies by mutableStateOf<List<Movie>>(emptyList())
        private set  // Chỉ ViewModel có thể thay đổi, UI chỉ được đọc

    var moviesFromApi by mutableStateOf<List<Movie>>(emptyList())
        private set

    var moviesFromDb by mutableStateOf<List<Movie>>(emptyList())
        private set

    // ─── init block chạy ngay khi ViewModel được tạo ────────────────────────
    init {
        viewModelScope.launch {       // Chạy coroutine trong scope của ViewModel
            try {
                // Bước 1: Gọi API lấy phim
                moviesFromApi = repository.getPopularMoviesFromApi("YOUR_API_KEY")

                // Bước 2: Lưu vào Room để dùng offline sau
                repository.insertMoviesToDb(moviesFromApi)

                // Bước 3: Gán vào movies để UI hiển thị
                movies = moviesFromApi

            } catch (e: Exception) {
                // Nếu API thất bại (mất mạng, lỗi server...) → fallback sang DB
                moviesFromDb = repository.getMoviesFromDb()
                movies = moviesFromDb
            }
        }
    }
}
```

> **`ViewModel` là gì?**
> ViewModel tồn tại độc lập với vòng đời của Activity/Fragment. Khi xoay màn hình, Activity bị destroy rồi recreate, nhưng ViewModel vẫn giữ nguyên dữ liệu. Tránh phải fetch lại API khi xoay màn.
>
> **`viewModelScope.launch` là gì?**
> `viewModelScope` là CoroutineScope gắn với ViewModel. Khi ViewModel bị destroy (người dùng thoát app), tất cả coroutine trong scope này tự động bị cancel. Tránh memory leak.
>
> **`mutableStateOf` là gì?**
> Là "observable state" của Jetpack Compose. Khi giá trị thay đổi, Compose tự động vẽ lại (recompose) các composable đang đọc state đó. Không cần gọi `notifyDataSetChanged()` như RecyclerView.

---

#### `viewmodel/MovieViewModelFactory.kt`

```kotlin
class MovieViewModelFactory(private val movieRepository: MovieRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Kiểm tra xem class được yêu cầu có phải MovieViewModel không
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(movieRepository) as T  // Tạo với dependency
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
```

> **Tại sao cần Factory?**
> `ViewModelProvider` mặc định chỉ tạo được ViewModel với **constructor không có tham số**. Vì `MovieViewModel` cần `MovieRepository` làm tham số, ta phải cung cấp Factory để "dạy" cho hệ thống cách tạo nó.

---

### 5.5 Lớp UI (Screens)

#### `screens/MovieScreen.kt` — Màn hình chính

```kotlin
@Composable
fun MovieScreen(viewModel: MovieViewModel) {
    val movieList = viewModel.movies   // Đọc state từ ViewModel
    MovieList(movies = movieList)      // Truyền xuống component con
}
```

> Đây là màn hình đơn giản nhất. Nhiệm vụ: lấy dữ liệu từ ViewModel và truyền vào `MovieList`.
> Khi `viewModel.movies` thay đổi, Compose tự động gọi lại hàm này (recompose).

---

#### `screens/MovieList.kt` — Danh sách phim

```kotlin
@Composable
fun MovieList(movies: List<Movie>) {
    LazyColumn {               // Giống RecyclerView: chỉ render các item đang hiển thị
        items(movies) {        // Duyệt qua từng phim trong danh sách
            MovieItem(movie = it)  // Render từng item
        }
    }
}
```

> **`LazyColumn` vs `Column`?**
> - `Column`: Render **tất cả** items cùng lúc → chậm khi có nhiều item, tốn bộ nhớ
> - `LazyColumn`: Chỉ render các items **đang hiển thị** trên màn hình → hiệu năng cao hơn nhiều
>
> Tương tự `RecyclerView` trong View system cũ.

---

#### `screens/MovieItem.kt` — Một card phim

```kotlin
@Composable
fun MovieItem(movie: Movie) {
    Card(
        elevation = CardDefaults.cardElevation(8.dp),  // Độ nổi (shadow)
        modifier = Modifier
            .padding(12.dp)                            // Khoảng cách bên ngoài
            .fillMaxWidth(),                           // Chiếm toàn bộ chiều ngang
        border = BorderStroke(2.dp, Color.Gray)        // Viền màu xám
    ) {
        Row(modifier = Modifier.padding(8.dp)) {       // Sắp xếp ngang (ảnh | text)

            AsyncImage(
                // Ghép URL đầy đủ để load ảnh poster
                model = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
                contentDescription = "Movie Poster",
                modifier = Modifier.clip(RoundedCornerShape(16.dp))  // Bo góc ảnh
            )

            Spacer(modifier = Modifier.width(8.dp))   // Khoảng trống giữa ảnh và text

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.labelLarge  // Font đậm, lớn
                )
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodySmall   // Font nhỏ, mô tả
                )
            }
        }
    }
}
```

> **`AsyncImage` (Coil) là gì?**
> Là composable từ thư viện Coil để tải ảnh từ URL bất đồng bộ. Tự động:
> - Tải ảnh trong background thread
> - Cache ảnh để không tải lại
> - Hiện placeholder trong khi tải
>
> **Tại sao URL ảnh cần prefix `https://image.tmdb.org/t/p/w500/`?**
> TMDB API chỉ trả về đường dẫn tương đối (`/abc.jpg`). Ta phải ghép thêm base URL và kích thước ảnh (`w500` = width 500px).

---

### 5.6 MainActivity

```kotlin
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Cho phép UI vẽ dưới status bar và navigation bar

        // Bước 1: Tạo Repository với context
        val repository = MovieRepository(applicationContext)

        // Bước 2: Tạo Factory (vì ViewModel cần dependency)
        val viewModelFactory = MovieViewModelFactory(repository)

        // Bước 3: Lấy (hoặc tạo) ViewModel
        val movieViewModel = ViewModelProvider(this, viewModelFactory)[MovieViewModel::class.java]
        //                    ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        //                    Nếu ViewModel đã tồn tại (vd: xoay màn hình) → trả về instance cũ
        //                    Nếu chưa → dùng factory để tạo mới

        // Bước 4: Set UI
        setContent {
            JetpackMovieAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MovieScreen(movieViewModel)  // Truyền ViewModel vào màn hình
                }
            }
        }
    }
}
```

> **`applicationContext` vs `this`?**
> - `this` (ActivityContext): Gắn với vòng đời của Activity → nếu Activity bị destroy, context cũng mất → **memory leak** nếu giữ tham chiếu lâu
> - `applicationContext`: Tồn tại suốt vòng đời app → an toàn để truyền vào Repository và Room

---

## 6. Giải thích chi tiết từng file

### Sơ đồ quan hệ giữa các class

```
MainActivity
    │ tạo
    ├──► MovieRepository ──► RetrofitInstance.api (gọi API)
    │         │
    │         └──────────────► MoviesDb.getInstance() ──► MovieDao (thao tác DB)
    │
    └──► MovieViewModelFactory
              │ tạo
              └──► MovieViewModel(repository)
                        │ cung cấp state
                        └──► MovieScreen
                                  └──► MovieList
                                            └──► MovieItem (x N)
```

### Vòng đời của dữ liệu

```
1. App start → MainActivity.onCreate()
2.           → MovieRepository khởi tạo (Room + Retrofit sẵn sàng)
3.           → MovieViewModel.init chạy
4.           → viewModelScope.launch (coroutine bắt đầu chạy trong background)
5.           → API call tới TMDB → nhận JSON → Gson parse → List<retrofit.Movie>
6.           → Convert sang List<room.Movie> → insert vào SQLite
7.           → movies = moviesFromApi (state thay đổi)
8.           → Compose phát hiện state thay đổi → recompose MovieScreen
9.           → MovieList nhận list mới → LazyColumn render từng MovieItem
10.          → AsyncImage load ảnh từ URL → hiển thị lên màn hình
```

---

## 7. Câu hỏi thường gặp cho người mới

**Q: Tại sao có 2 class `Movie` (retrofit và room)?**
> A: Mỗi lớp có trách nhiệm riêng. `retrofit.Movie` phục vụ cho việc parse JSON từ API. `room.Movie` là schema bảng trong SQLite. Trong dự án lớn, chúng thường khác nhau đáng kể (thêm cột, đổi kiểu dữ liệu...).

**Q: `suspend fun` có nghĩa gì?**
> A: Hàm "có thể tạm dừng" mà không block thread. Khi gọi API hoặc đọc DB (mất thời gian), app không bị đơ (ANR). Phải gọi bên trong một coroutine (như `viewModelScope.launch`).

**Q: Khi nào màn hình tự cập nhật?**
> A: Khi `movies` (được khai báo bằng `mutableStateOf`) thay đổi giá trị. Compose tự động theo dõi state và vẽ lại UI tương ứng.

**Q: Nếu API thất bại lần đầu (chưa có data trong DB) thì sao?**
> A: `getMoviesFromDb()` trả về `emptyList()` → `movies = emptyList()` → `LazyColumn` hiển thị danh sách rỗng (màn hình trắng). Trong thực tế, nên thêm màn hình trống (empty state) hoặc thông báo lỗi cho người dùng.

**Q: `@Volatile` và `synchronized` để làm gì?**
> A: Đảm bảo rằng chỉ có **1 instance** duy nhất của Room Database được tạo ra, ngay cả khi nhiều thread cùng gọi `getInstance()` đồng thời. Đây là pattern **Thread-safe Singleton**.

---

## Thiết lập và chạy dự án

1. Lấy API key miễn phí tại [themoviedb.org](https://www.themoviedb.org/settings/api)
2. Thay API key trong `MovieViewModel.kt`:
   ```kotlin
   repository.getPopularMoviesFromApi("YOUR_API_KEY_HERE")
   ```
3. Thêm permission internet vào `AndroidManifest.xml`:
   ```xml
   <uses-permission android:name="android.permission.INTERNET" />
   ```
4. Build và chạy trên emulator hoặc thiết bị thật (Android 7.0+)
