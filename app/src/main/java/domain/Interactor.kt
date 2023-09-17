package domain

import data.API
import data.MainRepository
import data.TmdbApi
import data.entily.Film
import data.entily.TmdbResultDTO
import data.preferenes.PreferenceProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import utils.Converter


class Interactor(
    private val repo: MainRepository,
    private val retrofitService: TmdbApi,
    private val preferences: PreferenceProvider) {

    var progBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun getFilmsFromAPI(page: Int) {
        progBarState.onNext(true)


        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, "ru-RU", page)
            .enqueue(object: Callback<TmdbResultDTO> {
                override fun onResponse(
                    call: Call<TmdbResultDTO>,
                    response: Response<TmdbResultDTO>) {

                    val list =
                        response.body()?.tmdbFilms?.let { Converter.convertAPIListToDTOList(it) }

                    Completable.fromSingle<List<Film>> {
                        list?.let { it1 -> repo.putToDB(it1) }
                    }.subscribeOn(Schedulers.io())
                        .subscribe()
                    progBarState.onNext(false)
                }

                override fun onFailure(call: Call<TmdbResultDTO>, t: Throwable) {
                    progBarState.onNext(false)
                }
            })
    }

    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }

    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()

    fun getFilmsFromDB(): Observable<List<Film>> = repo.getAllFromDB()


}