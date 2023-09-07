package viewmodel

import androidx.lifecycle.ViewModel
import com.example.moviesearch.App
import data.entily.Film
import domain.Interactor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeFragmentViewModel: ViewModel() {

    @Inject
    lateinit var interactor: Interactor
    val filmsListData: Flow<List<Film>>
    val progressBar: Channel<Boolean>

    init {
        App.instance.dagger.inject(this)
        progressBar = interactor.progBarState
        filmsListData = interactor.getFilmsFromDB()
    }

    fun getFilms() {
        interactor.getFilmsFromAPI(1)
    }

}