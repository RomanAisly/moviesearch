package utils

import com.example.remote_module.entity.TmdbFilm
import data.entily.Film

object Converter {
    fun convertAPIListToDTOList(list: List<TmdbFilm>): List<Film> {
        val result = mutableListOf<Film>()
        list.forEach {
            result.add(
                Film(
                    title = it.title,
                    poster = it.posterPath,
                    description = it.overview,
                    rating = it.voteAverage,
                    isInFavorites = false
                )
            )
        }
        return result
    }
}