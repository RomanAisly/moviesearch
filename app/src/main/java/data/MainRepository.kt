package data

import data.dao.FilmDao
import data.entily.Film
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.Executors

class MainRepository(private val filmDao: FilmDao) {

    fun putToDB(films: List<Film>) {
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertAll(films)
        }
    }

    fun getAllFromDB(): Observable<List<Film>> = filmDao.getCachedFilms()

}