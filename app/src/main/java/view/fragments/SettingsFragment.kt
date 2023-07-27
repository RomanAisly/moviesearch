package view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviesearch.R
import com.example.moviesearch.databinding.FragmentSettingsBinding
import utils.AnimationHelper
import viewmodel.SettingsFragmentViewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(SettingsFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(
            binding.fragmentSettings,
            requireActivity(),
            5
        )

        viewModel.categoryPropertyLifeData.observe(viewLifecycleOwner) {
            when (it) {
                POPULAR_CATEGORY -> binding.radioGroup.check(R.id.radio_popular)
                TOP_RATED_CATEGORY -> binding.radioGroup.check(R.id.radio_top_rated)
                UPCOMING_CATEGORY -> binding.radioGroup.check(R.id.radio_upcoming)
                NOW_PLAYING_CATEGORY -> binding.radioGroup.check(R.id.radio_now_playing)
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_popular -> viewModel.putCategoryProperty(POPULAR_CATEGORY)
                R.id.radio_top_rated -> viewModel.putCategoryProperty(TOP_RATED_CATEGORY)
                R.id.radio_upcoming -> viewModel.putCategoryProperty(UPCOMING_CATEGORY)
                R.id.radio_now_playing -> viewModel.putCategoryProperty(NOW_PLAYING_CATEGORY)
            }
        }
    }


    companion object {
        const val POPULAR_CATEGORY = "popular"
        const val TOP_RATED_CATEGORY = "top_rated"
        const val UPCOMING_CATEGORY = "upcoming"
        const val NOW_PLAYING_CATEGORY = "now_playing"
    }
}