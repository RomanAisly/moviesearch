package com.example.moviesearch

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()

        supportFragmentManager.beginTransaction().add(R.id.fragment_placeholder, HomeFragment())
            .addToBackStack(null).commit()


    }

    fun launchDetailsFragment(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable("film", film)
        val fragment = DetailsFragment()
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null).commit()
    }

    private fun initNavigation() {
        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    Toast.makeText(this, getString(R.string.toast_settings), Toast.LENGTH_SHORT)
                        .show()
                    true
                }
                else -> false
            }
        }

        binding.bottomNavig.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.favorites -> {
                    Toast.makeText(this, getString(R.string.toast_favorites), Toast.LENGTH_SHORT)
                        .show()
                    true
                }
                R.id.watch_later -> {
                    Toast.makeText(this, getString(R.string.toast_watchlater), Toast.LENGTH_SHORT)
                        .show()
                    true
                }
                R.id.selections -> {
                    Toast.makeText(this, getString(R.string.toast_selections), Toast.LENGTH_SHORT)
                        .show()
                    true
                }
                else -> false
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.alertdialog_title))
                .setIcon(android.R.drawable.presence_video_away)
                .setPositiveButton(getString(R.string.alertdialog_positive)) { _, _ -> finish() }
                .setNegativeButton(getString(R.string.alertdialog_negative)) { _, _ -> }
                .show()


    }


}