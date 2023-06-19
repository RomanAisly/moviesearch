package viewmodel

import com.example.moviesearch.App
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import domain.Film
import domain.Interactor

class HomeFragmentViewModel : ViewModel() {
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
    private var interactor: Interactor = App.instance.interactor

    init {
        val films = interactor.getFilmsDB()
        filmsListLiveData.postValue(films)
    }
}