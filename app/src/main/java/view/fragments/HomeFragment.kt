package view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesearch.databinding.FragmentHomeBinding
import data.entily.Film
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import utils.AnimationHelper
import utils.AutoDisposable
import utils.addTo
import view.MainActivity
import view.rv_adapters.FilmListRecyclerAdapter
import view.rv_adapters.TopSpacingItemDecoration
import viewmodel.HomeFragmentViewModel
import java.util.Locale

class HomeFragment: Fragment() {

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }

    private val autoDispose = AutoDisposable()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var filmsDataBase = listOf<Film>()


    //        set(value) {
    //            if (field == value) return
    //            field = value
    //            filmsAdapter.addItems(field)
    //        }
    var filmsAdapter =
        FilmListRecyclerAdapter(object: FilmListRecyclerAdapter.OnItemClickListener {
            override fun click(film: Film) {
                (requireActivity() as MainActivity).launchDetailsFragment(film)
            }
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let { viewModel.getFilms() }
        autoDispose.bindTo(lifecycle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(
            binding.fragmentHome,
            requireActivity(),
            1
        )

        initRecycler()
        initPullToRefresh()
        initSearchView()

        viewModel.filmsListData
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                filmsAdapter.addItems(list)
                filmsDataBase = list
            }.addTo(autoDispose)

        viewModel.progressBar
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { binding.progressBar.isVisible = it }.addTo(autoDispose)

    }

    //Нужен ли этот мето?
    override fun onDestroy() {
        super.onDestroy()
        autoDispose.onDestroy()
    }

    private fun initRecycler() {
        binding.mainRecycler.apply {
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(5)
            addItemDecoration(decorator)
        }
    }

    private fun initPullToRefresh() {
        binding.pullToRefresh.setOnRefreshListener {
            filmsAdapter.items.clear()
            context?.let { viewModel.getFilms() } //getFilms
            binding.pullToRefresh.isRefreshing = false
        }
    }

    private fun initSearchView() {
        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    filmsAdapter.addItems(filmsDataBase)
                    return true
                }
                val result = filmsDataBase.filter {
                    it.title.lowercase(Locale.getDefault())
                        .contains(newText.lowercase(Locale.getDefault()))
                }
                filmsAdapter.addItems(result)
                return true
            }
        })
    }
}