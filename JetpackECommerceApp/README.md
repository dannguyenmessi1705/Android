# JetpackECommerceApp

Ung dung thuong mai dien tu (E-Commerce) xay dung bang **Jetpack Compose** tren Android. Day la du an hoc tap giup ban nam vung cac cong nghe hien dai cua Android nhu Firebase, Room Database, Hilt Dependency Injection, Kotlin Coroutines va Flow.

---

## Muc luc

1. [Tong quan du an](#1-tong-quan-du-an)
2. [Cong nghe su dung](#2-cong-nghe-su-dung)
3. [Kien truc du an (Architecture)](#3-kien-truc-du-an-architecture)
4. [Cau truc thu muc](#4-cau-truc-thu-muc)
5. [Giai thich tung tang (Layer)](#5-giai-thich-tung-tang-layer)
   - [5.1 Model - Du lieu](#51-model---du-lieu)
   - [5.2 Room Database - Luu tru cuc bo](#52-room-database---luu-tru-cuc-bo)
   - [5.3 Repository - Trung gian du lieu](#53-repository---trung-gian-du-lieu)
   - [5.4 Dependency Injection voi Hilt](#54-dependency-injection-voi-hilt)
   - [5.5 ViewModel - Xu ly logic nghiep vu](#55-viewmodel---xu-ly-logic-nghiep-vu)
   - [5.6 Navigation - Dieu huong man hinh](#56-navigation---dieu-huong-man-hinh)
   - [5.7 Screens - Giao dien nguoi dung](#57-screens---giao-dien-nguoi-dung)
6. [Luong du lieu (Data Flow)](#6-luong-du-lieu-data-flow)
7. [Nghiep vu chinh](#7-nghiep-vu-chinh)
8. [Cac khai niem quan trong](#8-cac-khai-niem-quan-trong)

---

## 1. Tong quan du an

Ung dung nay mô phong mot cua hang truc tuyen (online store) voi cac chuc nang:

| Chuc nang | Mo ta |
|---|---|
| Dang ky / Dang nhap | Xac thuc nguoi dung qua Firebase Authentication |
| Xem danh muc | Hien thi cac danh muc san pham lay tu Firestore |
| Xem san pham | Xem san pham theo danh muc |
| Chi tiet san pham | Xem thong tin day du cua san pham |
| Gio hang | Them, xoa san pham, tinh tong tien |
| Tim kiem | Tim kiem san pham theo ten |
| Trang ca nhan | Xem thong tin nguoi dung, dang xuat |

---

## 2. Cong nghe su dung

```
- Kotlin                  : Ngon ngu lap trinh chinh
- Jetpack Compose         : Bo cong cu xay dung UI hien dai (khong dung XML)
- Firebase Authentication : Xac thuc nguoi dung (dang ky, dang nhap)
- Firebase Firestore      : Co so du lieu dam may (categories, products)
- Room Database           : Co so du lieu cuc bo (gio hang)
- Hilt                    : Dependency Injection tu dong
- Kotlin Coroutines       : Xu ly tac vu bat dong bo (async)
- Kotlin Flow             : Luong du lieu phan ung (reactive data stream)
- Coil                    : Thu vien tai anh tu URL
- Navigation Compose      : Dieu huong giua cac man hinh
```

---

## 3. Kien truc du an (Architecture)

Du an tuan theo kien truc **MVVM (Model - View - ViewModel)** ket hop voi **Repository Pattern**:

```
┌─────────────────────────────────────────────────────┐
│                    UI Layer (View)                   │
│         Screens (Composable Functions)              │
└──────────────────────┬──────────────────────────────┘
                       │ quan sat State
                       ▼
┌─────────────────────────────────────────────────────┐
│                  ViewModel Layer                     │
│   AuthViewModel / CartViewModel / ProductViewModel  │
│   CategoryViewModel / SearchViewModel               │
└──────────────────────┬──────────────────────────────┘
                       │ goi ham
                       ▼
┌─────────────────────────────────────────────────────┐
│                 Repository Layer                     │
│        FirebaseRepository / CartRepository          │
└──────────┬───────────────────────────┬──────────────┘
           │                           │
           ▼                           ▼
┌──────────────────┐        ┌──────────────────────────┐
│   Firebase       │        │   Room Database (Local)  │
│  (Firestore +    │        │   Cart Items             │
│   Auth)          │        │                          │
└──────────────────┘        └──────────────────────────┘
```

**Giai thich kien truc:**
- **UI (View)**: Chi hien thi du lieu, khong chua logic nghiep vu
- **ViewModel**: Chua logic nghiep vu, giu du lieu UI (ton tai khi xoay man hinh)
- **Repository**: Quyet dinh lay du lieu tu dau (mang hay cuc bo)
- **Data Sources**: Firebase (dam may) va Room (may tinh)

---

## 4. Cau truc thu muc

```
app/src/main/java/com/didan/jetpack/compose/jetpackecommerceapp/
│
├── di/                          # Dependency Injection
│   ├── MyApp.kt                 # Application class - khoi tao Hilt va Firebase
│   └── AppModule.kt             # Cung cap cac dependency (Firebase, Room, ...)
│
├── model/                       # Data classes (cau truc du lieu)
│   ├── Product.kt               # Mo hinh san pham (cung la Entity Room)
│   ├── Category.kt              # Mo hinh danh muc
│   └── UserProfile.kt           # Mo hinh nguoi dung
│
├── repository/                  # Truy cap du lieu tu Firebase
│   └── FirebaseRepository.kt    # Cac ham lay du lieu tu Firestore
│
├── room/                        # Co so du lieu cuc bo (Room)
│   ├── AppDatabase.kt           # Dinh nghia co so du lieu Room
│   ├── CartDao.kt               # Interface truy van gio hang
│   └── CartRepository.kt        # Quan ly logic gio hang
│
├── viewmodels/                  # ViewModel - xu ly logic
│   ├── AuthViewModel.kt         # Quan ly xac thuc nguoi dung
│   ├── CategoryViewModel.kt     # Quan ly danh muc
│   ├── ProductViewModel.kt      # Quan ly danh sach san pham
│   ├── ProductDetailsViewModel.kt # Quan ly chi tiet san pham
│   ├── CartViewModel.kt         # Quan ly gio hang
│   └── SearchViewModel.kt       # Quan ly tim kiem
│
├── screens/                     # Giao dien (UI Composables)
│   ├── navigation/
│   │   └── Screen.kt            # Dinh nghia cac route dieu huong
│   ├── home/
│   │   ├── HomeScreen.kt        # Man hinh chinh
│   │   ├── TopAppBar.kt         # Thanh tieu de
│   │   ├── BottomNavigationBar.kt # Thanh dieu huong duoi
│   │   ├── SearchBar.kt         # Thanh tim kiem
│   │   ├── CategoryChip.kt      # Chip chon danh muc
│   │   ├── FeaturedProductCard.kt # The san pham noi bat
│   │   ├── SectionTitle.kt      # Tieu de phan
│   │   └── DiscountBadge.kt     # Huy hieu giam gia
│   ├── categories/
│   │   ├── CategoryScreen.kt    # Man hinh danh muc
│   │   └── CategoryItem.kt      # Item danh muc
│   ├── products/
│   │   ├── ProductScreen.kt     # Danh sach san pham theo danh muc
│   │   ├── ProductDetailsScreen.kt # Chi tiet san pham
│   │   └── ProductItem.kt       # Item san pham
│   ├── cart/
│   │   ├── CartScreen.kt        # Man hinh gio hang
│   │   └── CartItemCard.kt      # The item gio hang
│   └── profile/
│       ├── LoginScreen.kt       # Man hinh dang nhap
│       ├── SignUpScreen.kt      # Man hinh dang ky
│       └── ProfileScreen.kt     # Man hinh ca nhan
│
├── ui/theme/                    # Chu de giao dien
│   ├── Color.kt                 # Mau sac
│   ├── Theme.kt                 # Chu de Material 3
│   └── Type.kt                  # Kieu chu
│
└── MainActivity.kt              # Diem khoi dau ung dung
```

---

## 5. Giai thich tung tang (Layer)

### 5.1 Model - Du lieu

**Model** la cac class mo ta cau truc du lieu trong ung dung.

#### `Product.kt`

```kotlin
@Entity(tableName = "cart_items")   // @Entity: danh dau day la bang trong Room Database
data class Product(
    @PrimaryKey                     // @PrimaryKey: truong nay la khoa chinh (khong trung lap)
    val id: String = "",            // ID duy nhat cua san pham (lay tu Firestore)
    val name: String = "",          // Ten san pham
    val price: Double = 0.0,        // Gia san pham
    val imageUrl: String = "",      // Duong dan anh
    val categoryId: String = ""     // ID danh muc (de loc san pham theo danh muc)
)
```

**Giai thich:**
- `data class`: Loai class dac biet trong Kotlin, tu dong tao ham `equals()`, `hashCode()`, `copy()`, `toString()`
- `@Entity`: Annotation cua Room - bao Room tao bang `cart_items` trong SQLite
- `@PrimaryKey`: Chi dinh truong `id` la khoa chinh - moi san pham co id khac nhau
- Gia tri mac dinh `= ""` va `= 0.0`: Cho phep Firestore tu dong anh xa du lieu khi cac truong co the null

#### `Category.kt`

```kotlin
data class Category(
    val id: Int = 0,         // ID so nguyen cua danh muc
    val name: String = "",   // Ten danh muc (vi du: "Electronics", "Clothes")
    val iconUrl: String = "" // URL icon dai dien cho danh muc
)
```

**Giai thich:** Category khong co `@Entity` vi no khong luu vao Room - chi lay tu Firestore va hien thi.

#### `UserProfile.kt`

```kotlin
data class UserProfile(
    val uid: String = "",    // User ID tu Firebase Authentication
    val name: String = "",   // Ten hien thi cua nguoi dung
    val email: String = ""   // Email dang nhap
)
```

---

### 5.2 Room Database - Luu tru cuc bo

Room la thu vien cua Android de lam viec voi SQLite mot cach de dang va an toan.

#### `CartDao.kt` - Data Access Object (Giao dien truy cap du lieu)

```kotlin
@Dao                        // @Dao: danh dau day la interface truy cap Room
interface CartDao {

    // Them san pham vao gio hang
    // onConflict = REPLACE: neu san pham da ton tai (cung id), se thay the
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: Product)  // suspend: ham bat dong bo (chay tren Coroutine)

    // Cap nhat thong tin san pham trong gio hang
    @Update
    suspend fun updateCartItem(cartItem: Product)

    // Xoa mot san pham khoi gio hang
    @Delete
    suspend fun deleteCartItem(cartItem: Product)

    // Lay tat ca san pham trong gio hang
    // Tra ve Flow<List<Product>>: tu dong cap nhat UI khi du lieu thay doi
    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<Product>>

    // Tim mot san pham cu the theo id
    @Query("SELECT * FROM cart_items WHERE id = :productId")
    suspend fun getCartItemById(productId: String): Product?  // ? nghia la co the tra ve null

    // Xoa toan bo gio hang
    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}
```

**Giai thich:**
- `@Dao`: Room tu dong tao implementation cua interface nay
- `suspend fun`: Ham nay phai chay trong Coroutine (khong chay tren main thread)
- `Flow<List<Product>>`: Khong can `suspend` vi Flow la mot luong du lieu bat dong bo, tu cap nhat khi DB thay doi

#### `AppDatabase.kt` - Dinh nghia Co so du lieu

```kotlin
// @Database: khai bao day la class co so du lieu Room
// entities: cac bang trong DB (Product -> bang cart_items)
// version: phien ban schema, tang len khi thay doi cau truc bang
// exportSchema: khong xuat file schema ra ngoai
@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Khai bao DAO de truy cap bang cart_items
    abstract fun cartDao(): CartDao

    companion object {          // companion object: tuong tu static trong Java
        @Volatile               // @Volatile: dam bao moi thread doc gia tri moi nhat tu RAM
        private var INSTANCE: AppDatabase? = null

        // Singleton Pattern: chi tao 1 instance duy nhat cua database
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // synchronized: chi cho phep 1 thread vao day cung 1 luc (thread-safe)
                val instance = Room.databaseBuilder(
                    context.applicationContext, // Dung applicationContext tranh memory leak
                    AppDatabase::class.java,
                    "cart_database"             // Ten file database tren thiet bi
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
```

**Giai thich Singleton Pattern:**
- Muc tieu: Toan ung dung chi dung 1 instance database duy nhat
- `INSTANCE ?: synchronized(...)`: Neu chua co instance, tao moi va luu lai
- `@Volatile`: Trong moi truong da luong (multi-thread), dam bao tat ca thread thay gia tri moi nhat

#### `CartRepository.kt` - Quan ly Logic Gio hang

```kotlin
class CartRepository @Inject constructor(  // @Inject: Hilt se tu inject CartDao vao day
    private val cartDao: CartDao
) {
    // Expose luong du lieu gio hang ra ngoai (read-only)
    val allCartItems: Flow<List<Product>> = cartDao.getAllCartItems()

    // Them san pham vao gio hang (co kiem tra trung lap)
    suspend fun addToCart(product: Product) {
        val existingItem = cartDao.getCartItemById(product.id)
        if (existingItem != null) {
            // San pham da co trong gio hang -> cap nhat
            cartDao.updateCartItem(product)
        } else {
            // San pham chua co -> them moi
            cartDao.insertCartItem(product)
        }
    }

    suspend fun removeCartItem(product: Product) {
        cartDao.deleteCartItem(product)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}
```

**Tai sao can CartRepository?**
- Chua logic nghiep vu (kiem tra san pham co trong gio hang chua)
- ViewModel khong can biet cach luu tru cu the (Room hay SharedPreferences)
- De dang thay doi cach luu tru ma khong anh huong den ViewModel

---

### 5.3 Repository - Trung gian du lieu

#### `FirebaseRepository.kt` - Lay du lieu tu Firestore

```kotlin
@Singleton                          // Chi tao 1 instance duy nhat trong toan ung dung
class FirebaseRepository @Inject constructor(
    private val firestore: FirebaseFirestore   // Duoc inject boi Hilt tu AppModule
) {

    // Lay danh sach danh muc voi live update (tu cap nhat khi co thay doi)
    fun getCategoriesFlow(): Flow<List<Category>> = callbackFlow {
        // addSnapshotListener: Firestore goi ham nay moi khi du lieu thay doi
        val listenerRegistration = firestore.collection("categories")
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    return@addSnapshotListener  // Dung xu ly khi co loi
                }
                if (snapshots != null) {
                    // Chuyen doi tu Firestore documents sang List<Category>
                    val categories = snapshots.toObjects(Category::class.java)
                    trySend(categories)  // Gui du lieu vao Flow
                }
            }

        // Bat buoc: Don dep khi Flow bi huy (tranh memory leak)
        awaitClose {
            listenerRegistration.remove()  // Huy listener khi khong can nua
        }
    }

    // Lay san pham theo danh muc (chi lay 1 lan, khong live update)
    suspend fun getProductsByCategory(categoryId: String): List<Product> {
        return try {
            val result = firestore.collection("products")
                .whereEqualTo("categoryId", categoryId)  // Loc theo categoryId
                .get()
                .await()    // await(): cho Firebase hoan thanh (bat dong bo)
            result.toObjects(Product::class.java)
        } catch (e: Exception) {
            emptyList()     // Neu loi tra ve danh sach rong
        }
    }

    // Lay chi tiet 1 san pham theo id
    suspend fun getProductById(productId: String): Product? {
        return try {
            val result = firestore.collection("products")
                .document(productId)    // Lay document cu the theo id
                .get()
                .await()
            result.toObject(Product::class.java)  // Chuyen doi sang Product
        } catch (e: Exception) {
            null    // Neu khong tim thay, tra ve null
        }
    }

    // Lay tat ca san pham
    suspend fun getAllProductsInFirestore(): List<Product> {
        return try {
            firestore.collection("products")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Product::class.java) }
                // mapNotNull: bo qua cac phan tu null sau khi chuyen doi
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Tim kiem san pham theo ten
    suspend fun searchProducts(query: String): List<Product> {
        return try {
            val allProducts = getAllProductsInFirestore()
            // Loc phia client (Firestore khong ho tro full-text search)
            allProducts.filter {
                it.name.lowercase().contains(query)  // So sanh khong phan biet hoa/thuong
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
```

**Tai sao dung `callbackFlow` cho danh muc?**
- Firestore `addSnapshotListener` la callback-based API (khong phai coroutine)
- `callbackFlow` chuyen doi callback API sang Kotlin Flow
- Khi Firestore co du lieu moi, `trySend()` day du lieu vao Flow
- `awaitClose` dam bao cleanup khi Flow bi huy (tranh memory leak)

**Tai sao `getProductsByCategory` la `suspend` thay vi Flow?**
- San pham chi can lay 1 lan khi vao man hinh (khong can live update)
- `suspend` + `.await()` = bat dong bo nhung chi lay du lieu 1 lan

---

### 5.4 Dependency Injection voi Hilt

#### `MyApp.kt` - Application Class

```kotlin
@HiltAndroidApp  // Bat buoc: kich hoat Hilt cho toan bo ung dung
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)  // Khoi tao Firebase khi app bat dau
    }
}
```

**Luu y:** Phai khai bao `MyApp` trong `AndroidManifest.xml`:
```xml
<application
    android:name=".di.MyApp"
    ...>
```

#### `AppModule.kt` - Cung cap Dependency

```kotlin
@Module                             // @Module: day la noi cung cap cac dependency
@InstallIn(SingletonComponent::class)  // Cac dependency song den khi app tat
object AppModule {

    // Cung cap FirebaseFirestore (chi tao 1 lan - Singleton)
    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    // Cung cap AppDatabase (chi tao 1 lan - Singleton)
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getDatabase(appContext)
    }

    // Cung cap CartDao (lay tu AppDatabase)
    @Provides
    fun provideCartDao(appDatabase: AppDatabase): CartDao {
        return appDatabase.cartDao()
    }

    // Cung cap CartRepository
    @Provides
    fun provideCartRepository(cartDao: CartDao): CartRepository {
        return CartRepository(cartDao)
    }

    // Cung cap FirebaseAuth (Singleton)
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth
}
```

**DI hoat dong the nao?**
```
Hilt biet:
  FirebaseFirestore  <-- provideFirebaseFirestore()
  AppDatabase        <-- provideAppDatabase(context)
  CartDao            <-- provideCartDao(AppDatabase)  <- tu dong inject AppDatabase
  CartRepository     <-- provideCartRepository(CartDao) <- tu dong inject CartDao
  FirebaseAuth       <-- provideFirebaseAuth()

Khi ViewModel can CartRepository:
  Hilt tu dong tao: FirebaseFirestore -> AppDatabase -> CartDao -> CartRepository
  Roi inject vao ViewModel
```

---

### 5.5 ViewModel - Xu ly logic nghiep vu

#### `AuthViewModel.kt` - Quan ly Xac thuc

```kotlin
@HiltViewModel  // Danh dau de Hilt co the inject vao ViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth   // Duoc inject boi Hilt
) : ViewModel() {

    // Sealed class: dinh nghia cac trang thai co the co (giong enum nhung manh hon)
    sealed class AuthState {
        object Idle : AuthState()           // Chua lam gi
        object Loading : AuthState()         // Dang xu ly
        data class Success(val user: String) : AuthState()  // Thanh cong
        data class Error(val message: String) : AuthState() // That bai
    }

    // MutableStateFlow: co the thay doi gia tri ben trong (private - chi ViewModel sua)
    private val _authState = MutableStateFlow<AuthState>(
        // Kiem tra ngay khi khoi tao: neu da dang nhap truoc do, dat trang thai Success
        if (auth.currentUser != null) {
            AuthState.Success(auth.currentUser!!.uid)
        } else {
            AuthState.Idle
        }
    )

    // StateFlow: chi doc (expose ra ngoai - UI chi doc, khong sua)
    val authState: StateFlow<AuthState> = _authState

    // Kiem tra nhanh xem co dang nhap khong
    val isLoggedIn: Boolean get() = authState.value is AuthState.Success

    // Lay thong tin nguoi dung hien tai (null neu chua dang nhap)
    val currentUser = auth.currentUser?.let {
        UserProfile(
            uid = it.uid,
            name = it.displayName ?: "User",  // ?: "User" = neu null, dung "User"
            email = it.email ?: ""
        )
    }

    // Ham dang nhap
    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading  // Bat dau: hien thi loading

        viewModelScope.launch {  // Chay bat dong bo trong scope cua ViewModel
            try {
                val result = auth
                    .signInWithEmailAndPassword(email, password)
                    .await()  // Cho Firebase tra ket qua

                result.user?.let {
                    _authState.value = AuthState.Success(it.uid)
                } ?: run {
                    _authState.value = AuthState.Error("Login failed")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Login failed")
            }
        }
    }

    // Ham dang ky tai khoan moi
    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading
                auth.createUserWithEmailAndPassword(email, password).await()
                _authState.value = AuthState.Success("Signup successful")
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Signup failed")
            }
        }
    }

    // Ham dang xuat
    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Idle  // Reset ve trang thai ban dau
    }
}
```

**Pattern MutableStateFlow / StateFlow:**
```
Private (ben trong ViewModel):    _authState = MutableStateFlow  <-- co the sua
Public (expose ra UI):             authState = StateFlow          <-- chi doc

Muc dich: Dam bao chi co ViewModel moi co the thay doi du lieu.
          UI chi duoc phep doc, tranh bug kho tim.
```

#### `CategoryViewModel.kt` - Quan ly Danh muc

```kotlin
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    init {
        fetchCategories()  // init block: chay ngay khi ViewModel duoc tao
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            firebaseRepository.getCategoriesFlow()
                .catch { println("Error in Flow") }  // Bat loi tu Flow
                .collect { categories ->
                    // Moi lan Firestore co du lieu moi, ham nay chay va cap nhat UI
                    _categories.value = categories
                }
        }
    }
}
```

**Tai sao dung `init { fetchCategories() }`?**
- Danh muc can tai ngay khi ViewModel duoc tao (khi vao app)
- `init` block chay 1 lan khi ViewModel khoi tao
- Sau do Flow tu dong cap nhat khi Firestore co thay doi

#### `CartViewModel.kt` - Quan ly Gio hang

```kotlin
@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {

    // Lay truc tiep Flow tu Repository (Room tu cap nhat khi DB thay doi)
    val cartItems = repository.allCartItems

    // Cac ham chuyen task nang sang Coroutine
    fun addToCart(product: Product) = viewModelScope.launch {
        repository.addToCart(product)  // Room tu cap nhat cartItems Flow
    }

    fun removeFromCart(product: Product) = viewModelScope.launch {
        repository.removeCartItem(product)
    }

    fun clearCart() = viewModelScope.launch {
        repository.clearCart()
    }

    // Tinh tong tien gio hang
    fun calculateTotal(items: List<Product>): Double {
        return items.sumOf { it.price }  // sumOf: tinh tong theo truong price
    }
}
```

#### `SearchViewModel.kt` - Quan ly Tim kiem

```kotlin
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: FirebaseRepository
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<Product>>(emptyList())
    val searchResults: StateFlow<List<Product>> get() = _searchResults

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> get() = _isSearching

    fun searchProducts(query: String) {
        if (query.isBlank()) {        // isBlank: chuoi rong hoac chi co khoang trang
            _searchResults.value = emptyList()
            _isSearching.value = false
            return
        }

        _isSearching.value = true     // Bat dau tim kiem: hien loading

        viewModelScope.launch {
            _searchResults.value = repository.searchProducts(query.lowercase())
            _isSearching.value = false  // Xong: tat loading
        }
    }
}
```

---

### 5.6 Navigation - Dieu huong man hinh

#### `Screen.kt` - Dinh nghia cac Route

```kotlin
// sealed class: giong enum nhung moi object co the chua them thuoc tinh
sealed class Screen(val route: String) {

    object Cart : Screen("Cart")
    object Home : Screen("Home")
    object Profile : Screen("Profile")
    object Category : Screen("Category")
    object Login : Screen("Login")
    object SignUp : Screen("SignUp")

    // Man hinh co tham so: them {productId} vao route
    object ProductDetails : Screen("product_details/{productId}") {
        // Ham tao route co gia tri cu the: "product_details/abc123"
        fun createRoute(productId: String) = "product_details/$productId"
    }

    object ProductList : Screen("product_list/{categoryId}") {
        fun createRoute(categoryId: String) = "product_list/$categoryId"
    }
}
```

**Cach dung:**
```kotlin
// Dieu huong den man hinh khong co tham so:
navController.navigate(Screen.Home.route)   // -> "Home"

// Dieu huong den man hinh co tham so:
navController.navigate(Screen.ProductDetails.createRoute("abc123"))
// -> "product_details/abc123"
```

#### `MainActivity.kt` - Cau hinh Navigation

```kotlin
@AndroidEntryPoint  // Bat buoc: cho phep Hilt inject vao Activity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Hien thi full man hinh (khe xuong co an)
        setContent {
            // rememberNavController: tao va ghi nho NavController
            val navHostController = rememberNavController()

            // Inject AuthViewModel qua Hilt (khong dung by viewModels())
            val authViewModel: AuthViewModel = hiltViewModel()

            // derivedStateOf: chi tinh lai khi authState thay doi
            val isLoggedIn by remember {
                derivedStateOf { authViewModel.isLoggedIn }
            }

            JetpackECommerceAppTheme {
                // NavHost: container chua tat ca cac man hinh
                NavHost(
                    navController = navHostController,
                    startDestination = Screen.Home.route  // Man hinh bat dau
                ) {
                    // Moi composable() = 1 man hinh trong app
                    composable(Screen.Home.route) {
                        HomeScreen(
                            navHostController,
                            onProfileClick = { navHostController.navigate(Screen.Profile.route) },
                            onCartClick = { navHostController.navigate(Screen.Cart.route) }
                        )
                    }

                    // Lay tham so tu route khi dieu huong
                    composable(Screen.ProductDetails.route) {
                        val productId = it.arguments?.getString("productId")
                        if (productId != null) {
                            ProductDetailsScreen(productId)
                        }
                    }

                    composable(Screen.Login.route) {
                        LoginScreen(
                            onNavigationToSignUp = { navHostController.navigate(Screen.SignUp.route) },
                            onLoginSuccess = { navHostController.navigate(Screen.Category.route) }
                        )
                    }
                    // ... cac man hinh khac tuong tu
                }
            }
        }
    }
}
```

---

### 5.7 Screens - Giao dien nguoi dung

#### `HomeScreen.kt` - Man hinh Chinh

```kotlin
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    onProfileClick: () -> Unit,   // Lambda callback khi nhan nut Profile
    onCartClick: () -> Unit,
    // hiltViewModel(): Hilt tu inject ViewModel, ViewModel ton tai qua recomposition
    productViewModel: ProductViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { MyTopAppBar(onProfileClick, onCartClick) },  // Thanh tieu de
        bottomBar = { BottomNavigationBar(navHostController) }  // Thanh dieu huong duoi
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)  // Tranh bi thanh tieu de va dieu huong che
        ) {
            // --- THANH TIM KIEM ---
            val searchQuery = remember { mutableStateOf("") }  // Ghi nho gia tri nhap
            val focusManager = LocalFocusManager.current

            SearchBar(
                query = searchQuery.value,
                onQueryChange = { searchQuery.value = it },  // Cap nhat khi nguoi dung go
                onSearch = {
                    searchViewModel.searchProducts(searchQuery.value)
                    focusManager.clearFocus()  // An ban phim sau khi tim kiem
                }
            )

            // Hien thi ket qua tim kiem neu co nhap lieu
            if (searchQuery.value.isNotBlank()) {
                SearchResultsSection(navHostController)
            }

            // --- DANH MUC ---
            SectionTitle("Categories", "See All") {
                navHostController.navigate(Screen.Category.route)
            }

            // collectAsState(): chuyen Flow -> State de Compose theo doi
            val categories = categoryViewModel.categories.collectAsState().value

            LazyRow {  // LazyRow: danh sach ngang, chi render phan tu hien thi tren man hinh
                items(categories.size) { index ->
                    CategoryChip(
                        icon = categories[index].iconUrl,
                        text = categories[index].name,
                        onClick = {
                            // Dieu huong den danh sach san pham theo danh muc
                            navHostController.navigate(
                                Screen.ProductList.createRoute(categories[index].id.toString())
                            )
                        }
                    )
                }
            }

            // --- SAN PHAM NOI BAT ---
            productViewModel.getAllProductsInFirestore()  // Goi lay du lieu
            val products = productViewModel.allProducts.collectAsState().value

            LazyRow {
                items(products) { product ->
                    FeaturedProductCard(product) {
                        navHostController.navigate(Screen.ProductDetails.createRoute(product.id))
                    }
                }
            }
        }
    }
}
```

#### `LoginScreen.kt` - Man hinh Dang nhap

```kotlin
@Composable
fun LoginScreen(
    onNavigationToSignUp: () -> Unit,
    onLoginSuccess: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    // Ghi nho gia tri nhap lieu (khong mat khi recompose)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Thu thap trang thai xac thuc tu ViewModel
    val authState by authViewModel.authState.collectAsState()

    // LaunchedEffect: chay side effect khi authState thay doi
    // Neu dang nhap thanh cong -> chuyen man hinh
    LaunchedEffect(authState) {
        if (authState is AuthViewModel.AuthState.Success) {
            onLoginSuccess()
        }
    }

    Column(...) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },  // Cap nhat state khi go chu
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,  // Hien thi ban phim email
                imeAction = ImeAction.Next           // Nut "Next" de chuyen sang o tiep theo
            )
        )

        OutlinedTextField(
            value = password,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,  // An mat khau
                imeAction = ImeAction.Done             // Nut "Done" de an ban phim
            )
        )

        Button(
            onClick = { authViewModel.login(email, password) }  // Goi ham dang nhap
        ) {
            Text("Login")
        }
    }
}
```

#### `SignUpScreen.kt` - Man hinh Dang ky

```kotlin
@Composable
fun SignUpScreen(...) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<String?>(null) }  // Luu thong bao loi

    Button(
        onClick = {
            // Kiem tra nghiep vu truoc khi goi API
            if (password != confirmPassword) {
                passwordError = "Passwords do not match"  // Mat khau khong khop
            } else if (password.length < 6) {
                passwordError = "Password must be at least 6 characters"  // Mat khau qua ngan
            } else {
                passwordError = null
                authViewModel.signup("User", email, password)  // Goi dang ky
            }
        }
    ) { Text("Sign Up") }

    // Hien thi loi mat khau
    OutlinedTextField(
        isError = passwordError != null,  // Doi mau do neu co loi
        supportingText = {
            passwordError?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    )
}
```

#### `CartScreen.kt` - Man hinh Gio hang

```kotlin
@Composable
fun CartScreen(
    navHostController: NavHostController,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    // Thu thap du lieu gio hang tu Room qua ViewModel
    val cartItems = cartViewModel.cartItems.collectAsState(initial = emptyList()).value

    Column {
        if (cartItems.isEmpty()) {
            // Hien thi thong bao gio hang trong
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Your Cart is Empty")
                Button(onClick = { navHostController.popBackStack() }) {
                    Text("Continue Shopping")  // Quay lai man hinh truoc
                }
            }
        } else {
            // Danh sach san pham trong gio hang
            LazyColumn(modifier = Modifier.weight(1f)) {  // weight(1f): chiem het khong gian con lai
                items(cartItems) { item ->
                    CartItemCard(
                        item = item,
                        onRemoveItem = { cartViewModel.removeFromCart(item) }
                    )
                }
            }

            // Tong tien va nut thanh toan
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total:")
                Text("$${cartViewModel.calculateTotal(cartItems)}")
            }

            Button(onClick = { /* TODO: xu ly thanh toan */ }) {
                Text("Process to Checkout")
            }
        }
    }
}
```

#### `ProductDetailsScreen.kt` - Chi tiet San pham

```kotlin
@Composable
fun ProductDetailsScreen(
    productId: String,
    productDetailsViewModel: ProductDetailsViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    // LaunchedEffect(productId): chay khi man hinh hien thi lan dau, hoac khi productId thay doi
    LaunchedEffect(productId) {
        productDetailsViewModel.fetchProductDetails(productId)
    }

    val product = productDetailsViewModel.product.collectAsState().value

    if (product == null) {
        Text("Product Not Found")
    } else {
        Column {
            // Hien thi anh san pham bang Coil
            Image(
                painter = rememberAsyncImagePainter(product.imageUrl),  // Tai anh tu URL
                contentScale = ContentScale.Crop  // Cat anh vua khung
            )
            Text(product.name)
            Text("$${product.price}")

            // Nut them vao gio hang
            IconButton(onClick = { cartViewModel.addToCart(product) }) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Add to Cart")
            }
        }
    }
}
```

#### `BottomNavigationBar.kt` - Thanh Dieu huong Duoi

```kotlin
// Data class mo ta moi item tren thanh dieu huong
data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val badgeCount: Int = 0  // So huy hieu (0 = khong hien)
)

@Composable
fun BottomNavigationBar(navHostController: NavHostController) {
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, Screen.Home.route),
        BottomNavItem("Categories", Icons.Default.Search, Screen.Cart.route),
        BottomNavItem("Wishlist", Icons.Default.Favorite, Screen.Cart.route, badgeCount = 5),
        BottomNavItem("Cart", Icons.Default.ShoppingCart, Screen.Cart.route, badgeCount = 3),
        BottomNavItem("Profile", Icons.Default.Person, Screen.Profile.route),
    )

    NavigationBar {
        // Lay route hien tai de danh dau tab dang chon
        val navBackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    if (item.badgeCount > 0) {
                        // Hien thi huy hieu so luong
                        BadgedBox(badge = { Badge { Text(item.badgeCount.toString()) } }) {
                            Icon(item.icon, item.title)
                        }
                    } else {
                        Icon(item.icon, item.title)
                    }
                },
                selected = currentRoute == item.route,  // Tab nao dang duoc chon
                onClick = {
                    navHostController.navigate(item.route) {
                        popUpTo(navHostController.graph.startDestinationId) // Xoa back stack
                        launchSingleTop = true  // Khong tao 2 instance cung 1 man hinh
                    }
                }
            )
        }
    }
}
```

---

## 6. Luong du lieu (Data Flow)

### Luong dang nhap

```
Nguoi dung nhap email/password
       |
       v
LoginScreen: authViewModel.login(email, password)
       |
       v
AuthViewModel.login():
  - _authState = Loading
  - goi Firebase Auth signInWithEmailAndPassword()
  - cho ket qua (await)
       |
  Thanh cong? -> _authState = Success
  That bai?   -> _authState = Error
       |
       v
LoginScreen quan sat authState qua LaunchedEffect:
  - Success -> onLoginSuccess() -> navigate(Category)
  - Error   -> Hien thong bao loi
```

### Luong them vao gio hang

```
Nguoi dung nhan "Add to Cart"
       |
       v
ProductDetailsScreen: cartViewModel.addToCart(product)
       |
       v
CartViewModel.addToCart():
  viewModelScope.launch { repository.addToCart(product) }
       |
       v
CartRepository.addToCart():
  - Kiem tra san pham co ton tai khong (getCartItemById)
  - Co -> updateCartItem | Khong -> insertCartItem
       |
       v
Room Database cap nhat bang cart_items
       |
       v (tu dong)
CartDao.getAllCartItems() Flow phat gia tri moi
       |
       v
CartViewModel.cartItems (Flow) duoc cap nhat
       |
       v (collectAsState)
CartScreen tu dong ve lai voi du lieu moi
```

### Luong tai danh muc (Live Update)

```
App khoi dong -> CategoryViewModel.init -> fetchCategories()
       |
       v
FirebaseRepository.getCategoriesFlow():
  Tao callbackFlow, dang ky addSnapshotListener voi Firestore
       |
       v (Firestore gui du lieu)
addSnapshotListener callback duoc goi
  - Chuyen doi sang List<Category>
  - trySend(categories) -> day vao Flow
       |
       v
CategoryViewModel.collect { categories ->
  _categories.value = categories  (cap nhat StateFlow)
       |
       v (collectAsState)
CategoryScreen / HomeScreen tu dong ve lai voi danh muc moi
```

---

## 7. Nghiep vu chinh

### Nghiep vu 1: Dang ky tai khoan

1. Nguoi dung nhap email, mat khau, xac nhan mat khau
2. Kiem tra phia client: mat khau khop va toi thieu 6 ky tu
3. Neu hop le: goi `authViewModel.signup()`
4. Firebase tao tai khoan moi
5. `authState` chuyen sang `Success`
6. `LaunchedEffect` phat hien `Success`, goi `onSignUpSuccess()`
7. Dieu huong ve `HomeScreen`

### Nghiep vu 2: Tim kiem san pham

1. Nguoi dung go vao `SearchBar`
2. `searchQuery` state duoc cap nhat moi ky tu
3. Nguoi dung nhan tim kiem -> `searchViewModel.searchProducts(query)`
4. `SearchViewModel` goi `repository.searchProducts()`
5. Repository lay tat ca san pham tu Firestore
6. Loc phia client theo ten (`contains`)
7. Ket qua duoc cap nhat vao `_searchResults`
8. `HomeScreen` hien thi `SearchResultsSection` khi `searchQuery` khong rong

### Nghiep vu 3: Xem danh sach san pham theo danh muc

1. Nguoi dung nhan vao `CategoryChip` hoac `CategoryItem`
2. Navigate den `Screen.ProductList.createRoute(categoryId)`
3. `ProductScreen` nhan `categoryId` tu Navigation arguments
4. `LaunchedEffect(categoryId)` goi `productViewModel.fetchProducts(categoryId)`
5. `ProductViewModel` goi `firebaseRepository.getProductsByCategory(categoryId)`
6. Firestore loc san pham theo `categoryId`
7. Ket qua cap nhat vao `_products`
8. `ProductScreen` hien thi danh sach san pham

---

## 8. Cac khai niem quan trong

### Kotlin Coroutines

**Coroutine** la cach xu ly tac vu bat dong bo (mang, database) ma khong can tao thread moi:

```kotlin
// KHONG DUNG: chay tren main thread -> app dong bang
val data = firestore.collection("products").get()  // Loi!

// DUNG DUNG: chay tren coroutine -> khong dong bang UI
viewModelScope.launch {
    val data = firestore.collection("products").get().await()
}
```

- `viewModelScope.launch`: Tao coroutine song cung ViewModel, tu dong huy khi ViewModel bi destroy
- `suspend fun`: Ham phai chay trong coroutine
- `.await()`: Cho coroutine hoan thanh ma khong khoa thread

### Kotlin Flow

**Flow** la luong du lieu bat dong bo, phat nhieu gia tri theo thoi gian:

```kotlin
// Flow chi bat dau phat du lieu khi co nguoi thu thap (collect)
val flow: Flow<List<Product>> = cartDao.getAllCartItems()

// Thu thap Flow trong ViewModel:
viewModelScope.launch {
    flow.collect { products ->
        _products.value = products  // Moi khi DB thay doi, chay doan nay
    }
}

// Thu thap Flow trong Compose:
val products by flow.collectAsState(initial = emptyList())
// products tu dong cap nhat, Compose tu dong ve lai
```

### StateFlow vs MutableStateFlow

```kotlin
// MutableStateFlow: co the sua (chi ViewModel su dung)
private val _data = MutableStateFlow<List<Product>>(emptyList())

// StateFlow: chi doc (expose ra ngoai cho UI)
val data: StateFlow<List<Product>> = _data

// MUC DICH: Dam bao UI khong the tu y thay doi du lieu
// Chi ViewModel moi co quyen cap nhat state
```

### remember va mutableStateOf trong Compose

```kotlin
@Composable
fun MyScreen() {
    // remember: luu gia tri, khong mat khi Compose ve lai (recompose)
    // mutableStateOf: tao gia tri co the thay doi va Compose se theo doi
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it }  // Khi nguoi dung go, text thay doi -> Compose ve lai
    )
}
```

### LaunchedEffect

```kotlin
// LaunchedEffect(key): chay 1 side effect khi key thay doi
// Thuong dung de: fetch data, dieu huong, hien thong bao

LaunchedEffect(productId) {
    // Chay khi productId thay doi (hoac lan dau man hinh xuat hien)
    viewModel.fetchProductDetails(productId)
}

LaunchedEffect(authState) {
    // Chay moi khi authState thay doi
    if (authState is AuthState.Success) {
        onLoginSuccess()  // Dieu huong khi dang nhap thanh cong
    }
}
```

### Scaffold trong Jetpack Compose

```kotlin
Scaffold(
    topBar = { /* Thanh tren */ },
    bottomBar = { /* Thanh duoi */ },
    floatingActionButton = { /* Nut noi */ }
) { paddingValues ->
    // paddingValues: khoang cach de noi dung khong bi che boi topBar/bottomBar
    Column(modifier = Modifier.padding(paddingValues)) {
        // Noi dung chinh
    }
}
```

---

## Ghi chu them

- **Du lieu Firestore**: Can tao 2 collection `categories` va `products` tren Firebase Console
- **Cau truc collection `products`**: `id`, `name`, `price`, `imageUrl`, `categoryId`
- **Cau truc collection `categories`**: `id`, `name`, `iconUrl`
- **google-services.json**: Phai them file nay vao thu muc `app/` (lay tu Firebase Console)
