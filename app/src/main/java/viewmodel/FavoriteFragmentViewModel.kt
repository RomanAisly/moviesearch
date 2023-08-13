package viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.App
import data.entily.Film
import domain.Interactor
import javax.inject.Inject


class FavoriteFragmentViewModel: ViewModel() {
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()

    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
    }

}