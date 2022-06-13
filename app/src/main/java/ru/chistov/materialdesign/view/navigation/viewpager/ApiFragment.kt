package ru.chistov.materialdesign.view.navigation.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.chistov.materialdesign.R
import ru.chistov.materialdesign.databinding.FragmentApiBinding
import ru.chistov.materialdesign.databinding.FragmentPicturesOfTheDayBinding

class ApiFragment : Fragment() {

    private var _binding: FragmentApiBinding? = null
    private val binding: FragmentApiBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout,binding.viewPager,object :TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                //TDDO HW настраиваем tab
                tab.text = when(position){
                    0-> "Earth"
                    1-> "System"
                    else -> "Mars"
                }
            }
        } ).attach()

    }

    companion object {

        @JvmStatic
        fun newInstance() = ApiFragment()
    }
}


