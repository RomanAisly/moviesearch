package viewmodel

import androidx.lifecycle.ViewModel
import com.example.moviesearch.App
import data.entily.Film
import domain.Interactor
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class HomeFragmentViewModel: ViewModel() {

    @Inject
    lateinit var interactor: Interactor
    val filmsListData: Observable<List<Film>>
    val progressBar: BehaviorSubject<Boolean>

    init {
        App.instance.dagger.inject(this)
        progressBar = interactor.progBarState
        filmsListData = interactor.getFilmsFromDB()
    }

    fun getFilms() {
        interactor.getFilmsFromAPI(1)
    }

    fun getSearchResult(search: String) = interactor.getSearchResultsFromApi(search)
}