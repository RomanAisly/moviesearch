package view.notifications

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
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
        val penIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(context,
                0,
                notifIntent,
                PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(context,
                0,
                notifIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notifBuilder =
            NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID).apply {
                setSmallIcon(R.drawable.ic_notify)
                setContentTitle(context.getString(R.string.notify_title_of_film))
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
                @RequiresApi(Build.VERSION_CODES.TIRAMISU)
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    notifBuilder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(resource))
                    if (ActivityCompat.checkSelfPermission(context,
                            Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(context as Activity,
                            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                            101)
                        return
                    }
                    notifManCom.notify(film.id, notifBuilder.build())
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })

        notifManCom.notify(film.id, notifBuilder.build())
    }
}