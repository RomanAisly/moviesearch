package viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import domain.Film
import domain.Interactor
import org.koin.core.KoinComponent
import org.koin.core.inject

//На данный момент это заглушка
class FavoriteFragmentViewModel: ViewModel(), KoinComponent {
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
    private val interactor: Interactor by inject()

    init {
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