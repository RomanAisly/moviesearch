package view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.moviesearch.R
import com.example.moviesearch.databinding.FragmentDetailsBinding
import com.google.android.material.snackbar.Snackbar
import data.ApiConstants
import domain.Film

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding!!

    private lateinit var film: Film
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFilmDetails()

        binding.detailsFabFavorites.setOnClickListener {
            val snackFavorites = Snackbar.make(
                binding.detailsFabFavorites,
                getString(R.string.snack_favorites),
                Snackbar.LENGTH_SHORT
            )
            val snackFavoritesDeleted = Snackbar.make(
                binding.detailsFabFavorites,
                getString(R.string.snack_favorites_deleted),
                Snackbar.LENGTH_SHORT
            )


            if (!film.isInFavorites) {
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_favorites_full)
                film.isInFavorites = true
                snackFavorites.show()
            } else {
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_favorite_empty)
                film.isInFavorites = false
                snackFavoritesDeleted.show()
            }

        }
        binding.detailsFabShare.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this film: ${film.title} \n\n ${film.description}"
            )
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share to:"))
        }
    }

    private fun setFilmDetails() {

        film = arguments?.get("film") as Film

        binding.detailsToolbar.title = film.title
        //Загрузка постеров из сети с помощью Glide
        Glide.with(this).load(ApiConstants.IMAGES_URL + "w780" + film.poster)
            .placeholder(R.drawable.loading_image).error(R.drawable.internet_is_disconnected)
            .into(binding.detailsPoster)
        binding.detailsDescription.text = film.description
        //Изменение внешнего вида кнопки "Добавить в избранное"
        binding.detailsFabFavorites.setImageResource(
            if (film.isInFavorites) {
                R.drawable.ic_favorites_full
            } else {
                R.drawable.ic_favorite_empty
            }
        )
    }

}