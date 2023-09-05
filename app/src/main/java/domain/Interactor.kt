package domain

import data.API
import data.MainRepository
import data.TmdbApi
import data.entily.Film
import data.entily.TmdbResultDTO
import data.preferenes.PreferenceProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import utils.Converter


class Interactor(
    private val repo: MainRepository,
    private val retrofitService: TmdbApi,
    private val preferences: PreferenceProvider) {

    val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    var progBarState = Channel<Boolean>(Channel.CONFLATED)

    fun getFilmsFromAPI(page: Int) {
        scope.launch {
            progBarState.send(true)
        }

        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, "ru-RU", page)
            .enqueue(object: Callback<TmdbResultDTO> {
                override fun onResponse(
                    call: Call<TmdbResultDTO>,
                    response: Response<TmdbResultDTO>) {

                    val list =
                        response.body()?.tmdbFilms?.let { Converter.convertAPIListToDTOList(it) }

                    scope.launch {
                        list?.let { repo.putToDB(it) }
                        progBarState.send(false)
                    }
                }

                override fun onFailure(call: Call<TmdbResultDTO>, t: Throwable) {
                    scope.launch {
                        progBarState.send(false)
                    }
                }
            })
    }

    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }

    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()

    fun getFilmsFromDB(): Flow<List<Film>> = repo.getAllFromDB()

}