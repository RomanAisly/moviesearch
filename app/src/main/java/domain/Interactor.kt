package domain

import data.API
import data.MainRepository
import data.TmdbApi
import data.entily.TmdbResultDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import utils.Converter

import viewmodel.HomeFragmentViewModel


class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi) {
    fun getFilmsFromAPI(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        retrofitService.getFilms(API.KEY, "ru-RU", page).enqueue(object : Callback<TmdbResultDTO> {
            override fun onResponse(call: Call<TmdbResultDTO>, response: Response<TmdbResultDTO>) {
                callback.onSuccess(Converter.convertAPIListToDTOList(response.body()!!.tmdbFilms))
            }

            override fun onFailure(call: Call<TmdbResultDTO>, t: Throwable) {
                callback.onFailure()
            }
        })
    }
}