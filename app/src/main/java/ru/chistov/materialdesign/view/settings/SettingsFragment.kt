package ru.chistov.materialdesign.view.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout
import ru.chistov.materialdesign.R
import ru.chistov.materialdesign.databinding.FragmentSettingsBinding
import ru.chistov.materialdesign.utils.ThemeBlueTheme
import ru.chistov.materialdesign.utils.ThemeGreenTheme
import ru.chistov.materialdesign.utils.ThemeMaterialDesign
import ru.chistov.materialdesign.utils.ThemeRedTheme
import ru.chistov.materialdesign.view.MainActivity


class SettingsFragment : Fragment() {
    private val KEY_SP_LOCAL = "sp_local"
    private val KEY_CURRENT_THEME_LOCAL = "current_theme_local"

    private lateinit var parentActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity =
            requireActivity() as MainActivity
    }


    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val context: Context =
            ContextThemeWrapper(parentActivity, getRealStyleLocal(getCurrentThemeLocal()))
        val localInflater = inflater.cloneInContext(context)
        _binding = FragmentSettingsBinding.inflate(localInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabLayout()
        chipClick()
        tabListener()
    }

    private fun tabListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {

                    0 -> {
                        setCurrentThemeLocal(ThemeMaterialDesign)
                        requireActivity().supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container, newInstance())
                            .commit()
                    }
                    1 -> {
                        setCurrentThemeLocal(ThemeRedTheme)
                        requireActivity().supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container, newInstance())
                            .commit()
                    }
                    2 -> {
                        setCurrentThemeLocal(ThemeGreenTheme)
                        requireActivity().supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container, newInstance())
                            .commit()
                    }
                    3 -> {
                        setCurrentThemeLocal(ThemeBlueTheme)
                        requireActivity().supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container, newInstance())
                            .commit()
                    }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }


    private fun chipClick() {

        binding.chipGroup.setOnCheckedChangeListener { group, position ->
            val chip = group.findViewById<Chip>(position)
            val tag = chip.tag
            when (tag) {
                "0" -> {
                    parentActivity.setCurrentTheme(ThemeMaterialDesign)
                    parentActivity.recreate()
                }
                "1" -> {
                    parentActivity.setCurrentTheme(ThemeRedTheme)
                    parentActivity.recreate()
                }
                "2" -> {
                    parentActivity.setCurrentTheme(ThemeGreenTheme)
                    parentActivity.recreate()

                }
                "3" -> {
                    parentActivity.setCurrentTheme(ThemeBlueTheme)
                    parentActivity.recreate()
                }


            }


        }
    }

    private fun initTabLayout() {
        val tab1 = binding.tabLayout.getTabAt(0)
        val tab2 = binding.tabLayout.getTabAt(1)
        val tab3 = binding.tabLayout.getTabAt(2)
        val tab4 = binding.tabLayout.getTabAt(3)
        when (getCurrentThemeLocal()) {
            0 -> binding.tabLayout.selectTab(tab1)
            1 -> binding.tabLayout.selectTab(tab2)
            2 -> binding.tabLayout.selectTab(tab3)
            3 -> binding.tabLayout.selectTab(tab4)
        }
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {

        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

    private fun setCurrentThemeLocal(currentTheme: Int) {
        val sharedPreferences =
            requireActivity().getSharedPreferences(KEY_SP_LOCAL, AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CURRENT_THEME_LOCAL, currentTheme)
        editor.apply()
    }

    private fun getCurrentThemeLocal(): Int {
        val sharedPreferences =
            requireActivity().getSharedPreferences(KEY_SP_LOCAL, AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME_LOCAL, -1)
    }

    private fun getRealStyleLocal(currentTheme: Int): Int {
        return when (currentTheme) {
            ThemeMaterialDesign -> R.style.Theme_MaterialDesign
            ThemeRedTheme -> R.style.MyRedTheme
            ThemeGreenTheme -> R.style.MyGreenTheme
            ThemeBlueTheme -> R.style.MyBlueTheme
            else -> 0
        }
    }


}