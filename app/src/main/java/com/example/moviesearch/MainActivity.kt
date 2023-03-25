package com.example.moviesearch

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtons()
    }

    private fun initButtons() {
        binding.button1.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_menu), Toast.LENGTH_SHORT).show()
        }

        binding.button2.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_favorites), Toast.LENGTH_SHORT).show()
        }

        binding.button3.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_watch_later), Toast.LENGTH_SHORT).show()
        }

        binding.button4.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_selections), Toast.LENGTH_SHORT).show()
        }

        binding.button5.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_settings), Toast.LENGTH_SHORT).show()
        }
    }
}