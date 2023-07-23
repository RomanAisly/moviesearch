package view.rv_viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearch.R
import com.example.moviesearch.databinding.FilmItemBinding
import data.ApiConstants
import domain.Film

class FilmViewHolder(binding: FilmItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val title = binding.title
    private val poster = binding.poster
    private val description = binding.description
    private val ratingDonut = binding.ratingDonut


    fun bind(film: Film) {
        title.text = film.title
        //Загрузка постеров из сети с помощью Glide
        Glide.with(itemView).load(ApiConstants.IMAGES_URL + "w342" + film.poster).centerCrop()
            .placeholder(
                R.drawable.loading_image
            ).error(R.drawable.internet_is_disconnected)
            .into(poster)
        description.text = film.description
        ratingDonut.setProgress((film.rating * 10).toInt())
    }
}