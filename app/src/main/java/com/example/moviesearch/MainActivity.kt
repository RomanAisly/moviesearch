package com.example.moviesearch

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.moviesearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()

        initRecyclerView()

        filmsAdapter.addItems(filmsDataBase)
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

    val filmsDataBase = listOf(
        Film("Agora", R.drawable.agora, "Ancient scientist against religion"),
        Film(
            "Everything, everywhere and at once",
            R.drawable.all_everywhere,
            "The story about woman who tries to save her daughter"
        ),
        Film(
            "Angry Birds 2",
            R.drawable.angry_birds,
            "The birds and green swines against the eagles"
        ),
        Film(
            "Dungeons and Dragons",
            R.drawable.dungeons_and_dragons,
            "The team of heroes against the dark forces"
        ),
        Film("Hobbit", R.drawable.hobbit, "The end of Bilbo Baggin's adventures"),
        Film("Love, Death and Robots", R.drawable.love_death_robots, "animated series"),
        Film("Mother", R.drawable.mother, "not trivial story about love"),
        Film("Spider Man: Into the Verbs", R.drawable.spiderman, " miles morales")
    )
    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    private fun initRecyclerView() {
        binding.mainRecycler.apply {
            filmsAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        val bundle = Bundle()
                        bundle.putParcelable("film", film)
                        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }

                })
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decorator = TopSpacingItemDecoration(7)
            addItemDecoration(decorator)
        }
    }
}