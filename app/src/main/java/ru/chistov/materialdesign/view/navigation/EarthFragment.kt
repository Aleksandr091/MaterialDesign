package ru.chistov.materialdesign.view.navigation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout
import ru.chistov.materialdesign.R
import ru.chistov.materialdesign.databinding.FragmentEarthBinding
import ru.chistov.materialdesign.databinding.FragmentSettingsBinding
import ru.chistov.materialdesign.databinding.FragmentSystemBinding
import ru.chistov.materialdesign.utils.ThemeBlueTheme
import ru.chistov.materialdesign.utils.ThemeGreenTheme
import ru.chistov.materialdesign.utils.ThemeMaterialDesign
import ru.chistov.materialdesign.utils.ThemeRedTheme
import ru.chistov.materialdesign.view.MainActivity


class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding: FragmentEarthBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {

        @JvmStatic
        fun newInstance() = EarthFragment()
    }

    


}