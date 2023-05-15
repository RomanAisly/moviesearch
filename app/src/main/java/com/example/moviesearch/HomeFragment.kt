package com.example.moviesearch

import android.os.Bundle
import android.transition.Scene
import android.transition.Slide
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesearch.databinding.FragmentHomeBinding
import com.example.moviesearch.databinding.MergeHomeScreenContentBinding
import java.util.*


class HomeFragment(val filmsDataBase: List<Film>) : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var binding2: MergeHomeScreenContentBinding
    private var filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
        override fun click(film: Film) {
            (requireActivity() as MainActivity).launchDetailsFragment(film)
        }

    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding2 = MergeHomeScreenContentBinding.inflate(layoutInflater, binding.fragmentHome, false)
        val scene = Scene(binding.fragmentHome, binding2.root)
        val searchSlide = Slide(Gravity.TOP).addTarget(R.id.search_view)
        val recyclerSlide = Slide(Gravity.BOTTOM).addTarget(R.id.main_recycler)
        val customTransition = TransitionSet().apply {
            duration = 700
            addTransition(searchSlide)
            addTransition(recyclerSlide)
        }
        TransitionManager.go(scene, customTransition)

        binding2.searchView.setOnClickListener {
            binding2.searchView.isIconified = false
        }

        binding2.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
        initRecycler()
        filmsAdapter.addItems(filmsDataBase)

    }


    private fun initRecycler() {
        binding2.mainRecycler.apply {
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(7)
            addItemDecoration(decorator)
        }
    }

}