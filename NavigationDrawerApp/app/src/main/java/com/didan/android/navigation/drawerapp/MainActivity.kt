package com.didan.android.navigation.drawerapp

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle // Khai báo biến toggle (Thanh điều khiển Drawer)
    lateinit var drawerLayout: DrawerLayout // Khai báo biến drawerLayout (Layout của Drawer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        drawerLayout = findViewById(R.id.drawerLayout) // Lấy tham chiếu đến DrawerLayout từ layout

        toggle = ActionBarDrawerToggle(
            this@MainActivity, // Tham chiếu đến MainActivity (this@<classname> để tránh nhầm lẫn với các this khác trong lambda)
            drawerLayout,
            R.string.open, // Chuỗi hiển thị khi Drawer mở
            R.string.close // Chuỗi hiển thị khi Drawer đóng
        )

        drawerLayout.addDrawerListener(toggle) // Thêm listener cho DrawerLayout để theo dõi trạng thái mở/đóng
        toggle.syncState() // Đồng bộ trạng thái của toggle với trạng thái hiện tại của Drawer

        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Hiển thị nút "hamburger" trên thanh ActionBar

        // Xử lý cho các mục trong Navigation Drawer
        val navView =
            findViewById<NavigationView>(R.id.nav_view) // Lấy tham chiếu đến NavigationView

        // Thiết lập listener cho các mục trong Navigation Drawer
        navView.setNavigationItemSelectedListener {
            it.isChecked = true // Mục được chọn sẽ được đánh dấu

            // Xử lý thay thế Fragment dựa trên mục được chọn
            when (it.itemId) {
                // Nếu mục Home được chọn
                R.id.home -> {
                    replaceFragment(
                        HomeFragment(),
                        it.title.toString()
                    ) // Thay thế bằng HomeFragment
                }
                // Nếu mục Message được chọn
                R.id.message -> {
                    replaceFragment(
                        MessageFragment(),
                        it.title.toString()
                    ) // Thay thế bằng MessageFragment
                }
                // Nếu mục Settings được chọn
                R.id.settings -> {
                    replaceFragment(
                        SettingsFragment(),
                        it.title.toString()
                    ) // Thay thế bằng SettingsFragment
                }
                // Nếu mục Login được chọn
                R.id.login -> {
                    replaceFragment(
                        LoginFragment(),
                        it.title.toString()
                    ) // Thay thế bằng LoginFragment
                }
            }
            true // Trả về true để xác nhận rằng sự kiện đã được xử lý
        }
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        val fragmentManager = supportFragmentManager // Lấy FragmentManager để quản lý các Fragment
        val fragmentTransaction =
            fragmentManager.beginTransaction() // Bắt đầu một giao dịch Fragment
        fragmentTransaction.replace(
            R.id.frame_layout_1,
            fragment
        ) // Thay thế FrameLayout bằng Fragment mới
        fragmentTransaction.commit() // Xác nhận thay đổi
        drawerLayout.closeDrawers() // Đóng Drawer sau khi chọn mục
        setTitle(title) // Cập nhật tiêu đề của ActionBar
    }

    /**
     * Xử lý sự kiện khi một mục trong ActionBar được chọn
     * @param item Mục được chọn
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true // Nếu toggle xử lý sự kiện, trả về true
        }
        // Nếu không, gọi phương thức của lớp cha để xử lý
        return super.onOptionsItemSelected(item)
    }
}