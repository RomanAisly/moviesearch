package com.example.moviesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun butMenuOn(view: View) {
        Toast.makeText(this, "Меню", Toast.LENGTH_SHORT).show()
    }
    fun butFavoritesOn(view: View) {
        Toast.makeText(this, "Избраное", Toast.LENGTH_SHORT).show()
    }
    fun butWatchLaterOn(view: View) {
        Toast.makeText(this, "Посмотреть позже", Toast.LENGTH_SHORT).show()
    }
    fun butSelectionsOn(view: View) {
        Toast.makeText(this, "Подборки", Toast.LENGTH_SHORT).show()
    }
    fun butSettingsOn(view: View) {
        Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
    }
}