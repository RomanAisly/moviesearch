package com.example.moviesearch

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviesearch.databinding.FragmentDetailsBinding
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var film: Film
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

        binding.detailsFabFavorites.setOnClickListener {
            if (!film.isInFavorites) {
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_favorites_full)
                film.isInFavorites = true
            } else{
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_favorite_empty)
                film.isInFavorites = false
            }
        }
        binding.detailsFabShare.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "Check out this film: ${film.title} \n\n ${film.description}")
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share to:"))
        }
    }

     private fun setFilmDetails() {

        val film = arguments?.get("film") as Film

        binding.detailsToolbar.title = film.title
        binding.detailsPoster.setImageResource(film.poster)
        binding.detailsDescription.text = film.description

        val snackFavorites = Snackbar.make(
            binding.detailsFabFavorites,
            getString(R.string.snack_favorites),
            Snackbar.LENGTH_SHORT
        )
        snackFavorites.setAction(getString(R.string.snack_delete)) {}
        binding.detailsFabFavorites.setOnClickListener {
            snackFavorites.show()
        }
        binding.detailsFabFavorites.setImageResource(
            if (film.isInFavorites) {
                R.drawable.ic_favorites_full
            } else {
                R.drawable.ic_favorite_empty
            }
        )
    }


}