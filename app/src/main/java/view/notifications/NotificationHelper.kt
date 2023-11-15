package view.notifications

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.moviesearch.R
import com.example.moviesearch.view.MainActivity
import com.example.remote_module.entity.ApiConstants
import data.entily.Film
import recisers.BroadcastReminder
import java.util.Calendar

object NotificationHelper {

    fun createNotify(context: Context, film: Film) {
        val notifIntent = Intent(context, MainActivity::class.java)
        val penIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            PendingIntent.getActivity(context, 0, notifIntent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(context, 0, notifIntent, PendingIntent.FLAG_UPDATE_CURRENT)
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

    fun notificationSet(context: Context, film: Film) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        DatePickerDialog(context, { _, dpdYear, dpdMonth, dayofMonth ->
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourofDay, pickerMinute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(
                    dpdYear, dpdMonth, dayofMonth, hourofDay, pickerMinute, 0
                )
                val dateTimeInMillis = pickedDateTime.timeInMillis
                watchLaterEvent(context, dateTimeInMillis, film)
            }
            TimePickerDialog(context, timeSetListener, currentHour, currentMinute, true).show()
        },
            currentYear, currentMonth, currentDay).show()
    }

    private fun watchLaterEvent(context: Context, dateTimeInMillis: Long, film: Film) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(film.title, null, context, BroadcastReminder::class.java)
        val bundle = Bundle()
        bundle.putParcelable(NotificationConstants.FILM_KEY, film)
        intent.putExtra(NotificationConstants.FILM_BUNDLE_KEY, bundle)
        val pendInt: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }


        alarmManager.setExact(AlarmManager.RTC_WAKEUP, dateTimeInMillis, pendInt)
    }

}