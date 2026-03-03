# JetpackNoteApp

Ứng dụng ghi chú xây dựng bằng **Jetpack Compose** + **MVVM** + **Room Database** dành cho người mới học Android.

---

## Mục lục

- [Tổng quan](#tổng-quan)
- [Tính năng](#tính-năng)
- [Kiến trúc MVVM](#kiến-trúc-mvvm)
- [Cấu trúc thư mục](#cấu-trúc-thư-mục)
- [Luồng dữ liệu](#luồng-dữ-liệu)
- [Giải thích chi tiết từng file](#giải-thích-chi-tiết-từng-file)
  - [1. Note.kt — Entity (Thực thể dữ liệu)](#1-notekt--entity-thực-thể-dữ-liệu)
  - [2. NoteDAO.kt — Data Access Object](#2-notedaokt--data-access-object)
  - [3. NotesDB.kt — Room Database Singleton](#3-notesdbkt--room-database-singleton)
  - [4. NoteRepository.kt — Repository Pattern](#4-noterepositorykt--repository-pattern)
  - [5. NoteViewModel.kt — ViewModel](#5-noteviewmodelkt--viewmodel)
  - [6. NoteViewModelFactory.kt — Factory](#6-noteviewmodelfactorykt--factory)
  - [7. MainActivity.kt — Điểm vào ứng dụng](#7-mainactivitykt--điểm-vào-ứng-dụng)
  - [8. DisplayNotesList.kt — Danh sách ghi chú](#8-displaynoteslistkt--danh-sách-ghi-chú)
  - [9. NoteListItem.kt — Card ghi chú](#9-notelistitemkt--card-ghi-chú)
  - [10. DisplayDialog.kt — Dialog tạo ghi chú](#10-displaydialogkt--dialog-tạo-ghi-chú)
  - [11. MyColorPicker.kt — Bộ chọn màu](#11-mycolorpickerkt--bộ-chọn-màu)
- [Các khái niệm quan trọng](#các-khái-niệm-quan-trọng)
- [Dependencies](#dependencies)
- [Cách chạy project](#cách-chạy-project)

---

## Tổng quan

JetpackNoteApp là ứng dụng ghi chú đơn giản giúp người dùng:
- Tạo ghi chú với tiêu đề, nội dung và màu sắc tùy chọn
- Xem toàn bộ ghi chú dưới dạng lưới so le (staggered grid)
- Lưu trữ dữ liệu cục bộ bằng Room Database

Đây là project học tập lý tưởng vì nó sử dụng **đầy đủ bộ công cụ Android hiện đại**:

| Công nghệ | Mục đích |
|-----------|----------|
| Jetpack Compose | Xây dựng UI khai báo (declarative UI) |
| Room Database | Lưu trữ dữ liệu cục bộ (SQLite wrapper) |
| MVVM Architecture | Tách biệt logic và giao diện |
| ViewModel | Quản lý trạng thái UI, sống sót qua rotation |
| LiveData | Quan sát dữ liệu phản ứng (reactive) |
| Coroutines | Xử lý bất đồng bộ (async) |
| Material Design 3 | Giao diện theo chuẩn Google |

---

## Tính năng

- Tạo ghi chú với tiêu đề và mô tả
- Chọn màu nền cho từng ghi chú (19 màu)
- Hiển thị ghi chú dạng lưới 2 cột so le (Pinterest style)
- Lưu trữ bền vững qua Room Database
- Hỗ trợ Dark Theme
- UI cập nhật tự động khi dữ liệu thay đổi

---

## Kiến trúc MVVM

```
┌─────────────────────────────────────────────────────────┐
│                      UI LAYER                           │
│  MainActivity → DisplayNotesList → NoteListItem         │
│               → DisplayDialog → MyColorPicker           │
└───────────────────────┬─────────────────────────────────┘
                        │ observe LiveData / call functions
┌───────────────────────▼─────────────────────────────────┐
│                   VIEWMODEL LAYER                       │
│  NoteViewModel (allNotes: LiveData, insert())           │
│  NoteViewModelFactory (tạo ViewModel với dependencies)  │
└───────────────────────┬─────────────────────────────────┘
                        │ gọi repository
┌───────────────────────▼─────────────────────────────────┐
│                  REPOSITORY LAYER                       │
│  NoteRepository (trung gian giữa ViewModel và DAO)      │
└───────────────────────┬─────────────────────────────────┘
                        │ gọi DAO
┌───────────────────────▼─────────────────────────────────┐
│                  DATABASE LAYER                         │
│  NoteDAO (interface SQL)                                │
│  NotesDB (Room Database singleton)                      │
│  Note (Entity / bảng dữ liệu)                          │
└─────────────────────────────────────────────────────────┘
```

**Nguyên tắc MVVM:**
- **Model**: `Note`, `NoteDAO`, `NotesDB`, `NoteRepository` — xử lý dữ liệu
- **ViewModel**: `NoteViewModel` — cầu nối giữa Model và View
- **View**: tất cả Composable functions — chỉ hiển thị, không chứa logic

---

## Cấu trúc thư mục

```
app/src/main/
├── java/com/didan/jetpack/compose/jetpacknoteapp/
│   │
│   ├── MainActivity.kt               ← Điểm vào, khởi tạo toàn bộ dependency
│   │
│   ├── roomdb/                       ← Tầng Database
│   │   ├── Note.kt                   ← Định nghĩa bảng dữ liệu
│   │   ├── NoteDAO.kt                ← Interface các câu lệnh SQL
│   │   └── NotesDB.kt                ← Singleton database instance
│   │
│   ├── repository/                   ← Tầng Repository
│   │   └── NoteRepository.kt         ← Trừu tượng hóa nguồn dữ liệu
│   │
│   ├── viewmodel/                    ← Tầng ViewModel
│   │   ├── NoteViewModel.kt          ← Quản lý trạng thái UI
│   │   └── NoteViewModelFactory.kt   ← Tạo ViewModel với tham số
│   │
│   ├── screens/                      ← Tầng UI (Composables)
│   │   ├── DisplayNotesList.kt       ← Hiển thị lưới ghi chú
│   │   ├── NoteListItem.kt           ← Card ghi chú đơn lẻ
│   │   ├── DisplayDialog.kt          ← Dialog tạo ghi chú mới
│   │   └── MyColorPicker.kt          ← Thanh chọn màu ngang
│   │
│   └── ui/theme/                     ← Cấu hình giao diện
│       ├── Color.kt                  ← Định nghĩa màu sắc
│       ├── Theme.kt                  ← Cấu hình theme Material 3
│       └── Type.kt                   ← Kiểu chữ (typography)
│
└── AndroidManifest.xml               ← Khai báo Activity, permissions
```

---

## Luồng dữ liệu

### Luồng 1: Khởi động ứng dụng — Hiển thị danh sách ghi chú

```
App starts
    │
    ▼
MainActivity.onCreate()
    │ tạo
    ├──► NotesDB.getInstance(context)     ← Room Database khởi tạo
    │        │ trả về
    │        ▼
    ├──► NoteRepository(noteDAO)          ← Repository nhận DAO
    │        │ trả về
    │        ▼
    ├──► NoteViewModelFactory(repo)       ← Factory chuẩn bị dependency
    │        │ trả về
    │        ▼
    └──► NoteViewModel(factory)           ← ViewModel được tạo
             │
             │ allNotes = repository.allNotes (LiveData)
             │
             ▼
    DisplayNotesList observes allNotes
             │
             │ Room DB trả dữ liệu qua LiveData
             │
             ▼
    LazyVerticalStaggeredGrid render NoteListItem cho mỗi note
             │
             ▼
    [Danh sách ghi chú hiển thị trên màn hình]
```

### Luồng 2: Tạo ghi chú mới

```
User nhấn FAB (+)
    │
    ▼
showDialog = true
    │
    ▼
DisplayDialog hiện lên
    │
    ├── User nhập Title
    ├── User nhập Description
    └── User chọn màu từ MyColorPicker
             │ selectedColor cập nhật
             │
             ▼
User nhấn "Save Note"
    │
    ▼
Note(title, description, color.toArgb()) được tạo
    │
    ▼
viewModel.insert(note)
    │
    ▼
viewModelScope.launch {          ← Coroutine bắt đầu (trên thread phụ)
    repository.insert(note)
        │
        ▼
    noteDAO.insert(note)         ← Room lưu vào SQLite
}
    │
    ▼
LiveData tự động phát dữ liệu mới
    │
    ▼
DisplayNotesList tự động cập nhật
    │
    ▼
[Ghi chú mới xuất hiện trong lưới]
```

---

## Giải thích chi tiết từng file

---

### 1. `Note.kt` — Entity (Thực thể dữ liệu)

**Vị trí:** `roomdb/Note.kt`

```kotlin
// @Entity báo cho Room biết đây là một bảng trong database
// tableName = "notes_table" đặt tên bảng trong SQLite
@Entity(tableName = "notes_table")
data class Note(

    // @PrimaryKey: cột khóa chính, mỗi note có ID duy nhất
    // autoGenerate = true: Room tự động tăng ID (1, 2, 3, ...)
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val title: String,        // Tiêu đề ghi chú
    val description: String,  // Nội dung ghi chú
    val color: Int            // Màu nền lưu dạng số nguyên ARGB
                              // Ví dụ: Color.Red.toArgb() → -65536
)
```

**Tại sao dùng `data class`?**
- `data class` tự động tạo `equals()`, `hashCode()`, `toString()`, `copy()`
- Không cần viết thêm code boilerplate

**Tại sao `color` là `Int` thay vì `Color`?**
- Room chỉ lưu được kiểu dữ liệu nguyên thủy (Int, String, Long...)
- `Color` của Compose không thể lưu trực tiếp vào Room
- Dùng `Color.toArgb()` để chuyển Color → Int khi lưu
- Dùng `Color(note.color)` để chuyển Int → Color khi đọc ra

---

### 2. `NoteDAO.kt` — Data Access Object

**Vị trí:** `roomdb/NoteDAO.kt`

```kotlin
// @Dao báo Room đây là interface chứa các câu lệnh truy vấn database
@Dao
interface NoteDAO {

    // @Insert: Room tự tạo câu lệnh INSERT INTO notes_table (...)
    // suspend: hàm này chạy bất đồng bộ (không chặn Main Thread)
    // Phải gọi từ Coroutine hoặc suspend function khác
    @Insert
    suspend fun insert(note: Note)

    // @Query: viết câu SQL tùy chỉnh
    // "SELECT * FROM notes_table": lấy tất cả các dòng
    // LiveData<List<Note>>: Room tự động trả về LiveData
    //   → Khi DB thay đổi, LiveData tự phát tín hiệu cập nhật UI
    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): LiveData<List<Note>>
}
```

**Tại sao `getAllNotes()` không có `suspend`?**
- `LiveData` đã tự xử lý bất đồng bộ bên trong
- Room tự động chạy query trên background thread khi trả về `LiveData`
- Nếu thêm `suspend`, sẽ bị lỗi compile

**Tại sao `insert()` cần `suspend`?**
- Ghi dữ liệu là thao tác tốn thời gian
- `suspend` giúp thao tác chạy trên background thread mà không cần callback
- Phải gọi trong `viewModelScope.launch { }` hoặc coroutine scope khác

---

### 3. `NotesDB.kt` — Room Database Singleton

**Vị trí:** `roomdb/NotesDB.kt`

```kotlin
// @Database khai báo đây là Room Database
// entities: danh sách các bảng (Note.kt = 1 bảng)
// version: phiên bản schema, tăng lên khi thay đổi cấu trúc bảng
// exportSchema = false: không xuất file JSON schema (thường dùng khi học)
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDB : RoomDatabase() {

    // Room sẽ tự tạo class implement interface này
    abstract fun getNoteDAO(): NoteDAO

    companion object {

        // @Volatile: đảm bảo mọi thread đều thấy giá trị mới nhất
        // Không có @Volatile, thread A có thể đang đọc giá trị cũ
        // trong khi thread B đã cập nhật INSTANCE
        @Volatile
        private var INSTANCE: NotesDB? = null

        // Singleton pattern: chỉ tạo database một lần duy nhất
        fun getInstance(context: Context): NotesDB {

            // Nếu INSTANCE đã tồn tại, trả về ngay (không tạo mới)
            // Kiểm tra ngoài synchronized để tối ưu hiệu năng
            return INSTANCE ?: synchronized(this) {
                // synchronized(this): khóa lại, chỉ 1 thread chạy đoạn này
                // Ngăn trường hợp 2 thread cùng tạo INSTANCE cùng lúc

                // Kiểm tra lại lần nữa (double-check locking pattern)
                // Vì thread khác có thể đã tạo INSTANCE trong khi đang chờ
                val instance = INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext, // dùng applicationContext tránh memory leak
                    NotesDB::class.java,        // class của database
                    "notes_db"                  // tên file SQLite trên thiết bị
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}
```

**Tại sao dùng Singleton?**
- Database nặng, tốn bộ nhớ — không nên tạo nhiều instance
- Đảm bảo toàn bộ app dùng chung 1 kết nối database
- Tránh conflict khi nhiều thread cùng ghi dữ liệu

**`@Volatile` + `synchronized` làm gì?**
- Đây là pattern **Thread-Safe Singleton** (Double-Checked Locking)
- Ví dụ không an toàn: Thread A và Thread B cùng kiểm tra `INSTANCE == null`, cùng tạo mới → 2 instance tồn tại
- `synchronized(this)` đảm bảo chỉ 1 thread vào đoạn tạo database

---

### 4. `NoteRepository.kt` — Repository Pattern

**Vị trí:** `repository/NoteRepository.kt`

```kotlin
// Repository nhận DAO qua constructor (Dependency Injection thủ công)
class NoteRepository(private val noteDAO: NoteDAO) {

    // allNotes là LiveData lấy từ DAO
    // val (không thể thay đổi) — chỉ đọc
    val allNotes: LiveData<List<Note>> = noteDAO.getAllNotes()

    // suspend vì noteDAO.insert() là suspend function
    suspend fun insert(note: Note) {
        noteDAO.insert(note)
    }
}
```

**Repository Pattern là gì và tại sao cần?**

Không có Repository (xấu):
```
ViewModel → DAO (trực tiếp)
```

Có Repository (tốt):
```
ViewModel → Repository → DAO
```

Lợi ích:
1. **Tách biệt mối quan tâm**: ViewModel không biết dữ liệu đến từ đâu (local DB, API, cache...)
2. **Dễ test**: Có thể mock Repository thay vì mock database thật
3. **Dễ mở rộng**: Sau này thêm API chỉ cần sửa Repository, ViewModel không đổi

---

### 5. `NoteViewModel.kt` — ViewModel

**Vị trí:** `viewmodel/NoteViewModel.kt`

```kotlin
// ViewModel kế thừa từ AndroidX ViewModel
// Sống sót qua configuration changes (xoay màn hình, đổi ngôn ngữ...)
class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    // Expose LiveData từ Repository lên UI
    // UI (Composable) sẽ observe LiveData này
    val allNotes: LiveData<List<Note>> = repository.allNotes

    // Hàm insert: UI gọi hàm này khi muốn lưu note
    fun insert(note: Note) {
        // viewModelScope: Coroutine scope gắn với ViewModel lifecycle
        // Khi ViewModel bị hủy, coroutine tự động cancel → không memory leak
        // launch: tạo coroutine chạy song song (không block UI thread)
        viewModelScope.launch {
            // repository.insert() là suspend function
            // Chạy trên Dispatchers.IO (background thread) tự động bởi Room
            repository.insert(note)
        }
    }
}
```

**ViewModel khác Activity/Fragment thế nào?**

| | Activity/Fragment | ViewModel |
|---|---|---|
| Xoay màn hình | Bị hủy và tạo lại | Sống sót |
| Chứa UI logic? | Không nên | Có |
| Chứa View reference? | Có | Không nên |
| Lifecycle | gắn với Activity | gắn với Activity nhưng độc lập |

**Tại sao không gọi `suspend fun` trực tiếp trong Activity?**
- Activity không có coroutine scope mặc định an toàn
- Nếu Activity bị hủy khi đang chạy coroutine → crash hoặc memory leak
- `viewModelScope` tự hủy coroutine khi ViewModel bị clear

---

### 6. `NoteViewModelFactory.kt` — Factory

**Vị trí:** `viewmodel/NoteViewModelFactory.kt`

```kotlin
// ViewModelProvider.Factory: interface để tạo ViewModel với tham số tùy chỉnh
// Mặc định Android không cho phép ViewModel có constructor có tham số
class NoteViewModelFactory(private val repository: NoteRepository) :
    ViewModelProvider.Factory {

    // create() được gọi bởi ViewModelProvider khi cần tạo ViewModel
    // modelClass: class của ViewModel cần tạo (NoteViewModel::class)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // isAssignableFrom: kiểm tra modelClass có phải NoteViewModel không
        // Tránh trường hợp Factory bị dùng sai để tạo ViewModel khác
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {

            // @Suppress: tắt cảnh báo unchecked cast (chúng ta đã kiểm tra ở trên)
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }

        // Nếu modelClass không phải NoteViewModel → throw exception
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
```

**Tại sao cần Factory?**

Không có Factory (lỗi):
```kotlin
// Android không biết cách tạo NoteViewModel(repository)
val viewModel = ViewModelProvider(this)[NoteViewModel::class.java] // CRASH!
```

Có Factory (đúng):
```kotlin
val factory = NoteViewModelFactory(repository)
val viewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java] // OK!
```

---

### 7. `MainActivity.kt` — Điểm vào ứng dụng

**Vị trí:** `MainActivity.kt`

```kotlin
class MainActivity : ComponentActivity() {

    // lateinit var: khai báo biến chưa khởi tạo ngay
    // sẽ được gán giá trị trong onCreate()
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ===== KHỞI TẠO DEPENDENCY CHAIN =====

        // Bước 1: Lấy DAO từ database singleton
        val noteDAO = NotesDB.getInstance(application).getNoteDAO()

        // Bước 2: Tạo Repository với DAO
        val repository = NoteRepository(noteDAO)

        // Bước 3: Tạo Factory với Repository
        val factory = NoteViewModelFactory(repository)

        // Bước 4: Lấy (hoặc tạo mới) ViewModel qua Factory
        // ViewModelProvider tự quản lý vòng đời ViewModel
        noteViewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]

        // ===== THIẾT LẬP UI =====
        enableEdgeToEdge() // UI mở rộng đến cạnh màn hình (full screen)

        setContent {
            JetpackNoteAppTheme {

                // mutableStateOf: biến trạng thái trong Compose
                // Khi showDialog thay đổi, Compose tự động recompose UI
                // remember: giữ giá trị qua các lần recompose
                val showDialog = remember { mutableStateOf(false) }

                // Scaffold: layout cơ bản của Material Design
                // Bao gồm: TopBar, BottomBar, FAB, Content
                Scaffold(
                    floatingActionButton = {
                        // MyFAB: nút + ở góc dưới phải
                        MyFAB(showDialog)
                    }
                ) { paddingValues ->
                    // paddingValues: padding mà Scaffold tự tính để tránh overlap với FAB

                    // observeAsState(): chuyển LiveData sang State trong Compose
                    // Compose sẽ recompose khi LiveData phát giá trị mới
                    // emptyList(): giá trị ban đầu khi chưa có dữ liệu
                    val notesList by noteViewModel.allNotes.observeAsState(emptyList())

                    // Hiển thị dialog tạo note nếu showDialog = true
                    if (showDialog.value) {
                        DisplayDialog(
                            onDismiss = { showDialog.value = false }, // đóng dialog
                            onNoteAdded = { note ->
                                noteViewModel.insert(note) // lưu note qua ViewModel
                                showDialog.value = false   // đóng dialog sau khi lưu
                            }
                        )
                    }

                    // Hiển thị danh sách ghi chú
                    DisplayNotesList(
                        notesList = notesList,
                        paddingValues = paddingValues
                    )
                }
            }
        }
    }
}

// Composable function riêng cho FloatingActionButton
// Tách ra để code gọn hơn, dễ đọc hơn
@Composable
fun MyFAB(showDialog: MutableState<Boolean>) {
    FloatingActionButton(
        onClick = {
            showDialog.value = true // khi nhấn FAB → hiện dialog
        }
    ) {
        // Icon dấu + bên trong FAB
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Note" // mô tả cho accessibility
        )
    }
}
```

---

### 8. `DisplayNotesList.kt` — Danh sách ghi chú

**Vị trí:** `screens/DisplayNotesList.kt`

```kotlin
@Composable
fun DisplayNotesList(
    notesList: List<Note>,         // danh sách note từ ViewModel
    paddingValues: PaddingValues   // padding từ Scaffold
) {
    // LazyVerticalStaggeredGrid: lưới dọc so le (giống Pinterest)
    // Tự động tái sử dụng item khi scroll (không render tất cả cùng lúc)
    LazyVerticalStaggeredGrid(

        // StaggeredGridCells.Fixed(2): cố định 2 cột
        // Mỗi cột có chiều rộng bằng nhau, chiều cao tự do
        columns = StaggeredGridCells.Fixed(2),

        // contentPadding: padding bên ngoài toàn bộ lưới
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = paddingValues.calculateTopPadding() + 38.dp, // tránh overlap status bar
            bottom = 16.dp
        ),

        // horizontalArrangement: khoảng cách ngang giữa các cột
        horizontalArrangement = Arrangement.spacedBy(16.dp),

        // verticalItemSpacing: khoảng cách dọc giữa các item
        verticalItemSpacing = 16.dp
    ) {
        // items(): render mỗi note trong danh sách
        // key: giúp Compose nhận biết item nào đã thay đổi (tối ưu hiệu năng)
        items(notesList, key = { note -> note.id }) { note ->
            NoteListItem(note = note) // render từng card note
        }
    }
}
```

**`LazyVerticalStaggeredGrid` vs `LazyVerticalGrid`:**

| | LazyVerticalGrid | LazyVerticalStaggeredGrid |
|---|---|---|
| Chiều cao item | Bằng nhau | Tự do (so le) |
| Kiểu bố cục | Bảng đều | Pinterest style |
| Phù hợp | Ảnh thumbnail | Text có độ dài khác nhau |

---

### 9. `NoteListItem.kt` — Card ghi chú

**Vị trí:** `screens/NoteListItem.kt`

```kotlin
@Composable
fun NoteListItem(note: Note) {
    // Card: container Material Design có shadow và border radius
    Card(
        modifier = Modifier
            .fillMaxWidth()  // rộng bằng cột chứa nó
            .wrapContentHeight(), // cao vừa đủ nội dung

        // elevation: tạo bóng đổ dưới card
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),

        // border: viền đen 1dp xung quanh card
        border = BorderStroke(1.dp, Color.Black),

        colors = CardDefaults.cardColors(
            // note.color là Int (ARGB), chuyển về Color của Compose
            containerColor = Color(note.color)
        )
    ) {
        // Column: xếp các thành phần theo chiều dọc
        Column(
            modifier = Modifier.padding(12.dp) // padding bên trong card
        ) {
            // Tiêu đề note
            Text(
                text = note.title,
                fontSize = 18.sp,           // 18sp = kích thước chữ
                fontWeight = FontWeight.Bold // in đậm
            )

            // Khoảng cách giữa title và description
            Spacer(modifier = Modifier.height(8.dp))

            // Nội dung note
            Text(
                text = note.description,
                fontSize = 16.sp
            )
        }
    }
}
```

**`sp` vs `dp`:**
- `sp` (scale-independent pixels): dùng cho text, tự động scale theo cài đặt cỡ chữ của hệ thống
- `dp` (density-independent pixels): dùng cho layout/kích thước, không bị ảnh hưởng bởi cài đặt chữ

---

### 10. `DisplayDialog.kt` — Dialog tạo ghi chú

**Vị trí:** `screens/DisplayDialog.kt`

```kotlin
@Composable
fun DisplayDialog(
    onDismiss: () -> Unit,           // callback khi đóng dialog
    onNoteAdded: (Note) -> Unit      // callback khi lưu note (truyền Note lên)
) {
    // remember { mutableStateOf("") }: biến trạng thái cục bộ của Composable
    // Khi user gõ chữ, titleState thay đổi → UI recompose → TextField cập nhật
    var titleState by remember { mutableStateOf("") }
    var descriptionState by remember { mutableStateOf("") }

    // Màu mặc định khi mở dialog (màu xanh lá)
    var selectedColor by remember { mutableStateOf(Color.Green) }

    // AlertDialog: dialog chuẩn Material Design
    AlertDialog(
        // Gọi onDismiss khi click ngoài dialog hoặc nhấn Back
        onDismissRequest = { onDismiss() },

        title = { Text("Add Note") },

        // text: nội dung chính của dialog
        text = {
            Column {
                // TextField: ô nhập liệu
                // value: giá trị hiện tại (controlled component)
                // onValueChange: callback khi user gõ
                TextField(
                    value = titleState,
                    onValueChange = { titleState = it }, // it = text mới user vừa gõ
                    placeholder = { Text("Enter title") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = descriptionState,
                    onValueChange = { descriptionState = it },
                    placeholder = { Text("Enter description") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Thanh chọn màu
                MyColorPicker(
                    onColorSelected = { color ->
                        selectedColor = color // cập nhật màu khi user chọn
                    }
                )
            }
        },

        // confirmButton: nút xác nhận (bên phải)
        confirmButton = {
            Button(
                onClick = {
                    // Tạo đối tượng Note với dữ liệu user nhập
                    val newNote = Note(
                        id = 0,                         // 0 vì autoGenerate sẽ tự đặt ID
                        title = titleState,
                        description = descriptionState,
                        color = selectedColor.toArgb()  // Color → Int để lưu vào Room
                    )
                    onNoteAdded(newNote) // trả Note lên MainActivity để lưu
                }
            ) {
                Text("Save Note")
            }
        },

        // dismissButton: nút hủy (bên trái)
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}
```

**Controlled vs Uncontrolled TextField:**

Controlled (đúng cách trong Compose):
```kotlin
var text by remember { mutableStateOf("") }
TextField(
    value = text,              // source of truth
    onValueChange = { text = it }
)
```

Không cần quản lý state thủ công (Compose tự xử lý) như trong View system cũ.

---

### 11. `MyColorPicker.kt` — Bộ chọn màu

**Vị trí:** `screens/MyColorPicker.kt`

```kotlin
@Composable
fun MyColorPicker(
    onColorSelected: (Color) -> Unit  // callback trả màu đã chọn về DisplayDialog
) {
    // Danh sách 19 màu Material Design
    val colors = listOf(
        Color.Red, Color.Blue, Color.Green, Color.Yellow,
        Color.Cyan, Color.Magenta, Color.Black, Color.White,
        Color(0xFFFF5722), // Deep Orange
        Color(0xFF9C27B0), // Purple
        // ... và nhiều màu khác
    )

    // remember state màu đang được chọn (mặc định chọn màu đầu tiên)
    var selectedColor by remember { mutableStateOf(colors[0]) }

    // LazyRow: danh sách ngang, lazy = chỉ render item trong màn hình
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(colors) { color ->
            // Box: container đơn giản cho mỗi nút màu
            Box(
                modifier = Modifier
                    .size(40.dp)           // hình vuông 40x40dp
                    .clip(CircleShape)     // cắt thành hình tròn
                    .background(color)     // tô màu nền
                    .clickable {
                        selectedColor = color
                        onColorSelected(color) // báo màu được chọn lên trên
                    }
                    // Nếu đang được chọn: vẽ viền đen 4dp
                    .then(
                        if (color == selectedColor)
                            Modifier.border(4.dp, Color.Black, CircleShape)
                        else Modifier
                    )
            )
        }
    }
}
```

**Kỹ thuật `Modifier.then()`:**
- Cho phép thêm Modifier có điều kiện
- `if (selected) Modifier.border(...) else Modifier` → thêm hoặc bỏ viền tùy trạng thái

---

## Các khái niệm quan trọng

### State trong Compose

```kotlin
// remember: giữ giá trị qua recompose, nhưng mất khi Composable bị remove khỏi cây
var count by remember { mutableStateOf(0) }

// rememberSaveable: giữ qua recompose VÀ qua configuration change (xoay màn hình)
var name by rememberSaveable { mutableStateOf("") }
```

### Recomposition

Khi state thay đổi, Compose chỉ recompose (render lại) phần UI dùng state đó, không render lại toàn bộ:

```kotlin
// Khi titleState thay đổi, chỉ TextField này recompose
TextField(
    value = titleState,
    onValueChange = { titleState = it }
)
```

### Coroutines + Room

```
Main Thread (UI Thread)          Background Thread (IO Thread)
      │                                    │
      │ viewModelScope.launch {            │
      │     repository.insert(note)  ──────► Room thực thi SQL
      │ }                           ◄──────  Kết quả trả về
      │                                    │
      │ LiveData phát giá trị mới          │
      │ UI recompose                       │
```

### LiveData vs StateFlow

| | LiveData | StateFlow |
|---|---|---|
| Lifecycle-aware | Có (tự stop khi UI ở background) | Không (cần collectAsState) |
| Initial value | Không bắt buộc | Bắt buộc |
| Thread safety | Tự động | Thủ công |
| Dùng với Room | `LiveData<T>` trực tiếp | Cần convert |

Project này dùng **LiveData** vì tích hợp tốt nhất với Room và đơn giản hơn cho người mới.

---

## Dependencies

**`app/build.gradle.kts`** — các thư viện chính:

```kotlin
dependencies {
    // Jetpack Compose
    implementation(platform("androidx.compose:compose-bom:..."))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended") // Icons.Default.Add

    // ViewModel + LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose")
    implementation("androidx.compose.runtime:runtime-livedata") // observeAsState()

    // Room Database
    implementation("androidx.room:room-runtime")
    implementation("androidx.room:room-ktx")        // Coroutines support cho Room
    ksp("androidx.room:room-compiler")              // Code generation (thay kapt)

    // Coroutines (đã có trong room-ktx)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android")
}
```

**KSP vs KAPT:**
- `kapt`: Kotlin Annotation Processing Tool (cũ, chậm hơn)
- `ksp`: Kotlin Symbol Processing (mới, nhanh hơn ~2x, được Room khuyến nghị)

---

## Cách chạy project

### Yêu cầu
- Android Studio Hedgehog (2023.1.1) trở lên
- JDK 11+
- Android SDK API 24+

### Các bước

```bash
# 1. Clone hoặc mở project trong Android Studio
# File → Open → Chọn thư mục JetpackNoteApp

# 2. Sync Gradle (Android Studio tự hỏi hoặc nhấn "Sync Now")

# 3. Kết nối thiết bị Android hoặc mở Emulator

# 4. Nhấn Run (▶) hoặc Shift+F10
```

### Cấu trúc build

```
Project Level: build.gradle.kts   ← version catalog, plugins
App Level:     app/build.gradle.kts ← dependencies, compileSdk, minSdk
```

---

## Tóm tắt luồng học tập

Nếu bạn mới học Android, hãy đọc code theo thứ tự sau:

```
1. Note.kt          → Hiểu cấu trúc dữ liệu
2. NoteDAO.kt       → Hiểu cách truy vấn database
3. NotesDB.kt       → Hiểu Singleton pattern
4. NoteRepository.kt→ Hiểu Repository pattern
5. NoteViewModel.kt → Hiểu ViewModel + Coroutines
6. NoteViewModelFactory.kt → Hiểu Dependency Injection thủ công
7. MyColorPicker.kt → Hiểu Composable cơ bản + State
8. NoteListItem.kt  → Hiểu layout Compose
9. DisplayDialog.kt → Hiểu Form + Callback
10. DisplayNotesList.kt → Hiểu Lazy layouts
11. MainActivity.kt → Ghép tất cả lại
```

---

*Được xây dựng với mục đích học tập Android hiện đại — Jetpack Compose + MVVM + Room*
