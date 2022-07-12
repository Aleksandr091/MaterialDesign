package ru.chistov.materialdesign.view.navigation.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.chistov.materialdesign.R
import ru.chistov.materialdesign.databinding.FragmentApiBottomBinding
import ru.chistov.materialdesign.view.pictures.PicturesOfTheDayFragment
import ru.chistov.materialdesign.view.recycler.RecyclerFragment
import ru.chistov.materialdesign.view.settings.SettingsFragment

class ApiBottomFragment : Fragment() {

    private var _binding: FragmentApiBottomBinding? = null
    private val binding: FragmentApiBottomBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApiBottomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       binding.bottomNavigation.setOnItemSelectedListener { item ->
           when (item.itemId) {
               R.id.action_bottom_navigation_earth -> {
                   requireActivity().supportFragmentManager.beginTransaction()
                       .replace(R.id.container_bottom, PicturesOfTheDayFragment.newInstance()).commit()
                   true
               }
               R.id.action_bottom_navigation_system -> {
                   requireActivity().supportFragmentManager.beginTransaction()
                       .replace(R.id.container_bottom, ApiViewPagerFragment.newInstance()).commit()
                   true
               }
               R.id.action_bottom_navigation_settings -> {
                   requireActivity().supportFragmentManager.beginTransaction()
                       .replace(R.id.container_bottom, SettingsFragment.newInstance()).commit()
                   true
               }
               R.id.action_bottom_recycle_view -> {
                   requireActivity().supportFragmentManager.beginTransaction()
                       .replace(R.id.container_bottom, RecyclerFragment.newInstance()).commit()
                   true
               }
               else -> {
                   true
               }
           }
       }
        binding.bottomNavigation.selectedItemId = R.id.action_bottom_navigation_earth


    }

    companion object {

        @JvmStatic
        fun newInstance() = ApiBottomFragment()
    }
}


