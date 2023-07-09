package viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.App
import com.example.moviesearch.R
import domain.Film
import domain.Interactor

class HomeFragmentViewModel : ViewModel() {
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
    private var interactor: Interactor = App.instance.interactor

   fun initContext(context: Context){
       interactor.getFilmsFromAPI(1, object : ApiCallback {
           override fun onSuccess(films: List<Film>) {
               filmsListLiveData.postValue(films)
           }

           override fun onFailure() {
               Toast.makeText(
                   context,
                   context.getString(R.string.toast_disconnected_internet),
                   Toast.LENGTH_SHORT
               ).show()
           }

       })
   }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }
}