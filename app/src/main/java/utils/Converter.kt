package utils

import data.entily.Film
import data.entily.TmdbFilm

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