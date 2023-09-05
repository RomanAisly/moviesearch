package view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesearch.databinding.FragmentFavoritesBinding
import data.entily.Film
import utils.AnimationHelper
import view.MainActivity
import view.rv_adapters.FilmListRecyclerAdapter
import view.rv_adapters.TopSpacingItemDecoration
import viewmodel.FavoriteFragmentViewModel

class FavoritesFragment: Fragment() {

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(FavoriteFragmentViewModel::class.java)
    }


    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding
        get() = _binding!!

    private var filmsDataBase = listOf<Film>()

            set(value) {
                if (field == value) return
                field = value.filter { it.isInFavorites }
                filmsAdapter.addItems(field)
            }
    private var filmsAdapter =
        FilmListRecyclerAdapter(object: FilmListRecyclerAdapter.OnItemClickListener {
            override fun click(film: Film) {
                (requireActivity() as MainActivity).launchDetailsFragment(film)
            }
        })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(
            binding.fragmentFavorites,
            requireActivity(),
            2
        )

        initRecycler()
        filmsAdapter.addItems(filmsDataBase)
        viewModel.filmsListLiveData.observe(
            viewLifecycleOwner
        ) {
            filmsDataBase = it
        }
    }

    private fun initRecycler() {
        binding.favoritesRecycler.apply {
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(6)
            addItemDecoration(decorator)
        }
    }
}
