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

## 2) Cấu trúc thư mục Project Android

### 2.1 Tổng quan về cấu trúc Project

Khi tạo một project Android mới trong Android Studio, hệ thống sẽ tự động tạo ra một cấu trúc thư mục tiêu chuẩn. Hiểu rõ cấu trúc này là bước đầu tiên quan trọng để phát triển ứng dụng Android hiệu quả.

**Có hai chế độ xem trong Android Studio:**
- **Android View** (mặc định): Hiển thị cấu trúc đơn giản hóa, dễ làm việc.
- **Project View**: Hiển thị cấu trúc thực sự trên ổ đĩa.

### 2.2 Cấu trúc thư mục tổng quan

```
MyAndroidProject/
├── .gradle/                    # Cache của Gradle (tự động tạo)
├── .idea/                      # Cấu hình Android Studio (tự động tạo)
├── app/                        # Module chính của ứng dụng
│   ├── build/                  # Thư mục output sau khi build (tự động tạo)
│   ├── libs/                   # Thư viện JAR bên ngoài (nếu có)
│   ├── src/                    # Source code và tài nguyên
│   │   ├── main/               # Code và resource chính
│   │   │   ├── java/           # Code Kotlin/Java
│   │   │   ├── res/            # Tài nguyên (layout, images, strings,...)
│   │   │   └── AndroidManifest.xml
│   │   ├── androidTest/        # Instrumented tests (chạy trên thiết bị/emulator)
│   │   └── test/               # Unit tests (chạy trên JVM)
│   ├── build.gradle.kts        # Cấu hình build cho module app
│   └── proguard-rules.pro      # Rules cho ProGuard (code obfuscation)
├── gradle/                     # Gradle wrapper
│   ├── libs.versions.toml      # Version catalog cho dependencies
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── build.gradle.kts            # Cấu hình build cấp project (root)
├── gradle.properties           # Cấu hình properties cho Gradle
├── gradlew                     # Gradle wrapper script (Linux/Mac)
├── gradlew.bat                 # Gradle wrapper script (Windows)
├── local.properties            # Đường dẫn SDK local (không commit lên git)
└── settings.gradle.kts         # Khai báo các module trong project
```

### 2.3 Chi tiết các thành phần quan trọng

#### 2.3.1 Thư mục `app/` (Module chính)

Đây là module chính chứa toàn bộ code và tài nguyên của ứng dụng.

**Cấu trúc chi tiết:**
```
app/
├── src/
│   └── main/
│       ├── java/com/example/myapp/    # Package chứa code Kotlin/Java
│       │   ├── MainActivity.kt        # Activity chính
│       │   ├── ui/                    # Thư mục chứa UI components
│       │   ├── data/                  # Thư mục chứa data layer
│       │   └── utils/                 # Thư mục chứa utilities
│       ├── res/                       # Tài nguyên
│       └── AndroidManifest.xml        # Manifest file
├── build.gradle.kts                   # Build config của module
└── proguard-rules.pro                 # ProGuard rules
```

#### 2.3.2 Thư mục `res/` (Resources)

Thư mục `res/` chứa tất cả tài nguyên không phải code của ứng dụng:

```
res/
├── drawable/               # Hình ảnh, shapes, selectors (không phụ thuộc density)
├── drawable-hdpi/          # Hình ảnh cho high-density screens
├── drawable-mdpi/          # Hình ảnh cho medium-density screens
├── drawable-xhdpi/         # Hình ảnh cho extra-high-density screens
├── drawable-xxhdpi/        # Hình ảnh cho extra-extra-high-density screens
├── drawable-xxxhdpi/       # Hình ảnh cho extra-extra-extra-high-density screens
├── layout/                 # Layout XML cho giao diện
├── layout-land/            # Layout cho chế độ landscape (ngang)
├── layout-sw600dp/         # Layout cho tablet (smallest width >= 600dp)
├── mipmap-hdpi/            # App icon cho high-density
├── mipmap-mdpi/            # App icon cho medium-density
├── mipmap-xhdpi/           # App icon cho extra-high-density
├── mipmap-xxhdpi/          # App icon cho extra-extra-high-density
├── mipmap-xxxhdpi/         # App icon cho extra-extra-extra-high-density
├── mipmap-anydpi-v26/      # Adaptive icon (Android 8.0+)
├── values/                 # Giá trị (strings, colors, dimensions, styles)
│   ├── colors.xml          # Định nghĩa màu sắc
│   ├── strings.xml         # Định nghĩa chuỗi văn bản
│   ├── dimens.xml          # Định nghĩa kích thước
│   ├── themes.xml          # Định nghĩa themes
│   └── styles.xml          # Định nghĩa styles
├── values-night/           # Giá trị cho Dark mode
├── values-vi/              # Chuỗi tiếng Việt (localization)
├── values-en/              # Chuỗi tiếng Anh
├── xml/                    # Các file XML khác (backup rules, network config,...)
├── raw/                    # File raw (audio, video, json,...)
├── font/                   # Custom fonts
├── anim/                   # Animation XML
├── animator/               # Property animation XML
├── menu/                   # Menu XML
└── navigation/             # Navigation graph XML
```

**Quy ước đặt tên file trong `res/`:**
- Chỉ dùng chữ thường, số và dấu gạch dưới.
- Không bắt đầu bằng số.
- Ví dụ: `activity_main.xml`, `ic_launcher.png`, `background_gradient.xml`

#### 2.3.3 File `AndroidManifest.xml`

File manifest khai báo thông tin thiết yếu về ứng dụng cho hệ thống Android:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <!-- Khai báo quyền ứng dụng cần -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.CAMERA" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.MyApp">

        <!-- Khai báo Activity -->
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:label="@string/app_name">
            <!-- Intent filter cho launcher -->
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- Khai báo các Activity khác -->
        <activity android:name=".SecondActivity" />

        <!-- Khai báo Service -->
        <service android:name=".MyService" />

        <!-- Khai báo BroadcastReceiver -->
        <receiver android:name=".MyReceiver" />

        <!-- Khai báo ContentProvider -->
        <provider
            android:name=".MyProvider"
            android:authorities="com.example.myapp.provider" />

    </application>
</manifest>
```

**Các thành phần chính trong Manifest:**

| Thành phần | Mô tả |
|------------|-------|
| `<manifest>` | Root element, chứa package name |
| `<uses-permission>` | Khai báo quyền cần thiết |
| `<uses-feature>` | Khai báo tính năng hardware cần thiết |
| `<application>` | Cấu hình ứng dụng (icon, theme, backup,...) |
| `<activity>` | Khai báo Activity |
| `<service>` | Khai báo Service |
| `<receiver>` | Khai báo BroadcastReceiver |
| `<provider>` | Khai báo ContentProvider |
| `<intent-filter>` | Khai báo Intent mà component xử lý |

#### 2.3.4 File `build.gradle.kts` (Module level - app)

File cấu hình build cho module app:

```kotlin
plugins {
    alias(libs.plugins.android.application)  // Plugin Android application
    alias(libs.plugins.kotlin.android)       // Plugin Kotlin
}

android {
    namespace = "com.example.myapp"          // Package name
    compileSdk = 34                          // SDK version để compile

    defaultConfig {
        applicationId = "com.example.myapp"  // ID duy nhất của app trên Play Store
        minSdk = 24                          // SDK tối thiểu để chạy app
        targetSdk = 34                       // SDK mục tiêu
        versionCode = 1                      // Version code (số nguyên, tăng mỗi lần release)
        versionName = "1.0"                  // Version name (hiển thị cho user)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false          // Bật/tắt ProGuard
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true              // Cho phép debug
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true                   // Bật ViewBinding
        // dataBinding = true                // Bật DataBinding (nếu cần)
        // compose = true                    // Bật Jetpack Compose (nếu cần)
    }
}

dependencies {
    // AndroidX Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
```

#### 2.3.5 File `build.gradle.kts` (Project level - root)

File cấu hình build cấp project:

```kotlin
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}
```

#### 2.3.6 File `settings.gradle.kts`

Khai báo các module trong project:

```kotlin
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MyApp"
include(":app")                    // Khai báo module app
// include(":library")             // Thêm module khác nếu có
```

#### 2.3.7 File `gradle/libs.versions.toml` (Version Catalog)

Quản lý version của dependencies tập trung:

```toml
[versions]
agp = "8.2.0"
kotlin = "1.9.0"
coreKtx = "1.12.0"
junit = "4.13.2"
junitVersion = "1.1.5"
espressoCore = "3.5.1"
appcompat = "1.6.1"
material = "1.11.0"
activity = "1.8.0"
constraintlayout = "2.1.4"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
androidx-appcompat = { group = "androidx.appcompat", name = "appcompat", version.ref = "appcompat" }
material = { group = "com.google.android.material", name = "material", version.ref = "material" }
androidx-activity = { group = "androidx.activity", name = "activity", version.ref = "activity" }
androidx-constraintlayout = { group = "androidx.constraintlayout", name = "constraintlayout", version.ref = "constraintlayout" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
```

### 2.4 Các file quan trọng khác

| File | Mô tả |
|------|-------|
| `gradle.properties` | Cấu hình JVM, AndroidX, Kotlin code style |
| `local.properties` | Đường dẫn Android SDK (không commit lên git) |
| `gradlew` / `gradlew.bat` | Script để chạy Gradle mà không cần cài Gradle |
| `proguard-rules.pro` | Rules cho code shrinking và obfuscation |
| `.gitignore` | Các file/thư mục không commit lên git |

### 2.5 Quy trình Build Android

```
Source Code + Resources
        ↓
    Compile (javac/kotlinc)
        ↓
    .class files
        ↓
    DEX (Dalvik Executable)
        ↓
    APK/AAB Packaging
        ↓
    Sign (debug/release key)
        ↓
    Final APK/AAB
```

**Các loại build:**
- **Debug build**: Để phát triển và test, tự động sign bằng debug key.
- **Release build**: Để publish, cần sign bằng release key.

### 2.6 Cấu trúc thư mục Project CurrencyConvertApp

Áp dụng vào project hiện tại:

```
CurrencyConvertApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/didan/android/currencyconvertapp/
│   │   │   │   └── MainActivity.kt      # Activity chính
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   └── activity_main.xml # Layout cho MainActivity
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml       # Chuỗi văn bản
│   │   │   │   │   ├── colors.xml        # Màu sắc
│   │   │   │   │   └── themes.xml        # Theme ứng dụng
│   │   │   │   ├── drawable/
│   │   │   │   │   └── background.jpg    # Ảnh nền
│   │   │   │   └── mipmap-*/             # App icons
│   │   │   └── AndroidManifest.xml
│   │   ├── androidTest/                  # Instrumented tests
│   │   └── test/                         # Unit tests
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── local.properties
└── Android.md                            # Tài liệu này
```

## 3) Views trong Android

### 3.1 Views và ViewGroups

#### 3.1.1 View là gì?

**View** là thành phần cơ bản nhất của giao diện người dùng (UI) trong Android. Mọi thứ bạn nhìn thấy trên màn hình đều là View hoặc được tạo từ View.

**Đặc điểm của View:**
- Chiếm một vùng hình chữ nhật trên màn hình.
- Có thể vẽ nội dung (text, image, shape,...).
- Có thể xử lý sự kiện (click, touch, scroll,...).
- Là lớp cơ sở cho tất cả UI components.

**Các View phổ biến:**

| View | Mô tả | Ví dụ sử dụng |
|------|-------|---------------|
| `TextView` | Hiển thị văn bản | Label, tiêu đề, nội dung |
| `EditText` | Nhập văn bản | Form input, search box |
| `Button` | Nút bấm | Submit, cancel, action |
| `ImageView` | Hiển thị hình ảnh | Avatar, icon, banner |
| `ImageButton` | Nút bấm có hình ảnh | Icon button |
| `CheckBox` | Hộp kiểm | Chọn nhiều options |
| `RadioButton` | Nút radio | Chọn một trong nhiều |
| `Switch` | Công tắc bật/tắt | Settings on/off |
| `ProgressBar` | Thanh tiến trình | Loading indicator |
| `SeekBar` | Thanh kéo | Volume, brightness |
| `Spinner` | Dropdown menu | Chọn từ danh sách |
| `WebView` | Hiển thị web | Trang web trong app |
| `RecyclerView` | Danh sách cuộn | List, grid của items |
| `ScrollView` | Cuộn nội dung | Nội dung dài hơn màn hình |

#### 3.1.2 ViewGroup là gì?

**ViewGroup** là View đặc biệt có khả năng chứa các View con khác. ViewGroup định nghĩa cách các View con được sắp xếp và hiển thị.

**Đặc điểm của ViewGroup:**
- Kế thừa từ lớp View.
- Có thể chứa nhiều View con (children).
- Quản lý layout (vị trí, kích thước) của các View con.
- Có thể lồng nhau (ViewGroup chứa ViewGroup khác).

**Các ViewGroup phổ biến:**

| ViewGroup | Mô tả |
|-----------|-------|
| `LinearLayout` | Sắp xếp View theo hàng ngang hoặc dọc |
| `RelativeLayout` | Sắp xếp View tương đối với nhau |
| `ConstraintLayout` | Sắp xếp View theo ràng buộc (constraint) |
| `FrameLayout` | Xếp chồng View lên nhau |
| `CoordinatorLayout` | Hỗ trợ animation và behavior |
| `ScrollView` | Cuộn một View con theo chiều dọc |
| `HorizontalScrollView` | Cuộn theo chiều ngang |
| `NestedScrollView` | ScrollView hỗ trợ nested scrolling |
| `RecyclerView` | Danh sách tối ưu hiệu năng |
| `CardView` | Container với shadow và corner radius |

#### 3.1.3 View Hierarchy (Cây phân cấp View)

Giao diện Android được tổ chức theo cấu trúc cây:

```
ViewGroup (Root - thường là Layout)
├── View (TextView)
├── View (Button)
├── ViewGroup (LinearLayout)
│   ├── View (ImageView)
│   └── View (TextView)
└── View (EditText)
```

**Ví dụ thực tế:**
```xml
<ConstraintLayout>                    <!-- Root ViewGroup -->
    <TextView />                      <!-- View con 1 -->
    <EditText />                      <!-- View con 2 -->
    <LinearLayout>                    <!-- ViewGroup con -->
        <Button />                    <!-- View cháu 1 -->
        <Button />                    <!-- View cháu 2 -->
    </LinearLayout>
    <ImageView />                     <!-- View con 3 -->
</ConstraintLayout>
```

**Lưu ý về hiệu năng:**
- Cây View càng sâu (nhiều lồng nhau) thì hiệu năng càng giảm.
- Android phải đo lường (measure) và vẽ (draw) từng View.
- Nên giữ hierarchy phẳng, sử dụng ConstraintLayout thay vì lồng nhiều Layout.

### 3.2 Chỉnh sửa UI trong Android Studio (UI Editing in Android Studio)

Android Studio cung cấp nhiều công cụ mạnh mẽ để thiết kế giao diện.

#### 3.2.1 Mở Layout Editor

1. Mở file layout XML trong `res/layout/` (ví dụ: `activity_main.xml`).
2. Android Studio tự động hiển thị Layout Editor.
3. Có thể chuyển đổi giữa các chế độ xem bằng các tab ở góc trên.

#### 3.2.2 Các thành phần của Layout Editor

```
┌─────────────────────────────────────────────────────────────────┐
│  [Palette]  │        [Design/Blueprint View]        │[Attributes]│
│             │                                       │            │
│  Common     │   ┌─────────────────────────────┐    │ id         │
│  - TextView │   │                             │    │ layout_w   │
│  - Button   │   │      Preview của           │    │ layout_h   │
│  - ImageView│   │      giao diện             │    │ text       │
│  Text       │   │                             │    │ textSize   │
│  - EditText │   │                             │    │ ...        │
│  Buttons    │   └─────────────────────────────┘    │            │
│  Widgets    │                                       │            │
│  Layouts    │   [Component Tree]                    │            │
│  Containers │   └─ ConstraintLayout                │            │
│  Helpers    │      ├─ TextView                     │            │
│             │      ├─ EditText                     │            │
│             │      └─ Button                       │            │
└─────────────────────────────────────────────────────────────────┘
```

**Các phần chính:**

| Thành phần | Mô tả |
|------------|-------|
| **Palette** | Danh sách các View/ViewGroup có thể kéo thả vào layout |
| **Design View** | Xem trước giao diện như khi chạy app |
| **Blueprint View** | Xem cấu trúc và constraint (không có styling) |
| **Component Tree** | Cây phân cấp các View trong layout |
| **Attributes** | Chỉnh sửa thuộc tính của View được chọn |
| **Toolbar** | Các công cụ: device preview, orientation, API level,... |

#### 3.2.3 Cách thêm View vào Layout

**Cách 1: Kéo thả từ Palette**
1. Chọn View trong Palette (ví dụ: Button).
2. Kéo vào Design View hoặc Component Tree.
3. Thả vào vị trí mong muốn.

**Cách 2: Viết trực tiếp XML**
1. Chuyển sang tab "Code" hoặc "Split".
2. Thêm tag XML cho View mới.
3. Android Studio sẽ tự động cập nhật preview.

**Cách 3: Sử dụng Code Completion**
1. Trong XML, gõ `<` và tên View.
2. Android Studio gợi ý các View phù hợp.
3. Chọn và nhấn Enter để thêm.

#### 3.2.4 Chỉnh sửa thuộc tính View

**Qua Attributes Panel:**
1. Chọn View trong Design View hoặc Component Tree.
2. Tìm thuộc tính cần sửa trong Attributes panel.
3. Nhập giá trị mới.

**Qua XML trực tiếp:**
```xml
<TextView
    android:id="@+id/myTextView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Hello World"
    android:textSize="18sp"
    android:textColor="#000000" />
```

### 3.3 Các chế độ của Layout Editor (Layout Editor Modes)

Android Studio cung cấp 3 chế độ làm việc với layout:

#### 3.3.1 Code Mode

Chỉ hiển thị XML code, không có preview.

**Khi nào sử dụng:**
- Chỉnh sửa nhanh XML.
- Máy tính cấu hình thấp, preview chậm.
- Cần copy/paste code.

**Shortcut:** Click tab "Code" hoặc nhấn `Ctrl+Shift+Right Arrow`

#### 3.3.2 Split Mode (Chế độ chia đôi)

Hiển thị cả XML code và preview cạnh nhau.

**Khi nào sử dụng:**
- Vừa viết code vừa xem kết quả.
- Debug layout issues.
- Học và hiểu XML layout.

**Shortcut:** Click tab "Split"

#### 3.3.3 Design Mode

Chỉ hiển thị visual editor, không có XML.

**Khi nào sử dụng:**
- Thiết kế nhanh bằng drag & drop.
- Tạo prototype.
- Người mới bắt đầu.

**Shortcut:** Click tab "Design" hoặc nhấn `Ctrl+Shift+Left Arrow`

#### 3.3.4 Design View vs Blueprint View

Trong Design Mode và Split Mode, có thể chọn hiển thị:

| Chế độ | Mô tả | Icon |
|--------|-------|------|
| **Design** | Hiển thị giao diện như khi chạy app | Hình điện thoại |
| **Blueprint** | Hiển thị cấu trúc, constraint, không có styling | Hình blueprint |
| **Design + Blueprint** | Hiển thị cả hai cạnh nhau | Cả hai icon |

**Blueprint hữu ích khi:**
- Xem và chỉnh sửa constraint trong ConstraintLayout.
- Debug vấn đề về layout.
- Làm việc với View có background trong suốt.

#### 3.3.5 Toolbar của Layout Editor

```
[Device] [Orientation] [API] [Theme] [Language] | [Eye] [Constraint] [Guideline] | [Refresh]
```

| Công cụ | Mô tả |
|---------|-------|
| **Device** | Chọn thiết bị preview (Pixel, tablet, custom,...) |
| **Orientation** | Xoay portrait/landscape |
| **API Level** | Chọn Android version để preview |
| **Theme** | Chọn theme (Light, Dark, custom) |
| **Language** | Chọn ngôn ngữ để preview localization |
| **Eye icon** | Hiện/ẩn các thành phần UI (system bars, notch,...) |
| **Constraint** | Bật/tắt hiển thị constraint |
| **Guideline** | Thêm guideline cho ConstraintLayout |
| **Refresh** | Làm mới preview |

### 3.4 Các thuộc tính của View (Views' Attributes)

#### 3.4.1 Thuộc tính cơ bản (Common Attributes)

Mọi View đều có các thuộc tính sau:

**ID - Định danh View:**
```xml
android:id="@+id/myView"
```
- `@+id/` tạo ID mới trong file `R.java`.
- `@id/` tham chiếu đến ID đã tồn tại.
- ID dùng để truy cập View từ code và tạo constraint.

**Kích thước (Width & Height):**
```xml
android:layout_width="..."
android:layout_height="..."
```

| Giá trị | Mô tả |
|---------|-------|
| `match_parent` | Chiếm toàn bộ không gian của parent |
| `wrap_content` | Vừa đủ với nội dung |
| `100dp` | Kích thước cố định (dp = density-independent pixels) |
| `0dp` | Trong ConstraintLayout, kích thước theo constraint |

**Visibility - Hiển thị:**
```xml
android:visibility="visible"    <!-- Hiển thị (mặc định) -->
android:visibility="invisible"  <!-- Ẩn nhưng vẫn chiếm không gian -->
android:visibility="gone"       <!-- Ẩn và không chiếm không gian -->
```

**Background - Nền:**
```xml
android:background="#FF5722"              <!-- Màu hex -->
android:background="@color/primary"       <!-- Màu từ resource -->
android:background="@drawable/bg_shape"   <!-- Drawable resource -->
```

**Padding - Khoảng cách bên trong:**
```xml
android:padding="16dp"              <!-- Tất cả các cạnh -->
android:paddingStart="8dp"          <!-- Bên trái (LTR) / phải (RTL) -->
android:paddingEnd="8dp"            <!-- Bên phải (LTR) / trái (RTL) -->
android:paddingTop="8dp"            <!-- Phía trên -->
android:paddingBottom="8dp"         <!-- Phía dưới -->
android:paddingHorizontal="16dp"    <!-- Trái và phải -->
android:paddingVertical="8dp"       <!-- Trên và dưới -->
```

**Margin - Khoảng cách bên ngoài:**
```xml
android:layout_margin="16dp"
android:layout_marginStart="8dp"
android:layout_marginEnd="8dp"
android:layout_marginTop="8dp"
android:layout_marginBottom="8dp"
android:layout_marginHorizontal="16dp"
android:layout_marginVertical="8dp"
```

**Minh họa Padding vs Margin:**
```
┌──────────────────────────────────┐
│          MARGIN                  │
│   ┌──────────────────────────┐   │
│   │      BORDER              │   │
│   │   ┌──────────────────┐   │   │
│   │   │    PADDING       │   │   │
│   │   │   ┌──────────┐   │   │   │
│   │   │   │ CONTENT  │   │   │   │
│   │   │   └──────────┘   │   │   │
│   │   └──────────────────┘   │   │
│   └──────────────────────────┘   │
└──────────────────────────────────┘
```

#### 3.4.2 Thuộc tính cho TextView

```xml
<TextView
    android:id="@+id/myTextView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Hello World"
    android:textSize="16sp"
    android:textColor="#333333"
    android:textStyle="bold|italic"
    android:textAlignment="center"
    android:fontFamily="sans-serif-medium"
    android:lineSpacingExtra="4dp"
    android:maxLines="2"
    android:ellipsize="end"
    android:drawableStart="@drawable/ic_icon"
    android:drawablePadding="8dp" />
```

| Thuộc tính | Mô tả |
|------------|-------|
| `text` | Nội dung văn bản |
| `textSize` | Cỡ chữ (sp = scale-independent pixels) |
| `textColor` | Màu chữ |
| `textStyle` | Kiểu chữ: normal, bold, italic |
| `textAlignment` | Căn lề: center, textStart, textEnd |
| `fontFamily` | Font chữ |
| `lineSpacingExtra` | Khoảng cách giữa các dòng |
| `maxLines` | Số dòng tối đa |
| `ellipsize` | Cách cắt text khi quá dài: end, start, middle, marquee |
| `drawableStart/End/Top/Bottom` | Icon kèm theo text |
| `drawablePadding` | Khoảng cách giữa icon và text |

#### 3.4.3 Đơn vị đo lường trong Android

| Đơn vị | Tên đầy đủ | Mô tả | Sử dụng cho |
|--------|------------|-------|-------------|
| `dp` | Density-independent Pixels | Tự động scale theo mật độ màn hình | Kích thước, margin, padding |
| `sp` | Scale-independent Pixels | Như dp nhưng còn scale theo cài đặt font size của user | Text size |
| `px` | Pixels | Pixel thực tế (không khuyến khích) | Hiếm khi dùng |
| `pt` | Points | 1/72 inch | Không khuyến khích |
| `mm` | Millimeters | Millimet thực tế | Không khuyến khích |
| `in` | Inches | Inch thực tế | Không khuyến khích |

**Quy tắc sử dụng:**
- Luôn dùng `dp` cho kích thước layout.
- Luôn dùng `sp` cho text size.
- Tránh dùng `px` vì không scale theo màn hình.

#### 3.4.4 Tham chiếu Resources

```xml
<!-- Tham chiếu string -->
android:text="@string/hello_world"

<!-- Tham chiếu color -->
android:textColor="@color/black"

<!-- Tham chiếu dimension -->
android:textSize="@dimen/text_normal"

<!-- Tham chiếu drawable -->
android:background="@drawable/background"

<!-- Tham chiếu style -->
style="@style/MyButtonStyle"

<!-- Tham chiếu từ theme hiện tại -->
android:textColor="?attr/colorOnPrimary"
android:background="?attr/selectableItemBackground"
```

### 3.5 Khai báo và Khởi tạo Views (Declaring & Initializing Views)

#### 3.5.1 Khai báo View trong XML

Mỗi View cần được khai báo trong file layout XML:

```xml
<!-- res/layout/activity_main.xml -->
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:id="@+id/titleTextView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Welcome"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

    <EditText
        android:id="@+id/nameEditText"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Enter your name"
        app:layout_constraintTop_toBottomOf="@id/titleTextView"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_margin="16dp" />

    <Button
        android:id="@+id/submitButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Submit"
        app:layout_constraintTop_toBottomOf="@id/nameEditText"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

#### 3.5.2 Khởi tạo View trong Code - Cách 1: findViewById

Đây là cách truyền thống để truy cập View từ code:

```kotlin
class MainActivity : AppCompatActivity() {

    // Khai báo biến (có thể dùng lateinit hoặc nullable)
    private lateinit var titleTextView: TextView
    private lateinit var nameEditText: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // Inflate layout

        // Khởi tạo View bằng findViewById
        titleTextView = findViewById(R.id.titleTextView)
        nameEditText = findViewById(R.id.nameEditText)
        submitButton = findViewById(R.id.submitButton)

        // Sử dụng View
        titleTextView.text = "Hello Android!"
        val name = nameEditText.text.toString()
    }
}
```

**Giải thích:**
- `setContentView(R.layout.activity_main)`: Nạp (inflate) file XML thành View objects.
- `findViewById<Type>(R.id.viewId)`: Tìm View theo ID và ép kiểu.
- `R.id.viewId`: ID được tự động generate trong class R.

**Nhược điểm của findViewById:**
- Không type-safe (có thể cast sai kiểu).
- Không null-safe (có thể return null nếu ID sai).
- Phải viết nhiều code boilerplate.
- Dễ gây crash runtime nếu ID không tồn tại.

#### 3.5.3 Khởi tạo View trong Code - Cách 2: View Binding (Khuyến nghị)

View Binding là cách hiện đại và an toàn hơn để truy cập View.

**Bước 1: Bật View Binding trong build.gradle.kts**
```kotlin
android {
    // ...existing code...
    buildFeatures {
        viewBinding = true
    }
}
```

**Bước 2: Sử dụng trong Activity**
```kotlin
class MainActivity : AppCompatActivity() {

    // Khai báo binding object
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Khởi tạo binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Truy cập View trực tiếp qua binding
        binding.titleTextView.text = "Hello Android!"
        binding.nameEditText.hint = "Enter your name"
        binding.submitButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            binding.titleTextView.text = "Hello, $name!"
        }
    }
}
```

**Ưu điểm của View Binding:**
- **Type-safe**: Tự động generate đúng kiểu cho mỗi View.
- **Null-safe**: Không có View nào là null (trừ khi dùng `<include>`).
- **Compile-time check**: Lỗi được phát hiện khi compile, không phải runtime.
- **Ít code hơn**: Không cần khai báo biến riêng cho mỗi View.

**Quy tắc đặt tên:**
- Layout file: `activity_main.xml` → Binding class: `ActivityMainBinding`
- Layout file: `fragment_home.xml` → Binding class: `FragmentHomeBinding`
- View ID: `titleTextView` → Property: `binding.titleTextView`
- View ID: `submit_button` → Property: `binding.submitButton` (camelCase)

#### 3.5.4 So sánh các cách khởi tạo View

| Tiêu chí | findViewById | View Binding | Data Binding |
|----------|--------------|--------------|--------------|
| Type-safe | Không | Có | Có |
| Null-safe | Không | Có | Có |
| Setup | Không cần | Bật trong gradle | Bật trong gradle |
| Hiệu năng | Chậm nhất | Nhanh | Trung bình |
| Code boilerplate | Nhiều | Ít | Ít nhất |
| Two-way binding | Không | Không | Có |
| Khuyến nghị | Không | Có | Tùy trường hợp |

### 3.6 EditText - Nhập văn bản

#### 3.6.1 Giới thiệu EditText

**EditText** là View cho phép người dùng nhập và chỉnh sửa văn bản. EditText kế thừa từ TextView nên có tất cả thuộc tính của TextView.

#### 3.6.2 Các thuộc tính quan trọng của EditText

```xml
<EditText
    android:id="@+id/emailEditText"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Enter your email"
    android:inputType="textEmailAddress"
    android:maxLength="50"
    android:maxLines="1"
    android:imeOptions="actionNext"
    android:drawableStart="@drawable/ic_email"
    android:drawablePadding="8dp" />
```

| Thuộc tính | Mô tả |
|------------|-------|
| `hint` | Văn bản gợi ý khi EditText trống |
| `inputType` | Loại bàn phím và định dạng input |
| `maxLength` | Số ký tự tối đa |
| `maxLines` | Số dòng tối đa |
| `imeOptions` | Nút action trên bàn phím (Next, Done, Search,...) |
| `textColorHint` | Màu của hint text |
| `autofillHints` | Gợi ý cho autofill |

#### 3.6.3 Các loại inputType

`inputType` quyết định loại bàn phím và cách xử lý input:

```xml
<!-- Text thường -->
android:inputType="text"

<!-- Password (ẩn ký tự) -->
android:inputType="textPassword"

<!-- Password hiển thị -->
android:inputType="textVisiblePassword"

<!-- Email -->
android:inputType="textEmailAddress"

<!-- Số điện thoại -->
android:inputType="phone"

<!-- Số -->
android:inputType="number"

<!-- Số có dấu thập phân -->
android:inputType="numberDecimal"

<!-- Số có dấu âm dương -->
android:inputType="numberSigned"

<!-- Số thập phân có dấu -->
android:inputType="numberDecimal|numberSigned"

<!-- Nhiều dòng -->
android:inputType="textMultiLine"

<!-- Tên người (viết hoa chữ cái đầu mỗi từ) -->
android:inputType="textPersonName"

<!-- Viết hoa chữ cái đầu câu -->
android:inputType="textCapSentences"

<!-- Viết hoa tất cả -->
android:inputType="textCapCharacters"

<!-- URI / URL -->
android:inputType="textUri"

<!-- Ngày tháng -->
android:inputType="date"

<!-- Thời gian -->
android:inputType="time"
```

#### 3.6.4 Các loại imeOptions

`imeOptions` quyết định nút action trên bàn phím:

```xml
android:imeOptions="actionDone"      <!-- Nút Done, đóng bàn phím -->
android:imeOptions="actionNext"      <!-- Nút Next, chuyển sang field tiếp -->
android:imeOptions="actionGo"        <!-- Nút Go -->
android:imeOptions="actionSearch"    <!-- Nút Search -->
android:imeOptions="actionSend"      <!-- Nút Send -->
android:imeOptions="actionNone"      <!-- Không có nút action -->
```

#### 3.6.5 Làm việc với EditText trong Code

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lấy text từ EditText
        val email = binding.emailEditText.text.toString()

        // Set text cho EditText
        binding.emailEditText.setText("example@email.com")

        // Xóa text
        binding.emailEditText.text.clear()
        // hoặc
        binding.emailEditText.setText("")

        // Lắng nghe thay đổi text
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Trước khi text thay đổi
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Khi text đang thay đổi
                Log.d("EditText", "Text: $s")
            }

            override fun afterTextChanged(s: Editable?) {
                // Sau khi text thay đổi
                validateEmail(s.toString())
            }
        })

        // Lắng nghe khi nhấn nút action trên bàn phím
        binding.emailEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Xử lý khi nhấn Done
                hideKeyboard()
                true
            } else {
                false
            }
        }

        // Focus vào EditText
        binding.emailEditText.requestFocus()

        // Hiển thị bàn phím
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.emailEditText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.emailEditText.windowToken, 0)
    }

    private fun validateEmail(email: String) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEditText.error = "Invalid email format"
        }
    }
}
```

#### 3.6.6 TextInputLayout (Material Design)

Để có EditText đẹp hơn với animation và error handling tốt hơn, sử dụng `TextInputLayout`:

```xml
<com.google.android.material.textfield.TextInputLayout
    android:id="@+id/emailInputLayout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Email"
    app:errorEnabled="true"
    app:startIconDrawable="@drawable/ic_email"
    style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox">

    <com.google.android.material.textfield.TextInputEditText
        android:id="@+id/emailEditText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="textEmailAddress" />

</com.google.android.material.textfield.TextInputLayout>
```

```kotlin
// Hiển thị error
binding.emailInputLayout.error = "Please enter a valid email"

// Xóa error
binding.emailInputLayout.error = null
binding.emailInputLayout.isErrorEnabled = false
```

### 3.7 Buttons và Click Listeners

#### 3.7.1 Các loại Button

**Button thường:**
```xml
<Button
    android:id="@+id/normalButton"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Normal Button" />
```

**MaterialButton (khuyến nghị):**
```xml
<com.google.android.material.button.MaterialButton
    android:id="@+id/materialButton"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Material Button"
    app:icon="@drawable/ic_send"
    app:iconGravity="textStart" />
```

**Các style của MaterialButton:**
```xml
<!-- Filled Button (mặc định) -->
<Button
    style="@style/Widget.MaterialComponents.Button" />

<!-- Outlined Button -->
<Button
    style="@style/Widget.MaterialComponents.Button.OutlinedButton" />

<!-- Text Button -->
<Button
    style="@style/Widget.MaterialComponents.Button.TextButton" />

<!-- Icon Button -->
<Button
    style="@style/Widget.MaterialComponents.Button.Icon" />
```

**ImageButton:**
```xml
<ImageButton
    android:id="@+id/imageButton"
    android:layout_width="48dp"
    android:layout_height="48dp"
    android:src="@drawable/ic_favorite"
    android:background="?attr/selectableItemBackgroundBorderless"
    android:contentDescription="Favorite" />
```

**FloatingActionButton (FAB):**
```xml
<com.google.android.material.floatingactionbutton.FloatingActionButton
    android:id="@+id/fab"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:src="@drawable/ic_add"
    android:contentDescription="Add item" />
```

#### 3.7.2 Thuộc tính Button

```xml
<Button
    android:id="@+id/myButton"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Click Me"
    android:textColor="@color/white"
    android:textSize="16sp"
    android:textAllCaps="false"
    android:enabled="true"
    android:backgroundTint="@color/primary"
    android:stateListAnimator="@null"
    app:cornerRadius="8dp"
    app:icon="@drawable/ic_send"
    app:iconGravity="textStart"
    app:iconPadding="8dp"
    app:rippleColor="@color/ripple" />
```

| Thuộc tính | Mô tả |
|------------|-------|
| `text` | Văn bản trên button |
| `textAllCaps` | Tự động viết hoa (mặc định true) |
| `enabled` | Bật/tắt button |
| `backgroundTint` | Màu nền |
| `cornerRadius` | Bo góc |
| `icon` | Icon trên button |
| `iconGravity` | Vị trí icon (textStart, textEnd, start, end, top) |
| `rippleColor` | Màu hiệu ứng ripple khi click |

#### 3.7.3 Click Listeners - Xử lý sự kiện click

**Cách 1: setOnClickListener với Lambda (Khuyến nghị)**
```kotlin
binding.myButton.setOnClickListener {
    // Code xử lý khi click
    Toast.makeText(this, "Button clicked!", Toast.LENGTH_SHORT).show()
}
```

**Cách 2: setOnClickListener với Object**
```kotlin
binding.myButton.setOnClickListener(object : View.OnClickListener {
    override fun onClick(v: View?) {
        // Code xử lý
    }
})
```

**Cách 3: Activity implement OnClickListener**
```kotlin
class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Gán listener
        binding.button1.setOnClickListener(this)
        binding.button2.setOnClickListener(this)
        binding.button3.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button1 -> handleButton1Click()
            R.id.button2 -> handleButton2Click()
            R.id.button3 -> handleButton3Click()
        }
    }
}
```

**Cách 4: Khai báo trong XML (Không khuyến nghị)**
```xml
<Button
    android:onClick="onButtonClick" />
```

```kotlin
// Trong Activity - phương thức phải public
fun onButtonClick(view: View) {
    // Code xử lý
}
```

#### 3.7.4 Các loại Click Listener khác

**Long Click Listener (Nhấn giữ):**
```kotlin
binding.myButton.setOnLongClickListener {
    Toast.makeText(this, "Long pressed!", Toast.LENGTH_SHORT).show()
    true  // return true để consume event
}
```

**Touch Listener (Chạm - chi tiết hơn):**
```kotlin
binding.myButton.setOnTouchListener { v, event ->
    when (event.action) {
        MotionEvent.ACTION_DOWN -> {
            // Ngón tay chạm vào
        }
        MotionEvent.ACTION_UP -> {
            // Ngón tay nhấc lên
        }
        MotionEvent.ACTION_MOVE -> {
            // Ngón tay di chuyển
        }
    }
    false  // return false để tiếp tục propagate event
}
```

#### 3.7.5 Ví dụ thực tế: Form đăng nhập

```kotlin
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Nút đăng nhập
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString()

            if (validateInput(email, password)) {
                performLogin(email, password)
            }
        }

        // Nút đăng ký
        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Link quên mật khẩu
        binding.forgotPasswordText.setOnClickListener {
            showForgotPasswordDialog()
        }

        // Disable nút khi đang loading
        binding.loginButton.isEnabled = true
    }

    private fun validateInput(email: String, password: String): Boolean {
        var isValid = true

        if (email.isEmpty()) {
            binding.emailInputLayout.error = "Email is required"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInputLayout.error = "Invalid email format"
            isValid = false
        } else {
            binding.emailInputLayout.error = null
        }

        if (password.isEmpty()) {
            binding.passwordInputLayout.error = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            binding.passwordInputLayout.error = "Password must be at least 6 characters"
            isValid = false
        } else {
            binding.passwordInputLayout.error = null
        }

        return isValid
    }

    private fun performLogin(email: String, password: String) {
        // Hiển thị loading
        binding.loginButton.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE

        // Thực hiện đăng nhập (API call, Firebase,...)
    }
}
```

### 3.8 ImageView - Hiển thị hình ảnh

#### 3.8.1 Giới thiệu ImageView

**ImageView** là View dùng để hiển thị hình ảnh từ nhiều nguồn khác nhau: drawable resource, file, URL,...

#### 3.8.2 Khai báo ImageView trong XML

```xml
<ImageView
    android:id="@+id/myImageView"
    android:layout_width="200dp"
    android:layout_height="200dp"
    android:src="@drawable/my_image"
    android:scaleType="centerCrop"
    android:contentDescription="Profile picture"
    android:adjustViewBounds="true" />
```

#### 3.8.3 Các thuộc tính quan trọng

| Thuộc tính | Mô tả |
|------------|-------|
| `src` | Nguồn ảnh (drawable, mipmap) |
| `scaleType` | Cách scale ảnh vào ImageView |
| `contentDescription` | Mô tả cho accessibility (TalkBack) |
| `adjustViewBounds` | Điều chỉnh bounds theo tỉ lệ ảnh |
| `tint` | Tô màu lên ảnh |
| `background` | Nền (khác với src) |

#### 3.8.4 Các loại ScaleType

ScaleType quyết định cách ảnh được scale và căn chỉnh trong ImageView:

```xml
android:scaleType="centerCrop"
```

| ScaleType | Mô tả |
|-----------|-------|
| `center` | Căn giữa, không scale |
| `centerCrop` | Scale đều để fill, cắt phần thừa, căn giữa |
| `centerInside` | Scale để vừa bên trong, căn giữa |
| `fitCenter` | Scale để vừa bên trong, căn giữa (mặc định) |
| `fitStart` | Scale để vừa bên trong, căn ở đầu (top/left) |
| `fitEnd` | Scale để vừa bên trong, căn ở cuối (bottom/right) |
| `fitXY` | Scale không đều để fill đầy (có thể méo) |
| `matrix` | Scale và dịch chuyển bằng matrix tùy chỉnh |

**Minh họa ScaleType:**
```
Ảnh gốc: 100x50 (ngang)
ImageView: 50x50 (vuông)

centerCrop:          centerInside:        fitXY:
┌─────────┐          ┌─────────┐          ┌─────────┐
│ ▓▓▓▓▓▓▓ │          │         │          │▓▓▓▓▓▓▓▓▓│
│ ▓▓▓▓▓▓▓ │          │▓▓▓▓▓▓▓▓▓│          │▓▓▓▓▓▓▓▓▓│
│ ▓▓▓▓▓▓▓ │          │         │          │▓▓▓▓▓▓▓▓▓│
└─────────┘          └─────────┘          └─────────┘
(cắt 2 bên)          (có khoảng trống)    (bị méo)
```

#### 3.8.5 Làm việc với ImageView trong Code

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set ảnh từ drawable resource
        binding.myImageView.setImageResource(R.drawable.my_image)

        // Set ảnh từ Drawable object
        val drawable = ContextCompat.getDrawable(this, R.drawable.my_image)
        binding.myImageView.setImageDrawable(drawable)

        // Set ảnh từ Bitmap
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.my_image)
        binding.myImageView.setImageBitmap(bitmap)

        // Set scaleType trong code
        binding.myImageView.scaleType = ImageView.ScaleType.CENTER_CROP

        // Tint màu cho ảnh
        binding.myImageView.setColorFilter(
            ContextCompat.getColor(this, R.color.primary),
            PorterDuff.Mode.SRC_IN
        )

        // Hoặc dùng ImageViewCompat
        ImageViewCompat.setImageTintList(
            binding.myImageView,
            ColorStateList.valueOf(Color.RED)
        )

        // Xóa tint
        binding.myImageView.clearColorFilter()

        // Set alpha (độ trong suốt)
        binding.myImageView.alpha = 0.5f

        // Click listener cho ImageView
        binding.myImageView.setOnClickListener {
            openFullScreenImage()
        }
    }
}
```

#### 3.8.6 Load ảnh từ Internet với Glide

Để load ảnh từ URL, nên dùng thư viện như **Glide** hoặc **Coil**:

**Thêm dependency (build.gradle.kts):**
```kotlin
dependencies {
    implementation("com.github.bumptech.glide:glide:4.16.0")
}
```

**Thêm permission (AndroidManifest.xml):**
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

**Sử dụng Glide:**
```kotlin
// Load ảnh từ URL
Glide.with(this)
    .load("https://example.com/image.jpg")
    .into(binding.myImageView)

// Với placeholder và error
Glide.with(this)
    .load("https://example.com/image.jpg")
    .placeholder(R.drawable.placeholder)  // Ảnh hiển thị khi đang load
    .error(R.drawable.error_image)        // Ảnh hiển thị khi lỗi
    .into(binding.myImageView)

// Với các tùy chọn khác
Glide.with(this)
    .load("https://example.com/image.jpg")
    .centerCrop()                         // ScaleType
    .circleCrop()                         // Cắt thành hình tròn
    .override(300, 300)                   // Resize
    .timeout(10000)                       // Timeout 10 giây
    .into(binding.myImageView)
```

#### 3.8.7 Load ảnh với Coil (Coroutine Image Loader)

**Thêm dependency:**
```kotlin
dependencies {
    implementation("io.coil-kt:coil:2.5.0")
}
```

**Sử dụng Coil:**
```kotlin
// Load ảnh đơn giản
binding.myImageView.load("https://example.com/image.jpg")

// Với options
binding.myImageView.load("https://example.com/image.jpg") {
    crossfade(true)
    placeholder(R.drawable.placeholder)
    error(R.drawable.error_image)
    transformations(CircleCropTransformation())
}
```

#### 3.8.8 ShapeableImageView (Material Design)

Để có ImageView với bo góc, viền, hình dạng đặc biệt:

```xml
<com.google.android.material.imageview.ShapeableImageView
    android:id="@+id/profileImage"
    android:layout_width="100dp"
    android:layout_height="100dp"
    android:src="@drawable/avatar"
    android:scaleType="centerCrop"
    app:shapeAppearanceOverlay="@style/CircleImageView"
    app:strokeWidth="2dp"
    app:strokeColor="@color/primary" />
```

**Định nghĩa shape trong styles.xml:**
```xml
<!-- Hình tròn -->
<style name="CircleImageView" parent="">
    <item name="cornerFamily">rounded</item>
    <item name="cornerSize">50%</item>
</style>

<!-- Bo góc -->
<style name="RoundedImageView" parent="">
    <item name="cornerFamily">rounded</item>
    <item name="cornerSize">16dp</item>
</style>

<!-- Bo góc một số cạnh -->
<style name="TopRoundedImageView" parent="">
    <item name="cornerFamily">rounded</item>
    <item name="cornerSizeTopLeft">16dp</item>
    <item name="cornerSizeTopRight">16dp</item>
    <item name="cornerSizeBottomLeft">0dp</item>
    <item name="cornerSizeBottomRight">0dp</item>
</style>
```

### 3.9 Tài nguyên (Resources)

Thay vì viết trực tiếp giá trị trong XML hoặc code, Android khuyến khích sử dụng resource files:

#### 3.9.1 String Resources (res/values/strings.xml)
```xml
<resources>
    <string name="app_name">My App</string>
    <string name="welcome_message">Welcome, %1$s!</string>
    <string name="item_count">%d items</string>
    <plurals name="items_plural">
        <item quantity="one">%d item</item>
        <item quantity="other">%d items</item>
    </plurals>
</resources>
```

#### 3.9.2 Color Resources (res/values/colors.xml)
```xml
<resources>
    <color name="primary">#6200EE</color>
    <color name="primary_dark">#3700B3</color>
    <color name="accent">#03DAC5</color>
    <color name="black_50">#80000000</color>  <!-- 50% transparent black -->
</resources>
```

#### 3.9.3 Dimension Resources (res/values/dimens.xml)
```xml
<resources>
    <dimen name="padding_small">8dp</dimen>
    <dimen name="padding_medium">16dp</dimen>
    <dimen name="padding_large">24dp</dimen>
    <dimen name="text_small">12sp</dimen>
    <dimen name="text_normal">14sp</dimen>
    <dimen name="text_large">18sp</dimen>
</resources>
```

#### 3.9.4 Sử dụng Resources

**Trong XML:**
```xml
android:text="@string/welcome_message"
android:textColor="@color/primary"
android:padding="@dimen/padding_medium"
android:background="@drawable/background"
```

**Trong Code:**
```kotlin
// String
val appName = getString(R.string.app_name)
val welcome = getString(R.string.welcome_message, "John")

// Color
val color = ContextCompat.getColor(this, R.color.primary)

// Dimension
val padding = resources.getDimensionPixelSize(R.dimen.padding_medium)

// Drawable
val drawable = ContextCompat.getDrawable(this, R.drawable.icon)
```

## 4) Layouts trong Android

### 4.1 Giới thiệu về Layouts (Introduction to Layouts)

Trong Android, **Layout** là một lớp con của **ViewGroup**, có nhiệm vụ xác định cấu trúc giao diện người dùng (UI). Layout quyết định cách các View con được sắp xếp và hiển thị trên màn hình.

**Tại sao cần Layout?**
- Tổ chức các thành phần UI một cách có hệ thống.
- Đảm bảo giao diện hiển thị đúng trên nhiều kích thước màn hình khác nhau.
- Tạo ra giao diện responsive (đáp ứng) và linh hoạt.
- Dễ dàng bảo trì và chỉnh sửa giao diện.

**Cách hoạt động:**
1. Layout được khai báo trong file XML (thường nằm trong `res/layout/`).
2. Mỗi Layout có thể chứa các View con (Button, TextView,...) hoặc các Layout con khác.
3. Khi Activity được tạo, `setContentView()` sẽ inflate (chuyển đổi) XML thành các đối tượng View trong bộ nhớ.

**Ví dụ cơ bản:**
```xml
<!-- Một Layout đơn giản chứa 2 View con -->
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Xin chào!" />
    
    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Bấm vào đây" />
</LinearLayout>
```

### 4.2 Các loại Layouts phổ biến (Types of Layouts)

Android cung cấp nhiều loại Layout khác nhau, mỗi loại phù hợp với các tình huống khác nhau:

| Layout | Mô tả | Khi nào sử dụng |
|--------|-------|-----------------|
| **LinearLayout** | Sắp xếp View theo hàng ngang hoặc dọc | Giao diện đơn giản, danh sách các phần tử theo một chiều |
| **RelativeLayout** | Đặt View tương đối với View khác hoặc với parent | Giao diện phức tạp hơn, cần linh hoạt vị trí |
| **ConstraintLayout** | Đặt View dựa trên ràng buộc (constraint) | Giao diện phức tạp, tối ưu hiệu năng, khuyến nghị dùng |
| **FrameLayout** | Xếp chồng View lên nhau | Hiển thị một View tại một thời điểm, overlay |
| **TableLayout** | Sắp xếp View theo dạng bảng (hàng/cột) | Giao diện dạng lưới đơn giản |
| **GridLayout** | Sắp xếp View theo lưới linh hoạt | Lưới phức tạp với các ô có kích thước khác nhau |
| **CoordinatorLayout** | Layout đặc biệt hỗ trợ animation và behavior | App bar, FAB, scrolling behavior |

**Lưu ý quan trọng:**
- **ConstraintLayout** được Google khuyến nghị sử dụng cho hầu hết các trường hợp vì hiệu năng tốt và tính linh hoạt cao.
- Tránh lồng quá nhiều Layout vào nhau (nested layouts) vì ảnh hưởng hiệu năng.

### 4.3 LinearLayout

**LinearLayout** là layout đơn giản nhất, sắp xếp các View con theo một hướng duy nhất: **ngang (horizontal)** hoặc **dọc (vertical)**.

#### Thuộc tính quan trọng:

| Thuộc tính | Mô tả |
|------------|-------|
| `android:orientation` | `vertical` (dọc) hoặc `horizontal` (ngang) |
| `android:gravity` | Căn lề nội dung bên trong Layout |
| `android:layout_weight` | Phân chia không gian còn lại cho View con |
| `android:weightSum` | Tổng weight của Layout (mặc định là tổng weight các con) |

#### Ví dụ LinearLayout dọc (Vertical):
```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">
    
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Tiêu đề"
        android:textSize="24sp"
        android:textStyle="bold" />
    
    <EditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Nhập tên của bạn"
        android:layout_marginTop="8dp" />
    
    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Gửi"
        android:layout_marginTop="16dp" />
</LinearLayout>
```

#### Ví dụ LinearLayout ngang (Horizontal):
```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">
    
    <Button
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="Hủy" />
    
    <Button
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="Đồng ý" />
</LinearLayout>
```

#### Giải thích `layout_weight`:
- Khi sử dụng `layout_weight`, đặt `layout_width="0dp"` (horizontal) hoặc `layout_height="0dp"` (vertical).
- View có weight lớn hơn sẽ chiếm nhiều không gian hơn.
- Ví dụ: 2 Button với weight = 1 sẽ chia đều 50% - 50% không gian.

**Ưu điểm:**
- Dễ hiểu, dễ sử dụng cho người mới.
- Phù hợp với giao diện đơn giản.

**Nhược điểm:**
- Không linh hoạt khi giao diện phức tạp.
- Cần lồng nhiều LinearLayout gây ảnh hưởng hiệu năng.

### 4.4 RelativeLayout

**RelativeLayout** cho phép đặt vị trí các View **tương đối** với:
- Parent (Layout cha).
- Các View anh em khác.

#### Thuộc tính đặt vị trí tương đối với Parent:

| Thuộc tính | Mô tả |
|------------|-------|
| `android:layout_alignParentTop` | Căn với cạnh trên của parent |
| `android:layout_alignParentBottom` | Căn với cạnh dưới của parent |
| `android:layout_alignParentStart` | Căn với cạnh trái của parent |
| `android:layout_alignParentEnd` | Căn với cạnh phải của parent |
| `android:layout_centerInParent` | Căn giữa parent |
| `android:layout_centerHorizontal` | Căn giữa theo chiều ngang |
| `android:layout_centerVertical` | Căn giữa theo chiều dọc |

#### Thuộc tính đặt vị trí tương đối với View khác:

| Thuộc tính | Mô tả |
|------------|-------|
| `android:layout_above` | Đặt phía trên View được chỉ định |
| `android:layout_below` | Đặt phía dưới View được chỉ định |
| `android:layout_toStartOf` | Đặt bên trái View được chỉ định |
| `android:layout_toEndOf` | Đặt bên phải View được chỉ định |
| `android:layout_alignTop` | Căn cạnh trên với View được chỉ định |
| `android:layout_alignBottom` | Căn cạnh dưới với View được chỉ định |
| `android:layout_alignBaseline` | Căn baseline (đường cơ sở văn bản) |

#### Ví dụ RelativeLayout:
```xml
<RelativeLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp">
    
    <!-- Tiêu đề ở trên cùng, căn giữa -->
    <TextView
        android:id="@+id/titleText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"
        android:text="Đăng nhập"
        android:textSize="28sp" />
    
    <!-- Ô nhập username, bên dưới tiêu đề -->
    <EditText
        android:id="@+id/usernameInput"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/titleText"
        android:layout_marginTop="32dp"
        android:hint="Tên đăng nhập" />
    
    <!-- Ô nhập password, bên dưới username -->
    <EditText
        android:id="@+id/passwordInput"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/usernameInput"
        android:layout_marginTop="16dp"
        android:hint="Mật khẩu"
        android:inputType="textPassword" />
    
    <!-- Nút đăng nhập, ở dưới cùng màn hình -->
    <Button
        android:id="@+id/loginButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:text="Đăng nhập" />
    
    <!-- Nút quên mật khẩu, phía trên nút đăng nhập -->
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_above="@id/loginButton"
        android:layout_centerHorizontal="true"
        android:layout_marginBottom="16dp"
        android:text="Quên mật khẩu?"
        android:textColor="#1976D2" />
</RelativeLayout>
```

**Ưu điểm:**
- Linh hoạt hơn LinearLayout.
- Giảm được việc lồng nhiều Layout.

**Nhược điểm:**
- Khó quản lý khi có nhiều View.
- Hiệu năng không tốt bằng ConstraintLayout.
- Đã bị thay thế bởi ConstraintLayout trong các dự án mới.

### 4.5 ConstraintLayout là gì? (What's Constraint Layout?)

**ConstraintLayout** là layout mạnh mẽ và linh hoạt nhất trong Android, được Google giới thiệu như giải pháp thay thế cho cả LinearLayout và RelativeLayout.

#### Khái niệm cốt lõi:

**Constraint (Ràng buộc)** là mối quan hệ giữa một View với:
- Parent (Layout cha).
- Các View anh em khác.
- Các guideline (đường hướng dẫn ảo).

Mỗi View cần **ít nhất một constraint ngang** và **một constraint dọc** để xác định vị trí.

#### Tại sao nên dùng ConstraintLayout?

1. **Hiệu năng tốt**: Layout phẳng (flat hierarchy), không cần lồng nhiều layout.
2. **Linh hoạt**: Có thể tạo mọi loại giao diện mà không cần lồng layout.
3. **Hỗ trợ design tool**: Android Studio cung cấp visual editor mạnh mẽ.
4. **Responsive**: Dễ dàng tạo giao diện thích ứng với nhiều kích thước màn hình.
5. **Animation**: Hỗ trợ MotionLayout cho animation phức tạp.

#### So sánh với các Layout khác:

| Tiêu chí | LinearLayout | RelativeLayout | ConstraintLayout |
|----------|--------------|----------------|------------------|
| Độ phức tạp | Đơn giản | Trung bình | Cao (nhưng mạnh mẽ) |
| Hiệu năng | Tốt (nếu không lồng) | Trung bình | Tốt nhất |
| Linh hoạt | Thấp | Trung bình | Cao nhất |
| Visual Editor | Hạn chế | Hạn chế | Tốt nhất |
| Google khuyến nghị | Không | Không | **Có** |

### 4.6 ConstraintLayout trong Android Studio

Android Studio cung cấp **Layout Editor** mạnh mẽ để thiết kế giao diện với ConstraintLayout một cách trực quan.

#### Các thành phần trong Layout Editor:

1. **Design View**: Xem giao diện như khi chạy app.
2. **Blueprint View**: Xem cấu trúc constraint (đường kẻ xanh).
3. **Component Tree**: Cây các View trong layout.
4. **Attributes Panel**: Chỉnh sửa thuộc tính của View được chọn.
5. **Palette**: Kéo thả các View vào layout.

#### Cách tạo Constraint bằng chuột:

1. **Kéo thả View** từ Palette vào Design/Blueprint view.
2. **Tạo constraint**: Click vào điểm tròn ở cạnh View, kéo đến cạnh parent hoặc View khác.
3. **Xóa constraint**: Click vào constraint rồi nhấn Delete, hoặc click vào điểm tròn.
4. **Chỉnh margin**: Dùng Attributes panel hoặc kéo trực tiếp.

#### Các công cụ hữu ích trong toolbar:

- **Infer Constraints**: Tự động tạo constraint cho các View chưa có.
- **Clear All Constraints**: Xóa tất cả constraint.
- **Pack**: Gom các View lại gần nhau.
- **Align**: Căn lề nhiều View cùng lúc.
- **Guidelines**: Thêm đường guideline.
- **Barriers**: Thêm barrier.

#### Ví dụ tạo constraint trong XML:

```xml
<androidx.constraintlayout.widget.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <Button
        android:id="@+id/buttonA"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Button A"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:layout_margin="16dp" />
    
    <Button
        android:id="@+id/buttonB"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Button B"
        app:layout_constraintStart_toEndOf="@id/buttonA"
        app:layout_constraintTop_toTopOf="@id/buttonA"
        android:layout_marginStart="16dp" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

### 4.7 Chi tiết về ConstraintLayout (More about Constraint Layout)

#### 4.7.1 Các loại Constraint

**Constraint cạnh (Edge Constraints):**
```xml
<!-- Constraint với parent -->
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintTop_toTopOf="parent"
app:layout_constraintBottom_toBottomOf="parent"

<!-- Constraint với View khác -->
app:layout_constraintStart_toEndOf="@id/otherView"
app:layout_constraintTop_toBottomOf="@id/otherView"
```

**Constraint baseline (cho văn bản):**
```xml
app:layout_constraintBaseline_toBaselineOf="@id/textView"
```

#### 4.7.2 Bias (Độ lệch)

Khi View có constraint ở cả hai phía (ví dụ: start và end), bạn có thể điều chỉnh vị trí bằng **bias**:

```xml
<!-- Bias 0.5 = căn giữa (mặc định) -->
<!-- Bias 0 = sát bên trái, Bias 1 = sát bên phải -->
app:layout_constraintHorizontal_bias="0.3"
app:layout_constraintVertical_bias="0.7"
```

#### 4.7.3 Kích thước View trong ConstraintLayout

**Các giá trị cho layout_width / layout_height:**

| Giá trị | Mô tả |
|---------|-------|
| `wrap_content` | Vừa với nội dung |
| `match_parent` | **Không khuyến khích** trong ConstraintLayout |
| `0dp` (match_constraint) | Kích thước theo constraint |
| Số cụ thể (vd: `100dp`) | Kích thước cố định |

**Ví dụ match_constraint:**
```xml
<Button
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    android:layout_marginStart="16dp"
    android:layout_marginEnd="16dp"
    android:text="Button full width với margin" />
```

#### 4.7.4 Chains (Chuỗi)

**Chain** là nhóm các View được liên kết với nhau theo chiều ngang hoặc dọc, cho phép phân bố không gian giữa các View.

**Các loại Chain style:**

| Style | Mô tả |
|-------|-------|
| `spread` | Phân bố đều không gian (mặc định) |
| `spread_inside` | Phân bố đều, View đầu và cuối sát cạnh |
| `packed` | Các View gom lại, có thể dùng bias để dịch chuyển |
| `weighted` | Kết hợp với layout_weight để phân chia theo tỉ lệ |

**Ví dụ Chain ngang:**
```xml
<!-- View đầu tiên định nghĩa chain style -->
<Button
    android:id="@+id/btn1"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:layout_constraintHorizontal_chainStyle="spread"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toStartOf="@id/btn2"
    android:text="1" />

<Button
    android:id="@+id/btn2"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:layout_constraintStart_toEndOf="@id/btn1"
    app:layout_constraintEnd_toStartOf="@id/btn3"
    android:text="2" />

<Button
    android:id="@+id/btn3"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:layout_constraintStart_toEndOf="@id/btn2"
    app:layout_constraintEnd_toEndOf="parent"
    android:text="3" />
```

#### 4.7.5 Guidelines (Đường hướng dẫn)

**Guideline** là đường ảo (không hiển thị) dùng để đặt constraint.

```xml
<!-- Guideline dọc ở vị trí 30% chiều rộng màn hình -->
<androidx.constraintlayout.widget.Guideline
    android:id="@+id/guideline"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    app:layout_constraintGuide_percent="0.3" />

<!-- Hoặc dùng giá trị cố định -->
<androidx.constraintlayout.widget.Guideline
    android:id="@+id/guideline2"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    app:layout_constraintGuide_begin="100dp" />
```

#### 4.7.6 Barriers (Rào cản)

**Barrier** là đường ảo có vị trí phụ thuộc vào một nhóm View. Hữu ích khi bạn không biết trước View nào sẽ lớn hơn.

```xml
<!-- Barrier sẽ ở vị trí bên phải của View lớn nhất trong nhóm -->
<androidx.constraintlayout.widget.Barrier
    android:id="@+id/barrier"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:barrierDirection="end"
    app:constraint_referenced_ids="label1,label2,label3" />

<!-- View này sẽ luôn ở bên phải tất cả các label -->
<EditText
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    app:layout_constraintStart_toEndOf="@id/barrier"
    app:layout_constraintEnd_toEndOf="parent" />
```

#### 4.7.7 Group (Nhóm)

**Group** cho phép điều khiển visibility của nhiều View cùng lúc.

```xml
<androidx.constraintlayout.widget.Group
    android:id="@+id/group"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:visibility="visible"
    app:constraint_referenced_ids="button1,button2,textView1" />
```

Trong code Kotlin:
```kotlin
group.visibility = View.GONE  // Ẩn tất cả View trong group
group.visibility = View.VISIBLE  // Hiện tất cả View trong group
```

#### 4.7.8 Tỉ lệ khung hình (Dimension Ratio)

Giữ tỉ lệ chiều rộng/chiều cao của View:

```xml
<!-- Hình vuông -->
<ImageView
    android:layout_width="0dp"
    android:layout_height="0dp"
    app:layout_constraintDimensionRatio="1:1"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintTop_toTopOf="parent" />

<!-- Tỉ lệ 16:9 -->
<ImageView
    android:layout_width="0dp"
    android:layout_height="0dp"
    app:layout_constraintDimensionRatio="H,16:9"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
```

#### 4.7.9 Best Practices khi dùng ConstraintLayout

1. **Tránh lồng ConstraintLayout**: Một ConstraintLayout phẳng thường đủ cho hầu hết giao diện.
2. **Sử dụng Guidelines cho responsive design**: Dùng percent thay vì giá trị cố định.
3. **Dùng Barriers khi nội dung động**: Khi không biết trước View nào sẽ lớn hơn.
4. **Dùng Chains để phân bố View**: Thay vì tính toán margin thủ công.
5. **Dùng 0dp thay vì match_parent**: Trong ConstraintLayout, dùng `0dp` với constraint để mở rộng View.
6. **Kiểm tra trên nhiều màn hình**: Dùng Preview trong Android Studio để test nhiều kích thước.

## 5) Giao diện CurrencyConvertApp (activity_main.xml)

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

## 6) Code Kotlin (MainActivity)

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

## 7) Luồng hoạt động của ứng dụng

1. App khởi động, `MainActivity` được tạo.
2. Layout `activity_main.xml` được inflate.
3. Người dùng nhập số USD vào `EditText`.
4. Bấm nút "Convert to Euro".
5. App lấy giá trị, chuyển sang Double, nhân 0.94.
6. Hiển thị kết quả trên `resultText`.

## 8) Tài nguyên string (strings.xml)

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

## 9) Gợi ý cho người mới (để hiểu và mở rộng)

- Nếu `EditText` rỗng, `toDouble()` sẽ gây lỗi. Có thể dùng:
  - `enteredUSD.toDoubleOrNull()` và xử lý nếu null.
- Có thể thêm định dạng số:
  - `String.format(Locale.US, "%.2f Euros", euros)`.
- Mở rộng app: cho phép chọn tỉ giá, đổi từ EUR -> USD, hoặc cập nhật tỉ giá từ API.
- Khi UI phức tạp hơn, nên dùng **ViewBinding** hoặc **DataBinding** thay vì `findViewById`.

## 10) Tóm tắt nhanh cho người mới

- **Android** là hệ điều hành di động, app được viết bằng Kotlin/Java, giao diện có thể khai báo XML.
- **Project** có `app/` là module chính, `res/` chứa tài nguyên UI, `AndroidManifest.xml` khai báo app.
- **View** là thành phần giao diện; **ViewGroup** là layout chứa các View con.
- App CurrencyConvertApp có 1 màn hình: nhập USD, bấm nút, hiển thị Euro.

## 11) Hình ảnh minh họa UI (thêm screenshot vào Android.md)

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

## 12) Hướng dẫn build và chạy ứng dụng

### 12.1 Chạy bằng Android Studio (dễ nhất cho người mới)
1. Mở Android Studio -> **Open** -> chọn thư mục project `CurrencyConvertApp`.
2. Chờ Gradle sync hoàn tất.
3. Chọn thiết bị (Emulator hoặc điện thoại thật).
4. Bấm nút **Run** (hình tam giác xanh).

### 12.2 Chạy bằng dòng lệnh (Gradle + ADB)
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

### 12.3 Mẹo xử lý lỗi thường gặp
- **Gradle sync fail**: kiểm tra lại `local.properties` có đường dẫn SDK chính xác.
- **Không thấy thiết bị**: kiểm tra cáp USB, driver, và bật USB debugging.
- **App crash khi nhập rỗng**: cần xử lý `toDoubleOrNull()` như gợi ý ở mục 8.
