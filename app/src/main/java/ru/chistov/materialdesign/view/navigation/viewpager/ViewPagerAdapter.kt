package ru.chistov.materialdesign.view.navigation.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.chistov.materialdesign.utils.EARTH_FRAGMENT
import ru.chistov.materialdesign.utils.MARS_FRAGMENT
import ru.chistov.materialdesign.utils.SYSTEM_FRAGMENT
import ru.chistov.materialdesign.view.navigation.EarthFragment
import ru.chistov.materialdesign.view.navigation.MarsFragment
import ru.chistov.materialdesign.view.navigation.SystemFragment

class ViewPagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = fragments.size

    private val fragments = arrayOf(EarthFragment(),  SystemFragment(), MarsFragment())
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragments[EARTH_FRAGMENT]
            1 -> fragments[SYSTEM_FRAGMENT]
            else -> fragments[MARS_FRAGMENT]
        }
    }




}