package com.didan.android.viewpagertablayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Class MyPagerAdapter là một adapter tùy chỉnh cho ViewPager2 trong Android. Nó kế thừa từ FragmentStateAdapter và
 * chịu trách nhiệm cung cấp các Fragment tương ứng cho từng trang trong ViewPager2.
 */
class MyPagerAdapter(fm: FragmentManager, lc: Lifecycle) :
    FragmentStateAdapter(fm, lc) {

    // Danh sách các Fragment sẽ được quản lý bởi adapter
    var fragmentsList: ArrayList<Fragment> = ArrayList()

    /**
     * Hàm addFragmentToList được sử dụng để thêm một Fragment vào danh sách fragmentsList.
     * Bạn có thể gọi hàm này để thêm các Fragment mà bạn muốn hiển thị trong ViewPager2.
     */
    fun addFragmentToList(fragment: Fragment) {
        fragmentsList.add(fragment)
    }

    /**
     * Hàm createFragment được gọi để tạo và trả về Fragment tương ứng với vị trí (position) cụ thể trong ViewPager2.
     * Bạn cần triển khai logic để trả về các Fragment khác nhau dựa trên vị trí
     */
    override fun createFragment(position: Int): Fragment {
        return fragmentsList.get(position)
    }

    /**
     * Hàm getItemCount trả về tổng số trang (Fragment) mà ViewPager2 sẽ hiển thị.
     * Bạn cần trả về số lượng Fragment mà bạn muốn trong ViewPager2
     */
    override fun getItemCount(): Int {
        return fragmentsList.size
    }
}