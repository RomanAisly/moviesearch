package com.example.moviesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviesearch.databinding.FragmentDetailsBinding
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFilmDetails()
    }

    private fun setFilmDetails(){

        val film = arguments?.get("film") as Film

        binding.detailsToolbar.title = film.title
        binding.detailsPoster.setImageResource(film.poster)
        binding.detailsDescription.text = film.description

        val snackFavorites = Snackbar.make(binding.detailsFabFavorites, getString(R.string.snack_favorites), Snackbar.LENGTH_SHORT)
        snackFavorites.setAction(getString(R.string.snack_delete)) {}

        val snackWatchLater = Snackbar.make(binding.detailsFabWatchlater, getString(R.string.snack_watch_later), Snackbar.LENGTH_SHORT)
        snackWatchLater.setAction(getString(R.string.snack_delete)) {}

        binding.detailsFabFavorites.setOnClickListener {
            snackFavorites.show()
        }
        binding.detailsFabWatchlater.setOnClickListener {
            snackWatchLater.show()
        }
    }
}