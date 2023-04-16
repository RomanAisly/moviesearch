package com.example.moviesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.moviesearch.databinding.ActivityDetailsBinding
import com.google.android.material.snackbar.Snackbar

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val film = intent.extras?.get("film") as Film

        binding.detailsToolbar.title = film.title
        binding.detailsPoster.setImageResource(film.poster)
        binding.detailsDescription.text = film.description

        val snack = Snackbar.make(binding.detailsFabFavorites, "Favorites", Snackbar.LENGTH_SHORT)
        snack.setAction("Add to Favorites") {}

        val snack2 = Snackbar.make(binding.detailsFabWatchlater, "Watch Later", Snackbar.LENGTH_SHORT)
        snack2.setAction("Add to Watch Later") {}
        binding.detailsFabFavorites.setOnClickListener {
            snack.show()
        }
        binding.detailsFabWatchlater.setOnClickListener {
            snack2.show()
        }
    }
}