package domain

import data.API
import data.MainRepository
import data.TmdbApi
import data.entily.TmdbResultDTO
import data.preferenes.PreferenceProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import utils.Converter
import viewmodel.HomeFragmentViewModel


class Interactor(private val repo: MainRepository,
                 private val retrofitService: TmdbApi,
                 private val preferences: PreferenceProvider) {
    
    fun getFilmsFromAPI(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, "ru-RU", page)
            .enqueue(object : Callback<TmdbResultDTO> {
                override fun onResponse(call: Call<TmdbResultDTO>,
                                        response: Response<TmdbResultDTO>) {
                    val list = response.body()?.tmdbFilms?.let {
                        Converter.convertAPIListToDTOList(it)
                    }
                    list?.forEach {
                        repo.putToDB(film = it)
                    }
                    list?.let { callback.onSuccess(it) }
                }
                
                override fun onFailure(call: Call<TmdbResultDTO>, t: Throwable) {
                    callback.onFailure()
                }
            })
    }
    
    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }
    
    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()
    
    fun getFilmsFromDB(): List<Film> = repo.getAllFromDB()
}