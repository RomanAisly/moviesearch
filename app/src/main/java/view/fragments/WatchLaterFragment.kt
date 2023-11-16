package view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviesearch.databinding.FragmentWatchLaterBinding
import utils.AnimationHelper

class WatchLaterFragment: Fragment() {
    private var _binding: FragmentWatchLaterBinding? = null
    private val binding: FragmentWatchLaterBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchLaterBinding.inflate(layoutInflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(
            binding.fragmentWatchLater,
            requireActivity(),
            3
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}