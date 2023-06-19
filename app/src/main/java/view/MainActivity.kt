package view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.moviesearch.*
import com.example.moviesearch.databinding.ActivityMainBinding
import domain.Film
import view.fragments.*

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initNavigation()

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_placeholder, HomeFragment())
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

        binding.bottomNavig.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.home -> {
                    val tag = "home"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: HomeFragment(), tag)
                    Toast.makeText(this, getString(R.string.toast_home), Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.favorites -> {
                    val tag = "favorites"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: FavoritesFragment(), tag)
                    Toast.makeText(this, getString(R.string.toast_favorites), Toast.LENGTH_SHORT)
                        .show()
                    true
                }


                R.id.watch_later -> {
                    val tag = "watch_later"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: WatchLaterFragment(), tag)
                    Toast.makeText(this, getString(R.string.toast_watchlater), Toast.LENGTH_SHORT)
                        .show()
                    true
                }

                R.id.selections -> {
                    val tag = "selections"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: SelectionsFragment(), tag)
                    Toast.makeText(this, getString(R.string.toast_selections), Toast.LENGTH_SHORT)
                        .show()
                    true
                }

                else -> false
            }
        }

    }


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


}