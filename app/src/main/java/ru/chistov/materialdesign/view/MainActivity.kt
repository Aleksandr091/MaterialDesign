package ru.chistov.materialdesign.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.chistov.materialdesign.R
import ru.chistov.materialdesign.utils.ThemeBlueTheme
import ru.chistov.materialdesign.utils.ThemeGreenTheme
import ru.chistov.materialdesign.utils.ThemeMaterialDesign
import ru.chistov.materialdesign.utils.ThemeRedTheme
import ru.chistov.materialdesign.view.navigation.viewpager.ApiBottomFragment

class MainActivity : AppCompatActivity() {
    private val KEY_SP = "sp"
    private val KEY_CURRENT_THEME = "current_theme"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getRealStyle(getCurrentTheme()))
        setContentView(R.layout.activity_main)
        if (savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.container, ApiBottomFragment.newInstance()).commit()
        }
    }
    fun getCurrentTheme(): Int {
         val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
         return sharedPreferences.getInt(KEY_CURRENT_THEME, -1)
     }

     private fun getRealStyle(currentTheme: Int): Int {
         return when (currentTheme) {
             ThemeMaterialDesign  -> R.style.Theme_MaterialDesign
             ThemeRedTheme -> R.style.MyRedTheme
             ThemeGreenTheme -> R.style.MyGreenTheme
             ThemeBlueTheme -> R.style.MyBlueTheme
             else -> 0
         }
     }
    fun setCurrentTheme(currentTheme: Int) {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CURRENT_THEME, currentTheme)
        editor.apply()
    }


}