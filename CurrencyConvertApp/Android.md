# Giới thiệu Android và ứng dụng CurrencyConvertApp

Tài liệu này trình bày tổng quan về Android, cấu trúc thư mục của project, giải thích Views trong Android, và phân tích chi tiết ví dụ code từ app CurrencyConvertApp trong project này.

## 1) Giới thiệu về Android (dành cho người mới)

Android là hệ điều hành di động mã nguồn mở do Google phát triển, chạy trên nhiều thiết bị như điện thoại, máy tính bảng, TV, đồng hồ thông minh. Lập trình Android thường dùng:
- Ngôn ngữ: Kotlin (khuyến nghị), Java.
- Giao diện: XML layout (View system truyền thống) hoặc Jetpack Compose (UI hiện đại).
- Công cụ: Android Studio, Gradle để build và quản lý thư viện.

Kiến trúc Android (đơn giản hóa):
- **Linux Kernel**: quản lý tiến trình, bộ nhớ, mạng, driver thiết bị.
- **HAL (Hardware Abstraction Layer)**: lớp trung gian giúp framework giao tiếp với phần cứng.
- **Android Runtime (ART)**: chạy app, gồm core libraries.
- **Application Framework**: cung cấp các API cho app (Activity Manager, Window Manager, Resource Manager,...).
- **Applications**: các ứng dụng người dùng.

Thành phần chính trong ứng dụng Android:
- **Activity**: màn hình giao diện. Mỗi activity giống như một "trang".
- **Fragment**: phần nhỏ trong giao diện, tái sử dụng.
- **Service**: chạy ngầm (phát nhạc, đồng bộ dữ liệu).
- **BroadcastReceiver**: lắng nghe sự kiện hệ thống.
- **ContentProvider**: chia sẻ dữ liệu giữa các app.

Trong project này, ứng dụng đơn giản dùng 1 Activity (MainActivity) và layout XML.

## 2) Cấu trúc thư mục project

Cây thư mục tổng quan (rút gọn):

```
CurrencyConvertApp/
├─ app/
│  ├─ src/
│  │  ├─ main/
│  │  │  ├─ java/com/didan/android/currencyconvertapp/MainActivity.kt
│  │  │  ├─ res/
│  │  │  │  ├─ layout/activity_main.xml
│  │  │  │  ├─ values/strings.xml
│  │  │  │  ├─ values/colors.xml
│  │  │  │  ├─ values/themes.xml
│  │  │  │  ├─ drawable/background.jpg
│  │  │  │  └─ mipmap-*/ic_launcher.*
│  │  │  └─ AndroidManifest.xml
│  │  ├─ androidTest/...
│  │  └─ test/...
│  ├─ build.gradle.kts
│  └─ proguard-rules.pro
├─ gradle/
├─ build.gradle.kts
├─ settings.gradle.kts
├─ gradle.properties
└─ Android.md
```

Giải thích nhanh:
- **`app/`**: module chính của ứng dụng (nơi chứa code và tài nguyên).
- **`app/src/main/java/`**: code Kotlin/Java. Trong project này là `MainActivity.kt`.
- **`app/src/main/res/`**: tài nguyên (layout, strings, images, colors, themes).
- **`app/src/main/AndroidManifest.xml`**: khai báo activity, quyền, thông tin app.
- **`app/build.gradle.kts`**: cấu hình build riêng cho module app.
- **`build.gradle.kts`** (root): cấu hình chung cho toàn bộ project.
- **`settings.gradle.kts`**: khai báo các module trong project.
- **`gradle/` + `gradlew`**: wrapper để build đồng nhất trên mỗi máy.

## 3) Views trong Android (tổng quan)

### 3.1 View và ViewGroup
- **View**: thành phần UI có thể vẽ lên màn hình (TextView, Button, EditText, ImageView,...).
- **ViewGroup**: là View chứa các View khác (ConstraintLayout, LinearLayout, FrameLayout,...).

Một giao diện Android thường là cây View:
- Root: một ViewGroup.
- Con: các View / ViewGroup con.

### 3.2 XML Layout và Inflate
- Layout được khai báo bằng XML trong `res/layout/`.
- Khi `setContentView(R.layout.activity_main)` được gọi, Android sẽ **inflate** (chuyển XML thành đối tượng View trong bộ nhớ).

### 3.3 Một số thuộc tính cơ bản
- `android:id`: định danh View (dùng để findViewById).
- `android:layout_width`, `android:layout_height`: kích thước (`match_parent`, `wrap_content`, hoặc số dp).
- `android:text`, `android:textSize`, `android:textColor`: văn bản và hiển thị.
- `android:background`: nền cho View hoặc layout.
- `android:hint`: gợi ý trong EditText.
- `android:inputType`: kiểu nhập (number, text, email,...).
- `android:layout_margin...`: khoảng cách bên ngoài.
- Các thuộc tính riêng cho layout (ví dụ ConstraintLayout có `app:layout_constraint...`).

### 3.4 Tài nguyên (Resources)
Thay vì viết trực tiếp chuỗi/màu sắc, Android khuyến khích dùng resource:
- `@string/...` trong `res/values/strings.xml`
- `@color/...` trong `res/values/colors.xml`
- `@drawable/...` trong `res/drawable/`

## 4) Giao diện CurrencyConvertApp (activity_main.xml)

File layout: `app/src/main/res/layout/activity_main.xml`

```xml
<androidx.constraintlayout.widget.ConstraintLayout
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@drawable/background">

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/currency_converter_app"
        android:textColor="@color/white"
        android:textSize="28sp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <EditText
        android:id="@+id/editText"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="@string/enter_usd"
        android:inputType="number"
        android:textColor="@color/white"
        android:textColorHint="@color/white"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/textView" />

    <Button
        android:id="@+id/converterBTN"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/convert_to_euro"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/editText" />

    <TextView
        android:id="@+id/resultText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/_0_euros"
        android:textColor="@color/white"
        android:textSize="64sp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintBottom_toBottomOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

Giải thích nhanh:
- **ConstraintLayout**: ViewGroup cho phép căn lề (constraint) giữa các View và parent.
- **TextView (title)**: hiển thị tiêu đề "Currency Converter App".
- **EditText**: người dùng nhập số USD; `inputType="number"` giới hạn chỉ nhập số.
- **Button**: nút bấm để chuyển đổi.
- **TextView (result)**: hiển thị kết quả, mặc định "0 Euros".
- **Background**: sử dụng `@drawable/background` (ảnh `background.jpg`).

## 5) Code Kotlin (MainActivity)

File: `app/src/main/java/com/didan/android/currencyconvertapp/MainActivity.kt`

```kotlin
class MainActivity : AppCompatActivity() {

    lateinit var titleTextView: TextView
    lateinit var resultTextView: TextView
    lateinit var editText: EditText
    lateinit var converterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        titleTextView = findViewById(R.id.textView)
        resultTextView = findViewById(R.id.resultText)
        editText = findViewById(R.id.editText)
        converterButton = findViewById(R.id.converterBTN)

        converterButton.setOnClickListener {
            val enteredUSD = editText.text.toString()
            val enteredUSDDouble = enteredUSD.toDouble()
            val euros = makeConversion(enteredUSDDouble)
            resultTextView.text = "$euros Euros"
        }
    }

    fun makeConversion(usd: Double): Double {
        return usd * 0.94
    }
}
```

Phân tích:
- **setContentView(R.layout.activity_main)**: nạp layout XML.
- **findViewById**: liên kết View trong XML với biến Kotlin.
- **setOnClickListener**: xử lý sự kiện bấm nút.
- **makeConversion**: tính toán tỉ giá USD -> EUR (tỉ lệ 0.94).

## 6) Luồng hoạt động của ứng dụng

1. App khởi động, `MainActivity` được tạo.
2. Layout `activity_main.xml` được inflate.
3. Người dùng nhập số USD vào `EditText`.
4. Bấm nút "Convert to Euro".
5. App lấy giá trị, chuyển sang Double, nhân 0.94.
6. Hiển thị kết quả trên `resultText`.

## 7) Tài nguyên string (strings.xml)

File: `app/src/main/res/values/strings.xml`

```xml
<resources>
    <string name="app_name">CurrencyConvertApp</string>
    <string name="currency_converter_app">Currency Converter App</string>
    <string name="enter_usd">Enter USD</string>
    <string name="convert_to_euro">Convert to Euro</string>
    <string name="_0_euros">0 Euros</string>
</resources>
```

Lợi ích:
- Dễ dàng dịch đa ngôn ngữ.
- Tránh hardcode chuỗi trong code.

## 8) Gợi ý cho người mới (để hiểu và mở rộng)

- Nếu `EditText` rỗng, `toDouble()` sẽ gây lỗi. Có thể dùng:
  - `enteredUSD.toDoubleOrNull()` và xử lý nếu null.
- Có thể thêm định dạng số:
  - `String.format(Locale.US, "%.2f Euros", euros)`.
- Mở rộng app: cho phép chọn tỉ giá, đổi từ EUR -> USD, hoặc cập nhật tỉ giá từ API.
- Khi UI phức tạp hơn, nên dùng **ViewBinding** hoặc **DataBinding** thay vì `findViewById`.

## 9) Tóm tắt nhanh cho người mới

- **Android** là hệ điều hành di động, app được viết bằng Kotlin/Java, giao diện có thể khai báo XML.
- **Project** có `app/` là module chính, `res/` chứa tài nguyên UI, `AndroidManifest.xml` khai báo app.
- **View** là thành phần giao diện; **ViewGroup** là layout chứa các View con.
- App CurrencyConvertApp có 1 màn hình: nhập USD, bấm nút, hiển thị Euro.

## 10) Hình ảnh minh họa UI (thêm screenshot vào Android.md)

Đặt ảnh minh họa UI vào một thư mục trong project (ví dụ: `docs/ui-screenshot.png`), sau đó nhúng vào file Markdown:

```md
![Giao diện CurrencyConvertApp](docs/ui-screenshot.png)
```

### Cách chụp màn hình trên Emulator (Android Studio)
- Mở App trên Emulator.
- Dùng nút **Take Screenshot** trong Android Studio hoặc thanh công cụ của Emulator.
- Lưu file vào `docs/ui-screenshot.png` để link Markdown hoạt động.

### Cách chụp màn hình trên thiết bị thật (ADB)
1. Bật **Developer options** và **USB debugging** trên điện thoại.
2. Kết nối điện thoại với máy tính, kiểm tra:
   - `adb devices`
3. Chụp màn hình và tải về máy:
   - `adb shell screencap -p /sdcard/currency_app.png`
   - `adb pull /sdcard/currency_app.png docs/ui-screenshot.png`

Sau khi có ảnh, mở `Android.md` và kiểm tra link ảnh có hiển thị đúng không.

## 11) Hướng dẫn build và chạy ứng dụng

### 11.1 Chạy bằng Android Studio (dễ nhất cho người mới)
1. Mở Android Studio -> **Open** -> chọn thư mục project `CurrencyConvertApp`.
2. Chờ Gradle sync hoàn tất.
3. Chọn thiết bị (Emulator hoặc điện thoại thật).
4. Bấm nút **Run** (hình tam giác xanh).

### 11.2 Chạy bằng dòng lệnh (Gradle + ADB)
Điều kiện: Đã cài Android SDK và ADB.

Build APK debug:
```
./gradlew assembleDebug
```

Cài lên thiết bị/emulator:
```
./gradlew installDebug
```

Kiểm tra thiết bị đang kết nối:
```
adb devices
```

Gỡ bỏ app (nếu cần):
```
adb uninstall com.didan.android.currencyconvertapp
```

### 11.3 Mẹo xử lý lỗi thường gặp
- **Gradle sync fail**: kiểm tra lại `local.properties` có đường dẫn SDK chính xác.
- **Không thấy thiết bị**: kiểm tra cáp USB, driver, và bật USB debugging.
- **App crash khi nhập rỗng**: cần xử lý `toDoubleOrNull()` như gợi ý ở mục 8.
