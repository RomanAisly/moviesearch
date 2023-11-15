package view.customeviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.example.moviesearch.R
import com.example.moviesearch.databinding.MergePromoBinding
import com.example.remote_module.entity.ApiConstants


class PromoView(context: Context, attributeSet: AttributeSet?): FrameLayout(context, attributeSet) {
    val binding = MergePromoBinding.inflate(LayoutInflater.from(context), this)
    val watchButton = binding.watchMovie

    fun setLinkForPoster(link: String) {
        Glide.with(binding.root)
            .load(ApiConstants.IMAGES_URL + "w500" + link)
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.internet_is_disconnected)
            .centerCrop()
            .into(binding.posterPromo)
    }
}