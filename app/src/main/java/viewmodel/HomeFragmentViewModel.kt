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
        interactor.getFilmsFromAPI(1, object : ApiCallback{
            override fun onSuccess(films: List<Film>) {
                filmsListLiveData.postValue(films)
            }

            override fun onFailure() {

            }

        })
    }

    interface ApiCallback{
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }
}