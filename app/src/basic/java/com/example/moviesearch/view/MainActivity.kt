package com.example.moviesearch.view

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moviesearch.App
import com.example.moviesearch.R
import com.example.moviesearch.databinding.ActivityMainBinding
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import data.entily.Film
import recisers.BroadcastConnectionChecker
import view.fragments.DetailsFragment
import view.fragments.HomeFragment
import view.fragments.SettingsFragment
import view.fragments.WatchLaterFragment

class MainActivity: AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var reciver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()
        initPromoView()

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_placeholder, HomeFragment())
            .addToBackStack(null).commit()

        reciver = BroadcastConnectionChecker()
        val intentFilters = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
            addAction(Intent.ACTION_BATTERY_LOW)
        }
        registerReceiver(reciver, intentFilters)
    }

    fun launchDetailsFragment(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable("film", film)
        val fragment = DetailsFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null).commit()
    }

    //Кнопки навигации
    private fun initNavigation() {
        binding.bottomNavig.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val tag = "home"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: HomeFragment(), tag)
                    true
                }

                R.id.favorites -> {
                    Toast.makeText(this,
                        getString(R.string.toast_available_pro),
                        Toast.LENGTH_SHORT)
                        .show()
                    true
                }

                R.id.watch_later -> {
                    val tag = "watch_later"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: WatchLaterFragment(), tag)
                    true
                }

                R.id.selections -> {
                    Toast.makeText(this,
                        getString(R.string.toast_available_pro),
                        Toast.LENGTH_SHORT)
                        .show()
                    true
                }

                R.id.settings -> {
                    val tag = "settings"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: SettingsFragment(), tag)
                    true
                }

                else -> false
            }
        }
    }

    //Кнопка "назад" с alert диалогом
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.alertdialog_title))
                .setIcon(android.R.drawable.presence_video_away)
                .setPositiveButton(getString(R.string.alertdialog_positive)) { _, _ -> finish() }
                .setNegativeButton(getString(R.string.alertdialog_negative)) { _, _ -> }
                .show()
        } else {
            super.onBackPressed()

        }
    }

    private fun checkFragmentExistence(tag: String): Fragment? =
        supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_placeholder, fragment, tag)
            .addToBackStack(null).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(reciver)
    }

    private fun initPromoView() {
        if (!App.instance.isPromoShown) {
            val firebaseRemConf = FirebaseRemoteConfig.getInstance()
            val firebaseRemoteSettings =
                FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(0).build()
            firebaseRemConf.setConfigSettingsAsync(firebaseRemoteSettings)
            firebaseRemConf.fetch().addOnCompleteListener {
                if (it.isSuccessful) {
                    firebaseRemConf.activate()
                    val filmLink = firebaseRemConf.getString("film_link")
                    if (filmLink.isNotBlank()) {
                        App.instance.isPromoShown = true
                        binding.promoView.apply {
                            visibility = View.VISIBLE
                            animate().setDuration(1500).alpha(1F).start()
                            setLinkForPoster(filmLink)
                            watchButton.setOnClickListener {
                                visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }
}