package view.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.moviesearch.R
import com.example.remote_module.entity.ApiConstants
import data.entily.Film
import view.MainActivity

object NotificationHelper {
    fun createNotify(context: Context, film: Film) {
        val notifIntent = Intent(context, MainActivity::class.java)
        val penIntent =
            PendingIntent.getActivity(context, 0, notifIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notifBuilder =
            NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID).apply {
                setSmallIcon(R.drawable.ic_notify)
                setContentTitle("Don't forget to watch")
                setContentText(film.title)
                priority = NotificationCompat.PRIORITY_DEFAULT
                setContentIntent(penIntent)
                setAutoCancel(true)
            }

        val notifManCom = NotificationManagerCompat.from(context)

        Glide.with(context)
            .asBitmap()
            .load(ApiConstants.IMAGES_URL + "w500" + film.poster)
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.internet_is_disconnected)
            .into(object: CustomTarget<Bitmap>() {

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    notifBuilder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(resource))
                    notifManCom.notify(film.id, notifBuilder.build())
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
        notifManCom.notify(film.id, notifBuilder.build())
    }
}