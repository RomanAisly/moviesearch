package viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.App
import com.example.moviesearch.R
import data.entily.Film
import domain.Interactor
import javax.inject.Inject

class HomeFragmentViewModel: ViewModel() {

    val progressBar: MutableLiveData<Boolean> = MutableLiveData()

    @Inject
    lateinit var interactor: Interactor
    val filmsListLiveData: LiveData<List<Film>>

    init {
        App.instance.dagger.inject(this)
        filmsListLiveData = interactor.getFilmsFromDB()
    }

    //Функция для Toast
    fun initContext(context: Context) {
        progressBar.postValue(true)
        interactor.getFilmsFromAPI(1, object: ApiCallback {
            override fun onSuccess() {
                progressBar.postValue(false)
            }

            override fun onFailure() {
                progressBar.postValue(false)
                Toast.makeText(
                    context,
                    context.getString(R.string.toast_disconnected_internet),
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()
    }
}