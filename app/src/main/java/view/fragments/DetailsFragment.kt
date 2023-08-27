package view.fragments

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.moviesearch.R
import com.example.moviesearch.databinding.FragmentDetailsBinding
import com.google.android.material.snackbar.Snackbar
import data.ApiConstants
import data.entily.Film
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import viewmodel.DetailsFragmentViewModel

class DetailsFragment: Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding!!

    private lateinit var film: Film
    private val viewModel: DetailsFragmentViewModel by viewModels()
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
                             ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFilmDetails()
        snackFavorites()
        share()
        binding.detailsFabDownloadWp.setOnClickListener {
            performAsyncLoadofPoster()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }


    private fun setFilmDetails() {
        film = arguments?.get("film") as Film
        binding.detailsToolbar.title = film.title
        //Загрузка постеров из сети с помощью Glide
        Glide.with(this).load(ApiConstants.IMAGES_URL + "w780" + film.poster)
            .placeholder(R.drawable.loading_image).error(R.drawable.internet_is_disconnected)
            .into(binding.detailsPoster)
        binding.detailsDescription.text = film.description
        //Изменение внешнего вида кнопки "Добавить в избранное"
        binding.detailsFabFavorites.setImageResource(
            if (film.isInFavorites) {
                R.drawable.ic_favorites_full
            } else {
                R.drawable.ic_favorite_empty
            })
    }

    private fun snackFavorites() {
        binding.detailsFabFavorites.setOnClickListener {
            val snackFavorites = Snackbar.make(
                binding.detailsFabFavorites,
                getString(R.string.snack_favorites),
                Snackbar.LENGTH_SHORT)
            val snackFavoritesDeleted = Snackbar.make(
                binding.detailsFabFavorites,
                getString(R.string.snack_favorites_deleted),
                Snackbar.LENGTH_SHORT)


            if (!film.isInFavorites) {
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_favorites_full)
                film.isInFavorites = true
                snackFavorites.show()
            } else {
                binding.detailsFabFavorites.setImageResource(R.drawable.ic_favorite_empty)
                film.isInFavorites = false
                snackFavoritesDeleted.show()
            }
        }
    }

    private fun share() {
        binding.detailsFabShare.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this film: ${film.title} \n\n ${film.description}")
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share to:"))
        }
    }


    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(requireContext(),
            android.Manifest.permission.READ_MEDIA_IMAGES)
        return result == PackageManager.PERMISSION_GRANTED
    }


    private fun requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(),
            (arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES)), 1)
    }

    private fun saveToGallery(bitmap: Bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val contVal = ContentValues().apply {
                put(MediaStore.Images.Media.TITLE, film.title.handleSingleQuote())
                put(MediaStore.Images.Media.DISPLAY_NAME, film.title.handleSingleQuote())
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/World Movies")
            }

            val contResolver = requireActivity().contentResolver
            val uri = contResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contVal)
            val outStream = contResolver.openOutputStream(uri!!)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            outStream?.close()

        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.insertImage(
                requireActivity().contentResolver, bitmap,
                film.title.handleSingleQuote(),
                film.description.handleSingleQuote())
        }
    }

    private fun String.handleSingleQuote(): String {
        return this.replace("'", "")
    }

    private fun performAsyncLoadofPoster() {
        if (!checkPermission()) {
            requestPermission()
            return
        }
        MainScope().launch {
            binding.progressBarForFab.isVisible = true
            val job = scope.async {
                viewModel.loadWallpaper(ApiConstants.IMAGES_URL + "original" + film.poster)
            }
            saveToGallery(job.await())
            Snackbar.make(binding.root, R.string.download_to_gallery, Snackbar.LENGTH_LONG)
                .setAction(R.string.open_gallery) {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.type = "image/*"
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }.show()
            binding.progressBarForFab.isVisible = false
        }
    }
}