package com.didan.android.viewpagertablayout

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val pages = listOf(
        PageItem(
            title = "Code App",
            description = "Getting started with ViewPager2 pages.",
            imageResId = R.drawable.img1,
            backgroundColor = Color.parseColor("#0F172A")
        ),
        PageItem(
            title = "Coding",
            description = "Swipe left or right to move between fragments.",
            imageResId = R.drawable.img2,
            backgroundColor = Color.parseColor("#1F2937")
        ),
        PageItem(
            title = "Last Advice",
            description = "Keep fragments light and use ViewModel for state.",
            imageResId = R.drawable.img3,
            backgroundColor = Color.parseColor("#111827")
        )
    )

    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var myAdapter: MyPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1- Khai báo và khởi tạo ViewPager2
        viewPager2 = findViewById(R.id.viewPager2) // Khởi tạo ViewPager2 từ layout
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL // Đặt hướng ngang cho ViewPager2

        // 2 - Thêm Fragment vào danh sách của MyPagerAdapter
        myAdapter = MyPagerAdapter(supportFragmentManager, lifecycle) // Khởi tạo MyPagerAdapter
        pages.forEach { page ->
            myAdapter.addFragmentToList(
                SimplePageFragment.newInstance(
                    title = page.title,
                    description = page.description,
                    imageResId = page.imageResId,
                    backgroundColor = page.backgroundColor
                )
            )
        }

        // 3 - Gán MyPagerAdapter cho ViewPager2
        viewPager2.adapter = myAdapter // Gán adapter cho ViewPager2

        // 4 - Kết nối TabLayout với ViewPager2
        tabLayout = findViewById(R.id.tablayout)
        TabLayoutMediator(
            tabLayout,
            viewPager2
        ) {
            tab, position ->
//            tab.text = "Tab ${position + 1}"
            tab.text = pages[position].title
        }.attach()

    }

}

private data class PageItem(
    val title: String,
    val description: String,
    val imageResId: Int,
    val backgroundColor: Int
)
