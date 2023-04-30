package com.example.moviesearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesearch.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainRecycler.apply {
            filmsAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }

                })
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(7)
            addItemDecoration(decorator)
        }
        filmsAdapter.addItems(filmsDataBase)
        updateData(newList = ArrayList(filmsDataBase))

    }

    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    private fun updateData(newList: ArrayList<Film>) {
        val filmDiff = FilmDiff(oldList = ArrayList(filmsDataBase), newList)
        val diffResult = DiffUtil.calculateDiff(filmDiff)
        filmsAdapter.addItems(newList)
        diffResult.dispatchUpdatesTo(filmsAdapter)
    }

    private val filmsDataBase = listOf(
        Film(
            ("Agora"),
            R.drawable.agora,
            "The tragic fate of Hypatia, the first woman scientist. Rachel Weiss in Alejandro Amenabar's provocative epic. The film is set in the territory of the Roman Empire, which was falling apart in two parts, in the city of Alexandria, Egypt, at the end of the fourth and beginning of the fifth century A.D"
        ),
        Film(
            "Everything, Everywhere, All at once",
            R.drawable.all_everywhere,
            "Evelyn gains access to the memories, emotions, and incredible abilities of other versions of herself. She can now live thousands of lives and be anything she wants--a famous actress, a martial arts master, an opera diva, even a celestial deity. But all the multiverse is threatened by a mysterious entity that Evelyn has to fight. Who knows, maybe she'll also deal with the worst evil of all: her taxes"
        ),
        Film(
            "Angry Birds 2",
            R.drawable.angry_birds,
            "The birds learned of the existence of the hungry green pigs, and a holy war ensued that lasted for many years. But now a new enemy has appeared on the horizon: Zeta, the inhabitant of the third, frozen island. She believes that everyone must feel the power of the cold, and launches an attack on distant relatives living in warmer lands. Now the birds and pigs must unite to stop the frost"
        ),
        Film(
            "Dungeons and Dragons",
            R.drawable.dungeons_and_dragons,
            "Dungeons & Dragons: Honor Among Thieves is a fantasy movie comedy directed by Jonathan Goldstein and John Francis Daly and based on the Dungeons & Dragons RPG system, set in the Forgotten Realms universe. The main roles in the film are played by Chris Pine and Michelle Rodriguez"
        ),
        Film(
            "Hobbit",
            R.drawable.hobbit,
            "When a troop of thirteen dwarves hired hobbit Bilbo Baggins as a burglar and the fourteenth, \"lucky,\" member of the trek to the Lonely Mountain, Bilbo assumed that his adventures would end when he completed his task of finding the treasure that Thorin, the leader of the dwarves, so badly needed. The journey to Erebor, captured by the dragon Smaug, the kingdom of the dwarves, proved even more dangerous than the dwarves and even Gandalf, the wise wizard who had extended a helping hand to Thorin and his party, had anticipated"
        ),
        Film(
            "Love, Death and Robots",
            R.drawable.love_death_robots,
            "\"Love. Death. Robots\", \"Love, Death and Robots\" is an American animated anthology series commissioned by the Netflix streaming service and aimed at an adult audience. The series was executive produced by David Fincher, Tim Miller, Joshua Donen and Jennifer Miller"
        ),
        Film(
            "Mother",
            R.drawable.mother,
            "A young couple's relationship is threatened when, disturbing the couple's peaceful existence, uninvited guests show up at their home"
        ),
        Film(
            "Spider Man: Into the Verbs",
            R.drawable.spiderman,
            "The plot of the unique and visually innovative film centers on a teenager from New York City, Miles Morales, who lives in a world of limitless possibilities in the Spider-Man universes, where the superhero costume is worn not only by him"
        )
    )
}