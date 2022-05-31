package ru.chistov.materialdesign.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.chistov.materialdesign.R
import ru.chistov.materialdesign.view.pictures.PicturesOfTheDayFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.container,PicturesOfTheDayFragment.newInstance()).commit()
        }
    }
}