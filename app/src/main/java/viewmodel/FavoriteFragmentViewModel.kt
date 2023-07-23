package viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.App
import domain.Film
import domain.Interactor
import javax.inject.Inject

//На данный момент это заглушка
class FavoriteFragmentViewModel : ViewModel() {
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()

    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        interactor.getFilmsFromAPI(1, object : ApiCallback {
            override fun onSuccess(films: List<Film>) {
                filmsListLiveData.postValue(films)
            }

            override fun onFailure() {

            }

        })
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }


}